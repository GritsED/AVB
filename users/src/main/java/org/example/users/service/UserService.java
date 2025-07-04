package org.example.users.service;

import org.example.dto.UserShortDto;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserFullDto;

import java.util.List;

public interface UserService {
    UserFullDto addUser(NewUserDto newUserDto);

    List<UserFullDto> getAllUsers(Integer from, Integer size);

    UserFullDto getUserById(Long id);

    UserFullDto updateUser(Long id, UpdateUserDto updateUserDto);

    void deleteUser(Long id);

    List<UserShortDto> getUsersByCompanyIds(List<Long> companyIds, Integer from, Integer size);

    List<UserShortDto> getUsersByCompanyId(Long companyId, Integer from, Integer size);
}
