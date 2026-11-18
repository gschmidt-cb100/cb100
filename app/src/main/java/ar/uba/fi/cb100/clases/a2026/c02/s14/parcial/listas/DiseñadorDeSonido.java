package ar.uba.fi.cb100.clases.a2026.c02.s14.parcial.listas;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Punto 3 del parcial: filtro de ruido sobre una melodía.
 *
 * Hipótesis (lo que el enunciado no define):
 *  H1. Una frecuencia es anormal si se aparta del promedio de la ventana en
 *      más del porcentaje recibido: |frecuencia - promedio| > promedio * porcentaje / 100.
 *  H2. Corregir una nota es reemplazar su frecuencia por ese promedio.
 *  H3. {@code porcentaje} se expresa de 0 a 100 o más (20 significa 20 %).
 *  H4. Si la melodía tiene {@code ventana} notas o menos, no se modifica.
 */
public class DiseñadorDeSonido {
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Recorre la melodía una sola vez y corrige las notas de frecuencia
     * anormal, comparando cada una con el promedio de las últimas
     * {@code ventana} frecuencias procesadas.
     *
     * Las últimas frecuencias se guardan en un vector circular de tamaño
     * {@code ventana}: la nota nueva pisa a la más vieja. Junto con el vector
     * se mantiene la suma de lo que contiene, así el promedio no obliga a
     * recorrerlo. Costo: O(n) en tiempo y O(ventana) en memoria.
     *
     * La lista se recorre con for-each (iterador), que cuesta O(n) tanto en
     * ArrayList como en LinkedList; con get(i) una LinkedList costaría O(n²).
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

        double[] ultimasFrecuencias = new double[ventana];
        double suma = 0;
        int procesadas = 0;

        for (Nota nota : melodía.getNotas()) {
            if (procesadas >= ventana) {
                double promedio = suma / ventana;
                if (esAnormal(nota.getFrecuencia(), promedio, porcentaje)) {
                    nota.setFrecuencia(promedio);
                }
            }
            // La nota entra a la ventana con su frecuencia final (corregida o no)
            // y ocupa el lugar de la más vieja.
            int lugar = procesadas % ventana;
            suma += nota.getFrecuencia() - ultimasFrecuencias[lugar];
            ultimasFrecuencias[lugar] = nota.getFrecuencia();
            procesadas++;
        }
    }

    /** Devuelve true si la frecuencia se aparta del promedio en más del porcentaje (H1). */
    private boolean esAnormal(double frecuencia, double promedio, double porcentaje) {
        double tolerancia = promedio * porcentaje / 100;
        return Math.abs(frecuencia - promedio) > tolerancia;
    }
}
