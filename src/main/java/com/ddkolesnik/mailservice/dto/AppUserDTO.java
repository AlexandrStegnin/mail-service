package com.ddkolesnik.mailservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Alexandr Stegnin
 */

@Data
@ToString(of = {"email"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUserDTO {

    @Email(message = "Email должен быть правильным")
    @NotBlank(message = "Email должен быть указан")
    String email;

    String confirmCode;

}
