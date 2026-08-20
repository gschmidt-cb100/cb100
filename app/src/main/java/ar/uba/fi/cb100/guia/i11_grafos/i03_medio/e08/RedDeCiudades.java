package ar.uba.fi.cb100.guia.i11_grafos.i03_medio.e08;

import ar.uba.fi.cb100.material.i11_grafos.Dijkstra;
import ar.uba.fi.cb100.material.i11_grafos.Grafo;

import java.util.List;
import java.util.StringJoiner;

/**
 * e08: Dijkstra con camino y nombres — la red de ciudades argentina.
 *
 * <p><b>Modelo:</b> por dentro el grafo trabaja con números (0 a n−1); por
 * fuera el usuario habla de "Buenos Aires" y "Bariloche". Esta clase hace de
 * TDA intermedio: traduce nombre → índice, delega en
 * {@code Dijkstra.caminosMinimos} y arma la respuesta como texto legible.</p>
 *
 * <p><b>Técnica:</b> Dijkstra ya devuelve el arreglo {@code anterior[]} y el
 * método {@code caminoHasta}; sólo queda mapear cada índice del camino a su
 * nombre y formatear {@code "A -> B -> C (N km)"}. La búsqueda de nombres es
 * lineal: con pocas ciudades alcanza (con muchas, un diccionario de la U7).</p>
 */
public class RedDeCiudades {

    private final String[] nombres;
    private final Grafo rutas;

    public RedDeCiudades(String[] nombres) {
        this.nombres = nombres.clone();
        this.rutas = new Grafo(nombres.length, false);   // las rutas van y vienen
    }

    /** Da de alta una ruta entre dos ciudades, por nombre. */
    public void agregarRuta(String unaCiudad, String otraCiudad, int kilometros) {
        rutas.agregarArista(indiceDe(unaCiudad), indiceDe(otraCiudad), kilometros);
    }

    /**
     * La ruta más corta en kilómetros, con el formato
     * {@code "Origen -> Escala -> Destino (N km)"}.
     *
     * @throws IllegalArgumentException si alguna ciudad no existe o no hay ruta
     */
    public String rutaMasCorta(String origen, String destino) {
        int desde = indiceDe(origen);
        int hasta = indiceDe(destino);

        Dijkstra.Resultado resultado = Dijkstra.caminosMinimos(rutas, desde);
        List<Integer> camino = resultado.caminoHasta(hasta);
        if (camino.isEmpty()) {
            throw new IllegalArgumentException(
                    "no hay ruta entre " + origen + " y " + destino);
        }

        StringJoiner texto = new StringJoiner(" -> ");
        for (int ciudad : camino) {
            texto.add(nombres[ciudad]);
        }
        return texto + " (" + resultado.distancia()[hasta] + " km)";
    }

    /** Traducción nombre → índice (búsqueda lineal). */
    private int indiceDe(String nombre) {
        for (int i = 0; i < nombres.length; i++) {
            if (nombres[i].equals(nombre)) {
                return i;
            }
        }
        throw new IllegalArgumentException("no conozco la ciudad " + nombre);
    }

    public static void main(String[] args) {
        RedDeCiudades red = new RedDeCiudades(new String[] {
                "Buenos Aires", "Rosario", "Córdoba", "Mendoza",
                "Neuquén", "Bariloche", "Mar del Plata"});
        red.agregarRuta("Buenos Aires", "Rosario", 300);
        red.agregarRuta("Buenos Aires", "Mar del Plata", 400);
        red.agregarRuta("Rosario", "Córdoba", 400);
        red.agregarRuta("Córdoba", "Mendoza", 600);
        red.agregarRuta("Buenos Aires", "Neuquén", 1150);
        red.agregarRuta("Mendoza", "Neuquén", 800);
        red.agregarRuta("Neuquén", "Bariloche", 430);

        System.out.println(red.rutaMasCorta("Buenos Aires", "Bariloche"));
        // Buenos Aires -> Neuquén -> Bariloche (1580 km)
        System.out.println(red.rutaMasCorta("Buenos Aires", "Mendoza"));
        // Buenos Aires -> Rosario -> Córdoba -> Mendoza (1300 km)
    }
}
