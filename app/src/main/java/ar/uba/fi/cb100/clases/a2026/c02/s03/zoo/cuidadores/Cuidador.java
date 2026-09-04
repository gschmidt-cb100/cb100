package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ActaDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ResultadoDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;

import java.time.LocalDate;

/**
 * TDA <b>Cuidador</b>. Abstracto: todo cuidador está especializado en una
 * especie, y es esa especialización la que define qué rutina sabe hacer.
 *
 * <h2>El método plantilla</h2>
 *
 * {@link #alimentar(Animal)} es el corazón de la clase, y es {@code final}
 * <b>a propósito</b>. Fija el <i>procedimiento</i>, que es el mismo para todos:
 * <ol>
 *   <li>¿Es de mi especialidad? Si no, no lo toco (regla R6).</li>
 *   <li>¿Ya comió todas sus veces de hoy? Si sí, salteo (regla R5).</li>
 *   <li>Preparo la ración <b>según mi rutina</b> — acá está el polimorfismo.</li>
 *   <li>Se la doy al animal, que actualiza su energía (regla R7).</li>
 *   <li>Dejo registrado lo que pasó en un acta.</li>
 * </ol>
 *
 * Las subclases <b>no pueden</b> cambiar ese orden ni saltearse un paso:
 * sólo completan los tres huecos que la plantilla les deja
 * ({@link #puedeAtender}, {@link #comidasPorDia}, {@link #prepararRacion}).
 * Así, es imposible escribir un cuidador que alimente sin actualizar la
 * energía, o que alimente a un animal que no le corresponde.
 *
 * <p>Esto se llama <b>método plantilla</b> (<i>template method</i>): la clase
 * base escribe el esqueleto del algoritmo y las subclases rellenan los pasos
 * que varían.
 */
public abstract class Cuidador {

    private final String nombre;
    private final int legajo;

    protected Cuidador(String nombre, int legajo) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el cuidador necesita un nombre");
        }
        if (legajo <= 0) {
            throw new IllegalArgumentException("el legajo debe ser positivo");
        }
        this.nombre = nombre;
        this.legajo = legajo;
    }

    // ==================================================================
    //  El procedimiento: igual para todos, y nadie lo puede cambiar
    // ==================================================================

    /**
     * Intenta alimentar a un animal siguiendo el procedimiento del zoológico.
     * Nunca lanza excepción por una situación normal del negocio: lo que pasó
     * queda dicho en el acta.
     *
     * @param animal el animal a alimentar; no puede ser nulo
     * @return el acta con el resultado
     */
    public final ActaDeAlimentacion alimentar(Animal animal) {
        if (animal == null) {
            throw new IllegalArgumentException("no se puede alimentar a un animal nulo");
        }
        double pesoAnterior = animal.getPeso();

        if (!puedeAtender(animal)) {                                       // R6
            return acta(animal, ResultadoDeAlimentacion.FUERA_DE_ESPECIALIDAD,
                    null, pesoAnterior, pesoAnterior);
        }
        if (!necesitaRacion(animal)) {                                     // R5
            return acta(animal, ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA,
                    null, pesoAnterior, pesoAnterior);
        }

        Racion racion = prepararRacion(animal);   // <-- polimorfismo: cada cuidador, su rutina
        animal.registrarRacion(racion);
        return acta(animal, ResultadoDeAlimentacion.ALIMENTADO,
                racion.pasos(), pesoAnterior, animal.getPeso());
    }

    /**
     * Regla R5: ¿le toca comer? Sí, mientras no haya llegado a su cuota de
     * hoy. La <b>cuota</b> la pone cada especialidad con {@link #comidasPorDia()}
     * (león 1, jirafa 2, oveja 3); la <b>regla</b> de compararla con lo que ya
     * comió es la misma para todos, por eso vive acá y no se repite tres veces.
     *
     * <p>Se apoya en el historial del animal: cuenta las raciones con fecha de
     * hoy. No hace falta ningún contador aparte que haya que reiniciar cada día.
     *
     * <p>Es {@code protected} y <b>no</b> {@code final} a propósito: una especie
     * con un régimen distinto (a demanda, según el peso) puede redefinirla.
     */
    protected boolean necesitaRacion(Animal animal) {
        return animal.getCantidadDeRaciones(LocalDate.now()) < comidasPorDia();
    }

    // ==================================================================
    //  Los huecos que completa cada especialidad
    // ==================================================================

    /** ¿Este cuidador sabe atender a este animal? (regla R6) */
    public abstract boolean puedeAtender(Animal animal);

    /** Cuántas veces por día come la especie que atiende. (regla R4) */
    protected abstract int comidasPorDia();

    /**
     * La rutina propia de la especialidad. Recibe el animal para poder
     * adaptarse a él (a su altura, a su agresividad), y devuelve la ración
     * lista para servir.
     *
     * <p>Se puede asumir que {@code animal} ya pasó por {@link #puedeAtender}:
     * el método plantilla lo garantiza.
     */
    protected abstract Racion prepararRacion(Animal animal);

    // ==================================================================
    //  Consultas
    // ==================================================================

    public String nombre() {
        return nombre;
    }

    public int legajo() {
        return legajo;
    }

    /** Descripción corta de la especialidad, para los informes. */
    public abstract String especialidad();

    @Override
    public String toString() {
        return nombre + " (legajo " + legajo + ", " + especialidad() + ")";
    }

    private ActaDeAlimentacion acta(Animal animal, ResultadoDeAlimentacion resultado,
                                    String[] pasos, double antes, double despues) {
        return new ActaDeAlimentacion(nombre, animal.getNombre(), animal.especie(),
                resultado, pasos, antes, despues);
    }
}
