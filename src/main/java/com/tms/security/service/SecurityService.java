package com.tms.security.service;

import com.tms.domain.Role;
import com.tms.domain.UserInfo;
import com.tms.repository.UserRepository;
import com.tms.security.JwtUtils;
import com.tms.security.domain.AuthRequest;
import com.tms.security.domain.RegistrationDTO;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final SecurityCredentials securityCredentials;
    private final UserInfo userInfo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserRepository userRepository,
                           SecurityCredentialsRepository securityCredentialsRepository,
                           SecurityCredentials securityCredentials, UserInfo userInfo, JwtUtils jwtUtils,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
        this.securityCredentials = securityCredentials;
        this.userInfo = userInfo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateToken(AuthRequest authRequest) {
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(authRequest.getLogin());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(), credentials.get().getUserPass())) {
            return jwtUtils.generateJwtToken(authRequest.getLogin());
        }
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDTO registrationDTO) {
        Optional<SecurityCredentials> result = securityCredentialsRepository.findByUserLogin(registrationDTO.getUserLogin());
        if (result.isEmpty()) {
            userInfo.setFirstName(registrationDTO.getFirstName());
            userInfo.setLastName(registrationDTO.getLastName());
            userInfo.setCreatedAt(LocalDateTime.now());
            userInfo.setUpdatedAt(LocalDateTime.now());
            UserInfo userInfoResult = userRepository.save(userInfo);

            securityCredentials.setUserLogin(registrationDTO.getUserLogin());
            securityCredentials.setUserPass(passwordEncoder.encode(registrationDTO.getUserPass()));
            securityCredentials.setUserRole(Role.USER);
            securityCredentials.setUserId(userInfoResult.getId());
            securityCredentialsRepository.save(securityCredentials);
        }
    }
}

