package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e09;

import java.util.Optional;

/**
 * TDA repositorio: almacena elementos identificados por un id entero y
 * permite recuperarlos por dicho id.
 *
 * @param <T> tipo de los elementos almacenados
 */
public interface Repositorio<T> {

    /** Guarda un elemento (o lo actualiza si ya existía uno con el mismo id). */
    void guardar(T elemento);

    /** Busca un elemento por su id; devuelve vacío si no existe. */
    Optional<T> buscarPorId(int id);
}
