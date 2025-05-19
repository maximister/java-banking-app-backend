package ru.maximister.bank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.maximister.bank.dto.AccountResponse;
import ru.maximister.bank.dto.CreateAccountRequest;
import ru.maximister.bank.enums.AccountStatus;
import ru.maximister.bank.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN','SUPER_ADMIN')")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccount(accountId));
    }

    @GetMapping("/card/{cardNumber}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AccountResponse> getAccountByCardNumber(@PathVariable String cardNumber) {
        return ResponseEntity.ok(accountService.getAccountByCardNumber(cardNumber));
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.getAccountsByCustomerId(customerId));
    }

    @GetMapping("/customer/{customerId}/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<AccountResponse>> getActiveAccountsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.getActiveAccountsByCustomerId(customerId));
    }

    @PatchMapping("/{accountId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AccountResponse> updateAccountStatus(
            @PathVariable String accountId,
            @RequestParam AccountStatus status) {
        return ResponseEntity.ok(accountService.updateAccountStatus(accountId, status));
    }

    @PatchMapping("/{accountId}/balance")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<AccountResponse> updateBalance(
            @PathVariable String accountId,
            @RequestParam BigDecimal balance) {
        return ResponseEntity.ok(accountService.updateBalance(accountId, balance));
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Boolean> existsByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(accountService.existsByCustomerId(customerId));
    }
} 