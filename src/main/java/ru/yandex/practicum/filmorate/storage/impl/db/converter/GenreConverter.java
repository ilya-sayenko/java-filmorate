package ru.yandex.practicum.filmorate.storage.impl.db.converter;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.impl.Genre;

import java.util.function.Function;

public class GenreConverter implements Function<SqlRowSet, Genre> {
    @Override
    public Genre apply(SqlRowSet sqlRowSet) {
        return new Genre(sqlRowSet.getInt("genre_id"), sqlRowSet.getString("name"));
    }
}
