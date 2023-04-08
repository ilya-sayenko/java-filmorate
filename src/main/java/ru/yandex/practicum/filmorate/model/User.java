package ru.yandex.practicum.filmorate.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    @Positive
    private int id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @Nullable
    private String name;
    @Past
    private LocalDate birthday;
}
