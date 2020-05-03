package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.model.AppMessage;
import com.ddkolesnik.mailservice.model.AppUser;
import com.ddkolesnik.mailservice.repository.AppMessageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Сервис для работы с сообщениям пользователей системы
 *
 * @author Alexandr Stegnin
 */

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppMessageService {

    static String KOLESNIK_EMAIL;

    @Value("${spring.mail.kolesnik.username}")
    protected void setKolesnikEmail(String value) {
        KOLESNIK_EMAIL = value;
    }

    final SimpleMailMessage template;

    final BCryptPasswordEncoder passwordEncoder;

    final AppMessageRepository appMessageRepository;

    final AppUserService appUserService;

    final MailSenderService mailSender;

    /**
     * Отправка сообщения по ID пользователя
     *
     * @param userId - id пользователя кому отправить сообщение
     * @return - отправленное сообщение
     */
    public AppMessage sendWelcomeMessage(Long userId) {
        AppUser recipient = appUserService.findById(userId);
        return sendMessage(recipient);
    }

    /**
     * Отправка сообщения на email пользователя
     *
     * @param email - email для отправки
     * @return - отправленное сообщение
     */
    public AppMessage sendWelcomeMessage(String email) {
        AppUser recipient = appUserService.findByEmail(email);
        return sendMessage(recipient);
    }

    private AppMessage sendMessage(AppUser recipient) {
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        AppMessage message = new AppMessage();
        message.setSender(KOLESNIK_EMAIL);
        String password = passwordEncoder.encode(uuid);
        String text = String.format(Objects.requireNonNull(template.getText()), recipient.getLogin(), uuid);
        message.setUserId(recipient.getId());
        message.setText(text);
        message.setSubject("Добро пожаловать в Доходный Дом Колесникъ!");
        try {
            mailSender.sendWelcomeMessage(message.getSender(), recipient.getEmail(),
                    message.getSubject(), message.getText(), "Доходный Дом Колесникъ");
        } catch (MessagingException | UnsupportedEncodingException e) {
            message.setError(e.getLocalizedMessage());
        }
        message.setSentAt(LocalDateTime.now());
        appMessageRepository.save(message);
        recipient.setPassword(password);
        appUserService.update(recipient);
        return message;
    }

}
