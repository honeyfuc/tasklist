package com.honeyfuc.tasklist.domain.task;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class Task implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
