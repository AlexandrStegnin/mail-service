package com.ddkolesnik.mailservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Alexandr Stegnin
 */

@Data
@Entity
@Table(name = "app_message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppMessage {

    @Id
    @TableGenerator(name = "appMessageSeqStore", table = "seq_store",
            pkColumnName = "seq_name", pkColumnValue = "APP.MESSAGE.ID.PK",
            valueColumnName = "seq_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "appMessageSeqStore")
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "sent_at")
    LocalDateTime sentAt;

    @Column(name = "sender")
    String sender;

    @Column(name = "subject")
    String subject;

    @Column(name = "text")
    String text;

    @JsonIgnore
    @Column(name = "last_error")
    String error;

}
