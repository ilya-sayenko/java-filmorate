package ru.yandex.practicum.filmorate.storage.impl.inmemory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;
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

    @Override
    public void addLike(Film film, User user) {
        film.addLike(user);
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.deleteLike(user);
    }
}
