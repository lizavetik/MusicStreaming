package com.tms.domain;

import com.tms.exception.NotAuthorizedException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity(name = "service_user")
@EqualsAndHashCode
@Component
public class UserInfo {
    @Id
    //@SequenceGenerator(name="mySeqGen", sequenceName = "user_id_seq", allocationSize = 1)
    //@Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "created")
    private LocalDateTime createdAt;

    @Column(name = "updated")
    private LocalDateTime updatedAt;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "l_service_user_song", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Collection<Song> songs;
    @PreRemove
    @PreUpdate
    private void preventUnAuthorizedAccess() throws NotAuthorizedException {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();

        if(roles.stream().noneMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))
                && !name.equals(this.userName)){
            throw new NotAuthorizedException("You can alter and remove only your profile");
        }
    }
}