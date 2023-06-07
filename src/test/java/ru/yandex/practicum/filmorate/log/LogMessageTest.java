package ru.yandex.practicum.filmorate.log;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogMessageTest {
    @Test
    public void shouldGetCorrectMessage() {
        assertEquals("User created", LogMessage.USER_CREATED.getMessage());
        assertEquals("Find all films", LogMessage.FIND_ALL_FILMS.getMessage());
    }
}
