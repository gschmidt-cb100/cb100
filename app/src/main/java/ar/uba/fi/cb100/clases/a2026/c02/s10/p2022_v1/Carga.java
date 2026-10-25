package ar.uba.fi.cb100.clases.a2026.c02.s10.p2022_v1;

/**
 * Cualquier cosa que viaja en un vagón: un pasajero, un contenedor, granel,
 * ganado. Lo único que el tren y los vagones necesitan saber de una carga es
 * <b>de qué tipo es</b> (para que el vagón decida si la acepta) y <b>cuánto
 * pesa</b> (para no pasarse de la capacidad). Todo lo demás es asunto de
 * cada carga.
 */
public interface Carga {

    /** post: devuelve el tipo de carga, que es lo que un vagón mira para aceptarla o no. */
    TipoDeCarga getTipo();

    /** post: devuelve el peso [t] de la carga; nunca negativo. */
    double getPeso();

    /** post: devuelve una descripción corta, para informes y mensajes. */
    String getDescripcion();
}
