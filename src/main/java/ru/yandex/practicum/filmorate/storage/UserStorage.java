package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.impl.User;

public interface UserStorage extends Storage<User> {
    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);
}
