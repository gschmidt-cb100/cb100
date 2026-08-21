package ar.uba.fi.cb100.material.i01_intro;

/**
 * Un {@code record}: la forma moderna (Java 16+) de declarar un dato
 * inmutable. En una sola línea, Java genera el constructor, el accesor
 * {@code celsius()}, y {@code equals}, {@code hashCode} y {@code toString}.
 */
public record Temperatura(double celsius) {

    // Constructor compacto: valida los invariantes del dato.
    public Temperatura {
        if (celsius < -273.15) {
            throw new IllegalArgumentException(
                    "Temperatura por debajo del cero absoluto: " + celsius);
        }
    }

    /** Clasifica la temperatura usando un switch expression. */
    public String clasificar() {
        return switch (franja()) {
            case 0 -> "helado";
            case 1 -> "frío";
            case 2 -> "templado";
            case 3 -> "cálido";
            default -> "caluroso";
        };
    }

    private int franja() {
        if (celsius < 0) {
            return 0;
        }
        if (celsius < 12) {
            return 1;
        }
        if (celsius < 22) {
            return 2;
        }
        if (celsius < 30) {
            return 3;
        }
        return 4;
    }
}
