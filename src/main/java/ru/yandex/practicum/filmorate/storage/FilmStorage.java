package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;

public interface FilmStorage extends Storage<Film> {
    void addLike(Film film, User user);

    void deleteLike(Film film, User user);
}
