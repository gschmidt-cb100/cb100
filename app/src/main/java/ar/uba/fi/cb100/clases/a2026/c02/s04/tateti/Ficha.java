package ar.uba.fi.cb100.clases.a2026.c02.s04.tateti;

public enum Ficha {
    X,
    O;

    public Ficha alternar() {
        if (this == X) {
            return O;
        } else {
            return X;
        }
    }
}
