package com.ddkolesnik.mailservice.service;

import com.ddkolesnik.mailservice.model.UserProfile;
import com.ddkolesnik.mailservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> findByEmail(String email) {
        return userProfileRepository.findByEmail(email);
    }

}
