package ru.yandex.practicum.filmorate.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogMessageTest {
    @Test
    public void shouldGetCorrectMessage() {
        assertEquals("User is created", LogMessage.USER_IS_CREATED.getMessage());
        assertEquals("Find all films", LogMessage.FIND_ALL_FILMS.getMessage());
    }
}
