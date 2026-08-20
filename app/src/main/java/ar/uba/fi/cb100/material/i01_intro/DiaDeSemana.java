package ar.uba.fi.cb100.material.i01_intro;

/**
 * Un {@code enum}: un tipo con un conjunto fijo y conocido de valores.
 * Es más seguro que usar enteros o cadenas "mágicas".
 */
public enum DiaDeSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    /** Indica si el día es fin de semana. */
    public boolean esFinDeSemana() {
        return this == SABADO || this == DOMINGO;
    }

    public static void main(String[] args) {
        for (DiaDeSemana d : values()) {   // values(): todos los valores del enum
            System.out.println(d + " → fin de semana: " + d.esFinDeSemana());
        }
    }
}
