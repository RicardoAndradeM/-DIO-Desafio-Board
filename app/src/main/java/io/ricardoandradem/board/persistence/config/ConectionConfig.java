package io.ricardoandradem.board.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConectionConfig {
    private ConectionConfig() {}

    public static Connection getConnection() throws SQLException {
        var conection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dio",
                "root",
                "ricardo");
        conection.setAutoCommit(false);
        return conection;
    }
}
