package ru.maximister.bank.web;

import  ru.maximister.bank.dto.NotificationRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationRestClient {

    @PostMapping("/bank/notifications/send")
    void sendNotification(@RequestBody NotificationRequestDTO request);
}
