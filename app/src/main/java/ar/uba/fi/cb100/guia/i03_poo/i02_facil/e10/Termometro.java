package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e10;

/**
 * e10: Termometro que valida la temperatura.
 * No se permite bajar del cero absoluto (-273.15 C). Si se intenta,
 * se lanza IllegalArgumentException para proteger la invariante.
 */
public class Termometro {

    /** Cero absoluto en grados Celsius. */
    public static final double CERO_ABSOLUTO = -273.15;

    private double temperatura;

    public Termometro(double temperatura) {
        setTemperatura(temperatura);
    }

    public double getTemperatura() {
        return temperatura;
    }

    /** Asigna la temperatura, rechazando valores por debajo del cero absoluto. */
    public void setTemperatura(double temperatura) {
        if (temperatura < CERO_ABSOLUTO) {
            throw new IllegalArgumentException(
                    "Temperatura invalida: " + temperatura + " (minimo " + CERO_ABSOLUTO + ")");
        }
        this.temperatura = temperatura;
    }

    public static void main(String[] args) {
        Termometro t = new Termometro(25.0);
        System.out.println("temperatura = " + t.getTemperatura());
        try {
            t.setTemperatura(-300);
        } catch (IllegalArgumentException e) {
            System.out.println("Rechazado: " + e.getMessage());
        }
    }
}
