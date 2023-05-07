package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.UserConverter;

import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        UserConverter userConverter = new UserConverter();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users order by user_id");
        while (userRows.next()) {
            User user = userConverter.apply(userRows);
            setFriendsForInstance(user);
            users.add(user);
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);
        if (userRow.next()) {
            User user = new UserConverter().apply(userRow);
            setFriendsForInstance(user);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        int id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        user.setId(id);

        return user;
    }

    @Override
    public User update(User user) {
        String sqlQuery = "update users set " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?";

        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        user.addFriend(friend);
        jdbcTemplate.update("insert into friends values(?, ?, 2)", user.getId(), friend.getId());
    }

    @Override
    public void deleteFriend(User user, User friend) {
        user.deleteFriend(friend);
        jdbcTemplate.update("delete from friends " +
                        " where first_user_id = ? " +
                        "   and second_user_id = ? ",
                user.getId(), friend.getId());
    }

    private void setFriendsForInstance(User user) {
        Set<User> friends = new HashSet<>();
        SqlRowSet friendRow = jdbcTemplate.queryForRowSet("select f.second_user_id " +
                        " from friends f " +
                        " where f.first_user_id = ? and f.frstat_frstat_id = 2",
                user.getId());
        while (friendRow.next()) {
            Optional<User> userOpt = findById(friendRow.getInt("second_user_id"));
            userOpt.ifPresent(friends::add);
        }
        user.setFriends(friends);
    }
}
