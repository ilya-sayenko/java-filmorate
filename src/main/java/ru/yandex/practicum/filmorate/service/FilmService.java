package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Service
@Slf4j
public class FilmService {
    private final Map<Integer, Film> data = new HashMap<>();
    private static int id;

    public List<Film> findAll() {
        log.info(FIND_ALL_FILMS.getMessage());
        return new ArrayList<>(data.values());
    }

    public Film create(Film film) {
        film.setId(++id);
        data.put(id, film);
        log.info(FILM_CREATED.getMessage());
        return film;
    }

    public Film update(Film film) {
        Optional<Film> filmFromData = Optional.ofNullable(data.get(film.getId()));
        if (filmFromData.isPresent()) {
            data.put(film.getId(), film);
            log.info(FILM_UPDATED.getMessage());
            return film;
        } else {
            throw new ValidationException("film with id=" + film.getId() + " is not found");
        }
    }
}
