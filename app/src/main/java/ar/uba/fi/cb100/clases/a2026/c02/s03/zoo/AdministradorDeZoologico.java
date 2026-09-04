package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ActaDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Jaula;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Zoologico;

import java.util.Arrays;

/**
 * TDA de <b>funcionalidad</b>: las operaciones del día a día sobre un
 * {@link Zoologico}. No tiene datos propios más allá del zoológico que
 * administra: el "día" ya no se cuenta a mano, es la fecha real que queda
 * registrada en el historial de raciones de cada animal.
 *
 * <h2>Dónde está el polimorfismo</h2>
 *
 * En {@link #hacerRondaDeAlimentacion()} hay una sola línea que alimenta:
 * <pre>{@code
 *     jaula.cuidador().alimentar(animal);
 * }</pre>
 * El administrador <b>no sabe</b> si ese cuidador es de leones, de jirafas o
 * de ovejas: para él es un {@code Cuidador} y punto. Es el objeto, según su
 * tipo <i>real</i>, el que decide qué rutina ejecutar. Por eso agregar una
 * especie nueva no obliga a tocar esta clase.
 */
public class AdministradorDeZoologico {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final Zoologico zoologico;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public AdministradorDeZoologico(Zoologico zoologico) {
        if (zoologico == null) {
            throw new IllegalArgumentException("el administrador necesita un zoológico");
        }
        this.zoologico = zoologico;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Recorre todas las jaulas y hace que cada cuidador intente alimentar a
     * cada animal de su jaula. Devuelve un acta por cada intento.
     *
     * <p>Regla R8: una jaula vacía se saltea sin generar actas.
     * Regla R10: una jaula con animales y sin cuidador es un <b>error de
     * configuración</b>, no una situación del negocio, y por eso lanza
     * excepción en vez de devolver un resultado.
     */
    public ActaDeAlimentacion[] hacerRondaDeAlimentacion() {
        ActaDeAlimentacion[] actas = new ActaDeAlimentacion[zoologico.getCantidadDeAnimales()];
        int cantidad = 0;

        for (int j = 1; j <= zoologico.getCantidadDeJaulas(); j++) {
            Jaula jaula = zoologico.getJaula(j);
            if (jaula.estaVacia()) {
                continue;                                  // R8
            }
            if (!jaula.tieneCuidador()) {
                throw new IllegalStateException("la jaula " + jaula.getNumero() + " tiene animales pero no tiene cuidador");
            }
            for (Animal animal : jaula.getAnimales()) {
                actas[cantidad] = jaula.getCuidador().alimentar(animal);   // <-- polimorfismo
                cantidad++;
            }
        }
        return actas;
    }

    /** Los animales que hoy están por debajo de su peso óptimo. Regla R2. */
    public Animal[] animalesConPesoBajo() {
        Animal[] bajos = new Animal[zoologico.getCantidadDeAnimales()];
        int cantidad = 0;
        for (int j = 1; j <= zoologico.getCantidadDeJaulas(); j++) {
            for (Animal animal : zoologico.getJaula(j).getAnimales()) {
                if (!animal.estaBienAlimentado()) {
                    bajos[cantidad] = animal;
                    cantidad++;
                }
            }
        }
        return Arrays.copyOf(bajos, cantidad);
    }

    public void alimentarAnimales() {
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public Zoologico getZoologico() {
        return zoologico;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
