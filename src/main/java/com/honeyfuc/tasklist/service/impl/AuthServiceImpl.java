package com.honeyfuc.tasklist.service.impl;

import com.honeyfuc.tasklist.service.AuthService;
import com.honeyfuc.tasklist.web.dto.auth.JwtRequest;
import com.honeyfuc.tasklist.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }

}
