package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    UserService userService;

    @BeforeEach
    public void installations() {
        userService = new UserService();
    }

    @Test
    public void shouldCreateUser() {
        User user = User.builder()
                .id(1)
                .email("example@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        userService.create(user);

        assertEquals(1, userService.findAll().size());
        assertEquals(user, userService.findAll().get(0));
    }

    @Test
    public void shouldUpdateUser() {
        User user = User.builder()
                .id(1)
                .email("example@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        userService.create(user);

        User userUpd = User.builder()
                .id(1)
                .email("example@mail.ru")
                .login("new_login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        userService.update(userUpd);

        assertEquals(userUpd, userService.findAll().get(0));
    }

    @Test
    public void shouldNotUpdateUser() {
        User user = User.builder()
                .id(1)
                .email("example@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();


        assertThrows(ValidationException.class, () -> userService.update(user));
        assertTrue(userService.findAll().isEmpty());
    }
}
