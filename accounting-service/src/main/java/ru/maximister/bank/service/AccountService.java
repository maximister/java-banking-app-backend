package ru.maximister.bank.service;

import ru.maximister.bank.dto.AccountResponse;
import ru.maximister.bank.dto.CreateAccountRequest;
import ru.maximister.bank.enums.AccountStatus;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest request);
    AccountResponse getAccount(String accountId);
    AccountResponse getAccountByCardNumber(String cardId);
    List<AccountResponse> getAccountsByCustomerId(String customerId);
    List<AccountResponse> getActiveAccountsByCustomerId(String customerId);
    AccountResponse updateAccountStatus(String accountId, AccountStatus status);
    AccountResponse updateBalance(String accountId, BigDecimal newBalance);
    void deleteAccount(String accountId);
    boolean existsByCustomerId(String customerId);
} 