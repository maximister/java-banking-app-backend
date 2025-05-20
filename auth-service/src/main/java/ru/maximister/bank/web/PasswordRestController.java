package ru.maximister.bank.web;

import ru.maximister.bank.dto.ChangePasswordRequestDTO;
import ru.maximister.bank.dto.VerifyDto;
import ru.maximister.bank.service.PasswordService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passwords")
public class PasswordRestController {

    private final PasswordService passwordService;

    public PasswordRestController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/ask/{email}")
    public void requestCodeToResetPassword(@PathVariable String email){
        passwordService.requestCodeToResetPassword(email);
    }

    @GetMapping("/verify/{username}")
    public void verifyUser(@PathVariable String username){
        passwordService.verifyUser(username);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody ChangePasswordRequestDTO dto){
        passwordService.resetPassword(dto);
    }

    @PostMapping("/verify")
    public void verify(@RequestBody VerifyDto dto){
        passwordService.verify(dto);
    }
}
