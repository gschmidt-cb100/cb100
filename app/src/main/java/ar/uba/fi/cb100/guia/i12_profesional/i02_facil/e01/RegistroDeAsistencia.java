package ar.uba.fi.cb100.guia.i12_profesional.i02_facil.e01;

import java.util.HashSet;
import java.util.Set;

/**
 * e01: registrar los alumnos presentes de una clase y responder rápido si
 * alguien estuvo.
 * <p>
 * <b>Elección de estructura:</b> {@link HashSet}. Necesitamos exactamente dos
 * cosas: que no haya repetidos (si alguien firma dos veces cuenta una sola) y
 * que {@code estuvo(nombre)} sea rápido. Un conjunto por tabla de hash (U7)
 * da {@code add} y {@code contains} en O(1) promedio. Una lista obligaría a
 * recorrer todo para chequear pertenencia (O(n)), y un {@code TreeSet} (U8)
 * pagaría O(log n) por un orden que acá nadie pidió.
 */
public class RegistroDeAsistencia {

    // El HashSet garantiza unicidad solo: add de un repetido devuelve false.
    private final Set<String> presentes = new HashSet<>();

    /**
     * Registra a un alumno como presente. Registrarlo dos veces no lo duplica.
     *
     * @param nombre nombre del alumno
     * @return {@code true} si es la primera vez que se lo registra hoy
     */
    public boolean registrar(String nombre) {
        return presentes.add(nombre);   // O(1) promedio (U7)
    }

    /**
     * ¿Estuvo el alumno en la clase?
     *
     * @param nombre nombre a consultar
     * @return {@code true} si fue registrado como presente
     */
    public boolean estuvo(String nombre) {
        return presentes.contains(nombre);   // O(1) promedio (U7)
    }

    /** @return cantidad de alumnos distintos que asistieron */
    public int cantidadDePresentes() {
        return presentes.size();
    }

    public static void main(String[] args) {
        RegistroDeAsistencia registro = new RegistroDeAsistencia();
        registro.registrar("Ana");
        registro.registrar("Beto");
        registro.registrar("Ana");   // repetido: no suma
        System.out.println("Presentes: " + registro.cantidadDePresentes());  // 2
        System.out.println("¿Estuvo Ana? " + registro.estuvo("Ana"));        // true
        System.out.println("¿Estuvo Carla? " + registro.estuvo("Carla"));    // false
    }
}
