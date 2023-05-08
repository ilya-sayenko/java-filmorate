package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.impl.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql({"/test-data.sql"})
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;

    @Test
    public void shouldCreate() {
        User user = User.userBuilder()
                .email("new@email.com")
                .login("newLogin")
                .name("newName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        assertThat(userDbStorage.create(user)).hasFieldOrPropertyWithValue("id", 3);
    }

    @Test
    public void shouldUpdate() {
        User user = User.userBuilder()
                .id(1)
                .email("new@email.com")
                .login("newLogin")
                .name("newName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        assertThat(userDbStorage.update(user)).hasFieldOrPropertyWithValue("name", "newName");
    }

    @Test
    public void shouldFindAll() {
        List<User> userList = userDbStorage.findAll();

        assertThat(userList).isNotEmpty();
        assertThat(userList).hasSize(2);
    }

    @Test
    public void shouldFindById() {
        Optional<User> userOptional = userDbStorage.findById(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(mpa ->
                        assertThat(mpa).hasFieldOrPropertyWithValue("id", 1));
    }

    @Test
    public void shouldAddFriend() {
        User user = userDbStorage.findById(2).orElseThrow(() -> new UserNotFoundException(2));
        User friend = userDbStorage.findById(1).orElseThrow(() -> new UserNotFoundException(1));

        userDbStorage.addFriend(user, friend);

        assertThat(user.getFriends()).isNotEmpty();
    }

    @Test
    public void shouldDeleteFriend() {
        User user = userDbStorage.findById(1).orElseThrow(() -> new UserNotFoundException(1));
        User friend = userDbStorage.findById(2).orElseThrow(() -> new UserNotFoundException(2));

        userDbStorage.deleteFriend(user, friend);

        assertThat(user.getFriends()).isEmpty();
    }
}