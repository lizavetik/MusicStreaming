package com.tms.security.domain;

import com.tms.domain.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity(name = "security_credentials")
@Component
public class SecurityCredentials {
    @Id
    @SequenceGenerator(name="mySeqGen", sequenceName = "security_create_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "mySeqGen")
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "user_password")
    private String userPass;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role userRole;

    @Column(name = "user_id")
    private Integer userId;
}
