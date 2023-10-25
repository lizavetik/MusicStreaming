package com.tms.security.domain;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
