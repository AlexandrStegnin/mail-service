package com.ddkolesnik.mailservice.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author Alexandr Stegnin
 */

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@PropertySource("classpath:yandex.mail.properties")
public class AppConfig {

  static final String ENCODING = StandardCharsets.UTF_8.name();

  @Value("${spring.mail.host}")
  String host;

  @Value("${spring.mail.port}")
  Integer port;

  @Value("${spring.mail.protocol}")
  String protocol;

  @Value("${spring.mail.smtp.auth}")
  String smtpAuth;

  @Value("${spring.mail.smtp.starttls.enable}")
  String smtpStarttlsEnable;

  @Value("${spring.mail.debug}")
  String debugEnable;

  @Value("${spring.mail.kolesnik.username}")
  String welcomeUsername;

  @Value("${spring.mail.kolesnik.password}")
  String welcomePassword;

  @Bean
  @Qualifier("welcomeSender")
  public JavaMailSender getWelcomeSender() {
    return createMailSender(welcomeUsername, welcomePassword);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private JavaMailSenderImpl createMailSender(String username, String password) {
    System.setProperty("mail.mime.encodefilename", "true");
    System.setProperty("mail.mime.encodeparameters", "false");

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setProtocol(protocol);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", smtpStarttlsEnable);
    props.put("mail.debug", debugEnable);
    props.put("mail.mime.charset", ENCODING);

    Session emailSession = Session.getInstance(mailSender.getJavaMailProperties(),
        new javax.mail.Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                mailSender.getUsername(), mailSender.getPassword());
          }
        });
    mailSender.setSession(emailSession);
    return mailSender;
  }

  @Bean
  @Qualifier("welcomeMessageTemplate")
  public SimpleMailMessage welcomeMessageTemplate() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText(
        "Здравствуйте, уважаемый Инвестор!<br/>" +
            "Вам предоставлен доступ в личный кабинет Доходного Дома &#171;КолесникЪ&#187; (https://www.lk.ddkolesnik.com)<br/>" +
            "Данные для входа:<br/><br/>" +
            "login: %s<br/>" +
            "Пароль: %s<br/><br/>" +
            "С уважением, \"Колесник.Инвестиции\"");
    return message;
  }

  @Bean
  @Qualifier("confirmMessageTemplate")
  public SimpleMailMessage confirmMessageTemplate() {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setText(
        "Здравствуйте, уважаемый Инвестор!<br/>" +
            "Ваш код подтверждения %s<br/>" +
            "Если Вы не запрашивали код подтверждения, то просто проигнорируйте сообщение<br/>" +
            "С уважением, \"Колесник.Инвестиции\"");
    return message;
  }

}
