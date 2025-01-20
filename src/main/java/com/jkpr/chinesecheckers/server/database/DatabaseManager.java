/* Struktura tabel */
/* games:
 * gameId int auto_increment primary key,
 * start_time timestamp not null,
 * type varchar(30),
 * end_time timestamp,
 * winner int,
 * numberOfPlayers int,
 * numberOfBots int,
 */
/* moves:
 * moveId int auto_increment primary key,
 * gameId int,
 * data varchar(50),
 * timestamp timestamp not null,
 * foreign key (gameId) references games(gameId)
 */

package com.jkpr.chinesecheckers.server.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;


@Repository
public class DatabaseManager {
    private final JdbcTemplate jdbcTemplate;
    private int gameId;

    @Autowired
    public DatabaseManager(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public List<String> getGamesFinished() {
        String sql = "SELECT gameId, numberOfPlayers, numberOfBots, start_time, type FROM games WHERE end_time IS NOT NULL";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("gameId");
            int players = rs.getInt("numberOfPlayers");
            int bots = rs.getInt("numberOfBots");
            Timestamp start = rs.getTimestamp("start_time");
            String type = rs.getString("type");
            return id + " " + players + " " + bots + " " + start + " " + type;
        });
    }
    public List<String> getGamesInProgress() {
        String sql = "SELECT gameId, numberOfPlayers, numberOfBots, start_time, type FROM games WHERE end_time IS NULL";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("gameId");
            int players = rs.getInt("numberOfPlayers");
            int bots = rs.getInt("numberOfBots");
            Timestamp start = rs.getTimestamp("start_time");
            String type = rs.getString("type");
            return id + " " + players + " " + bots + " " + start + " " + type;
        });
    }

    public List<String> getMoves() {
        String sql = "SELECT data FROM moves WHERE gameId = ?";
        return jdbcTemplate.query(sql, new Object[]{gameId}, (rs, rowNum) -> {
            return rs.getString("data");
        });
    }

    public void createGame(String type, int numberOfPlayers, int numberOfBots) {
        String sql = "INSERT INTO games (start_time, type, numberOfPlayers, numberOfBots) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setString(2, type);
            stmt.setInt(3, numberOfPlayers);
            stmt.setInt(4, numberOfBots);
            return stmt;
        }, keyHolder);
        gameId = Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
    public void recordMove(String data) {
        String sql = "INSERT INTO moves (gameId, data, timestamp) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                            gameId,
                            data,
                            new Timestamp(System.currentTimeMillis()));
    }
    public void endGame(int winner) {
        String sql = "UPDATE games SET end_time = ?, winner = ? WHERE gameId = ?";
        jdbcTemplate.update(sql,
                            new Timestamp(System.currentTimeMillis()),
                            winner,
                            gameId);
    }
    public int countGamesFinished() {
        String sql = "SELECT COUNT(*) FROM games WHERE end_time IS NOT NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    public int countGamesInProgress() {
        String sql = "SELECT COUNT(*) FROM games WHERE end_time IS NULL";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
