package io.ricardoandradem.board.persistence.dao;

import java.sql.Connection;

public class BoardColumnDAO {

    private Connection connection;

    public BoardColumnDAO(Connection connection) {
        this.connection = connection;
    }
}
