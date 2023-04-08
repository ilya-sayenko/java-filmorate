package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    @Positive
    private int id;
    @NonNull
    private String name;
    @Size(max = 200)
    private String description;
    // TODO: validation releaseDate
    private LocalDate releaseDate;
    @Positive
    private double duration;
}
