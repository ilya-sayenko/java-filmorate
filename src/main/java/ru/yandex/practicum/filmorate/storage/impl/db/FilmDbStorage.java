package ru.yandex.practicum.filmorate.storage.impl.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.impl.Film;
import ru.yandex.practicum.filmorate.model.impl.Genre;
import ru.yandex.practicum.filmorate.model.impl.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.FilmConverter;
import ru.yandex.practicum.filmorate.storage.impl.db.converter.GenreConverter;

@Component
@Primary
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MpaDbStorage mpaDbStorage;

    @Override
    public List<Film> findAll() {
        String sql = "select " +
                " f.*, " +
                " m.name mpa_name, " +
                " g.genre_id, " +
                " g.name genre_name, " +
                " from films f " +
                " join mpa m " +
                "   on m.mpa_id = f.mpa_mpa_id " +
                " left join films_genres fg " +
                "        on fg.film_film_id = f.film_id " +
                " left join genres g" +
                "        on g.genre_id = fg.genre_genre_id " +
                " order by f.film_id, g.genre_id";

        return jdbcTemplate.query(sql, FilmConverter::listFromResultSet);
    }

    @Override
    public Optional<Film> findById(int id) {
        return findByIds(List.of(id)).stream().findFirst();
    }

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        Map<String, Object> values = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "mpa_mpa_id", film.getMpa().getId()
        );

        int id = simpleJdbcInsert.executeAndReturnKey(values).intValue();
        film.setId(id);
        insertFilmsGenresIntoDb(film);
        setGenresForInstance(film);
        setMpaForInstance(film);

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
        insertFilmsGenresIntoDb(film);
        setGenresForInstance(film);

        return film;
    }

    @Override
    public void addLike(Film film, User user) {
        jdbcTemplate.update("insert into likes values(?, ?)", film.getId(), user.getId());
    }

    @Override
    public void deleteLike(Film film, User user) {
        jdbcTemplate.update("delete from likes where film_film_id = ? and user_user_id = ?", film.getId(),
                user.getId());
    }

    @Override
    public List<Film> findPopular(int count) {
        String sql = "select " +
                " f.*, " +
                " m.name mpa_name, " +
                " g.genre_id, " +
                " g.name genre_name " +
                " from films f " +
                " left join (" +
                "     select " +
                "     l.film_film_id, " +
                "     count(l.user_user_id) cnt_likes " +
                "     from likes l " +
                "     group by l.film_film_id" +
                "     order by cnt_likes desc" +
                "     limit ?" +
                " ) fl" +
                "   on fl.film_film_id = f.film_id " +
                " join mpa m" +
                "   on m.mpa_id = f.mpa_mpa_id " +
                " left join films_genres fg " +
                "        on fg.film_film_id = f.film_id " +
                " left join genres g " +
                "       on g.genre_id  = fg.genre_genre_id " +
                " order by coalesce(fl.cnt_likes, 0) desc, f.film_id, g.genre_id" +
                " limit ?";

        return jdbcTemplate.query(sql, FilmConverter::listFromResultSet, count, count);
    }

    public int getNumberOfLikes(int id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("select count(*) cnt from likes where film_film_id = ?",
                        (rs, rn) -> rs.getInt("cnt"),
                        id)
        ).orElse(0);
    }

    public boolean isFilmExists(int filmId) {
        Integer filmsCount = jdbcTemplate.queryForObject(
                "select count(*) cnt from films where film_id = ?",
                Integer.class,
                filmId
        );
        return filmsCount != null && filmsCount == 1;
    }

    @Override
    public List<Film> getCommon(int userId, int friendId) {
        String sql = "select * , m.name mpa_name, g.name genre_name " +
                "from " +
                "    (" +
                "        select film_film_id, count(user_user_id) cnt_likes " +
                "        from likes " +
                "        where film_film_id in ( " +
                "            select film_film_id " +
                "            from likes " +
                "            where user_user_id = ? " +
                "            INTERSECT " +
                "            select film_film_id " +
                "            from likes where user_user_id = ?) " +
                "        group by film_film_id " +
                "        order by cnt_likes) as l " +
                "left join films f on l.film_film_id = f.film_id " +
                "left join  mpa m on m.mpa_id = f.mpa_mpa_id " +
                "left join films_genres fg on fg.film_film_id = f.film_id " +
                "left join genres g on g.genre_id  = fg.genre_genre_id ";
        return jdbcTemplate.query(sql, FilmConverter::listFromResultSet, userId, friendId);
    }

    public List<Film> findByIds(List<Integer> ids) {
        String sql = "select " +
                " f.*, " +
                " m.name mpa_name, " +
                " g.genre_id, " +
                " g.name genre_name, " +
                " from films f " +
                " join mpa m " +
                "   on m.mpa_id = f.mpa_mpa_id " +
                " left join films_genres fg " +
                "        on fg.film_film_id = f.film_id " +
                " left join genres g" +
                "        on g.genre_id = fg.genre_genre_id " +
                " where f.film_id in (:ids) " +
                " order by f.film_id, g.genre_id";

        return namedParameterJdbcTemplate.query(sql, Map.of("ids", ids), FilmConverter::listFromResultSet);
    }

    private void setGenresForInstance(Film film) {
        List<Genre> genresList = jdbcTemplate.query("select " +
                        " g.* " +
                        " from films_genres fg " +
                        " join genres g " +
                        "   on g.genre_id = fg.genre_genre_id " +
                        " where fg.film_film_id = ? " +
                        " order by g.genre_id",
                (rs, rn) -> GenreConverter.fromResultSet(rs),
                film.getId());

        film.setGenres(new HashSet<>(genresList));
    }

    private void setMpaForInstance(Film film) {
        mpaDbStorage.findById(film.getMpa().getId()).ifPresent(film::setMpa);
    }

    private void insertFilmsGenresIntoDb(Film film) {
        if (film.getGenres() != null) {
            List<Integer> genreIds = film.getGenres().stream().map(Genre::getId).collect(Collectors.toList());

            jdbcTemplate.batchUpdate("insert into films_genres values(?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, film.getId());
                    ps.setInt(2, genreIds.get(i));
                }

                @Override
                public int getBatchSize() {
                    return genreIds.size();
                }
            });
        }
    }
}
