package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Los tipos de carga que un vagón puede o no aceptar. Agregar un tipo es
 * agregar una constante acá y un {@link Carga} que lo declare: ni el
 * {@link Tren} ni los vagones existentes cambian.
 */
public enum TipoDeCarga {
    PASAJERO,
    CONTENEDOR,
    GRANEL,
    GANADO
}
