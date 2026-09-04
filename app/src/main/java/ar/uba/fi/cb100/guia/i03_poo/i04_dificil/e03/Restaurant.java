package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

import java.util.Arrays;

/**
 * TDA Restaurant: administra un conjunto FIJO de mesas (se reciben en el
 * constructor) y la recaudación del día.
 *
 * <p>Delegación: las transiciones de estado las hace cada {@link Mesa}
 * ({@code reservar()}, {@code ocupar()}, {@code liberar()}); el Restaurant
 * sólo elige la mesa y le ordena la transición.</p>
 *
 * <p>Decisión sobre la recaudación: es un ATRIBUTO y no un cálculo porque es
 * una suma HISTÓRICA de gastos ya cobrados — una vez liberada la mesa, el
 * gasto no queda registrado en ningún otro lado del modelo, así que no es
 * calculable recorriendo las mesas. La regla "lo calculable es método" no se
 * viola: esto no es calculable. (La alternativa — guardar cada consumo en un
 * arreglo y sumar al pedirla — también vale, pero agrega estado más complejo
 * sin cambiar el contrato.)</p>
 */
public class Restaurant {

    private final Mesa[] mesas;
    private double recaudacionAcumulada;

    /**
     * pre: {@code mesas} no es null, tiene al menos una mesa, sin nulls y sin
     * números repetidos.<br>
     * post: crea el restaurant con una COPIA del arreglo (copia defensiva) y
     * recaudación 0.
     *
     * @param mesas las mesas del salón.
     * @throws IllegalArgumentException si el arreglo es inválido.
     */
    public Restaurant(Mesa[] mesas) {
        if (mesas == null || mesas.length == 0) {
            throw new IllegalArgumentException("Debe haber al menos una mesa");
        }
        for (int i = 0; i < mesas.length; i++) {
            if (mesas[i] == null) {
                throw new IllegalArgumentException("La mesa en la posición " + i + " es null");
            }
            for (int j = 0; j < i; j++) {
                if (mesas[j].numero() == mesas[i].numero()) {
                    throw new IllegalArgumentException(
                            "Número de mesa repetido: " + mesas[i].numero());
                }
            }
        }
        this.mesas = Arrays.copyOf(mesas, mesas.length);
        this.recaudacionAcumulada = 0;
    }

    /**
     * Reserva la mesa LIBRE más chica cuya capacidad alcance para
     * {@code personas} (criterio de mejor ajuste: no "quemar" mesas grandes
     * con grupos chicos).
     *
     * <p>pre: {@code personas} es mayor que 0 y existe una mesa LIBRE con
     * capacidad suficiente.<br>
     * post: esa mesa pasa a RESERVADA y se devuelve su número.</p>
     *
     * @param personas cantidad de comensales.
     * @return el número de la mesa reservada.
     * @throws IllegalArgumentException si {@code personas} no es positivo.
     * @throws IllegalStateException si ninguna mesa libre alcanza.
     */
    public int reservar(int personas) {
        if (personas <= 0) {
            throw new IllegalArgumentException(
                    "Las personas deben ser más que 0, se recibió: " + personas);
        }
        Mesa mejor = null;
        for (Mesa mesa : mesas) {
            boolean alcanza = mesa.estado() == EstadoDeMesa.LIBRE && mesa.capacidad() >= personas;
            if (alcanza && (mejor == null || mesa.capacidad() < mejor.capacidad())) {
                mejor = mesa;
            }
        }
        if (mejor == null) {
            throw new IllegalStateException(
                    "No hay mesa libre con capacidad para " + personas + " personas");
        }
        mejor.reservar();
        return mejor.numero();
    }

    /**
     * Ocupa la mesa reservada con ese número (llegaron los comensales).
     *
     * <p>pre: existe una mesa con ese número y está RESERVADA.<br>
     * post: la mesa pasa a OCUPADA.</p>
     *
     * @param numero número de la mesa.
     * @throws IllegalArgumentException si no existe mesa con ese número.
     * @throws IllegalStateException si la mesa no está reservada (lo valida la Mesa).
     */
    public void ocupar(int numero) {
        buscarMesa(numero).ocupar();
    }

    /**
     * Libera la mesa ocupada con ese número y acumula el gasto de los
     * comensales en la recaudación.
     *
     * <p>pre: existe una mesa con ese número, está OCUPADA y
     * {@code gasto} no es negativo.<br>
     * post: la mesa pasa a LIBRE y la recaudación aumenta en {@code gasto}.</p>
     *
     * @param numero número de la mesa.
     * @param gasto lo consumido por los comensales.
     * @throws IllegalArgumentException si no existe la mesa o el gasto es negativo.
     * @throws IllegalStateException si la mesa no está ocupada (lo valida la Mesa).
     */
    public void liberar(int numero, double gasto) {
        if (gasto < 0) {
            throw new IllegalArgumentException("El gasto no puede ser negativo: " + gasto);
        }
        buscarMesa(numero).liberar();
        recaudacionAcumulada += gasto;
    }

    /**
     * pre: -.<br>
     * post: devuelve la recaudación acumulada del día (suma histórica de los
     * gastos de todas las mesas liberadas).
     *
     * @return la recaudación acumulada.
     */
    public double recaudacion() {
        return recaudacionAcumulada;
    }

    /**
     * pre: -.<br>
     * post: devuelve cuántas mesas están LIBRES ahora. Es calculable
     * recorriendo las mesas: MÉTODO, no atributo.
     *
     * @return la cantidad de mesas libres.
     */
    public int mesasLibres() {
        int libres = 0;
        for (Mesa mesa : mesas) {
            if (mesa.estado() == EstadoDeMesa.LIBRE) {
                libres++;
            }
        }
        return libres;
    }

    /**
     * pre: -.<br>
     * post: devuelve todas las mesas con su estado y la recaudación.
     */
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("Restaurant (recaudación: $")
                .append(recaudacionAcumulada).append(')');
        for (Mesa mesa : mesas) {
            texto.append("\n  ").append(mesa);
        }
        return texto.toString();
    }

    private Mesa buscarMesa(int numero) {
        for (Mesa mesa : mesas) {
            if (mesa.numero() == numero) {
                return mesa;
            }
        }
        throw new IllegalArgumentException("No existe la mesa número " + numero);
    }

    /**
     * Demostración breve: reserva por mejor ajuste, ocupación y liberación.
     */
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant(new Mesa[]{
                new Mesa(1, 2), new Mesa(2, 4), new Mesa(3, 6)
        });

        int numeroReservado = restaurant.reservar(3);
        System.out.println("reservar(3) asignó la mesa: " + numeroReservado + " (la de 4, no la de 6)");
        restaurant.ocupar(numeroReservado);
        System.out.println(restaurant);

        restaurant.liberar(numeroReservado, 45000.0);
        System.out.println("Después de liberar con gasto 45000:");
        System.out.println(restaurant);
        System.out.println("mesasLibres(): " + restaurant.mesasLibres());
    }
}
