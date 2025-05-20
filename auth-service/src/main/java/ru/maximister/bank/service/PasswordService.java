package ru.maximister.bank.service;


import ru.maximister.bank.dto.ChangePasswordRequestDTO;
import ru.maximister.bank.dto.VerifyDto;

public interface PasswordService {

    void requestCodeToResetPassword(String email);
    void verifyUser(String username);

    void resetPassword(ChangePasswordRequestDTO dto);

    void verify(VerifyDto verifyDto);

}
