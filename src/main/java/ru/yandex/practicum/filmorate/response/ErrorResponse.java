package ru.yandex.practicum.filmorate.response;

import lombok.Value;

@Value
public class ErrorResponse {
    String error;
    String description;
}
