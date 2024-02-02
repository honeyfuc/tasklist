package com.honeyfuc.tasklist.service.impl;

import com.honeyfuc.tasklist.config.TestConfig;
import com.honeyfuc.tasklist.domain.MailType;
import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.repository.TaskRepository;
import com.honeyfuc.tasklist.repository.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Properties;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
public class MailServiceImplTest {

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private Configuration configuration;

    @MockBean
    private JavaMailSender mailSender;

    @Autowired
    private MailServiceImpl mailService;

    @Test
    public void sendRegistrationEmail_test() {
        try {
            String name = "John";
            String username = "john@gmail.com";
            User user = new User();
            user.setName(name);
            user.setUsername(username);

            Mockito.when(mailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));
            Mockito.when(configuration.getTemplate("register.ftlh"))
                    .thenReturn(Mockito.mock(Template.class));
            mailService.sendEmail(user, MailType.REGISTRATION, new Properties());

            Mockito.verify(configuration).getTemplate("register.ftlh");
            Mockito.verify(mailSender).send(Mockito.any(MimeMessage.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendReminderEmail_test() {
        try {
            String name = "John";
            String username = "john@gmail.com";
            User user = new User();
            user.setName(name);
            user.setUsername(username);

            Mockito.when(mailSender.createMimeMessage())
                    .thenReturn(Mockito.mock(MimeMessage.class));
            Mockito.when(configuration.getTemplate("reminder.ftlh"))
                    .thenReturn(Mockito.mock(Template.class));
            mailService.sendEmail(user, MailType.REMINDER, new Properties());

            Mockito.verify(configuration).getTemplate("reminder.ftlh");
            Mockito.verify(mailSender).send(Mockito.any(MimeMessage.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
