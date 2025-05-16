package ru.maximister.bank.dto;

import lombok.Builder;
import lombok.Data;
import ru.maximister.bank.enums.TransactionStatus;
import ru.maximister.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class TransactionResponse {
    private String id;
    private String accountId;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private TransactionStatus status;
    private Map<String, Object> metadata;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
    private String email;
} 