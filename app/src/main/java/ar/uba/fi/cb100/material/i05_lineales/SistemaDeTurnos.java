package ar.uba.fi.cb100.material.i05_lineales;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b>Ejemplo integrador de la Unidad 5</b>: un sistema de turnos de una mesa de
 * ayuda que combina las cuatro estructuras lineales, usando la <b>API nativa</b>
 * de Java (la herramienta de parciales, TPs y finales):
 * <ul>
 *   <li><b>Cola</b> (FIFO, {@link ArrayDeque} como {@code Deque}): la fila de espera
 *       — se atiende en orden de llegada;</li>
 *   <li><b>Conjunto</b> ({@link HashSet}): los DNI ya registrados — nadie saca
 *       dos turnos;</li>
 *   <li><b>Vector</b> ({@link ArrayList}): el historial de atendidos, en orden;</li>
 *   <li><b>Pila</b> (LIFO, {@link ArrayDeque}): las atenciones "deshacibles"
 *       — deshacer vuelve a poner al ÚLTIMO atendido al frente de la fila.</li>
 * </ul>
 */
public class SistemaDeTurnos {

    private final Deque<Integer> filaDeEspera = new ArrayDeque<>();  // Cola (FIFO)
    private final Set<Integer> registrados = new HashSet<>();        // Conjunto
    private final List<Integer> atendidos = new ArrayList<>();       // Vector
    private final Deque<Integer> deshacer = new ArrayDeque<>();      // Pila (LIFO)

    /** Da un turno al DNI si no tenía uno ya. Devuelve si lo obtuvo. */
    public boolean sacarTurno(int dni) {
        if (!registrados.add(dni)) {      // el Conjunto rechaza duplicados en O(1)
            return false;
        }
        filaDeEspera.addLast(dni);        // encolar: al FONDO de la fila
        return true;
    }

    /** Atiende al primero de la fila y lo agrega al historial. */
    public int atender() {
        if (filaDeEspera.isEmpty()) throw new IllegalStateException("no hay turnos");
        int dni = filaDeEspera.pollFirst();   // desencolar: del FRENTE (FIFO)
        atendidos.add(dni);                   // historial en orden de atención
        deshacer.push(dni);                   // apilar para poder deshacer (LIFO)
        return dni;
    }

    /** Deshace la ÚLTIMA atención: el cliente vuelve al frente de la fila. */
    public int deshacerUltimaAtencion() {
        if (deshacer.isEmpty()) throw new IllegalStateException("nada para deshacer");
        int dni = deshacer.pop();                     // el último atendido (LIFO)
        atendidos.remove(atendidos.size() - 1);       // sale del historial
        filaDeEspera.addFirst(dni);                   // vuelve AL FRENTE de la fila
        return dni;
    }

    public int enEspera()          { return filaDeEspera.size(); }
    public List<Integer> historial() { return List.copyOf(atendidos); }

    public static void main(String[] args) {
        SistemaDeTurnos mesa = new SistemaDeTurnos();
        System.out.println(mesa.sacarTurno(111));   // true
        System.out.println(mesa.sacarTurno(222));   // true
        System.out.println(mesa.sacarTurno(111));   // false: ya tenía turno (Conjunto)
        System.out.println(mesa.sacarTurno(333));   // true

        System.out.println(mesa.atender());         // 111 (FIFO: el primero que llegó)
        System.out.println(mesa.atender());         // 222
        System.out.println(mesa.historial());       // [111, 222]

        System.out.println(mesa.deshacerUltimaAtencion());  // 222 (LIFO: el último atendido)
        System.out.println(mesa.atender());         // 222 (volvió al frente de la fila)
        System.out.println(mesa.atender());         // 333
        System.out.println(mesa.historial());       // [111, 222, 333]
    }
}
