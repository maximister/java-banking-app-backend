package ru.maximister.bank.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.maximister.bank.dto.NotificationRequestDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationsKafkaProducer notificationsProducer;

    public void send(String to, String subject, String body) {
        NotificationRequestDTO dto = new NotificationRequestDTO(to, subject, body);
        notificationsProducer.sendMessage(dto);
    }
}
