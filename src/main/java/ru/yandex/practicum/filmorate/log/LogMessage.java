package ru.yandex.practicum.filmorate.log;

import org.springframework.util.StringUtils;

public enum LogMessage {
    CREATE_FILM,
    FILM_IS_CREATED,
    UPDATE_FILM,
    FILM_IS_UPDATED,
    CREATE_USER,
    USER_IS_CREATED,
    UPDATE_USER,
    USER_IS_UPDATED,
    FIND_ALL_FILMS,
    FIND_FILM_BY_ID,
    FIND_ALL_USERS,
    FIND_USER_BY_ID,
    FIND_ALL_GENRES,
    FIND_GENRE_BY_ID,
    FIND_ALL_MPA,
    FIND_MPA_BY_ID,
    ADD_LIKE,
    LIKE_IS_ADDED,
    DELETE_LIKE,
    LIKE_IS_DELETED,
    GET_POPULAR_FILM,
    ADD_FRIEND,
    FRIEND_IS_ADDED,
    DELETE_FRIEND,
    FRIEND_IS_DELETED,
    GET_FRIENDS,
    GET_COMMON_FRIENDS,
    GET_RECOMMENDATIONS,
    VALIDATION_ERROR,
    USER_NOT_FOUND,
    FILM_NOT_FOUND,
    APPLICATION_ERROR,
    MODEL_NOT_FOUND,
    MPA_NOT_FOUND,
    GENRE_NOT_FOUND,
    FIND_REVIEW_BY_ID,
    FIND_REVIEW_BY_FILM_ID,
    CREATE_REVIEW,
    REVIEW_IS_CREATED,
    UPDATE_REVIEW,
    REVIEW_IS_UPDATED,
    ADD_LIKE_FOR_REVIEW,
    LIKE_FOR_REVIEW_IS_ADDED,
    ADD_DISLIKE_FOR_REVIEW,
    DISLIKE_FOR_REVIEW_IS_ADDED,
    DELETE_REVIEW,
    REVIEW_IS_DELETED,
    DELETE_LIKE_FOR_REVIEW,
    LIKE_FOR_REVIEW_IS_DELETED,
    DELETE_DISLIKE_FOR_REVIEW,
    DISLIKE_FOR_REVIEW_IS_DELETED,
    GET_COMMON_FILMS,
    GET_EVENTS_BY_USER,
    DELETE_FILM,
    FILM_IS_DELETED,
    DELETE_USER,
    USER_IS_DELETED;

    public String getMessage() {
        return StringUtils.capitalize(this.name().toLowerCase().replaceAll("_", " "));
    }
}
