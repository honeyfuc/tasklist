package com.honeyfuc.tasklist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class Task {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
