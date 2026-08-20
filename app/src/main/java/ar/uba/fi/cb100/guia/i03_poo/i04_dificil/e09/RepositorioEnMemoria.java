package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e09;

import java.util.Arrays;
import java.util.Optional;

/**
 * Implementación de {@link Repositorio} sobre arreglos paralelos: uno con los
 * ids y otro con los elementos, alineados por índice. El id de cada elemento
 * se obtiene mediante un {@link ExtractorId} recibido en el constructor.
 * No usa colecciones del JDK.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class RepositorioEnMemoria<T> implements Repositorio<T> {

    private final ExtractorId<T> extractor;
    private int[] ids;
    private Object[] elementos;
    private int cantidad;

    public RepositorioEnMemoria(ExtractorId<T> extractor) {
        this.extractor = extractor;
        this.ids = new int[4];
        this.elementos = new Object[4];
        this.cantidad = 0;
    }

    @Override
    public void guardar(T elemento) {
        int id = extractor.idDe(elemento);
        int i = indiceDe(id);
        if (i >= 0) {
            elementos[i] = elemento; // actualiza el existente
            return;
        }
        if (cantidad == elementos.length) {
            ids = Arrays.copyOf(ids, ids.length * 2);
            elementos = Arrays.copyOf(elementos, elementos.length * 2);
        }
        ids[cantidad] = id;
        elementos[cantidad] = elemento;
        cantidad++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<T> buscarPorId(int id) {
        int i = indiceDe(id);
        if (i < 0) {
            return Optional.empty();
        }
        return Optional.of((T) elementos[i]);
    }

    public int tamanio() {
        return cantidad;
    }

    private int indiceDe(int id) {
        for (int i = 0; i < cantidad; i++) {
            if (ids[i] == id) {
                return i;
            }
        }
        return -1;
    }

    /** Entidad de ejemplo usada por el main de demostración. */
    private record Usuario(int id, String nombre) {
    }

    public static void main(String[] args) {
        Repositorio<Usuario> repo = new RepositorioEnMemoria<>(Usuario::id);
        repo.guardar(new Usuario(1, "Ana"));
        repo.guardar(new Usuario(2, "Beto"));
        repo.guardar(new Usuario(1, "Ana María")); // mismo id: actualiza

        System.out.println("Id 1: " + repo.buscarPorId(1).orElse(null));
        System.out.println("Id 2: " + repo.buscarPorId(2).orElse(null));
        System.out.println("Id 9: " + repo.buscarPorId(9));
    }
}
