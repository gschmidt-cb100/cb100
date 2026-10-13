package ar.uba.fi.cb100.clases.a2026.c02.s08.parcial;

public class ParcialForos {

    /**
     * Opcion 1
     * @param args

    public static Mensaje buscarMensajeMasVotadoDelUsuarioSegunTematica(String[] args) {
        Mensaje maximo = null;
        for(Foro foro: foros) {
            if (tieneLaTematica(foro, tematicaBuscada)) {
                List<Mensaje> = getMensajes(foro, autorBuscado);
                maximo = obtenerMaximo(mensajes, maximo);
            }
        }
        return maximo;
    }
     */

    /**
     * Opcion 1
     * @param args

    public static Mensaje buscarMensajeMasVotadoDelUsuarioSegunTematica2(String[] args) {
        Mensaje maximo = null;
        for(Foro foro: foros) {
    if (tieneLaTematica(foro, tematicaBuscada)) {
    List<Mensaje> = getMensajes(foro, autorBuscado);
    maximo = obtenerMaximo(mensajes, maximo);
    }
    }
    return maximo;
    }
     */

    /**
     * Opcion 2
     * @param args

    public static void main(String[] args) {

        for(Foro foro: foros) {
            if (foro.tieneLaTematica(tematicaBuscada)) {
                List<Mensaje> = foro.getMensajes(autorBuscado);
            }
        }
    }
     */

    /**
     * Opcion 3: no va
     * @param args

    public static void main2(String[] args) {

        for(Foro foro: foros) {
            if (foro.tieneLaTematica(tematicaBuscada)) {
                List<Mensaje> = getMensajes(foro);
                filtrar aca
            }
        }
    }
     */

    /**
     * No hacer nunca
     * @param args

    public static void main(String[] args) {
        List<Foro> forosConTematica = filtrarTematica(foros, tematicaBuscada);
        List<Mensaje> mensajesDelAutor = filtrarAutor(forosConTematica, autorBuscado);
    }
     */
}
