package com.ddkolesnik.mailservice.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Alexandr Stegnin
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageDTO {

  @NotEmpty
  List<String> recipients;
  @NotBlank
  String subject;
  @NotBlank
  String text;

}
