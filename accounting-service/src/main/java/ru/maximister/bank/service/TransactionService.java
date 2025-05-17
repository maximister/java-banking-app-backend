package ru.maximister.bank.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.maximister.bank.dto.TransactionRequest;
import ru.maximister.bank.dto.TransactionResponse;
import ru.maximister.bank.dto.TransferRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    TransactionResponse transfer(TransferRequest request);
    TransactionResponse getTransaction(String transactionId);
    List<TransactionResponse> getTransactionsByAccountId(String accountId);
    Page<TransactionResponse> getTransactionsByAccountId(String accountId, Pageable pageable);
    List<TransactionResponse> getTransactionsByAccountIdAndDateRange(String accountId, LocalDateTime startDate, LocalDateTime endDate);
    List<TransactionResponse> getTransactionsByAccountIdAndType(String accountId, String type);
    void deleteTransaction(String transactionId);
} 