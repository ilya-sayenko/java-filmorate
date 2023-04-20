package ru.yandex.practicum.filmorate.exception;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String message) {
        super(message);
    }
}
