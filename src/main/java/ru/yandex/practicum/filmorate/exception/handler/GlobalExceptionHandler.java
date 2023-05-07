package ru.yandex.practicum.filmorate.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.global.GlobalAppException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.exception.response.ErrorResponse;

import static ru.yandex.practicum.filmorate.log.LogMessage.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn(VALIDATION_ERROR.getMessage());
        throw new ValidationException(ex.getMessage());
    }

    @ExceptionHandler(GlobalAppException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleGlobalAppException(GlobalAppException ex) {
        String logMessage = ex.getLogMessage();
        log.warn(logMessage);
        return new ErrorResponse(logMessage, ex.getMessage());
    }
}
