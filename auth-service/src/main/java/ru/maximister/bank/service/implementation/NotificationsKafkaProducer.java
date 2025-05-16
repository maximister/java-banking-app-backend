package ru.maximister.bank.service.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.maximister.bank.dto.NotificationRequestDTO;

import java.util.ArrayList;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsKafkaProducer {

    private static final String TOPIC = "notifications";

    private final KafkaTemplate<String, NotificationRequestDTO> kafkaTemplate;

    public void sendMessage(NotificationRequestDTO notification) {
        kafkaTemplate.send(TOPIC, notification);
        log.info("Message sent: {}", notification);
    }
}

