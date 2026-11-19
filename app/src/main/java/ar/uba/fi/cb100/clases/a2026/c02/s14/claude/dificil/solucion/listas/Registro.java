package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

import java.util.LinkedList;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * El registro de un sensor: su nombre y la lista de lecturas en el orden en
 * que se tomaron.
 * Los atributos son los que da el enunciado; se agrega lo mínimo para usarlo.
 *
 * La lista es una {@link LinkedList}, como dice la regla 7 del enunciado:
 * eliminar con el iterador es O(1), pero llegar a una posición es O(n).
 */
public class Registro {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private List<Lectura> lecturas;
    private String sensor;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code sensor} no es null ni está vacío.
     * post: crea el registro sin lecturas. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Registro(String sensor) {
        ValidacionesUtiles.validarDistintoDeNull(sensor, "sensor");
        ValidacionesUtiles.validarFalso(sensor.isBlank(), "El nombre del sensor no puede estar vacío.");
        this.sensor = sensor;
        this.lecturas = new LinkedList<>();
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code lectura} no es null.
     * post: la lectura queda al final del registro.
     */
    public void agregarLectura(Lectura lectura) {
        ValidacionesUtiles.validarDistintoDeNull(lectura, "lectura");
        lecturas.add(lectura);
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "Registro de " + sensor + ": " + lecturas;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve la lista de lecturas del registro (la misma, no una copia). */
    public List<Lectura> getLecturas() {
        return lecturas;
    }

    public String getSensor() {
        return sensor;
    }
}
