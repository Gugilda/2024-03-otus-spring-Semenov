package ru.otus.hw.repositories;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<Author> mapper = (rs, rowNum) -> new Author(rs.getLong("id"), rs.getString("full_name"));

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("SELECT * FROM authors", mapper);
    }

    @Override
    public Optional<Author> findById(long id) {
        SqlParameterSource paramSource = new MapSqlParameterSource("id", id);
        return jdbcTemplate.query("SELECT * FROM authors WHERE id = :id", paramSource, mapper).stream().findFirst();
    }

}
