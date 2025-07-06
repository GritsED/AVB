package org.example.users.mapper;

import org.example.dto.CompanyShortDto;
import org.example.dto.UserShortDto;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserFullDto;
import org.example.users.model.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "companyId", target = "companyId")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "phone", target = "phone")
    User toEntity(NewUserDto newUser);

    @Mapping(source = "company", target = "company")
    @Mapping(source = "user.id", target = "id")
    UserFullDto toDto(User user, CompanyShortDto company);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "companyId", target = "companyId")
    UserShortDto toShortDto(User user);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UpdateUserDto updateUserDto);

    List<UserShortDto> toDtos(List<User> users);

    default List<UserFullDto> toDtoList(List<User> users, Map<Long, CompanyShortDto> companyMap) {
        return users.stream()
                .map(user -> toDto(user, companyMap.get(user.getCompanyId())))
                .toList();
    }
}
