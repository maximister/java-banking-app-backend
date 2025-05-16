package ru.maximister.bank.service.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.maximister.bank.dto.NotificationRequestDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationsConsumer {
    private final NotificationsProcessor notificationsProcessor;

    @KafkaListener(topics = "notifications", groupId = "notification-service")
    public void consume(NotificationRequestDTO changeEvent) {
        log.info("Received notification: {}", changeEvent);
        notificationsProcessor.process(changeEvent);
    }
}
