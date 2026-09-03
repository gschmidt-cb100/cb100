package ar.uba.fi.cb100.clases.a2026.c02.s03.herencia;

public class Gallina extends Animal {

    public Gallina(String nombre) {
        super(nombre);
    }

    @Override
    public String sonido() {
        return "Pío pío";
    }
}
