package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.dto.AppUserDTO;
import com.ddkolesnik.mailservice.dto.MessageDTO;
import com.ddkolesnik.mailservice.model.AppMessage;
import com.ddkolesnik.mailservice.model.AppUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Alexandr Stegnin
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppMessageService {

  @Value("${spring.mail.kolesnik.username}")
  String kolesnikEmail;

  String kolesnikPersonal = "Колесник.Инвестиции";

  final SimpleMailMessage welcomeMessageTemplate;

  final SimpleMailMessage confirmMessageTemplate;

  final BCryptPasswordEncoder passwordEncoder;

  final AppUserService appUserService;

  final MailSenderService mailSender;

  /**
   * Отправка сообщения пользователю
   *
   * @param userDTO - DTO пользователя
   */
  public void sendWelcomeMessage(AppUserDTO userDTO) {
    sendWelcomeMessage(userDTO.getEmail());
  }

  private void sendWelcomeMessage(String email) {
    AppUser recipient = appUserService.findByEmail(email);
    sendMessage(recipient);
  }

  private void sendMessage(AppUser recipient) {
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    AppMessage message = new AppMessage();
    message.setSender(kolesnikEmail);
    String password = passwordEncoder.encode(uuid);
    String text = String.format(Objects.requireNonNull(welcomeMessageTemplate.getText()), recipient.getLogin(), uuid);
    message.setUserId(recipient.getId());
    message.setText(text);
    message.setSubject("Добро пожаловать в Доходный Дом КолесникЪ!");
    try {
      mailSender.sendWelcomeMessage(message.getSender(), recipient.getProfile().getEmail(),
          message.getSubject(), message.getText(), "ДД КолесникЪ");
    } catch (MessagingException | UnsupportedEncodingException e) {
      message.setError(e.getLocalizedMessage());
    }
    message.setSentAt(LocalDateTime.now());
    recipient.setPassword(password);
    appUserService.update(recipient);
  }

  public void sendConfirmMessage(AppUserDTO dto) {
    AppUser user = appUserService.findByEmail(dto.getEmail());
    AppMessage message = new AppMessage();
    message.setSender(kolesnikEmail);
    String text = String.format(Objects.requireNonNull(confirmMessageTemplate.getText()), dto.getConfirmCode());
    message.setUserId(user.getId());
    message.setText(text);
    message.setSubject("Код подтверждения");
    try {
      mailSender.sendConfirmMessage(message.getSender(), user.getProfile().getEmail(),
          message.getSubject(), message.getText(), "ДД КолесникЪ");
    } catch (MessagingException | UnsupportedEncodingException e) {
      log.error(e.getLocalizedMessage());
      message.setError(e.getLocalizedMessage());
    }
  }

  @SneakyThrows
  public void sendMessage(MessageDTO message) {
    for (String recipient : message.getRecipients()) {
      AppUser user = appUserService.findByEmail(recipient);
      mailSender.sendMessage(kolesnikEmail, user.getProfile().getEmail(), message.getSubject(), message.getText(), kolesnikPersonal);
    }
  }

}
