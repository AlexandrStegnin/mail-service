package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.model.AppUser;
import com.ddkolesnik.mailservice.repository.AppUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис для работы с пользователями
 *
 * @author Alexandr Stegnin
 */

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AppUserService {

    AppUserRepository appUserRepository;

    /**
     * Найти пользователя по id
     *
     * @param userId - id пользователя
     * @return - найденный пользователь
     */
    @Transactional(readOnly = true)
    public AppUser findById(Long userId) {
        Optional<AppUser> user = appUserRepository.findById(userId);
        if (user.isEmpty()) {
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
        List<AppUser> users = appUserRepository.findByEmail(email);
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
