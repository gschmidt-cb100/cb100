package ar.uba.fi.cb100.guia.i02_memoria.i04_dificil.e02;

/**
 * Deteccion de ciclos en una lista enlazada con el algoritmo de Floyd
 * (la liebre y la tortuga). Usa memoria constante: solo dos referencias.
 */
public class DetectarCiclo {

    /**
     * Devuelve true si la lista que comienza en inicio contiene un ciclo.
     * La tortuga avanza de a un nodo; la liebre, de a dos. Si hay ciclo,
     * en algun momento apuntan al mismo nodo (misma referencia).
     */
    public static boolean tieneCiclo(Nodo inicio) {
        Nodo tortuga = inicio;
        Nodo liebre = inicio;
        while (liebre != null && liebre.siguiente != null) {
            tortuga = tortuga.siguiente;          // un paso
            liebre = liebre.siguiente.siguiente;  // dos pasos
            if (tortuga == liebre) {
                // Se encontraron: hay ciclo.
                return true;
            }
        }
        // La liebre llego al final (null): la lista termina, no hay ciclo.
        return false;
    }

    public static void main(String[] args) {
        Nodo a = new Nodo(1);
        Nodo b = new Nodo(2);
        Nodo c = new Nodo(3);
        a.siguiente = b;
        b.siguiente = c;
        System.out.println("Sin ciclo: " + tieneCiclo(a));

        c.siguiente = a; // cerramos el ciclo enlazando el ultimo al primero
        System.out.println("Con ciclo: " + tieneCiclo(a));
    }
}
