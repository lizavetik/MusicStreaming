package com.tms.service;

import com.tms.domain.UserInfo;
import com.tms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

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
        return userRepository.findAll();
    }

    public Optional<UserInfo> getUser(Integer id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        //1. по логину достешь пользователя
        //2. сравниваешь id
        //3. если id совпали то все ОК! Если не совпали то кидаем ошибку.
        return userRepository.findById(id);
    }

    public Optional<UserInfo> getUserInfo(Integer id) {
        //1. SpringContext -> login;
        //2. дай польщователя и возвращаете его.
        return userRepository.findById(id);
    }
    public void createUser(UserInfo userInfo) {
        userInfo.setCreatedAt(LocalDateTime.now());
        userInfo.setUpdatedAt(LocalDateTime.now());
        userRepository.save(userInfo);
    }
    public void updateUser(UserInfo userInfo) {
        userInfo.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(userInfo);
    }
    public Optional<UserInfo> findUserByLastName(String firstName) {
        return userRepository.findUsersByLastName(firstName);
    }
    public Optional<UserInfo> findUserByFirstName(String firstName) {
        return userRepository.findUsersByFirstName(firstName);
    }
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}