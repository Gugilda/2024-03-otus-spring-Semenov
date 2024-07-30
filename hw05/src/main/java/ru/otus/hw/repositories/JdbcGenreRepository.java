package ru.otus.hw.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcGenreRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select * from genres", new GnreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Genre> genres;
        genres = jdbc.query("select * from genres where id = :id", params, new GnreRowMapper());
        return Optional.of(genres.get(0));
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}