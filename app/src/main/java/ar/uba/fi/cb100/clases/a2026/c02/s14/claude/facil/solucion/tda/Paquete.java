package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.tda;

import java.util.Objects;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

/**
 * TDA Paquete: un bulto que se despacha a un destino.
 *
 * Responsabilidad: conocer sus datos, garantizar que son válidos y responder
 * las preguntas sobre sí mismo (¿va a tal destino?, ¿es frágil?, ¿pesa más
 * que otro?). Es inmutable: una vez creado no cambia, por eso se puede
 * compartir entre el camión y quien lo consulta sin riesgo.
 *
 * Invariante:
 *  - codigo y destino no son null ni están vacíos.
 *  - peso es mayor que cero.
 *
 * Hipótesis:
 *  H1. Dos paquetes son el mismo si tienen el mismo código. El destino, el
 *      peso y la fragilidad no lo identifican.
 *  H2. Ser frágil es una característica fija del paquete, no una etapa por
 *      la que pasa: no hay diagrama de estados, así que alcanza un boolean
 *      (si fuera un estado que cambia, sería un enum).
 *  H3. El destino se compara sin distinguir mayúsculas ni espacios en los
 *      extremos.
 */
public class Paquete {
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private final String codigo;
    private final String destino;
    private final double peso;
    private final boolean fragil;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * pre: {@code codigo} y {@code destino} no son null ni están vacíos;
     *      {@code peso} (kg) es mayor que cero.
     * post: crea el paquete con esos datos. Si no se cumple la pre, lanza
     *       {@link IllegalArgumentException}.
     */
    public Paquete(String codigo, String destino, double peso, boolean fragil) {
        validarTexto(codigo, "código");
        validarTexto(destino, "destino");
        ValidacionesUtiles.esMayorQueCero(peso, "peso");
        this.codigo = codigo.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.fragil = fragil;
    }

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * pre: {@code destino} no es null.
     * post: devuelve true si el paquete va a ese destino (H3).
     */
    public boolean vaA(String destino) {
        ValidacionesUtiles.validarDistintoDeNull(destino, "destino");
        return this.destino.equalsIgnoreCase(destino.trim());
    }

    /**
     * pre: {@code codigo} no es null.
     * post: devuelve true si el código del paquete es el recibido.
     */
    public boolean tieneCodigo(String codigo) {
        ValidacionesUtiles.validarDistintoDeNull(codigo, "código");
        return this.codigo.equals(codigo.trim());
    }

    /**
     * pre: {@code otro} no es null.
     * post: devuelve true si este paquete pesa estrictamente más que el otro.
     */
    public boolean esMasPesadoQue(Paquete otro) {
        ValidacionesUtiles.validarDistintoDeNull(otro, "paquete");
        return this.peso > otro.peso;
    }

    /** post: devuelve true si el paquete es frágil. */
    public boolean esFragil() {
        return fragil;
    }

    private static void validarTexto(String texto, String nombreDelDato) {
        ValidacionesUtiles.validarDistintoDeNull(texto, nombreDelDato);
        ValidacionesUtiles.validarFalso(texto.isBlank(), "El dato " + nombreDelDato + " no puede estar vacío.");
    }

//METODOS GENERALES ---------------------------------------------------------------------------------------

    /** Dos paquetes son iguales si tienen el mismo código (H1). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquete otro = (Paquete) o;
        return this.codigo.equals(otro.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Paquete " + codigo + " a " + destino + " (" + peso + " kg" + (fragil ? ", frágil" : "") + ")";
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public String getCodigo() {
        return codigo;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}
