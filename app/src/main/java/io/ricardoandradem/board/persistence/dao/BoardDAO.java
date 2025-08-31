package io.ricardoandradem.board.persistence.dao;

import io.ricardoandradem.board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BoardDAO {

    Connection connection;

    public BoardDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        var query = "DELETE INTO BOARDS (nome) VALUES (? )";
        try(var statement = connection.prepareStatement(query)) {
            statement.setString(1, entity.getName() );
            statement.executeUpdate();
        }
        return entity;
    }

    public void delete(Long id) throws SQLException {
        var query = "DELETE FROM BOARDS WHERE id = ?";
        try(var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        if (exists(id)){
            var query = "SELECT id, name FROM BOARDS WHERE id = ?";
            try(var statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                statement.executeQuery();
                var resultSet = statement.getResultSet();
                var board = new BoardEntity(
                        resultSet.getLong("id"),
                        resultSet.getNString("name")
                );
                return Optional.of(board);
            }
        }
        return Optional.empty();
    }

    public boolean exists(Long id) throws SQLException {
        var query = "SELECT 1 FROM BOARDS WHERE id = ?";
        try(var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeQuery();
            return statement.getResultSet().next();
        }
    }
}
