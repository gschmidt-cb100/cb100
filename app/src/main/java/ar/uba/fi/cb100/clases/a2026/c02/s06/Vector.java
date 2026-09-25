package ar.uba.fi.cb100.clases.a2026.c02.s06;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

import java.util.Arrays;

public class Vector<T> implements ar.uba.fi.cb100.clases.a2026.c02.s06.vector.Vector<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private T[] elementos;
    private int tamanio;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea un vector con capacidad inicial de 10 elementos.
     */
    public Vector() {
        this(10); // Capacidad inicial de 10
    }

    /**
     * Crea un vector con la capacidad inicial especificada.
     * @param capacidadInicial: la capacidad inicial del vector, debe ser mayor que cero.
     */
    public Vector(int capacidadInicial) {
        ValidacionesUtiles.esMayorQueCero(capacidadInicial, "La capacidad inicial debe ser mayor que cero.");
        this.elementos = (T[]) new Object[capacidadInicial]; // Capacidad inicial de 10
        this.tamanio = 0;
        Arrays.fill(elementos, null);
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    @Override
    public void agregar(T valor) {
        ValidacionesUtiles.validarDistintoDeNull(valor, "El valor a agregar no puede ser null.");
        if (this.tamanio() == this.capacidad()) {
            this.redimensionar();
        }
        this.elementos[this.tamanio()] = valor;
        this.tamanio++;
    }

    /**
     * Redimensiona el vector duplicando su capacidad.
     * Se crea un nuevo arreglo con el doble de capacidad y se copian los elementos existentes al nuevo arreglo.
     * Luego, se actualiza la referencia del arreglo de elementos al nuevo arreglo
     */
    private void redimensionar() {
        int nuevaCapacidad = this.capacidad() * 2;
        T[] nuevosElementos = (T[]) new Object[nuevaCapacidad];
        System.arraycopy(this.elementos, 0, nuevosElementos, 0, this.tamanio());
        this.elementos = nuevosElementos;
    }

    @Override
    public T obtener(int index) {
        ValidacionesUtiles.validarRango(index, 0, this.tamanio() - 1, "El índice está fuera de rango.");
        return this.elementos[index];
    }

    @Override
    public void insertar(int index, T value) {
        ValidacionesUtiles.validarRango(index, 0, this.tamanio(), "El índice está fuera de rango.");
        ValidacionesUtiles.validarDistintoDeNull(value, "El valor a insertar no puede ser null.");
        if (this.tamanio() == this.capacidad()) {
            this.redimensionar();
        }
        // Mueve los elementos a la derecha para hacer espacio para el nuevo elemento
        System.arraycopy(this.elementos, index, this.elementos, index + 1, this.tamanio() - index);
        this.elementos[index] = value;
        this.tamanio++;
    }

    @Override
    public void eliminar(int index) {
        ValidacionesUtiles.validarRango(index, 0, this.tamanio() - 1, "El índice está fuera de rango.");
        // Mueve los elementos a la izquierda para llenar el espacio del elemento eliminado
        System.arraycopy(this.elementos, index + 1, this.elementos, index, this.tamanio() - index - 1);
        this.elementos[this.tamanio() - 1] = null; // Limpia la última posición
        this.tamanio--;
    }

    @Override
    public boolean contiene(T valor) {
        return this.indiceDe(valor) != -1;
    }

    @Override
    public int indiceDe(T valor) {
        ValidacionesUtiles.validarDistintoDeNull(valor, "El valor a buscar no puede ser null.");
        for(int i = 0; i < this.tamanio(); i++) {
            if(this.elementos[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void ordenar() {
        Arrays.sort(this.elementos, 0, this.tamanio());
    }

    @Override
    public void agregarTodos(ar.uba.fi.cb100.clases.a2026.c02.s06.vector.Vector<T> otroVector) {
        for(int i = 0; i < otroVector.tamanio(); i++) {
            this.agregar(otroVector.obtener(i));
        }
    }

    @Override
    public int tamanio() {
        for(int i = 0; i < this.elementos.length; i++) {
            if(this.elementos[i] == null) {
                return i;
            }
        }
        return this.elementos.length;
    }

    @Override
    public int capacidad() {
        return this.elementos.length;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
