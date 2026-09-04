package ar.uba.fi.cb100.guia.i03_poo.i04_dificil.e03;

/**
 * Los estados posibles de una mesa. Un enum (y no booleans sueltos como
 * {@code estaLibre}/{@code estaReservada}) hace imposibles los estados
 * contradictorios.
 */
public enum EstadoDeMesa {
    LIBRE,
    RESERVADA,
    OCUPADA
}
