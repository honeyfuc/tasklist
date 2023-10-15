package com.honeyfuc.tasklist.web.mappers;

import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
