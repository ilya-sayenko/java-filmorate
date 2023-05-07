package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.GenreConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();
        GenreConverter genreConverter = new GenreConverter();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres order by genre_id");
        while (genreRows.next()) {
            genres.add(genreConverter.apply(genreRows));
        }
        return genres;
    }

    @Override
    public Optional<Genre> findById(int id) {
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet("select * from genres where genre_id = ?", id);
        if (genreRow.next()) {
            return Optional.of(new GenreConverter().apply(genreRow));
        } else {
            return Optional.empty();
        }
    }
}
