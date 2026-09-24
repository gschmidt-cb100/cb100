package ar.uba.fi.cb100.clases.a2026.c02.s04.tarea.Piano;

public class Tecla {
    private static final String[] notasValidas = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si"};
    private final String nota;

    private boolean esNotaValida(String nota){
        for(String notaValida : notasValidas){
            if(notaValida.equals(nota)){
                return true;
            }
        }
        return false;
    }
    //Constructor
    public Tecla(String nota){
        if(!esNotaValida(nota)){
            throw new IllegalArgumentException("Nota no valida"+ nota);
        }
        this.nota = nota;
    }
//getters simples
    public String getNota(){
        return nota;
    }
    @Override
    public String toString() {
        return nota;
    }
}
