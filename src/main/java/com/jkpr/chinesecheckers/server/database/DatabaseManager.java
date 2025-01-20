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
 * from varchar(10),
 * to varchar(10),
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

    public DatabaseManager(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    public List<String> getGames() {
        String sql = "SELECT gameId, start_time, type FROM games";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            int id = rs.getInt("gameId");
            Timestamp start = rs.getTimestamp("start_time");
            String type = rs.getString("type");
            return id + " " + start + " " + type;
        });
    }

    public String getMoves() {
        String sql = "SELECT `from`, `to` FROM moves WHERE gameId = ?";
        return jdbcTemplate.query(sql, new Object[]{gameId}, (rs, rowNum) -> {
            String from = rs.getString("from");
            String to = rs.getString("to");
            return from + " " + to;
        }).toString();
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
    public void recordMove(String from, String to) {
        String sql = "INSERT INTO moves (gameId, `from`, `to`, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, gameId);
            stmt.setString(2, from);
            stmt.setString(3, to);
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void endGame() {
        String sql = "UPDATE games SET end_time = ?, winner = ? WHERE gameId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, 1);
            stmt.setInt(3, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
