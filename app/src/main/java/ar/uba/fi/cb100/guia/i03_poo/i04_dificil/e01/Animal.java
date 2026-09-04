package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e01;

/**
 * Un animal del zoológico: nombre, especie y peso en kilos.
 *
 * <p>Es un record porque es un valor inmutable sin identidad propia más allá
 * de sus datos; el constructor compacto valida y garantiza que todo Animal
 * construido es válido.</p>
 *
 * @param nombre nombre propio del animal (no vacío).
 * @param especie especie del animal (no vacía).
 * @param pesoEnKilos peso estrictamente positivo.
 */
public record Animal(String nombre, String especie, double pesoEnKilos) {

    /**
     * pre: {@code nombre} y {@code especie} no son null ni vacíos;
     * {@code pesoEnKilos} es mayor que 0.<br>
     * post: el animal queda construido y válido.
     *
     * @throws IllegalArgumentException si algún parámetro es inválido.
     */
    public Animal {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede ser null ni vacío");
        }
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("La especie no puede ser null ni vacía");
        }
        if (pesoEnKilos <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser mayor que 0, se recibió: " + pesoEnKilos);
        }
    }
}
