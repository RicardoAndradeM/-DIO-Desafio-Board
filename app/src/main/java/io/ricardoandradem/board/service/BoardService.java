package io.ricardoandradem.board.service;

import io.ricardoandradem.board.persistence.dao.BoardDAO;
import io.ricardoandradem.board.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BoardService {

    private final Connection connection;
    private final BoardDAO boardDAO;
    private final BoardColumnService boardColumnService;

    public BoardService(Connection connection) {
        this.connection = connection;
        this.boardDAO = new BoardDAO(connection);
        this.boardColumnService = new BoardColumnService(connection);
    }

    public void delete(Long id) throws SQLException {
        try {
            boardDAO.delete(id);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public BoardEntity insert(BoardEntity board) throws SQLException {
        BoardEntity result;
        try {
            result = boardDAO.insert(board);
            for (var boardColumn : result.getBoardColumnList()) {
                boardColumn.setBoard(board);
                boardColumnService.insert(boardColumn);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return result;
    }

    public Optional<BoardEntity> findById(Long id) throws SQLException {
        var result = boardDAO.findById(id);
        result.ifPresent(board -> {
            try {
                board.setBoardColumnList(boardColumnService.findByBoardId(board.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }
}
