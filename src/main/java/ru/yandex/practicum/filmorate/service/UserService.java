package ru.yandex.practicum.filmorate.service;

import java.util.List;

import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;

public interface UserService extends Service<User> {
    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> getFriends(int userId);

    List<User> getCommonFriends(int id, int otherId);

    List<Film> getRecommendations(int userId);
}
