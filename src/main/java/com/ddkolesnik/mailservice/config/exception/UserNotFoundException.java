package com.ddkolesnik.mailservice.config.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

/**
 * @author Alexandr Stegnin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNotFoundException extends RuntimeException {

  String message;
  HttpStatus status;

  public static UserNotFoundException build404Exception(String message) {
    return new UserNotFoundException(message, HttpStatus.NOT_FOUND);
  }

}
