package com.honeyfuc.tasklist.web.mappers;

import com.honeyfuc.tasklist.domain.user.User;
import com.honeyfuc.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {

}
