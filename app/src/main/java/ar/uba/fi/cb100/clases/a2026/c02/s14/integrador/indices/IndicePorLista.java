package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Índice sobre una <b>lista enlazada</b> ordenada por clave. En una lista no
 * hay acceso directo, así que no se puede hacer búsqueda binaria: buscar es
 * <b>recorrer desde el principio</b> con el iterador hasta pasarse del
 * prefijo, O(n). Es el punto de comparación: el orden ayuda a cortar antes,
 * pero no a saltar.
 */
public class IndicePorLista implements IndiceDePersonas {

    private final CampoDeBusqueda campo;
    private final LinkedList<Entrada> entradas;

    public IndicePorLista(CampoDeBusqueda campo, List<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        this.campo = campo;
        List<Entrada> ordenadas = new ArrayList<>(personas.size());
        for (Persona persona : personas) {
            ordenadas.add(new Entrada(campo.claveDe(persona), persona));
        }
        Collections.sort(ordenadas);
        this.entradas = new LinkedList<>(ordenadas);
    }

    @Override
    public List<Persona> buscarPorPrefijo(String prefijo) {
        ValidacionesUtiles.validarDistintoDeNull(prefijo, "prefijo");
        String buscado = CampoDeBusqueda.normalizar(prefijo);
        List<Persona> encontradas = new ArrayList<>();
        for (Entrada entrada : entradas) {                 // con iterador: get(i) acá sería O(n) por vuelta
            if (entrada.clave().startsWith(buscado)) {
                encontradas.add(entrada.persona());
            } else if (entrada.clave().compareTo(buscado) > 0) {
                break;                                     // está ordenada: ya nos pasamos
            }
        }
        return encontradas;
    }

    @Override
    public CampoDeBusqueda getCampo() {
        return campo;
    }

    @Override
    public TipoDeIndice getTipo() {
        return TipoDeIndice.LISTA;
    }

    @Override
    public int getCantidad() {
        return entradas.size();
    }
}
