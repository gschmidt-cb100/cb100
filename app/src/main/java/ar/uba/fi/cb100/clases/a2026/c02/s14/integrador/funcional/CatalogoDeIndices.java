package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.IndiceDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.indices.TipoDeIndice;

/**
 * Arma los nueve índices (tres estructuras por tres campos), mide lo que
 * cuesta cada uno y resuelve las búsquedas midiendo el tiempo.
 * <p>
 * Sobre la medición de memoria: Java no informa cuánto ocupa un objeto. Se
 * estima como la diferencia de memoria usada antes y después de armar el
 * índice, pidiendo recolección de basura en los dos momentos. Es una
 * aproximación (el recolector no obedece órdenes), suficiente para comparar
 * las tres estructuras entre sí, que es para lo que se usa.
 */
public class CatalogoDeIndices {

    /** Cuántas búsquedas se promedian para medir el tiempo de cada índice. */
    public static final int BUSQUEDAS_DE_MUESTRA = 200;
    private static final long SEMILLA_DE_LA_MUESTRA = 100L;

    private final Map<TipoDeIndice, Map<CampoDeBusqueda, IndiceDePersonas>> indices = new EnumMap<>(TipoDeIndice.class);
    private final List<Medicion> mediciones = new ArrayList<>();

    /**
     * pre: {@code personas} no es null.
     * post: quedan armados y medidos los nueve índices.
     */
    public CatalogoDeIndices(List<Persona> personas) {
        ValidacionesUtiles.validarDistintoDeNull(personas, "personas");
        for (TipoDeIndice tipo : TipoDeIndice.values()) {
            indices.put(tipo, new EnumMap<>(CampoDeBusqueda.class));
            for (CampoDeBusqueda campo : CampoDeBusqueda.values()) {
                long memoriaAntes = memoriaEnUso();
                long inicio = System.nanoTime();
                IndiceDePersonas indice = tipo.crear(campo, personas);
                long nanosDeConstruccion = System.nanoTime() - inicio;
                long bytes = Math.max(0, memoriaEnUso() - memoriaAntes);
                indices.get(tipo).put(campo, indice);
                mediciones.add(new Medicion(tipo, campo, bytes, nanosDeConstruccion,
                        medirBusquedas(indice, personas)));
            }
        }
    }

    /**
     * pre: ningún argumento es null.
     * post: devuelve las personas cuya clave en {@code campo} empieza con
     *       {@code prefijo}, buscadas con el índice de ese tipo, y el tiempo que llevó.
     */
    public ResultadoDeBusqueda buscar(TipoDeIndice tipo, CampoDeBusqueda campo, String prefijo) {
        ValidacionesUtiles.validarDistintoDeNull(tipo, "tipo de índice");
        ValidacionesUtiles.validarDistintoDeNull(campo, "campo");
        ValidacionesUtiles.validarDistintoDeNull(prefijo, "prefijo");
        IndiceDePersonas indice = indices.get(tipo).get(campo);
        long inicio = System.nanoTime();
        List<Persona> encontradas = indice.buscarPorPrefijo(prefijo);
        long nanos = System.nanoTime() - inicio;
        return new ResultadoDeBusqueda("índice por " + tipo.getTitulo().toLowerCase() + " sobre " + campo.getTitulo(),
                encontradas, nanos);
    }

    /** post: devuelve la misma búsqueda hecha con las tres estructuras, para comparar tiempos. */
    public List<ResultadoDeBusqueda> buscarConTodos(CampoDeBusqueda campo, String prefijo) {
        List<ResultadoDeBusqueda> resultados = new ArrayList<>();
        for (TipoDeIndice tipo : TipoDeIndice.values()) {
            resultados.add(buscar(tipo, campo, prefijo));
        }
        return resultados;
    }

    /** post: devuelve una copia de las mediciones, en el orden en que se armaron los índices. */
    public List<Medicion> getMediciones() {
        return List.copyOf(mediciones);
    }

    /** Promedia el tiempo de buscar claves completas elegidas al azar entre las personas. */
    private static long medirBusquedas(IndiceDePersonas indice, List<Persona> personas) {
        if (personas.isEmpty()) {
            return 0;
        }
        Random azar = new Random(SEMILLA_DE_LA_MUESTRA);
        List<String> claves = new ArrayList<>();
        for (int i = 0; i < BUSQUEDAS_DE_MUESTRA; i++) {
            claves.add(indice.getCampo().claveDe(personas.get(azar.nextInt(personas.size()))));
        }
        long inicio = System.nanoTime();
        for (String clave : claves) {
            indice.buscarPorPrefijo(clave);
        }
        return (System.nanoTime() - inicio) / BUSQUEDAS_DE_MUESTRA;
    }

    private static long memoriaEnUso() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 3; i++) {
            System.gc();
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
