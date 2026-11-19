package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.tda;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Turno: una reserva de la supercomputadora para un usuario, durante un
 * intervalo de minutos del día.
 *
 * Responsabilidad: conocer sus datos, garantizar que son válidos y responder
 * todo lo que tiene que ver con SU intervalo (cuánto dura, si se superpone
 * con otro, si termina después de un minuto). La Agenda le pregunta al
 * turno; no resuelve estas cuentas por él.
 * Es inmutable: una vez creado no cambia, por eso se puede compartir entre
 * la agenda y quien lo consulta sin riesgo.
 *
 * Invariante:
 *  - codigo y usuario no son null ni están vacíos.
 *  - 0 <= inicio < fin <= MINUTOS_POR_DIA.
 *
 * Hipótesis:
 *  H1. El intervalo es semiabierto, [inicio, fin): el minuto de fin ya no
 *      pertenece al turno. Un turno que termina a las 600 y otro que empieza
 *      a las 600 NO están en conflicto.
 *  H2. Dos turnos son el mismo si tienen el mismo código.
 *  H3. Un turno no cruza la medianoche.
 */
public class Turno {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

    public static final int MINUTOS_POR_DIA = 24 * 60;

//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String codigo;
    private final String usuario;
    private final int inicio;
    private final int fin;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code codigo} y {@code usuario} no son null ni están vacíos;
     *      {@code inicio} y {@code fin} son minutos desde las 0:00, con
     *      0 <= inicio < fin <= MINUTOS_POR_DIA.
     * post: crea el turno con esos datos. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Turno(String codigo, String usuario, int inicio, int fin) {
        validarTexto(codigo, "código");
        validarTexto(usuario, "usuario");
        ValidacionesUtiles.validarRango(inicio, 0, MINUTOS_POR_DIA - 1, "inicio");
        ValidacionesUtiles.validarRango(fin, inicio + 1, MINUTOS_POR_DIA, "fin");
        this.codigo = codigo.trim();
        this.usuario = usuario.trim();
        this.inicio = inicio;
        this.fin = fin;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /** post: devuelve la duración del turno en minutos. */
    public int getDuracion() {
        return fin - inicio;
    }

    /**
     * post: devuelve true si el turno comparte al menos un minuto con el
     *       intervalo [{@code inicio}, {@code fin}) recibido (H1).
     */
    public boolean seSuperponeCon(int inicio, int fin) {
        return this.inicio < fin && inicio < this.fin;
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve true si los intervalos de los dos turnos se superponen.
     */
    public boolean estaEnConflictoCon(Turno otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "turno");
        return seSuperponeCon(otro.inicio, otro.fin);
    }

    /** post: devuelve true si el turno sigue ocupando la máquina después de ese minuto. */
    public boolean terminaDespuesDe(int minuto) {
        return fin > minuto;
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve true si este turno empieza antes que el otro.
     */
    public boolean empiezaAntesQue(Turno otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "turno");
        return this.inicio < otro.inicio;
    }

    /**
     * pre: {@code codigo} no es null.
     * post: devuelve true si el código del turno es el recibido.
     */
    public boolean tieneCodigo(String codigo) {
        ValidacionesUtiles.validarDistintoDeNull(codigo, "código");
        return this.codigo.equals(codigo.trim());
    }

    private static void validarTexto(String texto, String nombreDelDato) {
        ValidacionesUtiles.validarDistintoDeNull(texto, nombreDelDato);
        ValidacionesUtiles.validarFalso(texto.isBlank(), "El dato " + nombreDelDato + " no puede estar vacío.");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /** Dos turnos son iguales si tienen el mismo código (H2). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turno otro = (Turno) o;
        return this.codigo.equals(otro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Turno " + codigo + " de " + usuario + " [" + inicio + ", " + fin + ")";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }
}
