package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.a1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

        // copia: así no se modifica la lista que me pasan
        List<Atuendo> noComprados = new ArrayList<>(stockDeAtuendos);

        for (Comprador c : compradores) {
            double presupuesto = c.getPresupuesto();
            Atuendo elegido1 = null;
            Atuendo elegido2 = null;

            Iterator<Atuendo> it1 = noComprados.iterator();
            while (it1.hasNext() && elegido1 == null) {
                Atuendo a1 = it1.next();

                if (a1.getPrecio() <= presupuesto) {
                    Iterator<Atuendo> it2 = noComprados.iterator();
                    while (it2.hasNext() && elegido1 == null) {
                        Atuendo a2 = it2.next();

                        if (!a1.getTipo().equals(a2.getTipo())
                                && a1.getColor().equals(a2.getColor())
                                && a1.getPrecio() + a2.getPrecio() <= presupuesto) {
                            elegido1 = a1;
                            elegido2 = a2;
                        }
                    }
                }
            }

            // ya salí de los dos recorridos: recién ahora borro
            if (elegido1 != null) {
                noComprados.remove(elegido1);
                noComprados.remove(elegido2);
            }
        }
        return noComprados;
    }
}
