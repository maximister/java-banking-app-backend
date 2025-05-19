package ru.maximister.bank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximister.bank.dto.AccountResponse;
import ru.maximister.bank.dto.CreateAccountRequest;
import ru.maximister.bank.entity.Account;
import ru.maximister.bank.enums.AccountStatus;
import ru.maximister.bank.repository.AccountRepository;
import ru.maximister.bank.repository.CardRepository;
import ru.maximister.bank.service.AccountService;
import ru.maximister.bank.service.CardService;
import ru.maximister.bank.service.KafkaService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final KafkaService kafkaService;

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {
        Account account = Account.builder()
                .customerId(request.getCustomerId())
                .balance(request.getInitialBalance())
                .type(request.getType())
                .status(AccountStatus.ACTIVE)
                .build();
        
        AccountResponse response = mapToResponse(accountRepository.save(account));
        kafkaService.sendAccountEvent(response);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountByCardNumber(String cardNumber) {
        var card = cardRepository.findByCardNumber(cardNumber).orElseThrow();
        var account = card.getAccount();

        return mapToResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByCustomerId(String customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getActiveAccountsByCustomerId(String customerId) {
        return accountRepository.findActiveAccountsByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponse updateAccountStatus(String accountId, AccountStatus status) {
        Account account = getAccountEntity(accountId);
        account.setStatus(status);
        AccountResponse response = mapToResponse(accountRepository.save(account));
        kafkaService.sendAccountEvent(response);
        return response;
    }

    @Override
    public AccountResponse updateBalance(String accountId, BigDecimal newBalance) {
        Account account = getAccountEntity(accountId);
        account.setBalance(newBalance);
        AccountResponse response = mapToResponse(accountRepository.save(account));
        kafkaService.sendAccountEvent(response);
        return response;
    }

    @Override
    public void deleteAccount(String accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException("Account not found with id: " + accountId);
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCustomerId(String customerId) {
        return accountRepository.existsByCustomerId(customerId);
    }

    private Account getAccountEntity(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + accountId));
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .type(account.getType())
                .status(account.getStatus())
                .createdDate(account.getCreatedDate())
                .createdBy(account.getCreatedBy())
                .lastModifiedDate(account.getLastModifiedDate())
                .lastModifiedBy(account.getLastModifiedBy())
                .build();
    }
} 