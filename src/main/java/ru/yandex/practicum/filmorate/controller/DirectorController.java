package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.impl.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import javax.validation.Valid;
import java.util.Collection;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@Slf4j
@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping
    public Collection<Director> getDirectors() {
        log.info(FIND_ALL_DIRECTORS.getMessage());
        return directorService.findAll();
    }

    @GetMapping("/{id}")
    public Director getDirector(@PathVariable("id") Integer directorId) {
        log.info(FIND_DIRECTOR_BY_ID.getMessage());
        return directorService.findById(directorId);
    }

    @PostMapping
    public Director addDirector(@Valid @RequestBody Director director) {
        log.info(CREATE_DIRECTOR.getMessage());
        return directorService.create(director);
    }

    @PutMapping
    public Director updateDirector(@Valid @RequestBody Director director) {
        log.info(UPDATE_DIRECTOR.getMessage());
        return directorService.update(director);
    }

    @DeleteMapping("/{id}")
    public void removeDirector(@PathVariable("id") Integer directorId) {
        log.info(DELETE_DIRECTOR.getMessage());
        directorService.removeDirector(directorId);
    }
}