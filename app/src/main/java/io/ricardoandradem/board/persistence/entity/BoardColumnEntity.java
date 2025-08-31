package io.ricardoandradem.board.persistence.entity;

import java.util.ArrayList;

public class BoardColumnEntity {

    private long id;
    private String Name;
    private int orgerColmun;
    private BoardColumnKind kind;
    private ArrayList<CardEntity> cardList = new ArrayList<>();
    private BoardEntity board = new BoardEntity();

    public BoardColumnEntity() {
    }

    public BoardColumnEntity(long id, String name, int orgerColmun, BoardColumnKind kind) {
        this.id = id;
        Name = name;
        this.orgerColmun = orgerColmun;
        this.kind = kind;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getOrgerColmun() {
        return orgerColmun;
    }

    public void setOrgerColmun(int orgerColmun) {
        this.orgerColmun = orgerColmun;
    }

    public BoardColumnKind getKind() {
        return kind;
    }

    public void setKind(BoardColumnKind kind) {
        this.kind = kind;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public ArrayList<CardEntity> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<CardEntity> cardList) {
        this.cardList = cardList;
    }

    @Override
    public String toString() {
        return "BoardColumnEntity{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", orgerColmun=" + orgerColmun +
                ", kind=" + kind +
                '}';
    }

    public void addCard(CardEntity card) {
        cardList.add(card);
    }
}
