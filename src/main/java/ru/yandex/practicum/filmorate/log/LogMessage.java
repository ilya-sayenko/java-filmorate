package ru.yandex.practicum.filmorate.log;

public enum LogMessage {
    FILM_CREATED("film created"),
    FILM_UPDATED("film updated"),
    USER_CREATED("user created"),
    USER_UPDATED("user updated"),
    FIND_ALL_FILMS("find all films"),
    FIND_ALL_USERS("find all users");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
