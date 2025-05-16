package ru.maximister.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO для создания банковской карты
 */
@Data
public class CardRequest {
    /**
     * ID банковского счета, к которому привязывается карта
     */
    @NotBlank(message = "Account ID is required")
    private String accountId;

    /**
     * Имя владельца карты (латиницей)
     */
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z\\s-]+$", message = "First name should contain only letters, spaces and hyphens")
    private String holderFirstName;

    /**
     * Фамилия владельца карты (латиницей)
     */
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z\\s-]+$", message = "Last name should contain only letters, spaces and hyphens")
    private String holderLastName;
} 