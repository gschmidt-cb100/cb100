package ar.uba.fi.cb100.material.i05_lineales;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Implementación del TDA {@link Lista} sobre un <b>arreglo</b> que crece solo
 * (vector dinámico). Guarda {@code tamanio} elementos dentro de un arreglo de
 * {@code capacidad} celdas; cuando se llena, <b>duplica</b> la capacidad.
 * <p>
 * Costos: {@code obtener} y {@code agregar} al final son O(1) (este último
 * <b>amortizado</b>, por la redimensión); {@code insertar}/{@code eliminar} en el
 * medio son O(n) porque hay que correr los elementos.
 */
public class VectorDinamico<T> implements Lista<T> {

    private Object[] datos;
    private int tamanio;

    public VectorDinamico() {
        this.datos = new Object[4];   // capacidad inicial
        this.tamanio = 0;
    }

    @Override
    public void agregar(T x) {
        if (tamanio == datos.length) redimensionar(datos.length * 2);
        datos[tamanio++] = x;
    }

    @Override
    public void insertar(int i, T x) {
        if (i < 0 || i > tamanio) throw new IndexOutOfBoundsException("índice " + i);
        if (tamanio == datos.length) redimensionar(datos.length * 2);
        for (int k = tamanio; k > i; k--) datos[k] = datos[k - 1];   // correr a la derecha
        datos[i] = x;
        tamanio++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T obtener(int i) {
        verificar(i);
        return (T) datos[i];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T eliminar(int i) {
        verificar(i);
        T valor = (T) datos[i];
        for (int k = i; k < tamanio - 1; k++) datos[k] = datos[k + 1];  // correr a la izquierda
        datos[--tamanio] = null;   // liberar la referencia (evitar fuga)
        return valor;
    }

    @Override
    public int indiceDe(T x) {
        for (int i = 0; i < tamanio; i++) {
            if (Objects.equals(datos[i], x)) return i;
        }
        return -1;
    }

    @Override
    public boolean contiene(T x) {
        return indiceDe(x) >= 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void agregarTodos(Lista<T> otra) {
        for (int i = 0; i < otra.tamanio(); i++) agregar(otra.obtener(i));
    }

    @Override public int tamanio()      { return tamanio; }
    @Override public boolean estaVacia() { return tamanio == 0; }

    public int capacidad() { return datos.length; }

    /**
     * Ordena los elementos usando el {@code comparador} dado. Se aprovecha el
     * acceso directo del arreglo: se ordenan las primeras {@code tamanio} celdas
     * ($O(n \log n)$).
     */
    @SuppressWarnings("unchecked")
    public void ordenar(Comparator<? super T> comparador) {
        T[] vista = (T[]) Arrays.copyOf(datos, tamanio);
        Arrays.sort(vista, comparador);
        System.arraycopy(vista, 0, datos, 0, tamanio);
    }

    private void redimensionar(int nuevaCapacidad) {
        datos = Arrays.copyOf(datos, nuevaCapacidad);
    }

    private void verificar(int i) {
        if (i < 0 || i >= tamanio) throw new IndexOutOfBoundsException("índice " + i);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tamanio; i++) {
            if (i > 0) sb.append(", ");
            sb.append(datos[i]);
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        VectorDinamico<String> v = new VectorDinamico<>();
        v.agregar("a"); v.agregar("b"); v.agregar("c");
        v.insertar(1, "X");                 // [a, X, b, c]
        System.out.println(v + "  contiene X? " + v.contiene("X"));
        v.eliminar(0);                      // [X, b, c]
        System.out.println(v + "  índiceDe(b)=" + v.indiceDe("b")
                + "  capacidad=" + v.capacidad());
        v.ordenar(Comparator.naturalOrder());
        System.out.println("ordenado: " + v);   // [X, b, c] -> [X, b, c] (ya orden alfabético)
    }
}
