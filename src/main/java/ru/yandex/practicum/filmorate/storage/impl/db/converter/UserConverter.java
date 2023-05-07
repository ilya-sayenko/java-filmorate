package ru.yandex.practicum.filmorate.storage.impl.db.converter;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.impl.User;

import java.util.Objects;
import java.util.function.Function;

public class UserConverter implements Function<SqlRowSet, User> {
    @Override
    public User apply(SqlRowSet sqlRowSet) {
        return User.userBuilder()
                .id(sqlRowSet.getInt("user_id"))
                .email(sqlRowSet.getString("email"))
                .login(sqlRowSet.getString("login"))
                .name(sqlRowSet.getString("name"))
                .birthday(Objects.requireNonNull(sqlRowSet.getDate("birthday")).toLocalDate())
                .build();
    }
}
