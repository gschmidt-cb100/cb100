package ar.uba.fi.cb100.guia.i05_lineales.i04_dificil.e10;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion del TAD Lista apoyada en un ArrayList de la API.
 *
 * No hay nada que programar "a mano": cada operacion del contrato se traduce
 * a la operacion equivalente de ArrayList. Lo que se ejercita es la
 * ADAPTACION: nuestro contrato (agregar/insertar/eliminar/obtener/tamanio) se
 * cumple con otra clase que ya lo sabe hacer (add/add(i,x)/remove(i)/get/size).
 *
 * Complejidad (la hereda de ArrayList): obtener O(1), agregar O(1) amortizado,
 * insertar y eliminar O(n) porque corren los elementos que siguen.
 */
public class ListaSobreArrayList<T> implements Lista<T> {

    private final List<T> datos = new ArrayList<>();

    @Override
    public void agregar(T x) {
        datos.add(x);
    }

    @Override
    public void insertar(int i, T x) {
        datos.add(i, x);        // ArrayList ya valida 0 <= i <= size
    }

    @Override
    public T eliminar(int i) {
        return datos.remove(i); // remove(int): por posicion, no por valor
    }

    @Override
    public T obtener(int i) {
        return datos.get(i);
    }

    @Override
    public int tamanio() {
        return datos.size();
    }

    @Override
    public String toString() {
        return datos.toString();
    }
}
