package ru.maximister.bank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.maximister.bank.config.KafkaConfig;
import ru.maximister.bank.dto.AccountResponse;
import ru.maximister.bank.dto.NotificationRequestDTO;
import ru.maximister.bank.dto.TransactionResponse;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTransactionEvent(TransactionResponse transaction) {
        kafkaTemplate.send(KafkaConfig.TRANSACTION_TOPIC, transaction.getId(), transaction);
    }

    public void sendAccountEvent(AccountResponse account) {
        kafkaTemplate.send(KafkaConfig.ACCOUNT_TOPIC, account.getId(), account);
    }

    public void sendNotification(NotificationRequestDTO notification) {
        log.info("Sending notification to Kafka: {}", notification);
        kafkaTemplate.send(KafkaConfig.NOTIFICATION_TOPIC, notification);
    }
} 