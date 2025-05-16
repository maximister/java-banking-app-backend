package ru.maximister.bank.web;

import  ru.maximister.bank.dto.LoginRequestDTO;
import  ru.maximister.bank.dto.LoginResponseDTO;
import  ru.maximister.bank.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    public AuthenticationRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponseDTO authenticate(@RequestBody LoginRequestDTO requestDTO) {
        return authenticationService.authenticate(requestDTO);
    }
}
