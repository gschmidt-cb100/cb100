package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e09;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class RepositorioEnMemoriaTest {

    /** Entidad de prueba con id entero. */
    private record Producto(int id, String nombre) {
    }

    @Test
    @DisplayName("Buscar en un repositorio vacío devuelve Optional.empty")
    void buscarEnVacio() {
        Repositorio<Producto> repo = new RepositorioEnMemoria<>(Producto::id);
        assertTrue(repo.buscarPorId(1).isEmpty());
    }

    @Test
    @DisplayName("Guardar y buscar por id recupera el elemento")
    void guardarYBuscar() {
        Repositorio<Producto> repo = new RepositorioEnMemoria<>(Producto::id);
        Producto p = new Producto(10, "Café");
        repo.guardar(p);
        assertEquals(p, repo.buscarPorId(10).orElseThrow());
        assertTrue(repo.buscarPorId(11).isEmpty());
    }

    @Test
    @DisplayName("Guardar con un id existente actualiza el elemento")
    void guardarActualiza() {
        RepositorioEnMemoria<Producto> repo = new RepositorioEnMemoria<>(Producto::id);
        repo.guardar(new Producto(5, "Viejo"));
        repo.guardar(new Producto(5, "Nuevo"));
        assertEquals("Nuevo", repo.buscarPorId(5).orElseThrow().nombre());
        assertEquals(1, repo.tamanio());
    }

    @Test
    @DisplayName("Almacena muchos elementos y crece automáticamente")
    void almacenaMuchos() {
        RepositorioEnMemoria<Producto> repo = new RepositorioEnMemoria<>(Producto::id);
        for (int i = 0; i < 100; i++) {
            repo.guardar(new Producto(i, "P" + i));
        }
        assertEquals(100, repo.tamanio());
        assertEquals("P0", repo.buscarPorId(0).orElseThrow().nombre());
        assertEquals("P99", repo.buscarPorId(99).orElseThrow().nombre());
        assertTrue(repo.buscarPorId(100).isEmpty());
    }
}
