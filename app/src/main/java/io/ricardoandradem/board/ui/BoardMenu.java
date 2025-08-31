package io.ricardoandradem.board.ui;

import io.ricardoandradem.board.persistence.config.ConectionConfig;
import io.ricardoandradem.board.persistence.entity.*;
import io.ricardoandradem.board.service.CardService;
import io.ricardoandradem.board.util.PromptStrings;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class BoardMenu {

    private BoardEntity board;
    private final Scanner input = new Scanner(System.in);

    public BoardMenu(BoardEntity board) {
        this.board = board;
    }

    public void run() {
        int userInput = 0;
        do {
            System.out.println(PromptStrings.boardMenu);
            userInput = input.nextInt();
            switch (userInput) {
                case 1 -> createCard();
                case 2 -> moveCard();
                case 3 -> bloquearDesbloquearCard();
                case 4 -> cancelarCard();
                default -> System.out.println(PromptStrings.opcaoInvalida);
            }
        } while (userInput != 5);
    }

    private void cancelarCard() {
        System.out.print("Digite o ID do card que deseja cancelar: ");
        var id = input.nextLong();

        try {
            CardEntity card = findCard(id);
            BoardColumnEntity oldColumn = findColumn(card.getBoardColumn().getId());
            if (oldColumn.getKind() == BoardColumnKind.FINAL || oldColumn.getKind() == BoardColumnKind.CANCEL){
                throw new Exception("Este card já esta finalizado ou cancelado");
            }
            BoardColumnEntity newColumn = findCancellColumn();
            oldColumn.getCardList().remove(card);
            newColumn.getCardList().add(card);
            card.setBoardColumn(newColumn);
            try (var connection = ConectionConfig.getConnection()) {
                new CardService(connection).update(card);
                System.out.println("Card Atualizado.");
            } catch (SQLException e) {
                System.out.println("Problema no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void bloquearDesbloquearCard() {
        System.out.print("Digite o ID do card que deseja bloquear/desbloquear: ");
        var id = input.nextLong();

        try {
            CardEntity card = findCard(id);
            String comment;
            if (card.getIsBlocked() == BlockStatus.blocked) {
                System.out.print("O cartão esta bloqueado, digite o motivo de desbloqueio: ");
                comment = input.next();
                card.setIsBlocked(BlockStatus.unblocked);
            } else {
                System.out.print("O cartão esta desbloqueado, digite o motivo de bloqueio: ");
                comment = input.next();
                card.setIsBlocked(BlockStatus.blocked);
            }
            card.setBlockDescription(comment);
            try (var connection = ConectionConfig.getConnection()) {
                new CardService(connection).update(card);
                System.out.println("Card Atualizado.");
            } catch (SQLException e) {
                System.out.println("Problema no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void moveCard() {
        System.out.print("Digite o ID do card que deseja mover: ");
        var id = input.nextLong();

        try {
            CardEntity card = findCard(id);
            BoardColumnEntity oldColumn = findColumn(card.getBoardColumn().getId());
            if (oldColumn.getKind() == BoardColumnKind.FINAL || oldColumn.getKind() == BoardColumnKind.CANCEL){
                throw new Exception("Este card já esta finalizado ou cancelado");
            }
            BoardColumnEntity newColumn = findColumn(card.getBoardColumn().getId() + 1);
            oldColumn.getCardList().remove(card);
            newColumn.getCardList().add(card);
            card.setBoardColumn(newColumn);
            try (var connection = ConectionConfig.getConnection()) {
                new CardService(connection).update(card);
                System.out.println("Card Atualizado.");
            } catch (SQLException e) {
                System.out.println("Problema no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private BoardColumnEntity findColumn(long id) throws Exception {
        for (var column: board.getBoardColumnList()) {
            if (column.getId() == id) {
                return column;
            }
        }
        throw new Exception("Card Não encontrado.");
    }

    private BoardColumnEntity findCancellColumn() throws Exception {
        for (var column: board.getBoardColumnList()) {
            if (column.getKind() == BoardColumnKind.CANCEL) {
                return column;
            }
        }
        throw new Exception("Card Não encontrado.");
    }

    private CardEntity findCard(Long id) throws Exception {
        for (var column: board.getBoardColumnList()) {
            for (var card : column.getCardList()) {
                if (Objects.equals(card.getId(), id)) {
                    return card;
                }
            }
        }
        throw new Exception("Card Não encontrado.");
    }

    private void createCard() {
        var card = new CardEntity();
        System.out.print("Informe o titulo do card: ");
        card.setTitle(input.next());

        System.out.print("Informe a descricao do card: ");
        card.setDescription(input.next());

        var column = board.getBoardColumnList()
                .stream().filter(n -> n.getOrgerColmun() == 0).findFirst().get();

        card.setBoardColumn(column);
        column.addCard(card);

        try (var connection = ConectionConfig.getConnection()) {
            new CardService(connection).insert(card);
            System.out.println("Card Criado.");
        } catch (SQLException e) {
            System.out.println("Problema no banco de dados.");
        }
    }
}
