package ru.maximister.bank.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maximister.bank.dto.NotificationRequestDTO;
import ru.maximister.bank.service.NotificationService;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationsProcessor {
    private final NotificationService notificationService;

    public void process(NotificationRequestDTO notificationRequest) {
        notificationService.send(notificationRequest);
    }
}
