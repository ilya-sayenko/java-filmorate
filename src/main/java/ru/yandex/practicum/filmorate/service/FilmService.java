package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new FilmNotFoundException(id));
    }

    public Film create(Film film) {
        Film createdFilm = filmStorage.create(film);
        log.info(FILM_CREATED.getMessage());
        return createdFilm;
    }

    public Film update(Film film) {
        Optional<Film> filmFromData = filmStorage.findById(film.getId());
        if (filmFromData.isPresent()) {
            film.setLikes(filmFromData.get().getLikes());
            filmStorage.update(film);
            log.info(FILM_UPDATED.getMessage());
            return film;
        } else {
            throw new FilmNotFoundException(film.getId());
        }
    }

    public void addLike(int filmId, int userId) throws FilmNotFoundException, UserNotFoundException {
        Film film = findById(filmId);
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.addLike(user);
        log.info(LIKE_ADDED.getMessage());
    }

    public void deleteLike(int filmId, int userId) {
        Film film = findById(filmId);
        User user = userStorage.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        film.deleteLike(user);
        log.info(LIKE_DELETED.getMessage());
    }

    public List<Film> getPopular(int count) {
        return filmStorage.findAll()
                .stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
