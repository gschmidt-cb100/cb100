package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.facil.solucion.listas;

import java.util.List;

public class PrincipalServicioDeStreaming {

    public static void main(String[] args) {
        Serie elEternauta = new Serie("El Eternauta", "Ciencia ficción");
        Serie darkMatter = new Serie("Dark Matter", "Ciencia ficción");
        Serie elEncargado = new Serie("El Encargado", "Comedia");
        Serie okupas = new Serie("Okupas", "Drama");

        List<Opinion> opiniones = List.of(
                new Opinion(elEternauta, 9, EstadoOpinion.PUBLICADA),
                new Opinion(elEternauta, 10, EstadoOpinion.PUBLICADA),
                new Opinion(elEternauta, 1, EstadoOpinion.MODERADA),      // no cuenta
                new Opinion(darkMatter, 6, EstadoOpinion.PUBLICADA),
                new Opinion(darkMatter, 7, EstadoOpinion.PUBLICADA),
                new Opinion(elEncargado, 8, EstadoOpinion.PUBLICADA),
                new Opinion(elEncargado, 9, EstadoOpinion.PUBLICADA),
                new Opinion(elEncargado, 7, EstadoOpinion.PUBLICADA),
                new Opinion(okupas, 10, EstadoOpinion.PUBLICADA),         // género no pedido
                new Opinion(okupas, 10, EstadoOpinion.PUBLICADA));

        ServicioDeStreaming servicio = new ServicioDeStreaming();
        List<Serie> resultado = servicio.buscarSeriesMejorPuntuadas(
                opiniones, List.of("Ciencia ficción", "Comedia"), 2, 7);

        // [El Eternauta (Ciencia ficción), El Encargado (Comedia)]  (9,5 y 8; Dark Matter tiene 6,5)
        System.out.println(resultado);
    }
}
