package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class UserNotFoundException extends ModelNotFoundException {

    public UserNotFoundException(int id) {
        super("User id=" + id + " not found", LogMessage.USER_NOT_FOUND.getMessage());
    }
}
