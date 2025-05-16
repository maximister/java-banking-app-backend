package ru.maximister.bank.dto;

import lombok.Builder;
import lombok.Data;
import ru.maximister.bank.enums.AccountStatus;
import ru.maximister.bank.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse {
    private String id;
    private String customerId;
    private BigDecimal balance;
    private AccountType type;
    private AccountStatus status;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime lastModifiedDate;
    private String lastModifiedBy;
} 