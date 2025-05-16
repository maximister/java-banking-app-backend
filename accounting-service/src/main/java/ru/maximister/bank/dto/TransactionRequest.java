package ru.maximister.bank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.maximister.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class TransactionRequest {
    @NotBlank(message = "Account ID is required")
    private String accountId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    
    private String description;
    
    private Map<String, Object> metadata;
} 