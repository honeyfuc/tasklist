package com.honeyfuc.tasklist.web.controller;

import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.service.AuthService;
import com.honeyfuc.tasklist.service.UserService;
import com.honeyfuc.tasklist.web.dto.auth.JwtRequest;
import com.honeyfuc.tasklist.web.dto.auth.JwtResponse;
import com.honeyfuc.tasklist.web.dto.user.UserDto;
import com.honeyfuc.tasklist.web.dto.validation.OnCreate;
import com.honeyfuc.tasklist.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public JwtResponse login(@Validated
                                 @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public UserDto register(@Validated(OnCreate.class)
                                @RequestBody final UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Get updated access/refresh tokens")
    public JwtResponse refresh(@RequestBody final String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
