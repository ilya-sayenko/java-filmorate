package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
public class FilmServiceImpl extends AbstractService<Film> implements FilmService {
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    @Autowired
    public FilmServiceImpl(FilmStorage storage, UserStorage userStorage) {
        super(storage);
        this.userStorage = userStorage;
        this.filmStorage = storage;
    }

    @Override
    protected String getModelName() {
        return "Film";
    }

    @Override
    public Film create(Film film) {
        Film createdFilm = super.create(film);
        log.info(FILM_CREATED.getMessage());
        return createdFilm;
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        try {
            Film updatedFilm = super.update(film);
            log.info(FILM_UPDATED.getMessage());
            return updatedFilm;
        } catch (ModelNotFoundException ex) {
            throw new FilmNotFoundException(film.getId());
        }
    }

    @Override
    public void addLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = findById(filmId);
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        filmStorage.addLike(film, user);
        log.info(LIKE_ADDED.getMessage());
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        Film film = findById(filmId);
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        filmStorage.deleteLike(film, user);
        log.info(LIKE_DELETED.getMessage());
    }

    @Override
    public List<Film> getPopular(int count) {
        return filmStorage.findPopular(count);
    }

    @Override
    public List<Film> getCommon(int userId, int friendId) {
        userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userStorage.findById(friendId).orElseThrow(() -> new UserNotFoundException(friendId));
        return filmStorage.getCommon(userId,friendId);
    }
}
