package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listasv2;

import java.util.ArrayDeque;
import java.util.Queue;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas.Melodia;
import ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas.Nota;

/**
 * Punto 3 del parcial, segunda versión: la ventana es una cola en vez de un
 * vector circular. Usa las mismas {@link Melodia} y {@link Nota} de la
 * primera versión y devuelve exactamente lo mismo.
 *
 * Por qué una cola: la ventana es FIFO. La frecuencia que entra es la de la
 * nota recién procesada y la que sale es la más vieja, que es justo lo que
 * hacen encolar y desencolar. Con el vector circular ese comportamiento hay
 * que armarlo a mano con el índice {@code procesadas % ventana}.
 *
 * Hipótesis (las mismas de la primera versión):
 *  H1. Una frecuencia es anormal si se aparta del promedio de la ventana en
 *      más del porcentaje recibido: |frecuencia - promedio| > promedio * porcentaje / 100.
 *  H2. Corregir una nota es reemplazar su frecuencia por ese promedio.
 *  H3. {@code porcentaje} se expresa de 0 a 100 o más (20 significa 20 %).
 *  H4. Si la melodía tiene {@code ventana} notas o menos, no se modifica.
 *  H5. La cola nunca tiene más de {@code ventana} elementos, así que cumple
 *      la regla 7: su tamaño no depende de la cantidad de notas.
 */
public class DiseñadorDeSonido {
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Recorre la melodía una sola vez y corrige las notas de frecuencia
     * anormal, comparando cada una con el promedio de las últimas
     * {@code ventana} frecuencias procesadas.
     *
     * Las últimas frecuencias están en una cola. Mientras la cola no está
     * llena, la nota sólo se encola. Cuando está llena, se evalúa la nota
     * contra el promedio, se desencola la frecuencia más vieja y se encola
     * la de la nota. Junto con la cola se mantiene la suma de lo que
     * contiene, así el promedio no obliga a recorrerla.
     * Costo: O(n) en tiempo y O(ventana) en memoria.
     *
     * pre: {@code melodía} no es null; {@code porcentaje} es mayor o igual a
     *      cero; {@code ventana} es mayor que cero.
     * post: las notas de frecuencia anormal quedan con el promedio de su
     *       ventana como frecuencia. Las primeras {@code ventana} notas no
     *       cambian. No se crean notas ni se cambia la lista: se modifican
     *       las notas existentes. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public void filtrarRuido(Melodia melodía, double porcentaje, int ventana) {
        ValidacionesUtiles.validarDistintoDeNull(melodía, "melodía");
        ValidacionesUtiles.validarFalso(porcentaje < 0, "El porcentaje no puede ser negativo.");
        ValidacionesUtiles.esMayorQueCero(ventana, "ventana");

        Queue<Double> ultimasFrecuencias = new ArrayDeque<>(ventana);
        double suma = 0;

        for (Nota nota : melodía.getNotas()) {
            if (ultimasFrecuencias.size() == ventana) {
                double promedio = suma / ventana;
                if (esAnormal(nota.getFrecuencia(), promedio, porcentaje)) {
                    nota.setFrecuencia(promedio);
                }
                // Sale la frecuencia más vieja para hacerle lugar a la nueva.
                suma -= ultimasFrecuencias.remove();
            }
            // La nota entra a la ventana con su frecuencia final (corregida o no).
            ultimasFrecuencias.add(nota.getFrecuencia());
            suma += nota.getFrecuencia();
        }
    }

    /** Devuelve true si la frecuencia se aparta del promedio en más del porcentaje (H1). */
    private boolean esAnormal(double frecuencia, double promedio, double porcentaje) {
        double tolerancia = promedio * porcentaje / 100;
        return Math.abs(frecuencia - promedio) > tolerancia;
    }
}
