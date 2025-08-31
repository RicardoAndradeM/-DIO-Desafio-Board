package io.ricardoandradem.board.persistence.entity;

public enum BlockStatus {
    blocked("blocked"),unblocked("unblocked");

    private final String valor;

    BlockStatus(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
