package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ru.yandex.practicum.filmorate.log.LogMessage.VALIDATION_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleValidationException(MethodArgumentNotValidException ex) {
        log.error(VALIDATION_ERROR.getMessage());
        throw new ValidationException(ex.getMessage());
    }
}
