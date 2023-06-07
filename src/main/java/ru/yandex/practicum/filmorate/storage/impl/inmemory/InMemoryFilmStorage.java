package ru.yandex.practicum.filmorate.storage.impl.inmemory;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage extends InMemoryAbstractStorage<Film> implements FilmStorage {


    private final Set<Pair<Integer, Integer>> likes = new HashSet<>();


    @Override
    public void addLike(Film film, User user) {
        likes.add(Pair.of(film.getId(), user.getId()));
    }

    @Override
    public void deleteLike(Film film, User user) {
        likes.remove(Pair.of(film.getId(), user.getId()));
    }

    @Override
    public List<Film> findPopular(int count) {
        return likes.stream()
                .map(Pair::getFirst)
                .sorted((n1, n2) -> n2 - n1)
                .limit(count)
                .map(id -> findById(id).orElseThrow(() -> new FilmNotFoundException(id)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Film> getCommon(int userId, int friendId) {
        List<Integer> userFilms = likes.stream().filter(p -> p.getFirst() == userId).map(Pair::getFirst)
                .collect(Collectors.toList());
        List<Integer> friendFilms = likes.stream().filter(p -> p.getFirst() == friendId).map(Pair::getFirst)
                .collect(Collectors.toList());
        userFilms.retainAll(friendFilms);

        return userFilms.stream().map(id -> findById(id).orElseThrow(() -> new FilmNotFoundException(id)))
                .collect(Collectors.toList());
    }
}
