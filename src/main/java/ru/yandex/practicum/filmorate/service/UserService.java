package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final Map<Integer, User> data = new HashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    public User create(User user) {
        int id = data.size() + 1;
        user.setId(id);
        data.put(id, user);
        return user;
    }

    public User update(User user) {
        Optional<User> filmFromData = Optional.ofNullable(data.get(user.getId()));
        if (filmFromData.isPresent()) {
            data.put(user.getId(), user);
            log.info("user updated");
            return user;
        } else {
            throw new ValidationException("film with id=" + user.getId() + " is not found");
        }
    }
}
