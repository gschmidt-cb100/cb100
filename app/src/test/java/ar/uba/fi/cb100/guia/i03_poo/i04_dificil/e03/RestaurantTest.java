package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    private Restaurant restaurant;

    @BeforeEach
    void crearRestaurant() {
        restaurant = new Restaurant(new Mesa[]{
                new Mesa(1, 2), new Mesa(2, 4), new Mesa(3, 6)
        });
    }

    @Test
    @DisplayName("reservar asigna la mesa libre MÁS CHICA cuya capacidad alcanza")
    void mejorAjuste() {
        assertEquals(2, restaurant.reservar(3), "para 3 va la de 4, no la de 6");
        assertEquals(3, restaurant.reservar(3), "la de 4 ya no está libre: va la de 6");
        assertEquals(1, restaurant.reservar(1), "para 1 va la más chica (2)");
    }

    @Test
    @DisplayName("reservar lanza cuando ninguna mesa libre alcanza")
    void reservarSinMesa() {
        assertThrows(IllegalStateException.class, () -> restaurant.reservar(7),
                "nadie tiene capacidad 7");

        restaurant.reservar(5); // toma la de 6
        assertThrows(IllegalStateException.class, () -> restaurant.reservar(5),
                "la única con capacidad 5+ ya está reservada");
        assertThrows(IllegalArgumentException.class, () -> restaurant.reservar(0));
        assertThrows(IllegalArgumentException.class, () -> restaurant.reservar(-2));
    }

    @Test
    @DisplayName("ciclo completo LIBRE -> RESERVADA -> OCUPADA -> LIBRE con recaudación")
    void cicloCompleto() {
        assertEquals(3, restaurant.mesasLibres());
        assertEquals(0.0, restaurant.recaudacion());

        int numero = restaurant.reservar(2);
        assertEquals(2, restaurant.mesasLibres());

        restaurant.ocupar(numero);
        restaurant.liberar(numero, 30000.0);
        assertEquals(3, restaurant.mesasLibres());
        assertEquals(30000.0, restaurant.recaudacion());

        int otro = restaurant.reservar(4);
        restaurant.ocupar(otro);
        restaurant.liberar(otro, 20000.0);
        assertEquals(50000.0, restaurant.recaudacion(), "la recaudación es acumulada");
    }

    @Test
    @DisplayName("TODAS las transiciones inválidas de la mesa lanzan IllegalStateException")
    void transicionesInvalidas() {
        Mesa mesa = new Mesa(9, 4);

        // Desde LIBRE: sólo se puede reservar.
        assertThrows(IllegalStateException.class, mesa::ocupar, "LIBRE no se puede ocupar");
        assertThrows(IllegalStateException.class, mesa::liberar, "LIBRE no se puede liberar");

        mesa.reservar();
        // Desde RESERVADA: sólo se puede ocupar.
        assertThrows(IllegalStateException.class, mesa::reservar, "RESERVADA no se re-reserva");
        assertThrows(IllegalStateException.class, mesa::liberar, "RESERVADA no se libera");

        mesa.ocupar();
        // Desde OCUPADA: sólo se puede liberar.
        assertThrows(IllegalStateException.class, mesa::reservar, "OCUPADA no se reserva");
        assertThrows(IllegalStateException.class, mesa::ocupar, "OCUPADA no se re-ocupa");

        mesa.liberar();
        assertEquals(EstadoDeMesa.LIBRE, mesa.estado(), "el ciclo vuelve a LIBRE");
    }

    @Test
    @DisplayName("las transiciones inválidas también fallan a través del Restaurant")
    void transicionesInvalidasViaRestaurant() {
        assertThrows(IllegalStateException.class, () -> restaurant.ocupar(1),
                "no se puede ocupar una mesa que no fue reservada");
        assertThrows(IllegalStateException.class, () -> restaurant.liberar(1, 100.0),
                "no se puede liberar una mesa libre");

        int numero = restaurant.reservar(2);
        assertThrows(IllegalStateException.class, () -> restaurant.liberar(numero, 100.0),
                "una mesa reservada (no ocupada) no se libera");
    }

    @Test
    @DisplayName("parámetros inválidos: mesa inexistente, gasto negativo, construcción inválida")
    void parametrosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> restaurant.ocupar(99));
        assertThrows(IllegalArgumentException.class, () -> restaurant.liberar(99, 100.0));

        int numero = restaurant.reservar(2);
        restaurant.ocupar(numero);
        assertThrows(IllegalArgumentException.class, () -> restaurant.liberar(numero, -1.0));

        assertThrows(IllegalArgumentException.class, () -> new Restaurant(null));
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(new Mesa[]{}));
        assertThrows(IllegalArgumentException.class,
                () -> new Restaurant(new Mesa[]{new Mesa(1, 2), null}));
        assertThrows(IllegalArgumentException.class,
                () -> new Restaurant(new Mesa[]{new Mesa(1, 2), new Mesa(1, 4)}),
                "números de mesa repetidos");
        assertThrows(IllegalArgumentException.class, () -> new Mesa(-1, 4));
        assertThrows(IllegalArgumentException.class, () -> new Mesa(1, 0));
    }

    @Test
    @DisplayName("la copia defensiva del constructor aísla al restaurant del arreglo original")
    void copiaDefensiva() {
        Mesa[] salon = {new Mesa(1, 2), new Mesa(2, 4)};
        Restaurant otro = new Restaurant(salon);
        salon[0] = null;
        assertEquals(1, otro.reservar(2), "el restaurant conserva su propia copia de las mesas");
    }
}
