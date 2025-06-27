package org.example.users.mapper;

import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserDto;
import org.example.users.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(NewUserDto newUser);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(User user, UpdateUserDto updateUserDto);
}
