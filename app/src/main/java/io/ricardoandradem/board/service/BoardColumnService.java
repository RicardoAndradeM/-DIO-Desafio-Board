package io.ricardoandradem.board.service;

import io.ricardoandradem.board.persistence.dao.BoardColumnDAO;
import io.ricardoandradem.board.persistence.entity.BoardColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardColumnService {

    private final Connection connection;
    private final BoardColumnDAO boardColumnDAO;

    public BoardColumnService(Connection connection) {
        this.connection = connection;
        this.boardColumnDAO = new BoardColumnDAO(connection);
    }

    public BoardColumnEntity insert(BoardColumnEntity boardColumn) throws SQLException {
        BoardColumnEntity result;
        try {
            result = boardColumnDAO.insert(boardColumn);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return result;
    }

    public ArrayList<BoardColumnEntity> findByBoardId(Long id) {
        return boardColumnDAO.findByBoardId(id);
    }
}
