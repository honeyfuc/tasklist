package com.honeyfuc.tasklist.web.mappers;

import com.honeyfuc.tasklist.domain.task.TaskImage;
import com.honeyfuc.tasklist.web.dto.task.TaskImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto> {
}
