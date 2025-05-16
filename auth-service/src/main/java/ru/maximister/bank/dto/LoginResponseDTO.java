package ru.maximister.bank.dto;

public record LoginResponseDTO(String jwt, boolean passwordNeedToBeUpdate) {
}
