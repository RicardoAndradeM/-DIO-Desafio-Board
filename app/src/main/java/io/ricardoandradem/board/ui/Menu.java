package io.ricardoandradem.board.ui;

import io.ricardoandradem.board.persistence.config.ConectionConfig;
import io.ricardoandradem.board.persistence.entity.BoardColumnEntity;
import io.ricardoandradem.board.persistence.entity.BoardColumnKind;
import io.ricardoandradem.board.persistence.entity.BoardEntity;
import io.ricardoandradem.board.service.BoardService;
import io.ricardoandradem.board.util.PromptStrings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private final Scanner input = new Scanner(System.in);

    public void run() {
        int userInput = 0;
        do {
            System.out.println(PromptStrings.menu);
            userInput = input.nextInt();
            switch (userInput) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> {}
                default -> System.out.println(PromptStrings.opcaoInvalida);
            }
        } while (userInput != 4);
    }

    private void deleteBoard() {
        System.out.print("Infome o ID do board a ser excluido: ");
        var id = input.nextLong();
        try (var connection = ConectionConfig.getConnection()) {
            new BoardService(connection).delete(id);
            System.out.println("Deletado.");
        } catch (SQLException e) {
            System.out.println("Problema com banco de dados!");
        }
    }

    private void selectBoard() {
        System.out.print("Digite o ID do board a ser selecionado: ");
        var id = input.nextLong();
        try (var connection = ConectionConfig.getConnection()) {
            var board = new BoardService(connection).findById(id);
            board.ifPresentOrElse(
                    b -> new BoardMenu(b).run(),
                    () -> System.out.println("Board não encontrado.")
            );
        } catch (SQLException e) {
            System.out.println("Problema com banco de dados!");
        }
    }

    private void createBoard() {
        var board = new BoardEntity();
        System.out.print("Informe o nome do board: ");
        board.setName(input.next());

        System.out.print("Quantas colunas adicionais o board terá? ");
        var extraColumn = input.nextInt();

        ArrayList<BoardColumnEntity> boardColumnEntityList = new ArrayList<>();

        boardColumnEntityList.add(
                createBoardColumn("Informa o nome da coluna Inicial: ", 0, BoardColumnKind.INITIAL)
        );

        for (int i = 1; i <= extraColumn; i++) {
            boardColumnEntityList.add(
                    createBoardColumn("Informa o nome da coluna Pendente: ", i, BoardColumnKind.PENDING)
            );
        }

        boardColumnEntityList.add(
                createBoardColumn("Informa o nome da coluna Final: ", extraColumn + 1, BoardColumnKind.FINAL)
        );
        boardColumnEntityList.add(
                createBoardColumn("Informa o nome da coluna Cancelamento: ", extraColumn + 2, BoardColumnKind.CANCEL)
        );

        board.setBoardColumnList(boardColumnEntityList);
        try (var connection = ConectionConfig.getConnection()) {
            new BoardService(connection).insert(board);
            System.out.println("Adicionado.");
        } catch (SQLException e) {
            System.out.println("Problema com banco de dados!");
        }
    }

    private BoardColumnEntity createBoardColumn(String prompt, int order, BoardColumnKind kind) {
        var boardColumn = new BoardColumnEntity();
        System.out.print(prompt);
        boardColumn.setName(input.next());
        boardColumn.setOrgerColmun(order);
        boardColumn.setKind(kind);
        return boardColumn;
    }
}
