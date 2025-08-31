package io.ricardoandradem.board.persistence.entity;

import java.util.stream.Stream;

public enum BoardColumnKind {
    INITIAL, FINAL, CANCEL, PENDING;

    public static BoardColumnKind findByName(final String name){
        return Stream.of(BoardColumnKind.values())
                .filter(b -> b.name().equals(name))
                .findFirst().orElseThrow();
    }
}
