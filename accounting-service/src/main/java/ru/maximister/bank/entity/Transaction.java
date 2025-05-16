package ru.maximister.bank.entity;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.maximister.bank.enums.TransactionStatus;
import ru.maximister.bank.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    private String id;

    @Field("account_id")
    private String accountId;

    private BigDecimal amount;

    private TransactionType type;

    private String description;

    private TransactionStatus status;

    private Map<String, Object> metadata;

    @CreatedDate
    @Field("created_date")
    private LocalDateTime createdDate;

    @CreatedBy
    @Field("created_by")
    private String createdBy;

    @LastModifiedDate
    @Field("last_modified_date")
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    @Field("last_modified_by")
    private String lastModifiedBy;
} 