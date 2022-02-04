package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.config.exception.UserNotFoundException;
import com.ddkolesnik.mailservice.model.AppUser;
import com.ddkolesnik.mailservice.model.UserProfile;
import com.ddkolesnik.mailservice.repository.AppUserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователями
 *
 * @author Alexandr Stegnin
 */

@Service
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AppUserService {

  AppUserRepository appUserRepository;
  UserProfileService userProfileService;

  public AppUserService(AppUserRepository appUserRepository, UserProfileService userProfileService) {
    this.appUserRepository = appUserRepository;
    this.userProfileService = userProfileService;
  }

  @Transactional(readOnly = true)
  public AppUser findByEmail(String email) {
    List<UserProfile> profiles = userProfileService.findByEmail(email);
    List<AppUser> users = profiles
        .stream()
        .map(UserProfile::getUser)
        .collect(Collectors.toList());
    if (users.isEmpty()) {
      throw UserNotFoundException.build404Exception("Пользователь с email = [" + email + "] не найден");
    }
    return users.get(0);
  }

  @Transactional
  public void update(AppUser user) {
    AppUser dbUser = appUserRepository.getOne(user.getId());
    dbUser.setPassword(user.getPassword());
    appUserRepository.save(dbUser);
  }
}
