package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmServiceTest {
    FilmService filmService;

    @BeforeEach
    public void installations() {
        filmService = new FilmService();
    }

    @Test
    public void shouldCreateFilm() {
        Film film = Film.builder()
                .id(1)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(Duration.ofMinutes(50))
                .build();

        filmService.create(film);

        assertEquals(1, filmService.findAll().size());
        assertEquals(film, filmService.findAll().get(0));
    }

    @Test
    public void shouldUpdateFilm() {
        Film film = Film.builder()
                .id(1)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(Duration.ofMinutes(50))
                .build();

        filmService.create(film);

        Film filmUpd = Film.builder()
                .id(1)
                .name("Film")
                .description("New description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(Duration.ofMinutes(50))
                .build();

        filmService.update(filmUpd);

        assertEquals(filmUpd, filmService.findAll().get(0));
    }

    @Test
    public void shouldNotUpdateFilm() {
        Film film = Film.builder()
                .id(1)
                .name("Film")
                .description("Description")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(Duration.ofMinutes(50))
                .build();


        assertThrows(ValidationException.class, () -> filmService.update(film));
        assertTrue(filmService.findAll().isEmpty());
    }
}
