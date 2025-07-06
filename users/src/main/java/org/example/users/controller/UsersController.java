package org.example.users.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserShortDto;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserFullDto;
import org.example.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserFullDto addUser(@RequestBody @Valid NewUserDto userDto) {
        log.info("Adding new user: {}", userDto);
        return userService.addUser(userDto);
    }

    @GetMapping
    public List<UserFullDto> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        log.info("Getting all users with params: from {}, size {}", from, size);
        return userService.getAllUsers(from, size);
    }

    @GetMapping("/{userId}")
    public UserFullDto getUser(@PathVariable Long userId) {
        log.info("Getting user with id: {}", userId);
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserFullDto updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserDto updateUserDto) {
        log.info("Received update request for user {} with fields: {}", userId, updateUserDto);
        return userService.updateUser(userId, updateUserDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        log.info("Deleting user with id: {}", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/internal")
    public List<UserShortDto> getUsersByCompanyId(@RequestParam List<Long> companyIds) {
        log.info("[Internal] Get users for companies: {}", companyIds);

        return userService.getUsersByCompanyIds(companyIds);
    }

    @GetMapping("/internal/{companyId}")
    public List<UserShortDto> getUsersByCompanyId(@PathVariable Long companyId) {
        log.info("[Internal] Get users for company: {}", companyId);
        return userService.getUsersByCompanyId(companyId);
    }
}
