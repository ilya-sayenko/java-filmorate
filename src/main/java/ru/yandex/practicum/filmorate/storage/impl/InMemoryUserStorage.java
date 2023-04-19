package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> data = new HashMap<>();
    private int id;

    @Override
    public List<User> findAll() {
        return data.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User create(User user) {
        user.setId(++id);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        changeUserNameIfNull(user);
        data.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        changeUserNameIfNull(user);
        return data.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    private void changeUserNameIfNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
