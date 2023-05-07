package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.Genre;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.FilmConverter;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.GenreConverter;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.MpaConverter;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.UserConverter;

import java.util.*;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmConverter filmConverter = new FilmConverter();
    private final GenreConverter genreConverter = new GenreConverter();
    private final UserConverter userConverter = new UserConverter();
    private final MpaConverter mpaConverter = new MpaConverter();


    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();

        SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select * from films order by film_id");
        while (filmRow.next()) {
            Film film = filmConverter.apply(filmRow);
            setGenresForInstance(film);
            setLikesForInstance(film);
            setMpaForInstance(film);
            films.add(film);
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", id);
        if (filmRow.next()) {
            Film film = filmConverter.apply(filmRow);
            setGenresForInstance(film);
            setLikesForInstance(film);
            setMpaForInstance(film);
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        int id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        film.setId(id);
        insertGenres(film);

        return film;
    }

    @Override
    public Film update(Film film) {
        String updateQuery = "update films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, mpa_mpa_id = ? " +
                "where film_id = ?";

        jdbcTemplate.update(updateQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        jdbcTemplate.update("delete from films_genres where film_film_id = ?", film.getId());
        insertGenres(film);
        setGenresForInstance(film);

        return film;
    }

    private void insertGenres(Film film) {
        if (film.getGenres() != null) {
            film.getGenres().forEach(g ->
                    jdbcTemplate.update("insert into films_genres values(?, ?)", film.getId(), g.getId())
            );
        }
    }

    @Override
    public void addLike(Film film, User user) {
        film.addLike(user);
        jdbcTemplate.update("insert into likes values(?, ?)", film.getId(), user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        film.deleteLike(user);
        jdbcTemplate.update("delete from likes where film_film_id = ? and user_user_id = ?", film.getId(), user.getId());
    }

    private void setGenresForInstance(Film film) {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select g.* from films_genres fg " +
                " join genres g " +
                "   on g.genre_id = fg.genre_genre_id " +
                " where fg.film_film_id = ? " +
                " order by g.genre_id", film.getId());
        while (genreRows.next()) {
            genres.add(genreConverter.apply(genreRows));
        }
        film.setGenres(genres);
    }

    private void setLikesForInstance(Film film) {
        Set<User> users = new HashSet<>();
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("select u.* from likes l " +
                " join users u " +
                "   on u.user_id = l.user_user_id " +
                " where l.film_film_id = ?", film.getId());
        while (userRow.next()) {
            users.add(userConverter.apply(userRow));
        }
        film.setLikes(users);
    }

    private void setMpaForInstance(Film film) {
        SqlRowSet mpaRow = jdbcTemplate.queryForRowSet("select * from mpa where mpa_id = ?", film.getMpa().getId());
        mpaRow.next();
        film.setMpa(mpaConverter.apply(mpaRow));
    }
}
