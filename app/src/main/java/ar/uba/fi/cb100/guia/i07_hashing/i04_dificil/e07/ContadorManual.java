package ar.uba.fi.cb100.guia.i07_hashing.i04_dificil.e07;

/**
 * Contador de frecuencias de palabras SIN usar HashMap ni ninguna clase de
 * java.util: la tabla es la del ejercicio 1, replicada en este paquete.
 *
 * Reglas de normalizacion:
 *  - todo el texto se pasa a minusculas;
 *  - las palabras se separan con el patron "[^\\p{L}]+" (cualquier corrida
 *    de caracteres que NO sean letras: espacios, comas, numeros, etc.).
 *
 * El objeto guarda la ultima tabla contada, asi frecuenciaDe se puede
 * consultar despues de contar.
 */
public class ContadorManual {

    /** Capacidad de la tabla de frecuencias (fija: la tabla no rehashea). */
    private static final int CAPACIDAD = 64;

    /** Ultima tabla de frecuencias calculada (vacia hasta el primer contar). */
    private TablaHash<String, Integer> frecuencias;

    public ContadorManual() {
        this.frecuencias = new TablaHash<>(CAPACIDAD);
    }

    /**
     * Cuenta las frecuencias de las palabras del texto y devuelve la tabla
     * palabra -> cantidad de apariciones. La tabla devuelta queda ademas
     * guardada para consultarla con frecuenciaDe.
     */
    public TablaHash<String, Integer> contar(String texto) {
        TablaHash<String, Integer> tabla = new TablaHash<>(CAPACIDAD);
        String[] palabras = texto.toLowerCase().split("[^\\p{L}]+");
        for (String palabra : palabras) {
            if (palabra.isEmpty()) {
                continue; // split puede dejar un "" al principio.
            }
            Integer actual = tabla.obtener(palabra);
            tabla.poner(palabra, actual == null ? 1 : actual + 1);
        }
        this.frecuencias = tabla;
        return tabla;
    }

    /**
     * Frecuencia de la palabra en el ultimo texto contado (0 si no aparece).
     * La consulta normaliza a minusculas igual que contar.
     */
    public int frecuenciaDe(String palabra) {
        Integer valor = frecuencias.obtener(palabra.toLowerCase());
        return valor == null ? 0 : valor;
    }

    /** Demostracion con un refran. */
    public static void main(String[] args) {
        ContadorManual contador = new ContadorManual();
        String refran = "el que lee mucho y anda mucho, ve mucho y sabe mucho";
        TablaHash<String, Integer> tabla = contador.contar(refran);
        System.out.println("palabras distintas: " + tabla.tamanio());
        System.out.println("mucho -> " + contador.frecuenciaDe("mucho"));
        System.out.println("y     -> " + contador.frecuenciaDe("y"));
        System.out.println("gato  -> " + contador.frecuenciaDe("gato"));
    }
}
