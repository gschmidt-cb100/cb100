package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e09;

/**
 * Función que extrae el id entero de un elemento. Permite que el repositorio
 * sea genérico sin acoplarse a una clase concreta: se le pasa (típicamente
 * como lambda) cómo obtener el id de cada elemento.
 *
 * @param <T> tipo del elemento
 */
@FunctionalInterface
public interface ExtractorId<T> {

    int idDe(T elemento);
}
