package ar.uba.fi.cb100.guia.i03_poo.i02_facil.e05;

/** Implementacion informal del saludo. */
public class Informal implements Saludador {

    @Override
    public String saludar() {
        return "Hola! Como andas?";
    }

    public static void main(String[] args) {
        // Polimorfismo: la variable es de tipo Saludador,
        // pero el objeto decide que version de saludar() se ejecuta.
        Saludador s1 = new Formal();
        Saludador s2 = new Informal();
        System.out.println(s1.saludar());
        System.out.println(s2.saludar());
    }
}
