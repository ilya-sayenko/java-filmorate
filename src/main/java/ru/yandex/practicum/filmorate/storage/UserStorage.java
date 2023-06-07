package ru.yandex.practicum.filmorate.storage;

import java.util.List;

import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;

public interface UserStorage extends Storage<User> {
    void addFriend(User user, User friend);

    void deleteFriend(User user, User friend);

    List<User> findFriends(int id);

    List<User> findCommonFriends(int id, int otherId);

    List<Film> getRecommendations(int userId);
}
