package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Camion: un camión que lleva paquetes en una cantidad fija de lugares
 * y con un peso máximo de carga.
 *
 * Responsabilidad: administrar qué paquete va en cada lugar, defender los
 * dos topes (lugares y peso) y responder las consultas sobre la carga.
 *
 * Estructura elegida: un vector {@code Paquete[]} del tamaño de la cantidad
 * de lugares. Justificación:
 *  - El enunciado sólo permite usar hasta vectores.
 *  - La cantidad de lugares es fija y se conoce al crear el camión: no hace
 *    falta redimensionar.
 *  - La posición i-1 del vector es el lugar i, y un null dice que el lugar
 *    está libre: no hace falta un TDA Lugar ni un contador aparte.
 *  - Cargar en el primer lugar libre y descargar por código recorren el
 *    vector: O(n). Se acepta, un camión tiene pocos lugares.
 *
 * Invariante:
 *  - lugares.length es mayor que cero y no cambia; pesoMaximo es mayor que
 *    cero y no cambia.
 *  - El peso total cargado nunca supera el peso máximo.
 *  - No hay dos paquetes con el mismo código en el camión.
 *
 * Hipótesis:
 *  H1. Los lugares que ve el usuario van de 1 a la cantidad de lugares.
 *  H2. El peso total y la ocupación se calculan cuando se piden; no se
 *      guardan en atributos que haya que mantener actualizados.
 *  H3. El porcentaje de ocupación es en peso: peso cargado sobre peso máximo.
 *  H4. Si hay empate en el más pesado, se devuelve el del lugar de menor número.
 *  H5. Dos camiones tienen un destino en común si algún paquete de uno va al
 *      mismo destino que algún paquete del otro.
 */
public class Camion {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final double PORCENTAJE_TOTAL = 100.0;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Paquete[] lugares;
    private final double pesoMaximo;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code cantidadDeLugares} y {@code pesoMaximo} (kg) son mayores que cero.
     * post: crea el camión vacío, con todos los lugares libres. Si no se
     *       cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Camion(int cantidadDeLugares, double pesoMaximo) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeLugares, "cantidad de lugares");
        ValidacionesUtiles.esMayorQueCero(pesoMaximo, "peso máximo");
        this.lugares = new Paquete[cantidadDeLugares];
        this.pesoMaximo = pesoMaximo;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code paquete} no es null y no está ya cargado; el camión no está
     *      lleno y el paquete no hace superar el peso máximo.
     * post: el paquete queda en el primer lugar libre y se devuelve el número
     *       de ese lugar (1..cantidad de lugares). Si el paquete es null o ya
     *       está cargado lanza {@link IllegalArgumentException}; si el camión
     *       está lleno o se supera el peso máximo lanza
     *       {@link IllegalStateException}.
     */
    public int cargar(Paquete paquete) {
        ValidacionesUtiles.validarDistintoDeNull(paquete, "paquete");
        ValidacionesUtiles.validarFalso(contiene(paquete), "El paquete " + paquete.getCodigo() + " ya está cargado.");
        ValidacionesUtiles.validarEstadoFalso(estaLleno(), "El camión está lleno.");
        ValidacionesUtiles.validarEstadoFalso(getPesoTotal() + paquete.getPeso() > pesoMaximo,
                "El paquete " + paquete.getCodigo() + " hace superar el peso máximo de " + pesoMaximo + " kg.");

        int indice = buscarPrimerLugarLibre();
        lugares[indice] = paquete;
        return indice + 1;
    }

    /**
     * pre: {@code codigo} no es null y hay un paquete cargado con ese código.
     * post: el lugar del paquete queda libre y se devuelve el paquete
     *       descargado. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Paquete descargar(String codigo) {
        Integer indice = buscarLugarDe(codigo);
        ValidacionesUtiles.validarFalso(indice == null, "No hay un paquete cargado con el código " + codigo + ".");
        Paquete descargado = lugares[indice];
        lugares[indice] = null;
        return descargado;
    }

    /** post: devuelve la cantidad de lugares libres. */
    public int getCantidadDeLugaresLibres() {
        int libres = 0;
        for (Paquete paquete : lugares) {
            if (paquete == null) {
                libres++;
            }
        }
        return libres;
    }

    /** post: devuelve el peso total cargado, en kg. Cero si el camión está vacío. */
    public double getPesoTotal() {
        double pesoTotal = 0;
        for (Paquete paquete : lugares) {
            if (paquete != null) {
                pesoTotal += paquete.getPeso();
            }
        }
        return pesoTotal;
    }

    /**
     * post: devuelve el paquete más pesado de los cargados (H4), o null si
     *       el camión está vacío.
     */
    public Paquete getPaqueteMasPesado() {
        Paquete masPesado = null;
        for (Paquete paquete : lugares) {
            if (paquete != null && (masPesado == null || paquete.esMasPesadoQue(masPesado))) {
                masPesado = paquete;
            }
        }
        return masPesado;
    }

    /** post: devuelve la cantidad de paquetes frágiles cargados. */
    public int getCantidadDePaquetesFragiles() {
        int fragiles = 0;
        for (Paquete paquete : lugares) {
            if (paquete != null && paquete.esFragil()) {
                fragiles++;
            }
        }
        return fragiles;
    }

    /**
     * pre: {@code destino} no es null.
     * post: devuelve un vector con los paquetes que van a ese destino, en el
     *       orden de los lugares. Si no hay ninguno, el vector está vacío.
     */
    public Paquete[] buscarPaquetes(String destino) {
        ValidacionesUtiles.validarDistintoDeNull(destino, "destino");
        Paquete[] encontrados = new Paquete[lugares.length];
        int cantidadDeEncontrados = 0;
        for (Paquete paquete : lugares) {
            if (paquete != null && paquete.vaA(destino)) {
                encontrados[cantidadDeEncontrados] = paquete;
                cantidadDeEncontrados++;
            }
        }
        return recortar(encontrados, cantidadDeEncontrados);
    }

    /** post: devuelve el porcentaje de ocupación en peso, entre 0 y 100 (H3). */
    public double getPorcentajeDeOcupacion() {
        return getPesoTotal() * PORCENTAJE_TOTAL / pesoMaximo;
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve true si algún paquete de este camión va al mismo destino
     *       que algún paquete del otro (H5). Corta en la primera coincidencia.
     */
    public boolean tieneDestinoEnComun(Camion otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "camión");
        for (Paquete paquete : lugares) {
            if (paquete != null && otro.llevaPaquetesA(paquete.getDestino())) {
                return true;
            }
        }
        return false;
    }

    /**
     * pre: {@code destino} no es null.
     * post: devuelve true si el camión lleva al menos un paquete a ese destino.
     */
    public boolean llevaPaquetesA(String destino) {
        ValidacionesUtiles.validarDistintoDeNull(destino, "destino");
        for (Paquete paquete : lugares) {
            if (paquete != null && paquete.vaA(destino)) {
                return true;
            }
        }
        return false;
    }

    /**
     * pre: {@code paquete} no es null.
     * post: devuelve true si el camión tiene cargado un paquete igual al recibido.
     */
    public boolean contiene(Paquete paquete) {
        ValidacionesUtiles.validarDistintoDeNull(paquete, "paquete");
        return buscarLugarDe(paquete.getCodigo()) != null;
    }

    /** post: devuelve true si no quedan lugares libres. */
    public boolean estaLleno() {
        return getCantidadDeLugaresLibres() == 0;
    }

    /** post: devuelve true si no hay paquetes cargados. */
    public boolean estaVacio() {
        return getCantidadDeLugaresLibres() == lugares.length;
    }

    /**
     * pre: {@code numeroDeLugar} está entre 1 y la cantidad de lugares.
     * post: devuelve el paquete de ese lugar, o null si el lugar está libre.
     *       Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Paquete getPaquete(int numeroDeLugar) {
        ValidacionesUtiles.validarRango(numeroDeLugar, 1, lugares.length, "número de lugar");
        return lugares[numeroDeLugar - 1];
    }

    /**
     * pre: el camión no está lleno.
     * post: devuelve el índice (0..n-1) del primer lugar libre.
     */
    private int buscarPrimerLugarLibre() {
        int indice = 0;
        while (lugares[indice] != null) {
            indice++;
        }
        return indice;
    }

    /**
     * pre: {@code codigo} no es null.
     * post: devuelve el índice (0..n-1) del lugar donde está el paquete con
     *       ese código, o null si no está cargado.
     */
    private Integer buscarLugarDe(String codigo) {
        ValidacionesUtiles.validarDistintoDeNull(codigo, "código");
        for (int i = 0; i < lugares.length; i++) {
            if (lugares[i] != null && lugares[i].tieneCodigo(codigo)) {
                return i;
            }
        }
        return null;
    }

    /** Devuelve un vector nuevo con los primeros {@code cantidad} paquetes del original. */
    private static Paquete[] recortar(Paquete[] original, int cantidad) {
        Paquete[] recortado = new Paquete[cantidad];
        for (int i = 0; i < cantidad; i++) {
            recortado[i] = original[i];
        }
        return recortado;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String resultado = "Camión (" + getPesoTotal() + "/" + pesoMaximo + " kg, "
                + getCantidadDeLugaresLibres() + " lugares libres de " + lugares.length + ")";
        for (int i = 0; i < lugares.length; i++) {
            resultado += "\n  Lugar " + (i + 1) + ": " + (lugares[i] == null ? "libre" : lugares[i]);
        }
        return resultado;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getCantidadDeLugares() {
        return lugares.length;
    }

    public double getPesoMaximo() {
        return pesoMaximo;
    }
}
