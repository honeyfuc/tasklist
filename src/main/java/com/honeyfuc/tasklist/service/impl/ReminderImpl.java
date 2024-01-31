package com.honeyfuc.tasklist.service.impl;

import com.honeyfuc.tasklist.domain.MailType;
import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.service.MailService;
import com.honeyfuc.tasklist.service.Reminder;
import com.honeyfuc.tasklist.service.TaskService;
import com.honeyfuc.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReminderImpl implements Reminder {

    private final TaskService taskService;

    private final UserService userService;

    private final MailService mailService;

    private final Duration DURATION = Duration.ofHours(1);

    @Scheduled(cron = "0 * * * * *")
    //@Scheduled(cron = "0 0 * * * *")
    @Override
    public void remindForTask() {
        List<Task> tasks = taskService.getAllSoonTasks(DURATION);
        tasks.forEach(task -> {
            User user = userService.getTaskAuthor(task.getId());
            Properties properties = new Properties();
            properties.setProperty("task.title", task.getTitle());
            properties.setProperty("task.description", task.getDescription());

            mailService.sendEmail(user, MailType.REMINDER, properties);
        });
    }
}
