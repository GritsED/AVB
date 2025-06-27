package org.example.users.service;

import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(NewUserDto newUserDto);

    List<UserDto> getAllUsers(Integer from, Integer size);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UpdateUserDto updateUserDto);

    void deleteUser(Long id);
}
