package ru.maximister.bank.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO для ответа с информацией о банковской карте
 */
@Data
@Builder
public class CardResponse {
    /**
     * Уникальный идентификатор карты
     */
    private String id;

    /**
     * Номер карты (16 цифр)
     */
    private String cardNumber;

    /**
     * Имя владельца карты
     */
    private String holderFirstName;

    /**
     * Фамилия владельца карты
     */
    private String holderLastName;

    private String maskedCvv;

    /**
     * Дата создания карты
     */
    private LocalDateTime createdAt;

    /**
     * ID связанного банковского счета
     */
    private String accountId;
} 