package ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos.Ingrediente;
import ar.uba.fi.cb100.clases.a2026.c02.s13.tarea4.datos.Receta;

/**
 * Clase funcional: no guarda estado, solo busca recetas.
 *
 * Estrategia:
 *  1. Recorrer las recetas alternativas en orden y devolver la primera que sea sustituta.
 *  2. Una alternativa es sustituta si CADA ingrediente de la receta por sustituir es sustituible en ella.
 *  3. Un ingrediente es sustituible si la alternativa tiene uno con el mismo nombre
 *     y lo necesita en una cantidad inferior.
 *
 * Decisiones tomadas sobre el enunciado:
 *  - "Cantidad inferior": la alternativa usa estrictamente menos cantidad que la receta por sustituir
 *    (con lo que hay para la receta original, alcanza para la alternativa). Igual cantidad no cumple.
 *  - Solo se pide que los ingredientes de la receta por sustituir estén en la alternativa:
 *    la alternativa puede tener, además, otros ingredientes.
 *  - La receta por sustituir tiene que tener al menos un ingrediente (si no, cualquier receta cumpliría).
 */
public class Chef {

    /**
     * Busca en recetasAlternativas una receta que pueda ser preparada con los mismos ingredientes
     * que recetaPorSustituir: todos los ingredientes de recetaPorSustituir tienen que ser ingredientes
     * de la otra receta (comparando por nombre) y con una cantidad inferior.
     *
     * @param recetaPorSustituir receta que se quiere sustituir, no null y con al menos un ingrediente
     * @param recetasAlternativas recetas entre las que se busca, no null y sin elementos null
     * @return la primera receta de recetasAlternativas que cumple la condición, o null si ninguna cumple
     * @throws IllegalArgumentException si algún parámetro es null, si recetaPorSustituir no tiene
     *                                  ingredientes o si hay una receta alternativa null
     */
    public Receta buscarRecetaSustituta(Receta recetaPorSustituir, List<Receta> recetasAlternativas) {
        ValidacionesUtiles.validarDistintoDeNull(recetaPorSustituir, "recetaPorSustituir");
        ValidacionesUtiles.validarDistintoDeNull(recetasAlternativas, "recetasAlternativas");
        ValidacionesUtiles.validarFalso(recetaPorSustituir.estaVacia(),
                "La receta " + recetaPorSustituir.getNombre() + " no tiene ingredientes");

        for (Receta recetaAlternativa : recetasAlternativas) {
            if (esSustituta(recetaAlternativa, recetaPorSustituir)) {
                return recetaAlternativa;
            }
        }
        return null;
    }

    /**
     * @param recetaAlternativa receta candidata, no null
     * @param recetaPorSustituir receta que se quiere sustituir, no null
     * @return true si todos los ingredientes de recetaPorSustituir son sustituibles en recetaAlternativa
     * @throws IllegalArgumentException si alguna de las recetas es null
     */
    public boolean esSustituta(Receta recetaAlternativa, Receta recetaPorSustituir) {
        ValidacionesUtiles.validarDistintoDeNull(recetaAlternativa, "recetaAlternativa");
        ValidacionesUtiles.validarDistintoDeNull(recetaPorSustituir, "recetaPorSustituir");

        if (recetaAlternativa.getIngredientes().size() != recetaPorSustituir.getIngredientes().size()) {
            return false;
        }
        if (recetaAlternativa.equals(recetaPorSustituir)) {
            return false;
        }
        for (Ingrediente ingrediente : recetaPorSustituir.getIngredientes()) {
            if (!esSustituible(ingrediente, recetaAlternativa)) {
                return false;
            }
        }
        return true;
    }

    /**
     * pre: ingrediente y recetaAlternativa no son null
     * @param ingrediente ingrediente de la receta por sustituir
     * @param recetaAlternativa receta candidata
     * @return true si recetaAlternativa tiene un ingrediente con el mismo nombre y con cantidad inferior
     */
    private boolean esSustituible(Ingrediente ingrediente, Receta recetaAlternativa) {
        Ingrediente ingredienteAlternativo = recetaAlternativa.buscarIngrediente(ingrediente);
        return ingredienteAlternativo != null &&
               ingredienteAlternativo.tieneMenosCantidadQue(ingrediente);
    }
}
