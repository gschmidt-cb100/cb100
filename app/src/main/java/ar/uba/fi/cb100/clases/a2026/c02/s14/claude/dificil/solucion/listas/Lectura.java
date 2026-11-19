package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Una lectura de un sensor: el valor medido y durante cuánto tiempo se mantuvo.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarla.
 *
 * Hipótesis:
 *  H1. El valor es un entero y puede ser negativo (una temperatura bajo cero).
 *  H2. La duración es mayor que cero.
 */
public class Lectura {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int valor;
    private int duracion;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code duracion} es mayor que cero.
     * post: crea la lectura. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Lectura(int valor, int duracion) {
        ValidacionesUtiles.esMayorQueCero(duracion, "duración");
        this.valor = valor;
        this.duracion = duracion;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code tolerancia} es mayor o igual a cero.
     * post: devuelve true si el valor se aparta de {@code referencia} en más
     *       de {@code tolerancia}: |valor - referencia| > tolerancia.
     */
    public boolean seApartaDe(int referencia, int tolerancia) {
        ValidacionesUtiles.validarFalso(tolerancia < 0, "La tolerancia no puede ser negativa.");
        return Math.abs(valor - referencia) > tolerancia;
    }

    /** post: el valor de la lectura pasa a ser {@code valorCorregido}. */
    public void corregir(int valorCorregido) {
        this.valor = valorCorregido;
    }

    /**
     * pre: {@code otra} no es null.
     * post: devuelve true si las dos lecturas tienen el mismo valor.
     */
    public boolean tieneElMismoValorQue(Lectura otra) {
        ValidacionesUtiles.validarDistintoDeNull(otra, "lectura");
        return this.valor == otra.valor;
    }

    /**
     * pre: {@code repetida} no es null y tiene el mismo valor que esta lectura.
     * post: la duración de esta lectura aumenta en la duración de la
     *       repetida. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public void absorber(Lectura repetida) {
        ValidacionesUtiles.validarDistintoDeNull(repetida, "lectura");
        ValidacionesUtiles.validarFalso(!tieneElMismoValorQue(repetida),
                "Sólo se puede absorber una lectura con el mismo valor.");
        this.duracion += repetida.duracion;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return valor + " x" + duracion;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getValor() {
        return valor;
    }

    public int getDuracion() {
        return duracion;
    }
}
