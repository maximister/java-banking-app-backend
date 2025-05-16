package ru.maximister.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.maximister.bank.enums.AccountType;

import java.math.BigDecimal;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "Customer ID is required")
    private String customerId;
    
    @NotNull(message = "Initial balance is required")
    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance;
    
    @NotNull(message = "Account type is required")
    private AccountType type;
} 