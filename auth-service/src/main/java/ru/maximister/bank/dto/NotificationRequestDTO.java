package ru.maximister.bank.dto;

public record NotificationRequestDTO(String to, String subject, String body) {
}
