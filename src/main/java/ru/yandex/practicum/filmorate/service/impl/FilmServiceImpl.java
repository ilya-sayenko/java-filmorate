package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
public class FilmServiceImpl extends AbstractService<Film> implements FilmService {
    private static final Comparator<Film> FILM_COMPARATOR_BY_LIKES = (f1, f2) -> f2.getLikes().size() - f1.getLikes().size();
    private final UserStorage userStorage;

    @Autowired
    public FilmServiceImpl(Storage<Film> storage, UserStorage userStorage) {
        super(storage);
        this.userStorage = userStorage;
    }

    @Override
    protected void changeNewModelBeforeUpdate(Film newFilm, Film oldFilm) {
        newFilm.setLikes(oldFilm.getLikes());
    }

    @Override
    public Film findById(int id) throws FilmNotFoundException {
        try {
            return super.findById(id);
        } catch (ModelNotFoundException ex) {
            throw new FilmNotFoundException(id);
        }
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
        film.addLike(user);
        log.info(LIKE_ADDED.getMessage());
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        Film film = findById(filmId);
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.deleteLike(user);
        log.info(LIKE_DELETED.getMessage());
    }

    @Override
    public List<Film> getPopular(int count) {
        return storage.findAll()
                .stream()
                .sorted(FILM_COMPARATOR_BY_LIKES)
                .limit(count)
                .collect(Collectors.toList());
    }
}
