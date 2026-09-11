package ar.uba.fi.cb100.examenes.parciales.a2026.c01.o03.tda;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;

public class Piano {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private Tecla[] teclas;
    private Tecla[] historico;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    /**
     *
     * @param cantidadDeTeclas: cantidad de teclas que tendrá el piano. Debe ser mayor que cero.
     */
    public Piano(int cantidadDeTeclas) {
        ValidacionesUtiles.esMayorQueCero(cantidadDeTeclas, "Cantidad de teclas");
        this.teclas = new Tecla[cantidadDeTeclas];
        this.historico = new Tecla[100 * cantidadDeTeclas];
        for(int i = 0; i < this.teclas.length; i++) {
            this.teclas[i] = new Tecla(Nota.build(i), i);
        }
        for(int i = 0; i < this.historico.length; i++) {
            this.historico = null;
        }
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

    /**
     * Presiona la tecla indicada y la agrega al historial de teclas presionadas.
     * @param tecla: tecla que se desea presionar. Debe ser distinto de null y debe existir en el piano.
     */
    public void presionar(Tecla tecla) {
        ValidacionesUtiles.validarDistintoDeNull(tecla, "Tecla");
        Tecla teclaActual = getTecla(tecla);
        teclaActual.presionar();
        agregarAHistorico(teclaActual);
    }

    /**
     * Agrega la tecla presionada al historial de teclas presionadas.
     * @param tecla: tecla que se desea agregar al historial. Debe ser distinto de null y debe existir en el piano.
     */
    private void agregarAHistorico(Tecla tecla) {
        this.historico[getCantidadDeHistorico()] = tecla;
    }

    /**
     * Obtiene la tecla del piano que coincide con la tecla indicada.
     * @param tecla: tecla que se desea buscar. Debe ser distinto de null y debe existir en el piano.
     * @return la tecla del piano que coincide con la tecla indicada.
     */
    private Tecla getTecla(Tecla tecla) {
        for(int i = 0; i < this.teclas.length; i++) {
            if (this.teclas[i].equals(tecla)) {
                return this.teclas[i];
            }
        }
        throw new RuntimeException("No se encontro la tecla " + tecla);
    }

//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    /**
     * Obtiene la cantidad de teclas presionadas en el historial.
     * @return la cantidad de teclas presionadas en el historial.
     */
    public int getCantidadDeHistorico() {
        for(int i = 0; i < this.historico.length; i++) {
            if (this.historico[i] == null) {
                return i;
            }
        }
        return this.historico.length;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
