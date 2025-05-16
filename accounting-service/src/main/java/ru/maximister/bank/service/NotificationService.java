package ru.maximister.bank.service;

import ru.maximister.bank.dto.TransactionResponse;

public interface NotificationService {
    void sendTransactionNotification(TransactionResponse transaction);
} 