package ru.yandex.practicum.filmorate.log;

public enum LogMessage {
    CREATE_FILM("create film"),
    FILM_CREATED("film created"),
    UPDATE_FILM("update film"),
    FILM_UPDATED("film updated"),
    CREATE_USER("create user"),
    USER_CREATED("user created"),
    UPDATE_USER("update user"),
    USER_UPDATED("user updated"),
    FIND_ALL_FILMS("find all films"),
    FIND_ALL_USERS("find all users"),
    VALIDATION_ERROR("validation error");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
