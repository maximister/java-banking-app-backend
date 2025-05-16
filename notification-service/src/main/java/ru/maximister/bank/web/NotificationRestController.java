package ru.maximister.bank.web;

import jakarta.validation.Valid;
import ru.maximister.bank.dto.NotificationRequestDTO;
import ru.maximister.bank.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationRestController {

    private final NotificationService notificationService;

    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public void send(@RequestBody @Valid NotificationRequestDTO dto){
        notificationService.send(dto);
    }
}
