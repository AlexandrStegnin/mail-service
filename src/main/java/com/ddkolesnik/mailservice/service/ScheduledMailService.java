package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.model.AppMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для отправки писем по времени, если возникали ошибки при отправке
 *
 * @author Alexandr Stegnin
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduledMailService {

    AppMessageService appMessageService;

    @Scheduled(cron = "${spring.config.cron.expression}")
    public void sendMessage() {
        List<AppMessage> messages = appMessageService.getNotSent();
        messages.forEach(appMessageService::sendWelcomeMessage);
    }

}
