package ru.maximister.bank.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.maximister.bank.dto.NotificationRequestDTO;
import ru.maximister.bank.dto.TransactionResponse;
import ru.maximister.bank.service.KafkaService;
import ru.maximister.bank.service.NotificationService;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private static final String TRANSACTION_NOTIFICATION_SUBJECT = "Bank Transaction Notification";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final KafkaService kafkaService;

    @Async
    @Override
    public void sendTransactionNotification(TransactionResponse transaction) {
        try {
            String body = String.format(
                "Hello! A %s transaction has been processed on your account:\n" +
                "- Transaction ID: %s\n" +
                "- Amount: %s\n" +
                "- Type: %s\n" +
                "- Status: %s\n" +
                "- Date: %s\n" +
                "- Description: %s",
                transaction.getType().toString().toLowerCase(),
                transaction.getId(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCreatedDate().format(formatter),
                transaction.getDescription()
            );

            NotificationRequestDTO notificationRequest = new NotificationRequestDTO(
                transaction.getEmail(),
                TRANSACTION_NOTIFICATION_SUBJECT,
                body
            );

            kafkaService.sendNotification(notificationRequest);
            log.info("Transaction notification sent successfully for transaction ID: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Failed to send transaction notification for transaction ID: {}", transaction.getId(), e);
        }
    }
} 