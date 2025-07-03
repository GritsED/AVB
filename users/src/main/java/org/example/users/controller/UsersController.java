package org.example.users.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserShortDto;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserFullDto;
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
    public UserFullDto addUser(@RequestBody @Valid NewUserDto userDto) {
        return userService.addUser(userDto);
    }

    @GetMapping
    public List<UserFullDto> getAllUsers(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        return userService.getAllUsers(from, size);
    }

    @GetMapping("/{userId}")
    public UserFullDto getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserFullDto updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUser(userId, updateUserDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/internal")
    public List<UserShortDto> getUsersByCompanyId(@RequestParam List<Long> companyIds,
                                                  @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsersByCompanyIds(companyIds, from, size);
    }

    @GetMapping("/internal/{companyId}")
    public List<UserShortDto> getUsersByCompanyId(@PathVariable Long companyId,
                                                  @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsersByCompanyId(companyId, from, size);
    }
}
