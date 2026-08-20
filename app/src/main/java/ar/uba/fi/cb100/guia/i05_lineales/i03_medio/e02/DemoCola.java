package ar.uba.fi.cb100.guia.i05_lineales.i03_medio.e02;

/** Ejemplo de uso de {@link Cola}. */
public class DemoCola {

    public static void main(String[] args) {
        Cola<String> cola = new Cola<>();
        cola.encolar("primero");
        cola.encolar("segundo");
        cola.encolar("tercero");

        System.out.println("Tamaño: " + cola.tamanio());
        System.out.println("Frente: " + cola.frente());

        while (!cola.estaVacia()) {
            System.out.println("Desencolo: " + cola.desencolar());
        }
        System.out.println("¿Vacía? " + cola.estaVacia());
    }
}
