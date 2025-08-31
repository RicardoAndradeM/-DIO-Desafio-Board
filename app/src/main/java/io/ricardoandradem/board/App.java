package io.ricardoandradem.board;

import org.flywaydb.core.Flyway;

public class App {

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:mysql://localhost:3306/dio", "root", "ricardo")
                .load();

        flyway.migrate();

        System.out.println("Migrações aplicadas com sucesso!");
    }
}
