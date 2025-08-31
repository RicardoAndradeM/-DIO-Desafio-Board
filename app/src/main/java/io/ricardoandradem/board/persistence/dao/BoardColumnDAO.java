package io.ricardoandradem.board.persistence.dao;

import io.ricardoandradem.board.persistence.entity.BoardColumnEntity;
import io.ricardoandradem.board.persistence.entity.BoardColumnKind;
import io.ricardoandradem.board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class BoardColumnDAO {

    private final Connection connection;

    public BoardColumnDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardColumnEntity insert(BoardColumnEntity boardColumn) throws SQLException {
        try (
            var statement = connection.prepareStatement(
                    "INSERT INTO BOARDS (nome, order_column, kind, board_id) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1, boardColumn.getName());
            statement.setInt(2, boardColumn.getOrgerColmun());
            statement.setString(3, boardColumn.getKind().name());
            statement.setLong(4, boardColumn.getBoard().getId());
            statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    boardColumn.setId(keys.getLong(1));
                }
            }
        }
        return boardColumn;
    }

    public ArrayList<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        ArrayList<BoardColumnEntity> boardColumnEntities = new ArrayList<>();
        try(
            var statement = connection.prepareStatement(
                    "SELECT id, name, order_column, kind " +
                            "FROM BOARD_COLUMNS " +
                            "WHERE board_id = ? " +
                            "ORDER BY order_column"
            )
        ) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var boardColumn = new BoardColumnEntity();
                boardColumn.setId(resultSet.getLong("id"));
                boardColumn.setName(resultSet.getString("name"));
                boardColumn.setOrgerColmun(resultSet.getInt("order_column"));
                boardColumn.setKind(BoardColumnKind.findByName(resultSet.getString("kind")));
                boardColumnEntities.add(boardColumn);
            }
            return boardColumnEntities;
        }
    }
}
