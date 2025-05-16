package ru.maximister.bank.service;


import ru.maximister.bank.dto.ChangePasswordRequestDTO;

public interface PasswordService {

    void requestCodeToResetPassword(String email);

    void resetPassword(ChangePasswordRequestDTO dto);

}
