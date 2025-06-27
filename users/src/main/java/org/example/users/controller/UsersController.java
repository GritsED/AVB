package org.example.users.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserDto;
import org.example.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid NewUserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                     @RequestParam(defaultValue = "10") Integer size) {
        return userService.getAllUsers(from, size);
    }

    /**
     * получение пользователя по айди
     */
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * апдейт юзера по айди
     */
    @PatchMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUser(userId, updateUserDto);
    }

    /**
     * удаление пользователя по айди
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
