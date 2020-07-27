package com.ddkolesnik.mailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * @author Alexandr Stegnin
 */

@Data
@Entity
@Table(name = "app_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUser {

    @Id
    Long id;

    @Column(name = "login")
    String login;

    @JsonIgnore
    @Column(name = "password")
    String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserProfile profile;
}
