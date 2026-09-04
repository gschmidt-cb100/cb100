package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e06;

import java.util.Arrays;

/**
 * TDA Cadena (versión PRO): una secuencia INMUTABLE de caracteres sobre un
 * {@code char[]} nativo, con operaciones de concatenación, subcadena,
 * búsqueda de otra cadena y reemplazo de caracteres.
 *
 * <p>Todas las operaciones que "transforman" devuelven una Cadena NUEVA;
 * esta cadena nunca cambia. Decisión de diseño en {@link #indiceDe(Cadena)}:
 * "no aparece" se representa con {@code null} (por eso devuelve
 * {@code Integer} y no {@code int}), nunca con el centinela −1: el tipo
 * obliga al que llama a considerar el caso ausente.</p>
 */
public final class Cadena {

    private final char[] caracteres;

    /**
     * pre: {@code texto} no es null (puede ser vacío).<br>
     * post: la cadena contiene los caracteres de {@code texto}.
     *
     * @param texto el texto de origen.
     * @throws IllegalArgumentException si {@code texto} es null.
     */
    public Cadena(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("El texto no puede ser null");
        }
        this.caracteres = texto.toCharArray();
    }

    /**
     * pre: {@code caracteres} no es null.<br>
     * post: la cadena contiene una COPIA del arreglo (copia defensiva).
     *
     * @param caracteres los caracteres de origen.
     * @throws IllegalArgumentException si {@code caracteres} es null.
     */
    public Cadena(char[] caracteres) {
        if (caracteres == null) {
            throw new IllegalArgumentException("El arreglo de caracteres no puede ser null");
        }
        this.caracteres = Arrays.copyOf(caracteres, caracteres.length);
    }

    /**
     * pre: -.<br>
     * post: devuelve la cantidad de caracteres.
     *
     * @return el largo de la cadena.
     */
    public int largo() {
        return caracteres.length;
    }

    /**
     * pre: {@code otra} no es null.<br>
     * post: devuelve una cadena NUEVA con los caracteres de esta seguidos de
     * los de {@code otra}; ninguna de las dos se modifica.
     *
     * @param otra la cadena a concatenar al final.
     * @return la concatenación como cadena nueva.
     * @throws IllegalArgumentException si {@code otra} es null.
     */
    public Cadena concatenar(Cadena otra) {
        if (otra == null) {
            throw new IllegalArgumentException("La cadena a concatenar no puede ser null");
        }
        char[] resultado = new char[caracteres.length + otra.caracteres.length];
        for (int i = 0; i < caracteres.length; i++) {
            resultado[i] = caracteres[i];
        }
        for (int i = 0; i < otra.caracteres.length; i++) {
            resultado[caracteres.length + i] = otra.caracteres[i];
        }
        return new Cadena(resultado);
    }

    /**
     * Devuelve la subcadena entre {@code desde} (inclusive) y {@code hasta}
     * (exclusive), con la misma convención que {@code String.substring}.
     *
     * <p>pre: {@code 0 <= desde <= hasta <= largo()}.<br>
     * post: devuelve una cadena NUEVA de largo {@code hasta - desde};
     * esta cadena no se modifica.</p>
     *
     * @param desde índice inicial, inclusive.
     * @param hasta índice final, exclusive.
     * @return la subcadena como cadena nueva.
     * @throws IllegalArgumentException si el rango es inválido.
     */
    public Cadena subcadena(int desde, int hasta) {
        if (desde < 0 || hasta > caracteres.length || desde > hasta) {
            throw new IllegalArgumentException(
                    "Rango inválido: [" + desde + ", " + hasta + ") para largo " + caracteres.length);
        }
        char[] resultado = new char[hasta - desde];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = caracteres[desde + i];
        }
        return new Cadena(resultado);
    }

    /**
     * Busca la primera aparición de {@code otra} dentro de esta cadena.
     *
     * <p>pre: {@code otra} no es null.<br>
     * post: devuelve el índice de la primera aparición, o {@code null} si no
     * aparece ("sin valor" = null, nunca −1). La cadena vacía aparece en el
     * índice 0, igual que en {@code String.indexOf}.</p>
     *
     * @param otra la cadena a buscar.
     * @return el índice de la primera aparición, o null si no aparece.
     * @throws IllegalArgumentException si {@code otra} es null.
     */
    public Integer indiceDe(Cadena otra) {
        if (otra == null) {
            throw new IllegalArgumentException("La cadena a buscar no puede ser null");
        }
        for (int inicio = 0; inicio <= caracteres.length - otra.caracteres.length; inicio++) {
            boolean coincide = true;
            for (int i = 0; i < otra.caracteres.length && coincide; i++) {
                if (caracteres[inicio + i] != otra.caracteres[i]) {
                    coincide = false;
                }
            }
            if (coincide) {
                return inicio;
            }
        }
        return null;
    }

    /**
     * pre: -.<br>
     * post: devuelve una cadena NUEVA donde cada aparición de {@code buscado}
     * fue cambiada por {@code reemplazo}; esta cadena no se modifica.
     *
     * @param buscado el carácter a reemplazar.
     * @param reemplazo el carácter que lo sustituye.
     * @return la cadena reemplazada como cadena nueva.
     */
    public Cadena reemplazar(char buscado, char reemplazo) {
        char[] resultado = new char[caracteres.length];
        for (int i = 0; i < caracteres.length; i++) {
            resultado[i] = (caracteres[i] == buscado) ? reemplazo : caracteres[i];
        }
        return new Cadena(resultado);
    }

    /**
     * pre: -.<br>
     * post: devuelve true si y sólo si {@code otro} es una Cadena con los
     * mismos caracteres en el mismo orden.
     */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Cadena otraCadena)) {
            return false;
        }
        return Arrays.equals(this.caracteres, otraCadena.caracteres);
    }

    /**
     * pre: -.<br>
     * post: devuelve un hash consistente con {@link #equals(Object)}.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(caracteres);
    }

    /**
     * pre: -.<br>
     * post: devuelve el contenido completo como String.
     */
    @Override
    public String toString() {
        return new String(caracteres);
    }

    /**
     * Demostración breve del TDA Cadena PRO.
     */
    public static void main(String[] args) {
        Cadena hola = new Cadena("hola ");
        Cadena mundo = new Cadena("mundo");
        Cadena frase = hola.concatenar(mundo);

        System.out.println("concatenar: '" + frase + "'");
        System.out.println("subcadena(5, 10): '" + frase.subcadena(5, 10) + "'");
        System.out.println("indiceDe(\"mundo\"): " + frase.indiceDe(mundo));
        System.out.println("indiceDe(\"xyz\"): " + frase.indiceDe(new Cadena("xyz")) + " (null = no aparece)");
        System.out.println("reemplazar('o', '0'): '" + frase.reemplazar('o', '0') + "'");
        System.out.println("La original sigue intacta: '" + frase + "'");
    }
}
