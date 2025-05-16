package ru.maximister.bank.service;

import ru.maximister.bank.dto.CardRequest;
import ru.maximister.bank.dto.CardResponse;

public interface CardService {
    CardResponse createCard(CardRequest request);

    CardResponse getCard(String cardId);

    CardResponse getCardByAccountId(String accountId);

    void deleteCard(String cardId);
} 