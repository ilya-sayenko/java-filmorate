package ru.yandex.practicum.filmorate.exception.global;

import ru.yandex.practicum.filmorate.log.LogMessage;

public class GlobalAppException extends RuntimeException {
    private String logMessage;

    public GlobalAppException() {
        super();
    }

    public GlobalAppException(String message) {
        super(message);
        this.logMessage = LogMessage.APPLICATION_ERROR.getMessage();
    }

    public GlobalAppException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }
}
