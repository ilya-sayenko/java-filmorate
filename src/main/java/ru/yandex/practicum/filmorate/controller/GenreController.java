package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.impl.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_ALL_GENRES;
import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_GENRE_BY_ID;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<Genre> findAll() {
        log.info(FIND_ALL_GENRES.getMessage());
        return genreService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Genre findById(@PathVariable int id) {
        log.info(FIND_GENRE_BY_ID.getMessage());
        return genreService.findById(id);
    }
}
