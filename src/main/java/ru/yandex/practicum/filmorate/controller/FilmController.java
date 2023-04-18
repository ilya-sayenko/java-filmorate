package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public List<Film> findAll() {
        log.info(FIND_ALL_FILMS.getMessage());
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info(CREATE_FILM.getMessage());
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(UPDATE_FILM.getMessage());
        return filmService.update(film);
    }
}
