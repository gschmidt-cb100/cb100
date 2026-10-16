package ar.uba.fi.cb100.material.i13_diseno.tostadora;

/**
 * TDA Tostadora: el ejemplo completo de la Unidad 13 (cómo empezar a diseñar
 * una solución). Enunciado:
 * <p>
 * "Diseñar la especificación e implementar el TDA Tostadora. Debe proveer
 * operaciones para: construir con la cantidad de ranuras para panes que tiene
 * (cada ranura se identifica con un número); iniciar el tostado de pan
 * indicando la ranura; finalizar el tostado indicando la ranura; indicar el
 * nivel de calor, de 1 a 10; contar las ranuras libres; devolver la ranura que
 * más panes tostó. La tostadora puede estar apagada o encendida. Regla: la
 * tostadora es manual, no tiene automatizado el tiempo de cocción."
 * <p>
 * Hipótesis (lo que el enunciado no dice y hubo que decidir; están numeradas
 * como en el apunte):
 * <ul>
 *   <li>H1: las ranuras se numeran de 1 a {@code cantidadDeRanuras}.</li>
 *   <li>H2: la tostadora nace APAGADA, con todas las ranuras libres y el nivel
 *       de calor en {@link #NIVEL_INICIAL}.</li>
 *   <li>H3: sólo se puede iniciar un tostado con la tostadora ENCENDIDA y en
 *       una ranura LIBRE; en otro caso es un error de estado.</li>
 *   <li>H4: un tostado finalizado cuenta un pan en esa ranura (un pan por
 *       ranura por tostado).</li>
 *   <li>H5: no se puede apagar con ranuras ocupadas (habría panes a medio
 *       tostar sin contar); es un error de estado.</li>
 *   <li>H6: el nivel de calor es una perilla: se puede cambiar encendida o
 *       apagada.</li>
 *   <li>H7: la ranura que más tostó, en caso de empate, es la de menor número;
 *       si ninguna tostó todavía, no hay ranura: {@code null} (checklist 10 y 13).</li>
 * </ul>
 */
public class Tostadora {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    public static final int NIVEL_MINIMO = 1;
    public static final int NIVEL_MAXIMO = 10;
    public static final int NIVEL_INICIAL = 5;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Ranura[] ranuras;
    private int nivelDeCalor;
    private EstadoDeTostadora estado;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code cantidadDeRanuras} es mayor que cero.
     * post: crea la tostadora APAGADA, con {@code cantidadDeRanuras} ranuras
     *       LIBRES numeradas desde 1 (H1, H2) y el nivel de calor inicial.
     */
    public Tostadora(int cantidadDeRanuras) {
        if (cantidadDeRanuras <= 0) {
            throw new IllegalArgumentException("la tostadora necesita al menos una ranura: " + cantidadDeRanuras);
        }
        this.ranuras = new Ranura[cantidadDeRanuras];
        for (int i = 0; i < cantidadDeRanuras; i++) {
            this.ranuras[i] = new Ranura(i + 1);
        }
        this.nivelDeCalor = NIVEL_INICIAL;
        this.estado = EstadoDeTostadora.APAGADA;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: la tostadora está APAGADA.
     * post: queda ENCENDIDA. Si ya estaba encendida, lanza {@link IllegalStateException}.
     */
    public void encender() {
        if (estado == EstadoDeTostadora.ENCENDIDA) {
            throw new IllegalStateException("la tostadora ya está encendida");
        }
        estado = EstadoDeTostadora.ENCENDIDA;
    }

    /**
     * pre: la tostadora está ENCENDIDA y no tiene ranuras ocupadas (H5).
     * post: queda APAGADA. Si no se cumple la pre, lanza {@link IllegalStateException}.
     */
    public void apagar() {
        if (estado == EstadoDeTostadora.APAGADA) {
            throw new IllegalStateException("la tostadora ya está apagada");
        }
        if (contarRanurasLibres() < ranuras.length) {
            throw new IllegalStateException("no se puede apagar con ranuras ocupadas");
        }
        estado = EstadoDeTostadora.APAGADA;
    }

    /**
     * pre: la tostadora está ENCENDIDA (H3); {@code numeroDeRanura} existe y
     *      esa ranura está LIBRE.
     * post: la ranura queda OCUPADA. Si la tostadora está apagada o la ranura
     *       ocupada, lanza {@link IllegalStateException}; si el número no
     *       existe, {@link IllegalArgumentException}. En ningún caso cambia nada.
     */
    public void iniciarTostado(int numeroDeRanura) {
        if (estado != EstadoDeTostadora.ENCENDIDA) {
            throw new IllegalStateException("la tostadora está apagada");
        }
        buscarRanura(numeroDeRanura).iniciarTostado();   // la ranura valida su propio estado
    }

    /**
     * pre: {@code numeroDeRanura} existe y esa ranura está OCUPADA.
     * post: la ranura queda LIBRE y suma un pan tostado (H4). Si estaba libre,
     *       lanza {@link IllegalStateException}; si el número no existe,
     *       {@link IllegalArgumentException}.
     */
    public void finalizarTostado(int numeroDeRanura) {
        buscarRanura(numeroDeRanura).finalizarTostado();
    }

    /**
     * pre: {@code nivel} está entre {@link #NIVEL_MINIMO} y {@link #NIVEL_MAXIMO}.
     * post: fija el nivel de calor (H6: encendida o apagada). Fuera de rango,
     *       lanza {@link IllegalArgumentException} y no cambia nada.
     */
    public void fijarNivelDeCalor(int nivel) {
        if (nivel < NIVEL_MINIMO || nivel > NIVEL_MAXIMO) {
            throw new IllegalArgumentException("el nivel de calor va de " + NIVEL_MINIMO
                    + " a " + NIVEL_MAXIMO + ": " + nivel);
        }
        nivelDeCalor = nivel;
    }

    /** post: devuelve cuántas ranuras están LIBRES. */
    public int contarRanurasLibres() {
        int libres = 0;
        for (Ranura ranura : ranuras) {
            if (ranura.estaLibre()) {
                libres++;
            }
        }
        return libres;
    }

    /**
     * post: devuelve la ranura que más panes tostó; en caso de empate, la de
     *       menor número (H7). Si ninguna tostó todavía, devuelve {@code null}.
     */
    public Ranura ranuraQueMasTosto() {
        Ranura mejor = null;
        for (Ranura ranura : ranuras) {                        // en orden de número: el empate
            if (ranura.getPanesTostados() > 0                  // lo gana la primera (H7)
                    && (mejor == null || ranura.getPanesTostados() > mejor.getPanesTostados())) {
                mejor = ranura;
            }
        }
        return mejor;
    }

//METODOS PRIVADOS ----------------------------------------------------------------------------------------

    /**
     * pre: {@code numeroDeRanura} está entre 1 y la cantidad de ranuras (H1).
     * post: devuelve esa ranura; si no existe, lanza {@link IllegalArgumentException}.
     */
    private Ranura buscarRanura(int numeroDeRanura) {
        if (numeroDeRanura < 1 || numeroDeRanura > ranuras.length) {
            throw new IllegalArgumentException("no existe la ranura " + numeroDeRanura
                    + " (hay " + ranuras.length + ")");
        }
        return ranuras[numeroDeRanura - 1];
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "tostadora " + estado + ", nivel " + nivelDeCalor + ", "
                + contarRanurasLibres() + " de " + ranuras.length + " ranuras libres";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve el nivel de calor actual (entre 1 y 10). */
    public int getNivelDeCalor() {
        return nivelDeCalor;
    }

    /** post: devuelve el estado actual. */
    public EstadoDeTostadora getEstado() {
        return estado;
    }

    /** post: devuelve la cantidad total de ranuras. */
    public int getCantidadDeRanuras() {
        return ranuras.length;
    }
}
