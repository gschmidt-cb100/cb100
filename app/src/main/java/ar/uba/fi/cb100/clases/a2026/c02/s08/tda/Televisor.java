package ar.uba.fi.cb100.clases.a2026.c02.s08.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

public class Televisor {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private EntradaDeTelevisor[] entradas;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     * Crea un televisor con la cantidad de entradas HDMI y la cantidad de canales disponibles para la entrada de cable.
     * @param cantidadDeEntradasHDMI: la cantidad de entradas HDMI que tendrá el televisor.
     * @param cantidadDeCanalesDisponibles: la cantidad de canales disponibles para la entrada de cable del televisor.
     */
    public Televisor(int cantidadDeEntradasHDMI, int cantidadDeCanalesDisponibles) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeEntradasHDMI, "La cantidad de entradas HDMI debe ser mayor que cero.");
        ValidacionesUtiles.esMayorQueCero(cantidadDeCanalesDisponibles, "La cantidad de canales disponibles debe ser mayor que cero.");
        this.entradas = new EntradaDeTelevisor[cantidadDeEntradasHDMI + 1];
        this.entradas[0] = new EntradaDeCableDeTelevisor(cantidadDeCanalesDisponibles);
        for (int i = 1; i <= cantidadDeEntradasHDMI; i++) {
            this.entradas[i] = new EntradaHDMIDeTelevisor(i);
        }
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Selecciona un canal de cable en la entrada de cable del televisor y activa la entrada.
     * @param canal: el número del canal de cable que se desea seleccionar.
     */
    public void seleccionarCanalDeCable(int canal) {
        EntradaDeTelevisor entradaCable = entradas[0];
        ValidacionesUtiles.validarFalso(entradaCable.estaActiva(), "La entrada de cable ya está activa. No se puede cambiar el canal mientras la entrada está activa.");
        if (entradaCable instanceof EntradaDeCableDeTelevisor) {
            ((EntradaDeCableDeTelevisor) entradaCable).cambiarCanal(canal);
        }
    }

    /**
     * Activa la entrada de televisor especificada por su número. Desactiva todas las demás entradas.
     * @param numeroDeEntrada: el número de la entrada que se desea activar (0 para la entrada de cable, 1 para la primera entrada HDMI, etc.).
     */
    public void activarEntradaHDMI(int numeroDeEntrada) {
        ValidacionesUtiles.validarRango(numeroDeEntrada, 1, entradas.length - 1, "Número de entrada inválido.");
        EntradaDeTelevisor entrada = entradas[numeroDeEntrada];
        for(EntradaDeTelevisor e : entradas) {
            e.desactivar();
        }
        entrada.activar();
    }

    /**
     * Desactiva todas las entradas de televisor y activa la entrada de televisor especificada por su número.
     * @param numeroDeEntrada: el número de la entrada que se desea activar (0 para la entrada de cable, 1 para la primera entrada HDMI, etc.).
     */
    public void seleccionarEntradaHDMI(int numeroDeEntrada) {
        ValidacionesUtiles.validarRango(numeroDeEntrada, 1, entradas.length - 1, "Número de entrada inválido.");
        EntradaDeTelevisor entrada = entradas[numeroDeEntrada];
        for(EntradaDeTelevisor e : entradas) {
            e.desactivar();
        }
    }

    /**
     * Activa la entrada de cable del televisor y desactiva todas las demás entradas.
     */
    public void activarEntradaDeCable() {
        EntradaDeTelevisor entradaCable = entradas[0];
        for(EntradaDeTelevisor e : entradas) {
            e.desactivar();
        }
        entradaCable.activar();
    }

    /**
     * Sube al siguiente canal de cable en la entrada de cable del televisor. Si se supera el máximo de canales, vuelve al primer canal.
     */
    public void subirCanalDeCable() {
        EntradaDeTelevisor entradaCable = entradas[0];
        ValidacionesUtiles.validarFalso(entradaCable.estaActiva(), "La entrada de cable ya está activa. No se puede cambiar el canal mientras la entrada está activa.");
        if (entradaCable instanceof EntradaDeCableDeTelevisor) {
            ((EntradaDeCableDeTelevisor) entradaCable).subirCanal();
        }
    }

    /**
     * Baja al canal anterior de cable en la entrada de cable del televisor. Si se baja del primer canal, vuelve al último canal.
     */
    public void bajarCanalDeCable() {
        EntradaDeTelevisor entradaCable = entradas[0];
        ValidacionesUtiles.validarFalso(entradaCable.estaActiva(), "La entrada de cable ya está activa. No se puede cambiar el canal mientras la entrada está activa.");
        if (entradaCable instanceof EntradaDeCableDeTelevisor) {
            ((EntradaDeCableDeTelevisor) entradaCable).bajarCanal();
        }
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Devuelve la entrada de televisor que ha sido utilizada más veces.
     * @return la entrada de televisor más utilizada.
     */
    public EntradaDeTelevisor getEntradaMasUtilizada() {
        EntradaDeTelevisor entradaMasUtilizada = null;
        for(EntradaDeTelevisor entrada : entradas) {
            if (entradaMasUtilizada == null ||
                entrada.getCantidadDeUsos() > entradaMasUtilizada.getCantidadDeUsos()) {
                entradaMasUtilizada = entrada;
            }
        }
        return entradaMasUtilizada;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
