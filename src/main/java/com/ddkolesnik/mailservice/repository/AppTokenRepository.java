package com.ddkolesnik.mailservice.repository;

import com.ddkolesnik.mailservice.model.AppToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alexandr Stegnin
 */

@Repository
public interface AppTokenRepository extends JpaRepository<AppToken, Long> {

    Boolean existsByToken(String token);

}
