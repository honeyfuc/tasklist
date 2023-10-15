package com.honeyfuc.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.honeyfuc.tasklist.domain.task.Status;
import com.honeyfuc.tasklist.web.dto.validation.OnCreate;
import com.honeyfuc.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    @NotNull(message = "Id must not be null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Title length must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "Description length must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;

}
