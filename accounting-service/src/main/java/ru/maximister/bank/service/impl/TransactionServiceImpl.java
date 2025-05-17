package ru.maximister.bank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximister.bank.dto.TransactionRequest;
import ru.maximister.bank.dto.TransactionResponse;
import ru.maximister.bank.dto.TransferRequest;
import ru.maximister.bank.entity.Account;
import ru.maximister.bank.entity.Transaction;
import ru.maximister.bank.enums.TransactionStatus;
import ru.maximister.bank.enums.TransactionType;
import ru.maximister.bank.repository.AccountRepository;
import ru.maximister.bank.repository.TransactionRepository;
import ru.maximister.bank.service.KafkaService;
import ru.maximister.bank.service.NotificationService;
import ru.maximister.bank.service.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final KafkaService kafkaService;
    private final NotificationService notificationService;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        Transaction transaction = Transaction.builder()
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .metadata(request.getMetadata())
                .status(TransactionStatus.COMPLETED)
                .build();

        // Обновляем баланс счета в зависимости от типа транзакции
        BigDecimal newBalance = calculateNewBalance(account.getBalance(), request.getAmount(), request.getType());
        account.setBalance(newBalance);
        accountRepository.save(account);

        TransactionResponse response = mapToResponse(transactionRepository.save(transaction));
        
        // Отправляем события
        kafkaService.sendTransactionEvent(response);
       // notificationService.sendTransactionNotification(response);
        
        return response;
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        // Проверяем существование счетов
        Account sourceAccount = accountRepository.findById(request.getSourceAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Source account not found with id: " + request.getSourceAccountId()));
        Account targetAccount = accountRepository.findById(request.getTargetAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Target account not found with id: " + request.getTargetAccountId()));

        // Проверяем достаточность средств
        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient funds in source account");
        }

        // Создаем транзакцию списания
        Transaction outgoingTransaction = Transaction.builder()
                .accountId(request.getSourceAccountId())
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER_OUT)
                .description(request.getDescription())
                .status(TransactionStatus.COMPLETED)
                .build();

        // Создаем транзакцию зачисления
        Transaction incomingTransaction = Transaction.builder()
                .accountId(request.getTargetAccountId())
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER_IN)
                .description(request.getDescription())
                .status(TransactionStatus.COMPLETED)
                .build();

        // Обновляем балансы
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        targetAccount.setBalance(targetAccount.getBalance().add(request.getAmount()));

        // Сохраняем изменения
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);
        transactionRepository.save(outgoingTransaction);
        TransactionResponse response = mapToResponse(transactionRepository.save(incomingTransaction));

        // Отправляем события
        kafkaService.sendTransactionEvent(response);
        // notificationService.sendTransactionNotification(response);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id: " + transactionId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getTransactionsByAccountId(String accountId, Pageable pageable) {
        return transactionRepository.findByAccountId(accountId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByAccountIdAndDateRange(String accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByAccountIdAndDateRange(accountId, startDate, endDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionsByAccountIdAndType(String accountId, String type) {
        return transactionRepository.findByAccountIdAndType(accountId, type).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTransaction(String transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new EntityNotFoundException("Transaction not found with id: " + transactionId);
        }
        transactionRepository.deleteById(transactionId);
    }

    private BigDecimal calculateNewBalance(BigDecimal currentBalance, BigDecimal amount, TransactionType type) {
        return switch (type) {
            case DEPOSIT, TRANSFER_IN -> currentBalance.add(amount);
            case WITHDRAWAL, TRANSFER_OUT -> currentBalance.subtract(amount);
        };
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + transaction.getAccountId()));

        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .metadata(transaction.getMetadata())
                .createdDate(transaction.getCreatedDate())
                .createdBy(transaction.getCreatedBy())
                .lastModifiedDate(transaction.getLastModifiedDate())
                .lastModifiedBy(transaction.getLastModifiedBy())
//                .email(account.getEmail())
                .build();
    }
} 