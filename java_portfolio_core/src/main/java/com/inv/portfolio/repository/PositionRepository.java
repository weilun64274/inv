package com.inv.portfolio.repository;

import com.inv.portfolio.model.Position;
import com.inv.portfolio.util.SqlPath;
import com.inv.portfolio.util.SqlReader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部位 (Position) 專用的資料庫存取庫。
 * 使用 NamedParameterJdbcTemplate 來獲取比 JPA 更靈活的原生 SQL 撰寫與控制能力。
 */
@Repository
public class PositionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    
    private final String findAllSql = SqlReader.read(SqlPath.POSITION_FIND_ALL);
    private final String findBySymbolSql = SqlReader.read(SqlPath.POSITION_FIND_BY_SYMBOL);
    private final String insertSql = SqlReader.read(SqlPath.POSITION_INSERT);
    private final String updateSql = SqlReader.read(SqlPath.POSITION_UPDATE);

    /**
     * 預設建構子，注入 JDBC Template。
     *
     * @param jdbcTemplate 具備具名參數配置的 Spring JDBC Template
     */
    public PositionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 用於將查詢出的 ResultSet 對應為 Position 物件的通用 Mapping。
     */
    private final RowMapper<Position> rowMapper = (rs, rowNum) -> new Position(
        rs.getLong("id"),
        rs.getString("symbol"),
        rs.getBigDecimal("shares"),
        rs.getBigDecimal("average_cost"),
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getTimestamp("updated_at").toLocalDateTime()
    );

    /**
     * 讀取資料庫中所有的部位紀錄。
     *
     * @return 所有部位清單
     */
    public List<Position> findAll() {
        return jdbcTemplate.query(findAllSql, rowMapper);
    }

    /**
     * 讀取特定股票代號的所有持股部位。
     * 因為我們允許同一檔股票有不同成本的拆分部位，因此此方法會回傳一個陣列 (List)。
     *
     * @param symbol 欲查詢的股票代號
     * @return 該股票所有查到的部位清單
     */
    public List<Position> findBySymbol(String symbol) {
        return jdbcTemplate.query(findBySymbolSql,
                new MapSqlParameterSource("symbol", symbol), rowMapper);
    }

    /**
     * 新增一筆全新的部位資料紀錄至資料庫中。
     *
     * @param position 準備要寫入資料庫的部位物件
     * @return 新增完成後，由資料庫所核發的 Auto-increment ID
     */
    public Long insert(Position position) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("symbol", position.symbol())
            .addValue("shares", position.shares())
            .addValue("averageCost", position.averageCost());

        return jdbcTemplate.queryForObject(insertSql, params, Long.class);
    }

    /**
     * 更新一檔現有的特定部位 (基於 ID 查扣或新增股數，並更新均價)。
     *
     * @param position 帶有剛異動完之最新數據的部位物件
     */
    public void update(Position position) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("shares", position.shares())
            .addValue("averageCost", position.averageCost())
            .addValue("id", position.id());

        jdbcTemplate.update(updateSql, params);
    }
}
