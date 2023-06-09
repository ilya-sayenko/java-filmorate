package ru.yandex.practicum.filmorate.controller;

import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.filmorate.model.impl.Event;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.service.EventService;
import ru.yandex.practicum.filmorate.service.UserService;

import static ru.yandex.practicum.filmorate.log.LogMessage.ADD_FRIEND;
import static ru.yandex.practicum.filmorate.log.LogMessage.CREATE_USER;
import static ru.yandex.practicum.filmorate.log.LogMessage.DELETE_FRIEND;
import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_ALL_USERS;
import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_USER_BY_ID;
import static ru.yandex.practicum.filmorate.log.LogMessage.GET_COMMON_FRIENDS;
import static ru.yandex.practicum.filmorate.log.LogMessage.GET_EVENTS_BY_USER;
import static ru.yandex.practicum.filmorate.log.LogMessage.GET_FRIENDS;
import static ru.yandex.practicum.filmorate.log.LogMessage.GET_RECOMMENDATIONS;
import static ru.yandex.practicum.filmorate.log.LogMessage.UPDATE_USER;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final EventService eventService;

    @GetMapping
    public List<User> findAll() {
        log.info(FIND_ALL_USERS.getMessage());
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable int id) {
        log.info(FIND_USER_BY_ID.getMessage());
        return userService.findById(id);
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

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info(ADD_FRIEND.getMessage());
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info(DELETE_FRIEND.getMessage());
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info(GET_FRIENDS.getMessage());
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info(GET_COMMON_FRIENDS.getMessage());
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping("/{id}/recommendations")
    public List<Film> getRecommendations(@PathVariable int id) {
        log.info((GET_RECOMMENDATIONS.getMessage()));
        return userService.getRecommendations(id);
    }

    @GetMapping(value = "/{id}/feed")
    public List<Event> getEvents(@PathVariable int id) {
        log.info(GET_EVENTS_BY_USER.getMessage());
        return eventService.findByUserId(id);
    }
}
