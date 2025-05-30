package ru.maximister.bank.service.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.maximister.bank.configuration.ApplicationProperties;
import ru.maximister.bank.dto.NotificationRequestDTO;
import ru.maximister.bank.service.NotificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final String NOTIFICATION_TEMPLATE = "notification.html";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ApplicationProperties applicationProperties;

    @Async
    @Override
    public void send(NotificationRequestDTO dto) {
        log.info("In send()");
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
            Map<String, Object> properties = new HashMap<>();
            properties.put("data", dto.body());
            sendMail(dto.to(), dto.subject(), mimeMessage, helper, properties);
            log.info("Successfully sent notification");
        } catch (Exception e) {
            log.warn("Failed to send notification");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
        }

    }


    private void sendMail(String to, String subject, MimeMessage mimeMessage, MimeMessageHelper helper, Map<String, Object> properties) throws MessagingException {
        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom(applicationProperties.getSystemEmail());
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(NOTIFICATION_TEMPLATE, context);
        helper.setText(template, true);
        mailSender.send(mimeMessage);
    }
}
