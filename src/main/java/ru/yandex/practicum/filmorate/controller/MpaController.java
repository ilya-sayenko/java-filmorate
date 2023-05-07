package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.impl.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_ALL_MPA;
import static ru.yandex.practicum.filmorate.log.LogMessage.FIND_MPA_BY_ID;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> findAll() {
        log.info(FIND_ALL_MPA.getMessage());
        return mpaService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Mpa findById(@PathVariable int id) {
        log.info(FIND_MPA_BY_ID.getMessage());
        return mpaService.findById(id);
    }
}
