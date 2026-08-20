package ar.uba.fi.cb100.guia.i01_intro.i03_medio.e09;

/**
 * Ejercicio 09: Enum Moneda con datos.
 * Enum con un símbolo asociado a cada moneda mediante constructor.
 *
 * Moneda con su símbolo asociado.
 */
public enum Moneda {
    PESO("$"),
    DOLAR("US$"),
    EURO("€");

    private final String simbolo;

    Moneda(String simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * @return el símbolo de la moneda
     */
    public String simbolo() {
        return simbolo;
    }

    public static void main(String[] args) {
        for (Moneda m : Moneda.values()) {
            System.out.println(m + " -> " + m.simbolo());
        }
    }
}
