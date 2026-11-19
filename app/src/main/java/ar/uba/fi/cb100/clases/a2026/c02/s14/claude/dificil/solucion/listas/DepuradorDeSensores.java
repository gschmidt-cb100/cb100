package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

import java.util.Iterator;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * Punto 3 del parcial. Clase funcional: no guarda estado, sólo procesa el
 * registro que recibe.
 *
 * Estrategia (antes de escribir código):
 *  1. Recorrer la lista una sola vez con su {@link Iterator}. Es la única
 *     forma de cumplir la regla 7: avanzar y eliminar con el iterador son
 *     O(1) en una LinkedList, y no se accede nunca por posición.
 *  2. Por cada lectura hay tres subproblemas, cada uno con su método o su TDA:
 *       a. corregirla si es anómala          -> corregirSiEsAnomala
 *       b. recordarla en la ventana          -> VentanaDeMediana.agregar
 *       c. comprimirla si repite a la previa -> Lectura.absorber + it.remove()
 *  3. La "lectura anterior que permanece" se guarda en una variable: es la
 *     última que NO se eliminó. No hace falta volver atrás en la lista.
 *
 * Costo (regla 9): cada lectura cuesta O(ventana) por la actualización de la
 * ventana (dos corrimientos en un vector de tamaño ventana) y O(1) por todo
 * lo demás: obtener la mediana, corregir, comparar con la anterior y
 * eliminar con el iterador. Son n lecturas: O(n · ventana) en tiempo y
 * O(ventana) en memoria. Con get(i) o remove(i) cada acceso sería O(n) y el
 * total O(n²).
 *
 * Hipótesis:
 *  H1. La lectura i (contando desde 1) se evalúa si i > ventana. "Las
 *      primeras ventana lecturas" son las del registro ORIGINAL, se hayan
 *      eliminado o no por compresión.
 *  H2. Una lectura corregida cuenta en el resultado aunque después se
 *      elimine por la regla 5.
 *  H3. La comparación de la regla 3 es estricta: si la diferencia es igual
 *      a la tolerancia, la lectura no es anómala.
 *  H4. Si el registro tiene ventana lecturas o menos no se corrige nada,
 *      pero la compresión se aplica igual.
 *  H5. La compresión compara con la última lectura que quedó en la lista:
 *      tres lecturas iguales seguidas quedan en una sola con la suma de las
 *      tres duraciones.
 */
public class DepuradorDeSensores {
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Depura las lecturas del registro: corrige las anómalas contra la
     * mediana de las últimas {@code ventana} procesadas y comprime las
     * repetidas consecutivas.
     *
     * pre: {@code registro} no es null; {@code tolerancia} es mayor o igual
     *      a cero; {@code ventana} es impar y mayor que cero.
     * post: las lecturas anómalas quedan con la mediana de su ventana como
     *       valor; no quedan dos lecturas consecutivas con el mismo valor
     *       (la segunda se eliminó y su duración se sumó a la primera). Se
     *       modifica la lista del registro, no se crea otra. Devuelve la
     *       cantidad de lecturas corregidas. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public int depurar(Registro registro, int tolerancia, int ventana) {
        ValidacionesUtiles.validarDistintoDeNull(registro, "registro");
        ValidacionesUtiles.validarFalso(tolerancia < 0, "La tolerancia no puede ser negativa.");
        VentanaDeMediana ultimosValores = new VentanaDeMediana(ventana);   // valida que sea impar y mayor que cero

        int corregidas = 0;
        Lectura anterior = null;                                            // la última que permanece en la lista
        Iterator<Lectura> iterador = registro.getLecturas().iterator();
        while (iterador.hasNext()) {
            Lectura lectura = iterador.next();

            if (ultimosValores.estaLlena() &&
                corregirSiEsAnomala(lectura, ultimosValores.getMediana(), tolerancia)) {
                corregidas++;
            }
            // Entra a la ventana con su valor final (corregido o no), aunque después se elimine.
            ultimosValores.agregar(lectura.getValor());

            if (anterior != null && anterior.tieneElMismoValorQue(lectura)) {
                anterior.absorber(lectura);
                iterador.remove();
            } else {
                anterior = lectura;
            }
        }
        return corregidas;
    }

    /**
     * Si la lectura se aparta de la mediana en más de la tolerancia (H3), le
     * pone la mediana como valor.
     * post: devuelve true si la corrigió.
     */
    private boolean corregirSiEsAnomala(Lectura lectura, int mediana, int tolerancia) {
        if (lectura.seApartaDe(mediana, tolerancia)) {
            lectura.corregir(mediana);
            return true;
        }
        return false;
    }
}
