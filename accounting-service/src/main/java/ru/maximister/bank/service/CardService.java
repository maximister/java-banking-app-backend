package ru.maximister.bank.service;

import ru.maximister.bank.dto.CardRequest;
import ru.maximister.bank.dto.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse createCard(CardRequest request);

    CardResponse getCard(String cardId);

    CardResponse getCardByAccountId(String accountId);

    List<CardResponse> getCardsByCustomerId(String customerId);

    void deleteCard(String cardId);
} 