package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        log.info(FIND_ALL_USERS.getMessage());
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(CREATE_USER.getMessage());
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(UPDATE_USER.getMessage());
        return userService.update(user);
    }
}
