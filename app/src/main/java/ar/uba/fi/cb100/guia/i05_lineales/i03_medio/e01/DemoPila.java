package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e01;

/** Ejemplo de uso de {@link Pila}. */
public class DemoPila {

    public static void main(String[] args) {
        Pila<String> pila = new Pila<>();
        pila.apilar("primero");
        pila.apilar("segundo");
        pila.apilar("tercero");

        System.out.println("Tamaño: " + pila.tamanio());
        System.out.println("Tope: " + pila.tope());

        while (!pila.estaVacia()) {
            System.out.println("Desapilo: " + pila.desapilar());
        }
        System.out.println("¿Vacía? " + pila.estaVacia());
    }
}
