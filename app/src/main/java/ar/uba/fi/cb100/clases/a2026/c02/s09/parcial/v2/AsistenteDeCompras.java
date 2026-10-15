package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.v2;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class AsistenteDeCompras {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     *
     */
    public List<Atuendo> comprarAtuendos(List<Atuendo> stockDeAtuendos, List<Comprador> compradores) {
        ValidacionesUtiles.validarDistintoDeNull(stockDeAtuendos, "el stock de atuendos");
        ValidacionesUtiles.validarDistintoDeNull(compradores, "la lista de compradores");
        for (Comprador comprador : compradores) {
            comprarAtuendos(stockDeAtuendos, comprador);
        }
        return stockDeAtuendos;
    }

    /**
     * Intenta comprar atuendos del stock para el comprador dado.
     * @param stock: la lista de atuendos disponibles para comprar. Se modificará removiendo los atuendos comprados.
     * @param comprador:    el comprador que intenta comprar los atuendos.
     * @return true si el comprador pudo comprar al menos un par de atuendos, false si no pudo comprar nada.
     */
    private boolean comprarAtuendos(List<Atuendo> stock, Comprador comprador) {
        ListIterator<Atuendo> primero = stock.listIterator();
        while (primero.hasNext()) {
            Atuendo atuendo1 = primero.next();
            if (!puedePagar(comprador, atuendo1.getPrecio())) {
                continue;
            }
            // Segundo cursor: arranca justo después del atuendo 1, así no se
            // repiten pares ya mirados ni se compara un atuendo consigo mismo.
            ListIterator<Atuendo> segundo = stock.listIterator(primero.nextIndex());
            while (segundo.hasNext()) {
                Atuendo atuendo2 = segundo.next();
                if (puedeComprar(comprador, atuendo1, atuendo2)) {
                    segundo.remove();        // saca el atuendo 2 con el cursor que lo encontró
                    stock.remove(atuendo1);  // NO primero.remove(): ese cursor ya quedó inválido
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si el comprador puede comprar el par de atuendos dado.
     * @param comprador: el comprador que intenta comprar los atuendos.
     * @param atuendo1: el primer atuendo del par.
     * @param atuendo2: el segundo atuendo del par.
     * @return true si el comprador puede comprar ambos atuendos, false si no puede.
     */
    private boolean puedeComprar(Comprador comprador, Atuendo atuendo1, Atuendo atuendo2) {
        return puedePagar(comprador, atuendo1.getPrecio() + atuendo2.getPrecio()) &&
                atuendo1.getColor().equals(atuendo2.getColor()) &&
                !atuendo1.getTipo().equals(atuendo2.getTipo());
    }

    /**
     * Verifica si el comprador tiene presupuesto suficiente para pagar el precio dado.
     * @param comprador
     * @param precio
     * @return
     */
    private boolean puedePagar(Comprador comprador, double precio) {
        return comprador.getPresupuesto() >= precio;
    }
}
