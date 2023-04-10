package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Service
@Slf4j
public class FilmService {
    private final Map<Integer, Film> data = new HashMap<>();

    public List<Film> findAll() {
        return new ArrayList<>(data.values());
    }

    public Film create(Film film) {
        int id = data.size() + 1;
        film.setId(id);
        data.put(id, film);
        log.info("film created");
        return film;
    }

    public Film update(Film film) {
        Optional<Film> filmFromData = Optional.ofNullable(data.get(film.getId()));
        if (filmFromData.isPresent()) {
            data.put(film.getId(), film);
            log.info("film updated");
            return film;
        } else {
            throw new ValidationException("film with id=" + film.getId() + " is not found");
        }
    }
}
