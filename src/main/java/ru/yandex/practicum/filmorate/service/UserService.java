package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
public class UserService {
    private final Map<Integer, User> data = new HashMap<>();
    private static int id;

    private void changeUserNameIfNull(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
    }

    public List<User> findAll() {
        log.info(FIND_ALL_USERS.getMessage());
        return data.values().stream().collect(Collectors.toUnmodifiableList());
    }

    public User create(User user) {
        user.setId(++id);
        changeUserNameIfNull(user);
        data.put(id, user);
        log.info(USER_CREATED.getMessage());
        return user;
    }

    public User update(User user) {
        Optional<User> filmFromData = Optional.ofNullable(data.get(user.getId()));
        if (filmFromData.isPresent()) {
            changeUserNameIfNull(user);
            data.put(user.getId(), user);
            log.info(USER_UPDATED.getMessage());
            return user;
        } else {
            throw new ValidationException("film with id=" + user.getId() + " is not found");
        }
    }
}
