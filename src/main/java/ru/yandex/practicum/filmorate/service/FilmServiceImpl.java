package ru.yandex.practicum.filmorate.service;

import org.springframework.validation.Validator;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilmServiceImpl implements FilmService {
//    @Autowired
//    private Validator validator;
    private final Map<Integer, Film> data = new HashMap<>();

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Film create(Film film) {
        data.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        data.put(film.getId(), film);
        return film;
    }
}
