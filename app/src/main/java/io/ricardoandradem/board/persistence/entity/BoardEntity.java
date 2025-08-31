package io.ricardoandradem.board.persistence.entity;

import java.util.ArrayList;

public class BoardEntity {

    private Long id;
    private String name;
    private ArrayList<BoardColumnEntity> boardColumnList = new ArrayList<>();

    public BoardEntity() {
    }

    public BoardEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BoardColumnEntity> getBoardColumnList() {
        return boardColumnList;
    }

    public void setBoardColumnList(ArrayList<BoardColumnEntity> boardColumnList) {
        this.boardColumnList = boardColumnList;
    }

    @Override
    public String toString() {
        return "BoardEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
