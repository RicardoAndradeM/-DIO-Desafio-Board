package io.ricardoandradem.board.persistence.entity;

import java.util.stream.Stream;

public enum BlockStatus {
    blocked,unblocked;

    public static BlockStatus findByName(String name) {
        return Stream.of(BlockStatus.values())
                .filter(b -> b.name().equals(name))
                .findFirst().orElseThrow();
    }
}