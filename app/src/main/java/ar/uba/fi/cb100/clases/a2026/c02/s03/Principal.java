package ar.uba.fi.cb100.clases.a2026.c02.s03;

import ar.uba.fi.cb100.clases.a2026.c02.s03.temp.Persona;

public class Principal {

    public static void main() {
        Persona p = new Persona();
        p.nombre3 = "3";
        //p.nombre2 = "Juan 2" no compila porque nombre2 es protected y no se puede acceder desde otra clase que no sea subclase o del mismo paquete;
        // p.nombre1 = "Juan 1"; // No se puede acceder a nombre1 porque es private

        p.setNombre3("3");
    }
}
