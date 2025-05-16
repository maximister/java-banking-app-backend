package ru.maximister.bank.service;

import ru.maximister.bank.dto.NotificationRequestDTO;

public interface NotificationService {

    void send(NotificationRequestDTO dto);
}
