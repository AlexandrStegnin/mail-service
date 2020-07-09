package com.ddkolesnik.mailservice.repository;

import com.ddkolesnik.mailservice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    List<UserProfile> findByEmail(String email);

}
