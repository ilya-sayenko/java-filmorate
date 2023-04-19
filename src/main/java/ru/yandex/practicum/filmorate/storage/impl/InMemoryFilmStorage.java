package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> data = new HashMap<>();
    private int id;

    @Override
    public List<Film> findAll() {
        return data.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Film create(Film film) {
        film.setId(++id);
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        data.put(id, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        data.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }
}
