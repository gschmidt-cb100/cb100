package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Índice sobre un <b>árbol binario de búsqueda balanceado</b>: un
 * {@code TreeMap} (rojo-negro) de clave a las personas que la tienen. Buscar
 * por prefijo es una <b>consulta por rango</b>: todas las claves entre el
 * prefijo y el prefijo seguido del mayor carácter posible. O(log n) para
 * llegar más O(k) por los resultados; y agregar una persona nueva también
 * sería O(log n), que es lo que el vector ordenado no puede.
 */
public class IndicePorArbol implements IndiceDePersonas {

    private static final char MAYOR_CARACTER = Character.MAX_VALUE;

    private final CampoDeBusqueda campo;
    private final TreeMap<String, List<Persona>> personasPorClave;
    private final int cantidad;

    public IndicePorArbol(CampoDeBusqueda campo, List<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        this.campo = campo;
        this.personasPorClave = new TreeMap<>();
        for (Persona persona : personas) {
            personasPorClave.computeIfAbsent(campo.claveDe(persona), clave -> new ArrayList<>(1)).add(persona);
        }
        for (List<Persona> conEsaClave : personasPorClave.values()) {
            conEsaClave.sort(Comparator.comparingLong(Persona::dni));   // mismo desempate que los otros índices
        }
        this.cantidad = personas.size();
    }

    @Override
    public List<Persona> buscarPorPrefijo(String prefijo) {
        ValidacionesUtiles.validarDistintoDeNull(prefijo, "prefijo");
        String buscado = CampoDeBusqueda.normalizar(prefijo);
        List<Persona> encontradas = new ArrayList<>();
        for (List<Persona> conEsaClave
                : personasPorClave.subMap(buscado, true, buscado + MAYOR_CARACTER, true).values()) {
            encontradas.addAll(conEsaClave);
        }
        return encontradas;
    }

    @Override
    public CampoDeBusqueda getCampo() {
        return campo;
    }

    @Override
    public TipoDeIndice getTipo() {
        return TipoDeIndice.ARBOL;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }
}
