package com.tms.security;

import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final SecurityCredentialsRepository securityCredentialsRepository;

    public CustomUserDetailService(SecurityCredentialsRepository securityCredentialsRepository) {
        this.securityCredentialsRepository = securityCredentialsRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByUserLogin(username);
        if (securityCredentials.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(securityCredentials.get().getUserLogin())
                .password(securityCredentials.get().getUserPass())
                .roles(securityCredentials.get().getUserRole().toString())
                .build();
    }
}
