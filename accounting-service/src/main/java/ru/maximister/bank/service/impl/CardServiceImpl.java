package ru.maximister.bank.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximister.bank.dto.CardRequest;
import ru.maximister.bank.dto.CardResponse;
import ru.maximister.bank.entity.Account;
import ru.maximister.bank.entity.Card;
import ru.maximister.bank.repository.AccountRepository;
import ru.maximister.bank.repository.CardRepository;
import ru.maximister.bank.service.CardService;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;
    private final Random random = new SecureRandom();

    @Override
    public CardResponse createCard(CardRequest request) {
        // Проверяем существование аккаунта
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + request.getAccountId()));

        // Проверяем, нет ли уже карты у этого аккаунта
        if (cardRepository.existsByAccountId(request.getAccountId())) {
            throw new IllegalStateException("Account already has a card");
        }

        // Создаем карту
        Card card = Card.builder()
                .cardNumber(generateCardNumber())
                .holderFirstName(request.getHolderFirstName())
                .holderLastName(request.getHolderLastName())
                .cvv(generateCVV())
                .account(account)
                .customerId(account.getCustomerId())
                .build();

        return mapToResponse(cardRepository.save(card));
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCard(String cardId) {
        return cardRepository.findById(cardId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id: " + cardId));
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCardByAccountId(String accountId) {
        return cardRepository.findByAccountId(accountId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Card not found for account: " + accountId));
    }

    @Override
    public List<CardResponse> getCardsByCustomerId(String customerId) {
        return cardRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteCard(String cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new EntityNotFoundException("Card not found with id: " + cardId);
        }
        cardRepository.deleteById(cardId);
    }

    private String generateCardNumber() {
        StringBuilder number;
        do {
            number = new StringBuilder("4"); // Начинаем с 4 (как у Visa)
            for (int i = 0; i < 15; i++) {
                number.append(random.nextInt(10));
            }
        } while (cardRepository.findByCardNumber(number.toString()).isPresent());
        
        return number.toString();
    }

    private String generateCVV() {
        return String.format("%03d", random.nextInt(1000));
    }

    private CardResponse mapToResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .cardNumber(card.getCardNumber())
                .holderFirstName(card.getHolderFirstName())
                .holderLastName(card.getHolderLastName())
                .maskedCvv(card.getCvv())
                .createdAt(card.getCreatedAt())
                .accountId(card.getAccount().getId())
                .build();
    }
} 