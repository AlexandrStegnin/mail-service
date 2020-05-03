package com.ddkolesnik.mailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Модель для хранения информации о ключах приложения
 * Ключ нужен для авторизации в API
 *
 * @author Alexandr Stegnin
 */

@Data
@Entity
@Table(name = "app_token")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppToken {

    @Id
    Long id;

    @Column(name = "app_name")
    String name;

    @Column(name = "token")
    String token;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "creation_time")
    LocalDate creationTime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "modified_time")
    LocalDate modifiedTime;

}
