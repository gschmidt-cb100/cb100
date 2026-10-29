package ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas;

import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Ciudad;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.EstadoMedicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.datos.Medicion;
import ar.uba.fi.cb100.clases.a2026.c02.s11.parcial.listas.servicios.ServicioMeteorologico;

public class PrincipalServicioMeteorologico {

    public static void main(String[] args) {
        Ciudad mendoza = new Ciudad("Mendoza", "Mendoza");
        Ciudad sanRafael = new Ciudad("San Rafael", "Mendoza");
        Ciudad cordoba = new Ciudad("Córdoba", "Córdoba");
        Ciudad salta = new Ciudad("Salta", "Salta");

        List<Medicion> mediciones = List.of(
                new Medicion(mendoza, 5, EstadoMedicion.VALIDA),
                new Medicion(mendoza, 30, EstadoMedicion.VALIDA),
                new Medicion(mendoza, 60, EstadoMedicion.ERRONEA),     // no cuenta
                new Medicion(sanRafael, 10, EstadoMedicion.VALIDA),
                new Medicion(sanRafael, 22, EstadoMedicion.VALIDA),
                new Medicion(cordoba, 8, EstadoMedicion.VALIDA),
                new Medicion(cordoba, 28, EstadoMedicion.VALIDA),
                new Medicion(cordoba, 15, EstadoMedicion.VALIDA),
                new Medicion(salta, 0, EstadoMedicion.VALIDA),         // provincia no pedida
                new Medicion(salta, 40, EstadoMedicion.VALIDA));

        ServicioMeteorologico servicio = new ServicioMeteorologico();
        List<Ciudad> resultado = servicio.buscarCiudadesConMayorAmplitudTermica(
                mediciones, List.of("Mendoza", "Córdoba"), 2, 15);

        System.out.println(resultado);   // [Mendoza (Mendoza), Córdoba (Córdoba)]  (25 y 20; San Rafael tiene 12)
    }
}
