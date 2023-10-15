package com.honeyfuc.tasklist.service;

import com.honeyfuc.tasklist.web.dto.auth.JwtRequest;
import com.honeyfuc.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
