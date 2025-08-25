package org.example.dominio;

public enum Prioridade {

    BAIXO,
    MEDIO,
    ALTO;

    public int getLevel() {
        return switch (this) {
            case BAIXO -> 1;
            case MEDIO -> 2;
            case ALTO -> 3;
        };
    }

}
