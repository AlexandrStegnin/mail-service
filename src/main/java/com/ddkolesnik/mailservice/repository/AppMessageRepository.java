package com.ddkolesnik.mailservice.repository;

import com.ddkolesnik.mailservice.model.AppMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alexandr Stegnin
 */

@Repository
public interface AppMessageRepository extends JpaRepository<AppMessage, Long> {

    List<AppMessage> findByErrorNotNull();

}
