package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Índice sobre un <b>vector ordenado</b>: un {@code ArrayList} de entradas
 * ordenadas por clave. Buscar es una <b>búsqueda binaria</b> de la primera
 * entrada que no es menor que el prefijo, O(log n), y después avanzar
 * mientras las claves sigan empezando igual, O(k) para k resultados.
 * Armarlo cuesta el ordenamiento: O(n log n).
 */
public class IndicePorVector implements IndiceDePersonas {

    private final CampoDeBusqueda campo;
    private final List<Entrada> entradas;

    public IndicePorVector(CampoDeBusqueda campo, List<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        this.campo = campo;
        this.entradas = new ArrayList<>(personas.size());
        for (Persona persona : personas) {
            entradas.add(new Entrada(campo.claveDe(persona), persona));
        }
        Collections.sort(entradas);
    }

    @Override
    public List<Persona> buscarPorPrefijo(String prefijo) {
        ValidacionesUtiles.validarDistintoDeNull(prefijo, "prefijo");
        String buscado = CampoDeBusqueda.normalizar(prefijo);
        List<Persona> encontradas = new ArrayList<>();
        for (int i = primeraPosicionNoMenorQue(buscado);
             i < entradas.size() && entradas.get(i).clave().startsWith(buscado); i++) {
            encontradas.add(entradas.get(i).persona());
        }
        return encontradas;
    }

    /** Búsqueda binaria de la primera entrada cuya clave es mayor o igual que la buscada. */
    private int primeraPosicionNoMenorQue(String buscado) {
        int desde = 0;
        int hasta = entradas.size();
        while (desde < hasta) {
            int medio = (desde + hasta) / 2;
            if (entradas.get(medio).clave().compareTo(buscado) < 0) {
                desde = medio + 1;
            } else {
                hasta = medio;
            }
        }
        return desde;
    }

    @Override
    public CampoDeBusqueda getCampo() {
        return campo;
    }

    @Override
    public TipoDeIndice getTipo() {
        return TipoDeIndice.VECTOR;
    }

    @Override
    public int getCantidad() {
        return entradas.size();
    }
}
