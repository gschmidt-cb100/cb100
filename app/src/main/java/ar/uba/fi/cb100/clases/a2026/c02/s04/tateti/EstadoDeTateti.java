package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

public enum EstadoDeTateti {
    JUGANDO,
    EMPATE,
    GANO_X,
    GANO_O;

    public boolean esFinalizado() {
        return this != JUGANDO;
    }

    public boolean esGanadorX() {
        return this == GANO_X;
    }

    public boolean esGanadorO() {
        return this == GANO_O;
    }
}
