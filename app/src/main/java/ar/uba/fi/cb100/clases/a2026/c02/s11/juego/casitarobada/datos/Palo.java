package ar.uba.fi.cb100.clases.a2026.c02.s11.juego.casitarobada.datos;

/**
 * Los cuatro palos de la baraja española.
 */
public enum Palo {
    ORO("oros"),
    COPA("copas"),
    ESPADA("espadas"),
    BASTO("bastos");

    private final String nombre;

    Palo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return el nombre en plural, como se dice: "7 de oros"
     */
    public String getNombre() {
        return nombre;
    }
}
