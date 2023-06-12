package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.impl.Film;
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

    @GetMapping(value = "/{id}")
    public Film findById(@PathVariable int id) {
        log.info(FIND_FILM_BY_ID.getMessage());
        return filmService.findById(id);
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

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info(ADD_LIKE.getMessage());
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info(DELETE_LIKE.getMessage());
        filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10") int count) {
        log.info(GET_POPULAR_FILM.getMessage());
        return filmService.getPopular(count);
    }

    @GetMapping(value = "/common")
    public List<Film> getCommon(@RequestParam int userId, @RequestParam int friendId) {
        log.info(GET_COMMON_FILMS.getMessage());
        return filmService.getCommon(userId, friendId);
    }

    @GetMapping(value = "/director/{directorId}")
    public List<Film> getByDirector(@PathVariable int directorId, @RequestParam(name = "sortBy") String sortGy) {
        log.info(GET_FILMS_BY_DIRECTOR.getMessage());
        return filmService.getByDirector(directorId, sortGy);
    }

    @DeleteMapping(value = "/{filmId}")
    public void deleteFilmById(@PathVariable int filmId) {
        log.info(DELETE_FILM.getMessage());
        filmService.deleteFilmById(filmId);
    }
}
