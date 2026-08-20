package ar.uba.fi.cb100.guia.i01_intro.i02_facil.e10;

/**
 * Ejercicio 10 (facil): Enum de estaciones del anio.
 * Cada estacion sabe si es calida (PRIMAVERA y VERANO).
 */
public enum Estacion {
    PRIMAVERA,
    VERANO,
    OTONO,
    INVIERNO;

    /**
     * Indica si la estacion es calida.
     *
     * @return true para PRIMAVERA y VERANO, false para el resto
     */
    public boolean esCalida() {
        return this == PRIMAVERA || this == VERANO;
    }

    public static void main(String[] args) {
        for (var estacion : Estacion.values()) {
            System.out.println(estacion + " -> calida: " + estacion.esCalida());
        }
    }
}
