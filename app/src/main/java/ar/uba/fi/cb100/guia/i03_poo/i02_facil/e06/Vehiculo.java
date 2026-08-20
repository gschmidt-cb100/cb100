package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e06;

/**
 * e06: Vehiculo con metodo descripcion() redefinible.
 * Las subclases heredan el estado (marca) y pueden sobreescribir
 * el comportamiento.
 */
public class Vehiculo {

    private final String marca;

    public Vehiculo(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }

    /** Descripcion generica; las subclases la especializan. */
    public String descripcion() {
        return "Vehiculo de marca " + marca;
    }

    public static void main(String[] args) {
        Vehiculo[] vehiculos = {
                new Auto("Toyota"),
                new Moto("Honda")
        };
        for (Vehiculo v : vehiculos) {
            System.out.println(v.descripcion());
        }
    }
}
