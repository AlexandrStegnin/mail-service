package com.ddkolesnik.mailservice.config;

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
@PropertySource("classpath:yandex.mail.properties")
public class AppConfig {

  private static final String ENCODING = StandardCharsets.UTF_8.name();

  private static String HOST;

  private static Integer PORT;

  private static String PROTOCOL;

  private static String SMTP_AUTH;

  private static String SMTP_STARTTLS_ENABLE;

  private static String DEBUG_ENABLE;

  private static String WELCOME_USERNAME;

  private static String WELCOME_PASSWORD;

  @Value("${spring.mail.host}")
  protected void setHost(String value) {
    HOST = value;
  }

  @Value("${spring.mail.port}")
  protected void setPort(Integer value) {
    PORT = value;
  }

  @Value("${spring.mail.protocol}")
  protected void setProtocol(String value) {
    PROTOCOL = value;
  }

  @Value("${spring.mail.smtp.auth}")
  protected void setSmtpAuth(String value) {
    SMTP_AUTH = value;
  }

  @Value("${spring.mail.smtp.starttls.enable}")
  protected void setSmtpStarttlsEnable(String value) {
    SMTP_STARTTLS_ENABLE = value;
  }

  @Value("${spring.mail.debug}")
  protected void setDebugEnable(String value) {
    DEBUG_ENABLE = value;
  }

  @Value("${spring.mail.kolesnik.username}")
  protected void setWelcomeUsername(String value) {
    WELCOME_USERNAME = value;
  }

  @Value("${spring.mail.kolesnik.password}")
  protected void setWelcomePassword(String value) {
    WELCOME_PASSWORD = value;
  }

  @Bean
  @Qualifier("welcomeSender")
  public JavaMailSender getWelcomeSender() {
    return createMailSender(WELCOME_USERNAME, WELCOME_PASSWORD);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private JavaMailSenderImpl createMailSender(String username, String password) {
    System.setProperty("mail.mime.encodefilename", "true");
    System.setProperty("mail.mime.encodeparameters", "false");

    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(HOST);
    mailSender.setPort(PORT);
    mailSender.setProtocol(PROTOCOL);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.smtp.auth", SMTP_AUTH);
    props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
    props.put("mail.debug", DEBUG_ENABLE);
    props.put("mail.mime.charset", ENCODING);

    Session emailSession = Session.getInstance(mailSender.getJavaMailProperties(),
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                mailSender.getUsername(), mailSender.getPassword());
          }
        });
    mailSender.setSession(emailSession);
    return mailSender;
  }

  @Bean
  public SimpleMailMessage templateSimpleMessage() {
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

}
