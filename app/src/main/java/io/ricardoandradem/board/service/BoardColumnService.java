package io.ricardoandradem.board.service;

import io.ricardoandradem.board.persistence.dao.BoardColumnDAO;
import io.ricardoandradem.board.persistence.entity.BoardColumnEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardColumnService {

    private final Connection connection;
    private final BoardColumnDAO boardColumnDAO;
    private final CardService cardService;

    public BoardColumnService(Connection connection) {
        this.connection = connection;
        this.boardColumnDAO = new BoardColumnDAO(connection);
        this.cardService = new CardService(connection);
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

    public ArrayList<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        var boardList = boardColumnDAO.findByBoardId(id);
        boardList.forEach( boardColumn ->
                {
                    try {
                        boardColumn.setCardList(cardService.findByBoardColumnId(boardColumn.getId()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return boardList;
    }
}
