package io.ricardoandradem.board.service;

import io.ricardoandradem.board.persistence.dao.BoardDAO;
import io.ricardoandradem.board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardService {

    private final Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public void delete(Long id) throws SQLException {
        var boardDAO = new BoardDAO(connection);
        try {
            boardDAO.delete(id);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public BoardEntity insert(BoardEntity entity) throws SQLException {
        var boardDAO = new BoardDAO(connection);
        BoardEntity result;
        try {
            result = boardDAO.insert(entity);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return result;
    }
}
