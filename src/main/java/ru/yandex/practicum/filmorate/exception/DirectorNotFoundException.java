package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class DirectorNotFoundException extends ModelNotFoundException {
    public DirectorNotFoundException(int id) {
        super("Director id = " + id + " not found", LogMessage.DIRECTOR_NOT_FOUND.getMessage());
    }
}
