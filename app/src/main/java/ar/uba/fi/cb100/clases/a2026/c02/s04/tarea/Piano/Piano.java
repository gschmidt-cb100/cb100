package ar.uba.fi.cb100.clases.a2026.c02.s04.tarea.Piano;
import java.util.Vector;

public class Piano {
    private final Vector<Tecla> teclas;
    private final Vector <Tecla> historial; //el orden en que se presionaron

    //Constructor
    public Piano(Vector<Tecla> teclas){
        if(teclas == null || teclas.isEmpty()){
            throw new IllegalArgumentException("Tiene que haber al menos una tecla");
        }

        this.teclas = teclas;
        this.historial = new Vector<>();
    }
    public void presionar(Tecla tecla) {
        if (!teclas.contains(tecla)) {
            throw new IllegalArgumentException("esa tecla no pertenece a este piano");
        }
        historial.add(tecla);
    }

    private int contarPresiones(Tecla tecla) {
        int veces = 0;
        for (Tecla t : historial) {
            if (t.equals(tecla)) {   // <- acá usa "tecla"
                veces++;
            }
        }
        return veces;
    }

    public Vector<String> obtenerMelodiaInterpretada(){
        Vector<String> melodia = new Vector<>();
        for(Tecla tecla : historial){
                melodia.add(tecla.getNota());
            }

        return melodia;
    }

    public Tecla obtenerMasPresionada(){
        if(historial.isEmpty()){
            throw new IllegalStateException("no se presiono ninguna tecla");
        }

        Tecla masPresionada = historial.get(0); //primer elemento
        int maxVeces = contarPresiones(masPresionada);

        for (Tecla tecla : teclas) {
            int veces = contarPresiones(tecla);
            if (veces > maxVeces) {
                maxVeces = veces;
                masPresionada = tecla;
            }
        }
            return masPresionada;
        }

        public int obtenerCantTotalDePresiones(){
            return historial.size();
        }

    }

