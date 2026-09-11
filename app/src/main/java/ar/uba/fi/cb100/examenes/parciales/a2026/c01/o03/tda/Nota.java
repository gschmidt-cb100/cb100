package ar.uba.fi.cb100.examenes.parciales.a2026.c01.o03.tda;

public enum Nota {
    DO,
    RE,
    MI,
    FA,
    SOL,
    LA,
    SI;

    public static Nota build(int posicion) {
        Nota[] temp = {DO, RE, MI, FA, SOL, LA, SI};
        return temp[posicion % 8];
    }
}