package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e06;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    @DisplayName("Cada subclase redefine descripcion() manteniendo la marca")
    void subclasesRedefinenDescripcion() {
        Vehiculo auto = new Auto("Toyota");
        Vehiculo moto = new Moto("Honda");
        assertEquals("Auto de marca Toyota", auto.descripcion());
        assertEquals("Moto de marca Honda", moto.descripcion());
    }

    @Test
    @DisplayName("La superclase conserva su descripcion generica")
    void superclaseDescripcionGenerica() {
        Vehiculo v = new Vehiculo("Fiat");
        assertEquals("Vehiculo de marca Fiat", v.descripcion());
    }
}
