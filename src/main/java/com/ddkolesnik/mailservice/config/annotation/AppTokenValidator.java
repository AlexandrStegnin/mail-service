package com.ddkolesnik.mailservice.config.annotation;

import com.ddkolesnik.mailservice.service.AppTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидация токена приложения
 *
 * @author Alexandr Stegnin
 */

public class AppTokenValidator implements ConstraintValidator<ValidToken, String> {

   @Autowired
   private AppTokenService appTokenService;

   public void initialize(ValidToken constraint) {
   }

   public boolean isValid(String token, ConstraintValidatorContext context) {
      return appTokenService.existByToken(token);
   }

}
