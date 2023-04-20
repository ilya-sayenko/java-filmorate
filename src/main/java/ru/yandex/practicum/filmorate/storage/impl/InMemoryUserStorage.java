package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;

@Component
public class InMemoryUserStorage extends InMemoryAbstractStorage<User> implements UserStorage {

    @Override
    public User create(User user) {
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }
        return super.create(user);
    }
}
