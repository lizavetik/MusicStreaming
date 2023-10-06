package com.tms.service;

import com.tms.domain.UserInfo;
import com.tms.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserInfo> getUsers() {
        return userRepository.findAll(Sort.by("id"));
    }
    public Optional<UserInfo> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public UserInfo createUser(UserInfo userInfo) {
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userInfo);
    }
    public void updateUser(UserInfo userInfo) {
        userInfo.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(userInfo);
    }
}