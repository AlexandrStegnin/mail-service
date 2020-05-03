package com.ddkolesnik.mailservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Сервис для отправки email пользователям
 *
 * @author Alexandr Stegnin
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MailSenderService {

    JavaMailSender sender;

    private void sendMessage(String from, String to, String subject, String text, String personal, JavaMailSender sender) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom(from, personal);
        sender.send(message);
    }

    /**
     * Отправка сообщения
     *
     * @param from - от кого
     * @param to - кому
     * @param subject - тема
     * @param text - текст сообщения
     * @param personal - отображаемое имя отправителя
     * @throws MessagingException - при ошибке отправки сообщения
     * @throws UnsupportedEncodingException - при ошибке отправителя сообщения
     */
    public void sendWelcomeMessage(String from, String to, String subject, String text, String personal) throws MessagingException, UnsupportedEncodingException {
        sendMessage(from, to, subject, text, personal, sender);
    }

}
