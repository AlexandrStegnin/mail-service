package com.ddkolesnik.mailservice.controller;

import com.ddkolesnik.mailservice.config.annotation.ValidToken;
import com.ddkolesnik.mailservice.dto.AppUserDTO;
import com.ddkolesnik.mailservice.service.AppMessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Alexandr Stegnin
 */

@SuppressWarnings("unused")
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AppMessageController {

    AppMessageService appMessageService;

    @PostMapping(path = "/{token}/send/welcome", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendWelcomeMessage(@PathVariable(name = "token") @ValidToken String token,
                                   @RequestBody @Valid AppUserDTO userDTO) {
        appMessageService.sendWelcomeMessage(userDTO);
    }

}
