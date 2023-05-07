package ru.yandex.practicum.filmorate.storage.impl.db.converter;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.Mpa;

import java.util.Objects;
import java.util.function.Function;

public class FilmConverter implements Function<SqlRowSet, Film> {
    @Override
    public Film apply(SqlRowSet sqlRowSet) {
        return Film.filmBuilder()
                .id(sqlRowSet.getInt("film_id"))
                .name(sqlRowSet.getString("name"))
                .description(sqlRowSet.getString("description"))
                .releaseDate(Objects.requireNonNull(sqlRowSet.getDate("release_date")).toLocalDate())
                .duration(sqlRowSet.getInt("duration"))
                .mpa(new Mpa(sqlRowSet.getInt("mpa_mpa_id"), ""))
                .build();
    }
}
