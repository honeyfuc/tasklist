package com.honeyfuc.tasklist.config;

import com.honeyfuc.tasklist.repository.TaskRepository;
import com.honeyfuc.tasklist.repository.UserRepository;
import com.honeyfuc.tasklist.service.AuthService;
import com.honeyfuc.tasklist.service.ImageService;
import com.honeyfuc.tasklist.service.TaskService;
import com.honeyfuc.tasklist.service.UserService;
import com.honeyfuc.tasklist.service.impl.AuthServiceImpl;
import com.honeyfuc.tasklist.service.impl.ImageServiceImpl;
import com.honeyfuc.tasklist.service.impl.TaskServiceImpl;
import com.honeyfuc.tasklist.service.impl.UserServiceImpl;
import com.honeyfuc.tasklist.service.props.JwtProperties;
import com.honeyfuc.tasklist.service.props.MinioProperties;
import com.honeyfuc.tasklist.web.security.JwtTokenProvider;
import com.honeyfuc.tasklist.web.security.JwtUserDetailsService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    private final AuthenticationManager authenticationManager;

    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "YXNkZmFqc2Rma2pma3drZWZxandsZmtzZGFqaw==");
        return jwtProperties;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService(userService());
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setBucket("images");
        return minioProperties;
    }

    @Bean
    @Primary
    public ImageService imageService() {
        return new ImageServiceImpl(minioClient(), minioProperties());
    }

    @Bean
    public JwtTokenProvider tokenProvider() {
        return new JwtTokenProvider(jwtProperties(), userDetailsService(), userService());
    }

    @Bean
    @Primary
    public UserService userService() {
        return new UserServiceImpl(userRepository, testPasswordEncoder());
    }

    @Bean
    @Primary
    public TaskService taskService() {
        return new TaskServiceImpl(taskRepository, userService(), imageService());
    }

    @Bean
    @Primary
    public AuthService authService(){
        return new AuthServiceImpl(authenticationManager, userService(), tokenProvider());
    }
}
