package ar.uba.fi.cb100.guia.i08_arboles.i04_dificil.e08;

import java.util.StringJoiner;

/**
 * Serializar un ABB a texto y reconstruirlo despues.
 *
 * La clave: el PRE-orden alcanza para reconstruir un ABB identico.
 * Como el pre-orden visita cada nodo antes que a sus hijos, insertar
 * los valores en ese mismo orden vuelve a colgar cada nodo exactamente
 * donde estaba (la raiz entra primero, despues su hijo, etc.).
 *
 * Ojo: esto vale porque el arbol es un ABB (el orden de insercion
 * determina la forma). Para un arbol binario cualquiera el pre-orden
 * solo no alcanza: haria falta marcar los null o sumar el en-orden.
 */
public class SerializadorAbb {

    private SerializadorAbb() {
        // Clase de metodos estaticos: no se instancia.
    }

    /**
     * Serializa el arbol como sus valores en pre-orden separados por coma.
     * Un arbol vacio se serializa como cadena vacia. O(n).
     */
    public static String aPreOrden(ArbolBusqueda arbol) {
        StringJoiner joiner = new StringJoiner(",");
        for (Integer valor : arbol.preOrden()) {
            joiner.add(String.valueOf(valor));
        }
        return joiner.toString();
    }

    /**
     * Reconstruye el arbol insertando los valores en el orden en que
     * aparecen en el texto. Si el texto vino de aPreOrden, el arbol
     * resultante tiene EXACTAMENTE la misma forma que el original.
     * O(n * h): n inserciones de costo O(h).
     */
    public static ArbolBusqueda reconstruir(String texto) {
        ArbolBusqueda arbol = new ArbolBusqueda();
        if (texto == null || texto.isBlank()) {
            return arbol; // Cadena vacia: arbol vacio.
        }
        for (String parte : texto.split(",")) {
            arbol.insertar(Integer.parseInt(parte.trim()));
        }
        return arbol;
    }

    /** Demostracion: ida y vuelta sin perder la forma del arbol. */
    public static void main(String[] args) {
        ArbolBusqueda original = new ArbolBusqueda();
        for (int valor : new int[] {50, 30, 70, 20, 40}) {
            original.insertar(valor);
        }
        String texto = aPreOrden(original);
        System.out.println("serializado  = " + texto);

        ArbolBusqueda copia = reconstruir(texto);
        System.out.println("preOrden igual: " + original.preOrden().equals(copia.preOrden()));
        System.out.println("enOrden  igual: " + original.enOrden().equals(copia.enOrden()));
    }
}
