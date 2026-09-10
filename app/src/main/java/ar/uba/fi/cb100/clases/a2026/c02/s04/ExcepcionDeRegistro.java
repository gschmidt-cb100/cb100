package ar.uba.fi.cb100.clases.a2026.c02.s04;

public class ExcepcionDeRegistro extends RuntimeException {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------

    private int numeroDeRegistro;
    private RecordError tipoDeError;

//CONSTRUCTORES -------------------------------------------------------------------------------------------

    public ExcepcionDeRegistro(String mensaje, int numeroDeRegistro, RecordError tipoDeError) {
        super(mensaje);
        this.numeroDeRegistro = numeroDeRegistro;
        this.tipoDeError = tipoDeError;
    }

//METODOS ABSTRACTOS --------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

    public int getNumeroDeRegistro() {
        return numeroDeRegistro;
    }

    public RecordError getTipoDeError() {
        return tipoDeError;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------

}