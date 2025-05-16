package ru.maximister.bank.service;


import ru.maximister.bank.dto.LoginRequestDTO;
import ru.maximister.bank.dto.LoginResponseDTO;

public interface AuthenticationService {

    LoginResponseDTO authenticate(LoginRequestDTO dto);
}
