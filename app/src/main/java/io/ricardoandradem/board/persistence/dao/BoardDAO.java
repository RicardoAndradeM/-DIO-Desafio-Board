package io.ricardoandradem.board.persistence.dao;

import io.ricardoandradem.board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class BoardDAO {

    private final Connection connection;

    public BoardDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(BoardEntity board) throws SQLException {
        try(
            var statement = connection.prepareStatement(
                    "INSERT INTO BOARDS (name) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, board.getName() );
            statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    board.setId(keys.getLong(1));
                }
            }
        }
        return board;
    }

    public void delete(Long id) throws SQLException {
        try(
            var statement = connection
                    .prepareStatement("DELETE FROM BOARDS WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        if (exists(id)){
            try(
                var statement = connection
                        .prepareStatement("SELECT id, name FROM BOARDS WHERE id = ?")
            ) {
                statement.setLong(1, id);
                statement.executeQuery();
                var resultSet = statement.getResultSet();
                var board = new BoardEntity();
                if(resultSet.next()){
                    board.setId(resultSet.getLong("id"));
                    board.setName(resultSet.getString("name"));
                }
                return Optional.of(board);
            }
        }
        return Optional.empty();
    }

    public boolean exists(Long id) throws SQLException {
        try(
            var statement = connection
                    .prepareStatement("SELECT 1 FROM BOARDS WHERE id = ?")
        ) {
            statement.setLong(1, id);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }
}
