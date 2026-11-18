package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ArchivoDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.GeneradorDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.UbicacionDelArchivo;

/**
 * Punto 6 del enunciado: genera el Excel de ejemplo. Sin argumentos crea
 * 50.000 personas con la semilla por defecto; se puede pasar otra cantidad y
 * otra semilla: {@code PrincipalGenerarArchivo 1000 7}.
 */
public class PrincipalGenerarArchivo {

    public static final int CANTIDAD_POR_DEFECTO = 50_000;

    public static void main(String[] args) throws IOException {
        int cantidad = args.length > 0 ? Integer.parseInt(args[0]) : CANTIDAD_POR_DEFECTO;
        long semilla = args.length > 1 ? Long.parseLong(args[1]) : GeneradorDePersonas.SEMILLA_POR_DEFECTO;
        Path archivo = UbicacionDelArchivo.getArchivoDeEjemplo();

        long inicio = System.currentTimeMillis();
        List<Persona> personas = GeneradorDePersonas.generar(cantidad, semilla);
        ArchivoDePersonas.escribir(archivo, personas);
        System.out.printf("Generadas %d personas en %s (%.1f s)%n", personas.size(),
                archivo.toAbsolutePath(), (System.currentTimeMillis() - inicio) / 1000.0);
    }
}
