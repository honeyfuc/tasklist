package com.honeyfuc.tasklist.web.mappers;

import com.honeyfuc.tasklist.domain.task.Task;
import com.honeyfuc.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {

}
