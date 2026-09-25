package ar.uba.fi.cb100.clases.a2026.c02.s06.vector;

public interface Vector<T> {

    /**
     * Agrega un elemento al final del vector.
     * @param valor: el valor a agregar
     */
    void agregar(T valor);

    /**
     * Obtiene el elemento en la posición index del vector.
     * @param index: indice del elemento a obtener
     * @return valor en la posición index
     * @throws IndexOutOfBoundsException si index es menor que 0 o mayor o igual al tamaño del vector
     */
    T obtener(int index);

    /**
     * Elimina el elemento en la posición index del vector.
     * @param index: indice del elemento a eliminar
     * @param value: el valor a eliminar
     * @throws IndexOutOfBoundsException si index es menor que 0 o mayor o
     */
    void insertar(int index, T value);

    /**
     * Elimina el elemento en la posición index del vector.
     * @param index: indice del elemento a eliminar
     * @throws IndexOutOfBoundsException si index es menor que 0 o mayor o igual al tamaño del vector
     */
    void eliminar(int index);

    /**
     * Verifica si el vector contiene el valor especificado.
     * @param valor: el valor a buscar
     * @return true si el vector contiene el valor, false en caso contrario
     */
    boolean contiene(T valor);

    /**
     * Devuelve el índice del primer elemento que coincide con el valor especificado, o -1 si no se encuentra.
     * @param valor: el valor a buscar
     * @return el índice del primer elemento que coincide con el valor, o -1 si no se encuentra
     */
    int indiceDe(T valor);

    /**
     * Ordena los elementos del vector en orden ascendente. Se asume que los elementos son comparables.
     */
    void ordenar();

    /**
     * Agrega todos los elementos de otro vector al final del vector actual.
     * @param otroVector: el vector cuyos elementos se agregarán
     */
    void agregarTodos(Vector<T> otroVector);

    /**
     * Devuelve el número de elementos en el vector.
     * @return el tamaño del vector
     */
    int tamanio();

    /**
     * Devuelve la capacidad actual del vector.
     * @return la capacidad del vector
     */
    int capacidad();
}
