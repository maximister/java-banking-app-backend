package ru.maximister.bank.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maximister.bank.dto.ChangePasswordRequestDTO;
import ru.maximister.bank.dto.VerifyDto;
import ru.maximister.bank.entity.User;
import ru.maximister.bank.entity.Verification;
import ru.maximister.bank.exception.TooManyRequestsException;
import ru.maximister.bank.exception.UserNotFoundException;
import ru.maximister.bank.exception.VerificationCodeExpiredException;
import ru.maximister.bank.exception.VerificationNotFoundException;
import ru.maximister.bank.repository.UserRepository;
import ru.maximister.bank.repository.VerificationRepository;
import ru.maximister.bank.service.PasswordService;
import ru.maximister.bank.util.generator.CodeGenerator;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    public PasswordServiceImpl(VerificationRepository verificationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationService notificationService) {
        this.verificationRepository = verificationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }

    @Override
    public void requestCodeToResetPassword(String email) {
        log.info("Requesting code to reset password for email: {}", email);
        User user = findUserByEmail(email);

        Verification existingVerification = verificationRepository.findByEmail(user.getEmail()).orElse(null);

        if (existingVerification != null && existingVerification.getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new TooManyRequestsException("You must wait for 10 minutes before requesting a new code");
        }

        if (existingVerification != null) {
            verificationRepository.delete(existingVerification);
            log.info("Expired verification for email {} deleted", email);
        }

        Verification newVerification = createAndSaveVerification(user.getEmail());
        sendByMail(newVerification);

        log.info("Verification code sent to email: {}", email);
    }

    @Override
    public void verifyUser(String username) {
        log.info("Requesting code to reset password for user: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow();

        Verification existingVerification = verificationRepository.findByEmail(user.getEmail()).orElse(null);

        if (existingVerification != null && existingVerification.getExpiryDate().isAfter(LocalDateTime.now())) {
            throw new TooManyRequestsException("You must wait for 10 minutes before requesting a new code");
        }

        if (existingVerification != null) {
            verificationRepository.delete(existingVerification);
            log.info("Expired verification for email {} deleted", user.getEmail());
        }

        Verification newVerification = createAndSaveVerification(user.getEmail());
        sendByMail(newVerification);

        log.info("Verification code sent to email: {}", user.getEmail());
    }

    @Override
    public void resetPassword(@NotNull ChangePasswordRequestDTO dto) {
        log.info("resetPassword requested");
        Verification verification = verificationRepository.findByEmailAndCode(dto.code(), dto.email())
                .orElseThrow(() -> new VerificationNotFoundException("code not found"));
        if(verification.isExpired()){
            throw new VerificationCodeExpiredException("code is expired : ask for another code");
        }
        User user = findUserByEmail(dto.email());
        if(!dto.password().equals(dto.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
        log.info("user's password updated");
        verificationRepository.delete(verification);
    }

    @Override
    public void verify(VerifyDto verifyDto) {
        log.info("resetPassword requested");
        Verification verification = verificationRepository.findByEmailAndCode(verifyDto.code(), verifyDto.email())
                .orElseThrow(() -> new VerificationNotFoundException("code not found"));
        if(verification.isExpired()){
            throw new VerificationCodeExpiredException("code is expired : ask for another code");
        }

        log.info("user's verified");
        verificationRepository.delete(verification);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));
    }

    private @NotNull Verification createAndSaveVerification(String email) {
        Verification verification = new Verification();
        verification.setEmail(email);
        verification.setCode(CodeGenerator.generate(6));
        verification.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        return verificationRepository.save(verification);
    }

    private void sendByMail(@NotNull Verification savedVerification) {
        log.info(savedVerification.toString());
        String subject = "Reset password";
        String body = String.format("Here is your verification code %s . If you did not request this code, please contact the administrator.", savedVerification.getCode());
        notificationService.send(savedVerification.getEmail(), subject, body);
    }


}
