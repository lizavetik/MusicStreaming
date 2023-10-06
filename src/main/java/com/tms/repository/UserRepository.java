package com.tms.repository;

import com.tms.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM user_info WHERE email = :fn")
    Optional<UserInfo> findUsersByEmail(String fn);
}