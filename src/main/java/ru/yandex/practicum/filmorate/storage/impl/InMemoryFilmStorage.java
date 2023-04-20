package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashSet;

@Component
public class InMemoryFilmStorage extends InMemoryAbstractStorage<Film> implements FilmStorage {

    @Override
    public Film create(Film film) {
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        return super.create(film);
    }
}
