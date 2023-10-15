package com.honeyfuc.tasklist.web.mappers;

import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);

    List<TaskDto> toDto(List<Task> tasks);

    Task toEntity(TaskDto dto);
}
