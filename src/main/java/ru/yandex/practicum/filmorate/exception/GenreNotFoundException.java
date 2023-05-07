package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class GenreNotFoundException extends ModelNotFoundException {

    public GenreNotFoundException(int id) {
        super("Genre id=" + id + " not found", LogMessage.GENRE_NOT_FOUND.getMessage());
    }
}
