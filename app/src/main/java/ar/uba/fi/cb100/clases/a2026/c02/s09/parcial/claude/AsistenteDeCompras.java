package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.claude;

import java.util.ArrayList;
import java.util.List;

/**
 * Atiende a una fila de compradores contra un stock de atuendos.
 *
 * <p>Esta clase sólo <b>orquesta</b>: recorre compradores y stock, y le
 * pregunta al comprador si puede llevarse cada par. Las reglas de negocio
 * (mismo color, distinto tipo, presupuesto) no están acá sino en
 * {@link Atuendo#combinaCon(Atuendo)} y en
 * {@link Comprador#puedeComprar(Atuendo, Atuendo)}, que son los dueños de los
 * datos que esas reglas usan.
 */
public class AsistenteDeCompras {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code stockDeAtuendos} y {@code compradores} no son nulos ni
     *      contienen nulos.
     * post: busca en {@code stockDeAtuendos} dos atuendos por comprador que
     *       cumplan simultáneamente:
     *       <ol>
     *         <li>ambas prendas tienen exactamente el mismo color;</li>
     *         <li>precio del atuendo 1 + precio del atuendo 2 &lt;= presupuesto;</li>
     *         <li>los dos atuendos son de distinto tipo.</li>
     *       </ol>
     *       Los compradores se atienden en el orden de la lista y cada uno se
     *       lleva, como mucho, el <b>primer</b> par que le sirve (en el orden
     *       del stock). Un atuendo se compra una sola vez: cuando alguien se
     *       lo lleva, deja de estar disponible para los que siguen. Si a un
     *       comprador no le sirve ningún par, no compra nada y se pasa al
     *       siguiente.
     *       <p>Devuelve, en una lista nueva, todos los atuendos que nadie
     *       compró, en el mismo orden relativo en que estaban en el stock.
     *       La lista recibida como {@code stockDeAtuendos} <b>no se
     *       modifica</b>.
     *       <p>Si hay menos de dos atuendos en stock, nadie puede comprar y
     *       se devuelve una copia del stock tal cual.
     *
     * @param stockDeAtuendos atuendos disponibles al empezar
     * @param compradores fila de compradores, en el orden en que se atienden
     * @return los atuendos no comprados
     */
    public List<Atuendo> comprarAtuendos(List<Atuendo> stockDeAtuendos, List<Comprador> compradores) {
        validarSinNulos(stockDeAtuendos, "el stock de atuendos");
        validarSinNulos(compradores, "la lista de compradores");

        // Trabajo sobre una copia: lo que el que llama nos prestó, se lo
        // devolvemos intacto. La copia va perdiendo lo que se vende y al
        // final es exactamente "lo que quedó sin comprar".
        List<Atuendo> noComprados = new ArrayList<>(stockDeAtuendos);

        for (Comprador comprador : compradores) {
            venderUnParA(comprador, noComprados);
        }

        return noComprados;
    }

    /**
     * pre: {@code comprador} y {@code stock} no son nulos.
     * post: busca en {@code stock} el primer par de atuendos que el comprador
     *       pueda comprar. Si lo encuentra, saca los dos del stock y devuelve
     *       {@code true}. Si no, deja el stock como estaba y devuelve
     *       {@code false}.
     */
    private boolean venderUnParA(Comprador comprador, List<Atuendo> stock) {
        for (int i = 0; i < stock.size(); i++) {
            Atuendo atuendo1 = stock.get(i);

            // Poda: si el atuendo 1 solo ya no entra en el presupuesto, no
            // vale la pena buscarle compañero (el otro sólo sumaría).
            if (!comprador.puedePagar(atuendo1.getPrecio())) {
                continue;
            }

            // Arranco en i + 1: los pares (i, j) con j < i ya se miraron
            // cuando j era el atuendo 1, y un atuendo no combina consigo mismo.
            for (int j = i + 1; j < stock.size(); j++) {
                Atuendo atuendo2 = stock.get(j);

                if (comprador.puedeComprar(atuendo1, atuendo2)) {
                    // Primero el de mayor índice: si sacara el i primero, el j
                    // se correría un lugar y borraría el equivocado.
                    stock.remove(j);
                    stock.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * post: lanza {@link IllegalArgumentException} si {@code lista} es nula o
     *       contiene algún nulo. {@code nombre} se usa sólo para el mensaje.
     */
    private static void validarSinNulos(List<?> lista, String nombre) {
        if (lista == null) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo");
        }
        // No uso lista.contains(null): las listas inmutables (List.of) lanzan
        // NullPointerException ante esa consulta en vez de responder false.
        for (Object elemento : lista) {
            if (elemento == null) {
                throw new IllegalArgumentException(nombre + " no puede contener nulos");
            }
        }
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
