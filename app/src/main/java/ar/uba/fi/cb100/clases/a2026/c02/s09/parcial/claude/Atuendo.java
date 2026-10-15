package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.claude;

/**
 * Una prenda del stock: tiene un tipo (remera, pantalón, ...), un color y un
 * precio.
 *
 * <p>Es un TDA inmutable: una vez creado, un atuendo no cambia. Por eso todos
 * los atributos son {@code final} y no hay setters.
 *
 * <p>Las reglas que involucran a <b>dos</b> atuendos (mismo color, distinto
 * tipo) viven acá, porque el atuendo es el dueño de esos datos. Así el que lo
 * usa no necesita conocer cómo está representado el color ni el tipo: le
 * pregunta "¿combinás con este otro?".
 */
public class Atuendo {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String tipo;
    private final String color;
    private final double precio;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code tipo} y {@code color} no son nulos ni vacíos; {@code precio}
     *      no es negativo.
     * post: crea el atuendo con esos datos. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException} y no crea nada.
     *
     * @param tipo tipo de atuendo (remera, pantalón, ...)
     * @param color color del atuendo
     * @param precio precio [$] del atuendo
     */
    public Atuendo(String tipo, String color, double precio) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("el atuendo necesita un tipo");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("el atuendo necesita un color");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("el precio no puede ser negativo: " + precio);
        }
        this.tipo = tipo;
        this.color = color;
        this.precio = precio;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return String.format("%s %s ($%.2f)", tipo, color, precio);
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code otro} no es nulo.
     * post: devuelve {@code true} si {@code otro} tiene exactamente el mismo
     *       color que este atuendo (condición 1 del enunciado).
     */
    public boolean esDelMismoColorQue(Atuendo otro) {
        validarNoNulo(otro);
        return this.color.equals(otro.color);
    }

    /**
     * pre: {@code otro} no es nulo.
     * post: devuelve {@code true} si {@code otro} es de distinto tipo que este
     *       atuendo (condición 3 del enunciado).
     */
    public boolean esDeDistintoTipoQue(Atuendo otro) {
        validarNoNulo(otro);
        return !this.tipo.equals(otro.tipo);
    }

    /**
     * pre: {@code otro} no es nulo.
     * post: devuelve {@code true} si este atuendo y {@code otro} forman una
     *       combinación válida: mismo color y distinto tipo. No mira el
     *       precio: eso depende de quién lo compra, no del atuendo.
     */
    public boolean combinaCon(Atuendo otro) {
        return esDelMismoColorQue(otro) && esDeDistintoTipoQue(otro);
    }

    /** post: lanza {@link IllegalArgumentException} si {@code otro} es nulo. */
    private static void validarNoNulo(Atuendo otro) {
        if (otro == null) {
            throw new IllegalArgumentException("el otro atuendo no puede ser nulo");
        }
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /** post: devuelve el tipo de atuendo (remera, pantalón, ...). */
    public String getTipo() {
        return tipo;
    }

    /** post: devuelve el color del atuendo. */
    public String getColor() {
        return color;
    }

    /** post: devuelve el precio [$] del atuendo. */
    public double getPrecio() {
        return precio;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
