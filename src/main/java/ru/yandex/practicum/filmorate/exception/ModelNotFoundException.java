package ru.yandex.practicum.filmorate.exception;

import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exception.global.GlobalAppException;
import ru.yandex.practicum.filmorate.log.LogMessage;

public class ModelNotFoundException extends GlobalAppException {

    public ModelNotFoundException() {
        super();
    }

    public ModelNotFoundException(String message, String logMessage) {
        super(message, logMessage);
    }

    public ModelNotFoundException(int id) {
        super("Model id=" + id + " not found", LogMessage.MODEL_NOT_FOUND.getMessage());
    }

    public ModelNotFoundException(int id, String name) {
        super(StringUtils.capitalize(name) + " id=" + id + " not found", LogMessage.MODEL_NOT_FOUND.getMessage());
    }
}
