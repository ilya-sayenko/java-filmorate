package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class FilmNotFoundException extends ModelNotFoundException {

    public FilmNotFoundException(int id) {
        super("Film id=" + id + " not found", LogMessage.FILM_NOT_FOUND.getMessage());
    }
}
