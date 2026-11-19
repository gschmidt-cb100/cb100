package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Agenda: los turnos reservados de la supercomputadora para un día,
 * dentro de un horario de atención.
 *
 * Responsabilidad: mantener los turnos sin conflictos y responder las
 * consultas sobre la ocupación del horario.
 *
 * Estructura elegida: un vector {@code Turno[]} ORDENADO por minuto de
 * inicio, más la cantidad de posiciones ocupadas. Justificación:
 *  - El enunciado sólo permite usar hasta vectores.
 *  - {@code estaLibre} tiene que ser O(log n): eso pide búsqueda binaria, y
 *    la búsqueda binaria pide un vector ordenado con acceso directo.
 *  - {@code obtenerTurnosEnConflicto} y {@code fusionar} tienen que ser
 *    O(n + m): con las dos agendas ordenadas se resuelven con una única
 *    pasada simultánea (la mezcla de Mergesort). Sin orden serían O(n · m).
 *  - Con los turnos ordenados y sin conflictos, los huecos libres son el
 *    espacio entre dos turnos consecutivos: se ven en una pasada.
 *  - El precio es mantener el orden: reservar y cancelar corren elementos,
 *    O(n). Se acepta: la agenda se consulta mucho más de lo que se modifica.
 *  - Cuando el vector se llena se duplica su tamaño, así la agenda no tiene
 *    un máximo de turnos y agregar sigue siendo O(1) amortizado en el
 *    crecimiento.
 *
 * Invariante:
 *  - 0 <= apertura < cierre <= Turno.MINUTOS_POR_DIA, y no cambian.
 *  - Los turnos ocupan las posiciones 0..cantidadDeTurnos-1, sin huecos.
 *  - Están ordenados por inicio, de menor a mayor.
 *  - No hay dos turnos en conflicto. (Junto con el orden implica que también
 *    quedan ordenados por fin: fin[i] <= inicio[i+1] < fin[i+1].)
 *  - Todo turno está dentro del horario: apertura <= inicio y fin <= cierre.
 *  - No hay dos turnos con el mismo código.
 *
 * Orden de cada operación (n = turnos de esta agenda, m = turnos de la otra):
 *  - Agenda(apertura, cierre)        O(1)
 *  - reservar                        O(n)       binaria O(log n) + corrimiento O(n)
 *  - cancelar                        O(n)       búsqueda por código + corrimiento
 *  - estaLibre                       O(log n)   una búsqueda binaria
 *  - obtenerPrimerInicioLibre        O(n)       una pasada por los huecos
 *  - obtenerMayorIntervaloLibre      O(n)       una pasada por los huecos
 *  - obtenerPorcentajeDeOcupacion    O(n)       suma las duraciones
 *  - obtenerTurnosEnConflicto        O(n + m)   pasada simultánea
 *  - fusionar                        O(n + m)   mezcla de dos vectores ordenados
 *
 * Hipótesis:
 *  H1. Los intervalos son semiabiertos, [inicio, fin): dos turnos pegados
 *      (uno termina cuando empieza el otro) no están en conflicto.
 *  H2. Los minutos reservados y el porcentaje de ocupación se calculan
 *      cuando se piden; no se guardan en atributos que haya que mantener.
 *  H3. Si no entra un turno de la duración pedida, el primer inicio libre
 *      "no existe": se devuelve null, no un -1.
 *  H4. La agenda fusionada cubre los dos horarios: abre con la que abre
 *      antes y cierra con la que cierra después.
 *  H5. Los códigos de turno son únicos en todo el centro de cómputos. Dentro
 *      de una agenda se verifica al reservar. Al fusionar NO se verifica que
 *      las dos agendas no repitan códigos: costaría O(n · m) y anularía el
 *      O(n + m) pedido; queda como precondición de quien llama.
 *  H6. Las posiciones que ve el usuario van de 1 a la cantidad de turnos.
 */
public class Agenda {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    private static final int CAPACIDAD_INICIAL = 4;
    private static final int FACTOR_DE_CRECIMIENTO = 2;
    private static final double PORCENTAJE_TOTAL = 100.0;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final int apertura;
    private final int cierre;
    private Turno[] turnos;
    private int cantidadDeTurnos;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code apertura} y {@code cierre} son minutos desde las 0:00, con
     *      0 <= apertura < cierre <= Turno.MINUTOS_POR_DIA.
     * post: crea la agenda sin turnos para ese horario. Si no se cumple la
     *       pre, lanza {@link IllegalArgumentException}.
     */
    public Agenda(int apertura, int cierre) {
        this(apertura, cierre, CAPACIDAD_INICIAL);
    }

    /** Igual que el público, pero reservando lugar para {@code capacidad} turnos (la usa fusionar). */
    private Agenda(int apertura, int cierre, int capacidad) {
        ValidacionesUtiles.validarRango(apertura, 0, Turno.MINUTOS_POR_DIA - 1, "apertura");
        ValidacionesUtiles.validarRango(cierre, apertura + 1, Turno.MINUTOS_POR_DIA, "cierre");
        this.apertura = apertura;
        this.cierre = cierre;
        this.turnos = new Turno[capacidad];
        this.cantidadDeTurnos = 0;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code turno} no es null, está dentro del horario, su código no
     *      está ya en la agenda y no está en conflicto con ningún turno.
     * post: el turno queda en la agenda, en la posición que le corresponde
     *       por su inicio. Si es null, está fuera del horario o su código
     *       está repetido lanza {@link IllegalArgumentException}; si está en
     *       conflicto con otro turno lanza {@link IllegalStateException}.
     * Orden: O(n). Encontrar la posición es O(log n); abrir el hueco, O(n).
     */
    public void reservar(Turno turno) {
        ValidacionesUtiles.validarDistintoDeNull(turno, "turno");
        ValidacionesUtiles.validarFalso(!estaDentroDelHorario(turno.getInicio(), turno.getFin()),
                "El " + turno + " está fuera del horario [" + apertura + ", " + cierre + ").");
        ValidacionesUtiles.validarFalso(buscarTurno(turno.getCodigo()) != null,
                "Ya hay un turno con el código " + turno.getCodigo() + ".");

        // Por el invariante, el único turno que puede chocar es el primero que
        // termina después del inicio del nuevo; y esa es también su posición.
        int indice = buscarPrimeroQueTerminaDespuesDe(turno.getInicio());
        ValidacionesUtiles.validarEstadoFalso(
                indice < cantidadDeTurnos && turnos[indice].estaEnConflictoCon(turno),
                "El " + turno + " está en conflicto con el " + turnos[indice] + ".");

        if (cantidadDeTurnos == turnos.length) {
            aumentarCapacidad();
        }
        abrirHueco(indice);
        turnos[indice] = turno;
        cantidadDeTurnos++;
    }

    /**
     * pre: {@code codigo} no es null y hay un turno con ese código.
     * post: quita el turno de la agenda y lo devuelve. Si no se cumple la
     *       pre, lanza {@link IllegalArgumentException}.
     * Orden: O(n). La agenda está ordenada por inicio, no por código: hay
     * que buscarlo recorriendo, y después cerrar el hueco.
     */
    public Turno cancelar(String codigo) {
        Integer indice = buscarIndiceDe(codigo);
        ValidacionesUtiles.validarFalso(indice == null, "No hay un turno con el código " + codigo + ".");
        Turno cancelado = turnos[indice];
        cerrarHueco(indice);
        cantidadDeTurnos--;
        return cancelado;
    }

    /**
     * pre: [{@code inicio}, {@code fin}) es un intervalo no vacío dentro del horario.
     * post: devuelve true si ningún turno ocupa algún minuto de ese
     *       intervalo. Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     * Orden: O(log n). Una búsqueda binaria encuentra el único turno que
     * podría ocupar el intervalo; no se recorre la agenda.
     */
    public boolean estaLibre(int inicio, int fin) {
        ValidacionesUtiles.validarFalso(inicio >= fin, "El inicio del intervalo debe ser menor que el fin.");
        ValidacionesUtiles.validarFalso(!estaDentroDelHorario(inicio, fin),
                "El intervalo está fuera del horario [" + apertura + ", " + cierre + ").");
        int indice = buscarPrimeroQueTerminaDespuesDe(inicio);
        return indice == cantidadDeTurnos || !turnos[indice].seSuperponeCon(inicio, fin);
    }

    /**
     * pre: {@code duracion} es mayor que cero.
     * post: devuelve el primer minuto del horario en el que entra un turno
     *       de esa duración, o null si no entra en ningún hueco (H3). Si no
     *       se cumple la pre, lanza {@link IllegalArgumentException}.
     * Orden: O(n). Recorre los huecos de izquierda a derecha y corta en el
     * primero que alcanza.
     */
    public Integer obtenerPrimerInicioLibre(int duracion) {
        ValidacionesUtiles.esMayorQueCero(duracion, "duración");
        int inicioDelHueco = apertura;
        for (int i = 0; i < cantidadDeTurnos; i++) {
            if (turnos[i].getInicio() - inicioDelHueco >= duracion) {
                return inicioDelHueco;
            }
            inicioDelHueco = turnos[i].getFin();
        }
        if (cierre - inicioDelHueco >= duracion) {
            return inicioDelHueco;
        }
        return null;
    }

    /**
     * post: devuelve la duración, en minutos, del mayor intervalo sin turnos
     *       dentro del horario. Cero si el horario está completo.
     * Orden: O(n). Una pasada por los huecos.
     */
    public int obtenerMayorIntervaloLibre() {
        int mayor = 0;
        int inicioDelHueco = apertura;
        for (int i = 0; i < cantidadDeTurnos; i++) {
            mayor = Math.max(mayor, turnos[i].getInicio() - inicioDelHueco);
            inicioDelHueco = turnos[i].getFin();
        }
        return Math.max(mayor, cierre - inicioDelHueco);
    }

    /**
     * post: devuelve el porcentaje del horario que está reservado, entre 0 y 100.
     * Orden: O(n).
     */
    public double obtenerPorcentajeDeOcupacion() {
        return getMinutosReservados() * PORCENTAJE_TOTAL / (cierre - apertura);
    }

    /**
     * pre: {@code otra} no es null.
     * post: devuelve un vector con los turnos de ESTA agenda que están en
     *       conflicto con algún turno de la otra, ordenados por inicio. Si
     *       no hay ninguno, el vector está vacío. No modifica las agendas.
     * Orden: O(n + m). Se avanza por las dos agendas a la vez: en cada paso
     * se descarta un turno de una de las dos, y ninguno se vuelve a mirar.
     */
    public Turno[] obtenerTurnosEnConflicto(Agenda otra) {
        ValidacionesUtiles.validarDistintoDeNull(otra, "agenda");
        Turno[] enConflicto = new Turno[cantidadDeTurnos];
        int cantidadEnConflicto = 0;
        int propio = 0;
        int ajeno = 0;
        while (propio < cantidadDeTurnos && ajeno < otra.cantidadDeTurnos) {
            Turno turnoPropio = turnos[propio];
            Turno turnoAjeno = otra.turnos[ajeno];
            if (!turnoAjeno.terminaDespuesDe(turnoPropio.getInicio())) {
                // El ajeno terminó antes de que empiece el propio: no choca con éste ni con los que siguen.
                ajeno++;
            } else if (turnoPropio.estaEnConflictoCon(turnoAjeno)) {
                // Se anota una sola vez. El ajeno no avanza: puede chocar también con el propio siguiente.
                enConflicto[cantidadEnConflicto] = turnoPropio;
                cantidadEnConflicto++;
                propio++;
            } else {
                // El ajeno empieza después de que termina el propio: el propio no choca con nadie.
                propio++;
            }
        }
        return recortar(enConflicto, cantidadEnConflicto);
    }

    /**
     * pre: {@code otra} no es null, ningún turno de esta agenda está en
     *      conflicto con uno de la otra y no repiten códigos (H5, no se verifica).
     * post: devuelve una agenda NUEVA con los turnos de las dos, cuyo horario
     *       cubre ambos horarios (H4). No modifica las agendas originales.
     *       Si {@code otra} es null lanza {@link IllegalArgumentException};
     *       si hay turnos en conflicto lanza {@link IllegalStateException}.
     * Orden: O(n + m). Es la mezcla de Mergesort: como las dos agendas están
     * ordenadas, en cada paso se pasa el turno que empieza antes; el
     * conflicto se detecta comparando sólo con el último que se pasó.
     */
    public Agenda fusionar(Agenda otra) {
        ValidacionesUtiles.validarDistintoDeNull(otra, "agenda");
        int capacidad = Math.max(CAPACIDAD_INICIAL, cantidadDeTurnos + otra.cantidadDeTurnos);
        Agenda fusion = new Agenda(Math.min(apertura, otra.apertura), Math.max(cierre, otra.cierre), capacidad);
        int propio = 0;
        int ajeno = 0;
        while (propio < cantidadDeTurnos || ajeno < otra.cantidadDeTurnos) {
            boolean sigueElPropio = ajeno == otra.cantidadDeTurnos
                    || (propio < cantidadDeTurnos && turnos[propio].empiezaAntesQue(otra.turnos[ajeno]));
            if (sigueElPropio) {
                fusion.agregarAlFinal(turnos[propio]);
                propio++;
            } else {
                fusion.agregarAlFinal(otra.turnos[ajeno]);
                ajeno++;
            }
        }
        return fusion;
    }

    /** post: devuelve la cantidad de minutos del horario que están reservados. Orden: O(n). */
    public int getMinutosReservados() {
        int minutos = 0;
        for (int i = 0; i < cantidadDeTurnos; i++) {
            minutos += turnos[i].getDuracion();
        }
        return minutos;
    }

    /**
     * pre: {@code codigo} no es null.
     * post: devuelve el turno con ese código, o null si no está en la agenda.
     */
    public Turno buscarTurno(String codigo) {
        Integer indice = buscarIndiceDe(codigo);
        if (indice == null) {
            return null;
        }
        return turnos[indice];
    }

    /**
     * pre: {@code posicion} está entre 1 y la cantidad de turnos.
     * post: devuelve el turno de esa posición, contando por orden de inicio.
     *       Si no se cumple la pre, lanza {@link IllegalArgumentException}.
     */
    public Turno getTurno(int posicion) {
        ValidacionesUtiles.validarRango(posicion, 1, cantidadDeTurnos, "posición");
        return turnos[posicion - 1];
    }

    /**
     * post: devuelve un vector con todos los turnos, ordenados por inicio.
     *       Es una copia: modificarlo no afecta a la agenda.
     */
    public Turno[] getTurnos() {
        return recortar(turnos, cantidadDeTurnos);
    }

    /** post: devuelve true si la agenda no tiene turnos. */
    public boolean estaVacia() {
        return cantidadDeTurnos == 0;
    }

    /** Devuelve true si el intervalo [inicio, fin) no se sale del horario de atención. */
    private boolean estaDentroDelHorario(int inicio, int fin) {
        return apertura <= inicio && fin <= cierre;
    }

    /**
     * Búsqueda binaria. Como los turnos están ordenados también por fin
     * (invariante), "termina después de {@code minuto}" es falso para los
     * primeros turnos y verdadero para todos los que siguen: se busca la
     * frontera.
     * post: devuelve el índice del primer turno que termina después de
     *       {@code minuto}, o cantidadDeTurnos si ninguno lo hace.
     * Orden: O(log n).
     */
    private int buscarPrimeroQueTerminaDespuesDe(int minuto) {
        int desde = 0;
        int hasta = cantidadDeTurnos;
        while (desde < hasta) {
            int medio = (desde + hasta) / 2;
            if (turnos[medio].terminaDespuesDe(minuto)) {
                hasta = medio;
            } else {
                desde = medio + 1;
            }
        }
        return desde;
    }

    /**
     * pre: {@code codigo} no es null.
     * post: devuelve el índice (0..n-1) del turno con ese código, o null si
     *       no está en la agenda.
     */
    private Integer buscarIndiceDe(String codigo) {
        ValidacionesUtiles.validarDistintoDeNull(codigo, "código");
        for (int i = 0; i < cantidadDeTurnos; i++) {
            if (turnos[i].tieneCodigo(codigo)) {
                return i;
            }
        }
        return null;
    }

    /**
     * pre: hay capacidad libre y el turno no empieza antes que el último.
     * post: el turno queda último. Si está en conflicto con el que era
     *       último, lanza {@link IllegalStateException}.
     */
    private void agregarAlFinal(Turno turno) {
        ValidacionesUtiles.validarEstadoFalso(
                cantidadDeTurnos > 0 && turnos[cantidadDeTurnos - 1].estaEnConflictoCon(turno),
                "Las agendas tienen turnos en conflicto: el " + turno + " choca con otro turno.");
        turnos[cantidadDeTurnos] = turno;
        cantidadDeTurnos++;
    }

    /** Corre un lugar a la derecha los turnos desde {@code indice}, dejando libre esa posición. */
    private void abrirHueco(int indice) {
        for (int i = cantidadDeTurnos; i > indice; i--) {
            turnos[i] = turnos[i - 1];
        }
    }

    /** Corre un lugar a la izquierda los turnos que siguen a {@code indice}, pisando esa posición. */
    private void cerrarHueco(int indice) {
        for (int i = indice; i < cantidadDeTurnos - 1; i++) {
            turnos[i] = turnos[i + 1];
        }
        turnos[cantidadDeTurnos - 1] = null;
    }

    private void aumentarCapacidad() {
        Turno[] masGrande = new Turno[turnos.length * FACTOR_DE_CRECIMIENTO];
        for (int i = 0; i < cantidadDeTurnos; i++) {
            masGrande[i] = turnos[i];
        }
        turnos = masGrande;
    }

    /** Devuelve un vector nuevo con los primeros {@code cantidad} turnos del original. */
    private static Turno[] recortar(Turno[] original, int cantidad) {
        Turno[] recortado = new Turno[cantidad];
        for (int i = 0; i < cantidad; i++) {
            recortado[i] = original[i];
        }
        return recortado;
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String resultado = "Agenda [" + apertura + ", " + cierre + "): " + cantidadDeTurnos + " turnos, "
                + getMinutosReservados() + " minutos reservados";
        for (int i = 0; i < cantidadDeTurnos; i++) {
            resultado += "\n  " + (i + 1) + ". " + turnos[i];
        }
        return resultado;
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getApertura() {
        return apertura;
    }

    public int getCierre() {
        return cierre;
    }

    public int getCantidadDeTurnos() {
        return cantidadDeTurnos;
    }
}
