package ar.uba.fi.cb100.guia.i03_poo.i03_medio.e01;

import java.util.Arrays;

/**
 * TDA Cadena: una secuencia INMUTABLE de caracteres implementada sobre un
 * {@code char[]} nativo (sin usar {@code String} por dentro, salvo para
 * construir y para {@code toString}).
 *
 * <p>Inmutable: ninguna operación modifica esta cadena; las que "transforman"
 * ({@link #enMayusculas()}, {@link #invertida()}) devuelven una Cadena NUEVA.</p>
 */
public final class Cadena {

    private final char[] caracteres;

    /**
     * Crea una cadena a partir de un texto.
     *
     * <p>pre: {@code texto} no es null (puede ser vacío).<br>
     * post: la cadena contiene los mismos caracteres que {@code texto},
     * copiados una única vez a un arreglo interno.</p>
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
     * Crea una cadena a partir de un arreglo de caracteres.
     *
     * <p>pre: {@code caracteres} no es null (puede tener largo 0).<br>
     * post: la cadena contiene una COPIA del arreglo (copia defensiva):
     * modificar el arreglo original después no afecta a esta cadena.</p>
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
     * post: devuelve la cantidad de caracteres de la cadena (0 si es vacía).
     *
     * @return el largo de la cadena.
     */
    public int largo() {
        return caracteres.length;
    }

    /**
     * pre: {@code indice} está en el rango [0, largo()).<br>
     * post: devuelve el carácter en la posición {@code indice} (base 0),
     * sin modificar la cadena.
     *
     * @param indice posición del carácter pedido.
     * @return el carácter en esa posición.
     * @throws IllegalArgumentException si {@code indice} está fuera de rango.
     */
    public char caracterEn(int indice) {
        if (indice < 0 || indice >= caracteres.length) {
            throw new IllegalArgumentException(
                    "Índice fuera de rango: " + indice + " (largo=" + caracteres.length + ")");
        }
        return caracteres[indice];
    }

    /**
     * pre: -.<br>
     * post: devuelve true si y sólo si {@code buscado} aparece al menos una
     * vez en la cadena; la cadena no se modifica.
     *
     * @param buscado el carácter a buscar.
     * @return true si la cadena contiene el carácter.
     */
    public boolean contiene(char buscado) {
        for (char caracter : caracteres) {
            if (caracter == buscado) {
                return true;
            }
        }
        return false;
    }

    /**
     * pre: -.<br>
     * post: devuelve una cadena NUEVA con cada carácter pasado a mayúscula;
     * esta cadena queda intacta (inmutabilidad).
     *
     * @return una nueva Cadena en mayúsculas.
     */
    public Cadena enMayusculas() {
        char[] resultado = new char[caracteres.length];
        for (int i = 0; i < caracteres.length; i++) {
            resultado[i] = Character.toUpperCase(caracteres[i]);
        }
        return new Cadena(resultado);
    }

    /**
     * pre: -.<br>
     * post: devuelve una cadena NUEVA con los caracteres en orden inverso;
     * esta cadena queda intacta (inmutabilidad).
     *
     * @return una nueva Cadena invertida.
     */
    public Cadena invertida() {
        char[] resultado = new char[caracteres.length];
        for (int i = 0; i < caracteres.length; i++) {
            resultado[i] = caracteres[caracteres.length - 1 - i];
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
     * post: devuelve el contenido COMPLETO de la cadena como String
     * (único lugar donde se usa String como representación).
     */
    @Override
    public String toString() {
        return new String(caracteres);
    }

    /**
     * Demostración breve del TDA Cadena.
     */
    public static void main(String[] args) {
        Cadena saludo = new Cadena("hola mundo");
        System.out.println("Cadena: " + saludo);
        System.out.println("largo(): " + saludo.largo());
        System.out.println("caracterEn(4): '" + saludo.caracterEn(4) + "'");
        System.out.println("contiene('m'): " + saludo.contiene('m'));
        System.out.println("contiene('z'): " + saludo.contiene('z'));
        System.out.println("enMayusculas(): " + saludo.enMayusculas());
        System.out.println("invertida(): " + saludo.invertida());
        System.out.println("La original NO cambió (inmutable): " + saludo);
        System.out.println("equals con otra igual: " + saludo.equals(new Cadena("hola mundo")));
    }
}
