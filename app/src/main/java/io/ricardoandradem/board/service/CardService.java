package io.ricardoandradem.board.service;

import io.ricardoandradem.board.persistence.dao.CardDAO;
import io.ricardoandradem.board.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardService {

    Connection connection;
    CardDAO cardDAO;

    public CardService(Connection connection) {
        this.connection = connection;
        cardDAO = new CardDAO(connection);
    }

    public CardEntity insert(CardEntity card) throws SQLException {
        CardEntity result;
        try {
            result = cardDAO.insert(card);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return result;
    }

    public CardEntity update(CardEntity card) throws SQLException {
        CardEntity result;
        try {
            result = cardDAO.update(card);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return result;
    }

    public ArrayList<CardEntity> findByBoardColumnId(long id) throws SQLException {
        return cardDAO.findByBoardColumnId(id);
    }
}
