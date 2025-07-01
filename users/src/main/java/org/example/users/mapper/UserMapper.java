package org.example.users.mapper;

import org.example.dto.UserDto;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "phone", target = "phone")
    User toEntity(NewUserDto newUser);

    //    @Mapping(source = "company", target = "company")
    @Mapping(source = "user.id", target = "id")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UpdateUserDto updateUserDto);
}
