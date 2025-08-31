package io.ricardoandradem.board.persistence.dao;

import io.ricardoandradem.board.persistence.entity.BlockStatus;
import io.ricardoandradem.board.persistence.entity.BoardColumnEntity;
import io.ricardoandradem.board.persistence.entity.BoardColumnKind;
import io.ricardoandradem.board.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CardDAO {

    private final Connection connection;

    public CardDAO(Connection connection) {
        this.connection = connection;
    }

    public CardEntity insert(CardEntity card) throws SQLException {
        try (
            var statement = connection.prepareStatement(
                "INSERT INTO CARDS (title, description, board_column_id, is_blocked) " +
                        "VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            statement.setString(1,card.getTitle());
            statement.setString(2, card.getDescription());
            statement.setLong(3, card.getBoardColumn().getId());
            statement.setString(4, BlockStatus.unblocked.name());
            statement.executeUpdate();
            try (var keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    card.setId(keys.getLong(1));
                }
            }
        }
        return card;
    }

    public CardEntity update(CardEntity card) throws SQLException {
        try (
                var statement = connection.prepareStatement(
                        "UPDATE CARDS " +
                                "SET board_column_id = ?, is_blocked = ?, block_description = ? " +
                                "WHERE id = ?"
                )
        ) {
            statement.setLong(1, card.getBoardColumn().getId());
            statement.setString(2, card.getIsBlocked().name());
            statement.setString(3, card.getBlockDescription());
            statement.setLong(4, card.getId());
            statement.executeUpdate();
        }
        return card;
    }

    public ArrayList<CardEntity> findByBoardColumnId(long id) throws SQLException {
        ArrayList<CardEntity> cardEntityList = new ArrayList<>();
        try(
            var statement = connection.prepareStatement(
                    "SELECT id, title, description, board_column_id, is_blocked, block_description " +
                            "FROM CARDS " +
                            "WHERE board_column_id = ?"
                )
        ) {
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var card = new CardEntity();
                card.setId(resultSet.getLong("id"));
                card.setTitle(resultSet.getString("title"));
                card.setDescription(resultSet.getString("description"));
                var mockColumn = new BoardColumnEntity();
                mockColumn.setId(resultSet.getLong("board_column_id"));
                card.setBoardColumn(mockColumn);
                card.setIsBlocked(BlockStatus.findByName(resultSet.getString("is_blocked")));
                card.setBlockDescription(resultSet.getString("block_description"));
                cardEntityList.add(card);
            }
            return cardEntityList;
        }
    }
}
