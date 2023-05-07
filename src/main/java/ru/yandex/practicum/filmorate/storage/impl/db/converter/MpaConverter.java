package ru.yandex.practicum.filmorate.storage.impl.db.converter;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.model.impl.Mpa;

import java.util.function.Function;

public class MpaConverter implements Function<SqlRowSet, Mpa> {
    @Override
    public Mpa apply(SqlRowSet sqlRowSet) {
        return new Mpa(sqlRowSet.getInt("mpa_id"), sqlRowSet.getString("name"));
    }
}
