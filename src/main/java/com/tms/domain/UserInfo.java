package com.tms.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity(name = "service_user")
public class UserInfo {
    @Id
    @SequenceGenerator(name="mySeqGen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private LocalDateTime createdAt;

    @Column(name = "updated")
    private LocalDateTime updatedAt;;
}