package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA VentanaDeMediana: recuerda los últimos {@code capacidad} valores que
 * se le agregaron y responde su mediana.
 *
 * Es el subproblema del punto 3 con nombre propio: el depurador sólo dice
 * "agregá este valor" y "dame la mediana", sin saber cómo se guardan.
 *
 * Estructura elegida: dos vectores de tamaño {@code capacidad} con los
 * MISMOS valores, cada uno ordenado para lo que hace falta:
 *  - {@code porLlegada}: vector circular. Dice cuál es el valor más viejo,
 *    que es el que sale cuando entra uno nuevo (la ventana es FIFO).
 *  - {@code ordenados}: de menor a mayor. La mediana es el del medio, O(1).
 * Con uno solo no alcanza: el circular no sabe la mediana sin ordenar en
 * cada paso (O(capacidad · log capacidad)), y el ordenado no sabe cuál es el
 * más viejo.
 * Los dos vectores miden {@code capacidad}: cumplen la regla 8 del enunciado
 * (ninguna estructura depende de la cantidad de lecturas).
 *
 * Orden: agregar es O(capacidad) (sacar el viejo y meter el nuevo en el
 * vector ordenado son dos corrimientos); getMediana es O(1).
 *
 * Invariante:
 *  - capacidad es impar y mayor que cero, y no cambia.
 *  - 0 <= cantidad <= capacidad.
 *  - {@code ordenados[0..cantidad-1]} tiene los mismos valores que la parte
 *    ocupada de {@code porLlegada}, de menor a mayor.
 *  - Cuando la ventana está llena, {@code porLlegada[siguiente]} es el valor
 *    más viejo.
 */
public class VentanaDeMediana {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int[] porLlegada;
    private final int[] ordenados;
    private int cantidad;
    private int siguiente;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code capacidad} es impar y mayor que cero.
     * post: crea la ventana vacía. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public VentanaDeMediana(int capacidad) {
        ValidacionesUtiles.esMayorQueCero(capacidad, "capacidad de la ventana");
        ValidacionesUtiles.validarFalso(capacidad % 2 == 0, "La capacidad de la ventana debe ser impar.");
        this.porLlegada = new int[capacidad];
        this.ordenados = new int[capacidad];
        this.cantidad = 0;
        this.siguiente = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * post: {@code valor} pasa a ser el más nuevo de la ventana. Si la
     *       ventana estaba llena, sale el valor más viejo.
     * Orden: O(capacidad).
     */
    public void agregar(int valor) {
        if (estaLlena()) {
            quitarDeLosOrdenados(porLlegada[siguiente]);
        }
        porLlegada[siguiente] = valor;
        siguiente = (siguiente + 1) % porLlegada.length;
        insertarEnLosOrdenados(valor);
    }

    /**
     * pre: la ventana está llena.
     * post: devuelve la mediana de los valores de la ventana: el que queda
     *       en el medio al ordenarlos. Si no se cumple la pre, lanza
     *       {@link IllegalStateException}.
     * Orden: O(1).
     */
    public int getMediana() {
        ValidacionesUtiles.validarEstadoFalso(!estaLlena(), "La ventana todavía no está llena.");
        return ordenados[cantidad / 2];
    }

    /** post: devuelve true si la ventana ya tiene {@code capacidad} valores. */
    public boolean estaLlena() {
        return cantidad == porLlegada.length;
    }

    /**
     * pre: {@code valor} está entre los ordenados.
     * post: quita una aparición de {@code valor} y cierra el hueco.
     */
    private void quitarDeLosOrdenados(int valor) {
        int indice = 0;
        while (ordenados[indice] != valor) {
            indice++;
        }
        for (int i = indice; i < cantidad - 1; i++) {
            ordenados[i] = ordenados[i + 1];
        }
        cantidad--;
    }

    /**
     * pre: hay lugar para un valor más.
     * post: {@code valor} queda entre los ordenados, en la posición que le
     *       corresponde (un paso de ordenamiento por inserción).
     */
    private void insertarEnLosOrdenados(int valor) {
        int indice = cantidad;
        while (indice > 0 && ordenados[indice - 1] > valor) {
            ordenados[indice] = ordenados[indice - 1];
            indice--;
        }
        ordenados[indice] = valor;
        cantidad++;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String resultado = "Ventana (" + cantidad + "/" + porLlegada.length + "):";
        for (int i = 0; i < cantidad; i++) {
            resultado += " " + ordenados[i];
        }
        return resultado;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getCapacidad() {
        return porLlegada.length;
    }

    public int getCantidad() {
        return cantidad;
    }
}
