package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.model.AppUser;
import com.ddkolesnik.mailservice.model.UserProfile;
import com.ddkolesnik.mailservice.repository.AppUserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    /**
     * Найти пользователя по id
     *
     * @param userId - id пользователя
     * @return - найденный пользователь
     */
    @Transactional(readOnly = true)
    public AppUser findById(Long userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("Пользователь с id = [" + userId + "] не найден");
        }
        return user.get();
    }

    /**
     * Найти пользователя по email
     *
     * @param email - email пользователя
     * @return - найденный пользователь
     */
    @Transactional(readOnly = true)
    public AppUser findByEmail(String email) {
        List<UserProfile> profiles = userProfileService.findByEmail(email);
        List<AppUser> users = profiles
                .stream()
                .map(UserProfile::getUser)
                .collect(Collectors.toList());
        if (users.size() > 0) {
            AppUser user = users.get(0);
            if (Objects.isNull(user)) {
                throw new EntityNotFoundException("Пользователь с email = [" + email + "] не найден");
            }
            return user;
        } else {
            throw new EntityNotFoundException("Пользователь с email = [" + email + "] не найден");
        }
    }

    /**
     * Обновить пароль пользователя
     *
     * @param user - пользователь, которого надо обновить
     */
    @Transactional
    public void update(AppUser user) {
        AppUser dbUser = appUserRepository.getOne(user.getId());
        dbUser.setPassword(user.getPassword());
        appUserRepository.save(dbUser);
    }
}
