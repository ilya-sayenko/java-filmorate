package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.impl.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.MpaConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaConverter mpaConverter = new MpaConverter();

    @Override
    public List<Mpa> findAll() {
        List<Mpa> mpaList = new ArrayList<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from mpa order by mpa_id");
        while (genreRows.next()) {
            mpaList.add(mpaConverter.apply(genreRows));
        }
        return mpaList;
    }

    @Override
    public Optional<Mpa> findById(int id) {
        SqlRowSet genreRow = jdbcTemplate.queryForRowSet("select * from mpa where mpa_id = ?", id);
        if (genreRow.next()) {
            return Optional.of(mpaConverter.apply(genreRow));
        } else {
            return Optional.empty();
        }
    }
}
