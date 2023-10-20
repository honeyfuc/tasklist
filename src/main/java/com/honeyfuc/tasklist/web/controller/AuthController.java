package com.honeyfuc.tasklist.web.controller;

import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.service.AuthService;
import com.honeyfuc.tasklist.service.UserService;
import com.honeyfuc.tasklist.web.dto.auth.JwtRequest;
import com.honeyfuc.tasklist.web.dto.auth.JwtResponse;
import com.honeyfuc.tasklist.web.dto.user.UserDto;
import com.honeyfuc.tasklist.web.dto.validation.OnCreate;
import com.honeyfuc.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
