package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class MpaNotFoundException extends ModelNotFoundException {

    public MpaNotFoundException(int id) {
        super("Mpa id=" + id + " not found", LogMessage.MPA_NOT_FOUND.getMessage());
    }
}
