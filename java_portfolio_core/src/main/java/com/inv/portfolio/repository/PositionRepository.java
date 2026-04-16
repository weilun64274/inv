package com.inv.portfolio.repository;

import com.inv.portfolio.model.Position;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PositionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PositionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Position> rowMapper = (rs, rowNum) -> new Position(
        rs.getLong("id"),
        rs.getString("symbol"),
        rs.getBigDecimal("shares"),
        rs.getBigDecimal("average_cost"),
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getTimestamp("updated_at").toLocalDateTime()
    );

    public List<Position> findAll() {
        return jdbcTemplate.query("SELECT * FROM position", rowMapper);
    }
}
