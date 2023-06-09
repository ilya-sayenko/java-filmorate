package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.impl.Film;

import java.util.List;

public interface FilmService extends Service<Film> {
    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getPopular(int count, Integer genreId, Integer year);

    List<Film> getCommon(int userId, int friendId);

    void deleteFilmById(int filmId);
}
