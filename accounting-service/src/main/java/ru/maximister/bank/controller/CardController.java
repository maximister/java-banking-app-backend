package ru.maximister.bank.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.maximister.bank.dto.CardRequest;
import ru.maximister.bank.dto.CardResponse;
import ru.maximister.bank.service.CardService;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cardService.createCard(request));
    }

    @GetMapping("/{cardId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CardResponse> getCard(@PathVariable String cardId) {
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<CardResponse> getCardByAccountId(@PathVariable String accountId) {
        return ResponseEntity.ok(cardService.getCardByAccountId(accountId));
    }

    @DeleteMapping("/{cardId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteCard(@PathVariable String cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }
} 