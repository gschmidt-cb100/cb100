package ar.uba.fi.cb100.clases.a2026.c02.s09.parcial.a1;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Atuendo atuendo = (Atuendo) o;
        return Objects.equals(tipo, atuendo.tipo) && Objects.equals(color, atuendo.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, color);
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
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