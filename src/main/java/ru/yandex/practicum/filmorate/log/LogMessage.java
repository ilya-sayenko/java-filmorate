package ru.yandex.practicum.filmorate.log;

import org.springframework.util.StringUtils;

public enum LogMessage {
    CREATE_FILM,
    FILM_CREATED,
    UPDATE_FILM,
    FILM_UPDATED,
    CREATE_USER,
    USER_CREATED,
    UPDATE_USER,
    USER_UPDATED,
    FIND_ALL_FILMS,
    FIND_FILM_BY_ID,
    FIND_ALL_USERS,
    FIND_USER_BY_ID,
    FIND_ALL_GENRES,
    FIND_GENRE_BY_ID,
    FIND_ALL_MPA,
    FIND_MPA_BY_ID,
    ADD_LIKE,
    LIKE_ADDED,
    DELETE_LIKE,
    LIKE_DELETED,
    GET_POPULAR_FILM,
    ADD_FRIEND,
    FRIEND_ADDED,
    DELETE_FRIEND,
    FRIEND_DELETED,
    GET_FRIENDS,
    GET_COMMON_FRIENDS,
    GET_COMMON_FILMS,
    VALIDATION_ERROR,
    USER_NOT_FOUND,
    FILM_NOT_FOUND,
    APPLICATION_ERROR,
    MODEL_NOT_FOUND,
    MPA_NOT_FOUND,
    GENRE_NOT_FOUND;

    public String getMessage() {
        return StringUtils.capitalize(this.name().toLowerCase().replaceAll("_", " "));
    }
}
