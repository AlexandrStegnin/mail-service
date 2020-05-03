package com.ddkolesnik.mailservice.repository;

import com.ddkolesnik.mailservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    List<AppUser> findByEmail(String email);

}
