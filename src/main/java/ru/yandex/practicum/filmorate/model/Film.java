package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.time.DurationMin;
import ru.yandex.practicum.filmorate.validator.FilmReleaseDate;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @FilmReleaseDate
    private LocalDate releaseDate;
    @DurationMin(nanos = 1)
    private Duration duration;
}
