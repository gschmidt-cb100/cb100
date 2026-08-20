package ar.uba.fi.cb100.material.i05_lineales;

/**
 * Implementación del TDA {@link Conjunto} apoyada en una
 * {@link ListaSimplementeEnlazada}. Antes de agregar, chequea que el elemento no
 * esté (por eso {@code agregar}/{@code contiene} son O(n)). Es simple y didáctica;
 * más adelante veremos que una <b>tabla de hash</b> logra estas operaciones en
 * O(1) promedio (unidad de Hashing).
 */
public class ConjuntoLista<T> implements Conjunto<T> {

    private final ListaSimplementeEnlazada<T> elementos = new ListaSimplementeEnlazada<>();

    @Override
    public boolean agregar(T x) {
        if (elementos.contiene(x)) return false;   // no se admiten duplicados
        elementos.agregar(x);
        return true;
    }

    @Override public boolean contiene(T x) { return elementos.contiene(x); }

    @Override
    public boolean eliminar(T x) {
        int i = elementos.indiceDe(x);
        if (i < 0) return false;
        elementos.eliminar(i);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void agregarTodos(Conjunto<T> otro) {
        for (Object o : otro.aArreglo()) agregar((T) o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Conjunto<T> union(Conjunto<T> otro) {
        ConjuntoLista<T> r = new ConjuntoLista<>();
        for (Object o : aArreglo())        r.agregar((T) o);
        for (Object o : otro.aArreglo())   r.agregar((T) o);   // los repetidos se ignoran
        return r;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Conjunto<T> interseccion(Conjunto<T> otro) {
        ConjuntoLista<T> r = new ConjuntoLista<>();
        for (Object o : aArreglo()) {
            if (otro.contiene((T) o)) r.agregar((T) o);        // sólo los que están en ambos
        }
        return r;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Conjunto<T> diferencia(Conjunto<T> otro) {
        ConjuntoLista<T> r = new ConjuntoLista<>();
        for (Object o : aArreglo()) {
            if (!otro.contiene((T) o)) r.agregar((T) o);       // los que NO están en otro
        }
        return r;
    }

    @Override public int tamanio() { return elementos.tamanio(); }

    @Override
    public Object[] aArreglo() {
        Object[] a = new Object[elementos.tamanio()];
        for (int i = 0; i < a.length; i++) a[i] = elementos.obtener(i);
        return a;
    }

    @Override
    public String toString() {
        return "{" + String.join(", ", aStringArray()) + "}";
    }

    private String[] aStringArray() {
        Object[] a = aArreglo();
        String[] s = new String[a.length];
        for (int i = 0; i < a.length; i++) s[i] = String.valueOf(a[i]);
        return s;
    }

    public static void main(String[] args) {
        ConjuntoLista<String> c = new ConjuntoLista<>();
        System.out.println("agregó a? " + c.agregar("a"));
        System.out.println("agregó a otra vez? " + c.agregar("a"));   // false
        c.agregar("b");

        ConjuntoLista<String> otro = new ConjuntoLista<>();
        otro.agregar("b"); otro.agregar("c");

        System.out.println("unión: " + c.union(otro));              // {a, b, c}
        System.out.println("intersección: " + c.interseccion(otro)); // {b}
        System.out.println("diferencia: " + c.diferencia(otro));     // {a}
    }
}
