package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.SwingUtilities;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ArchivoDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.CatalogoDeIndices;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.UbicacionDelArchivo;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.vista.VentanaPrincipal;

/**
 * El programa del ejercicio integrador, con pantalla. Lee el Excel (si no
 * existe, lo genera), arma los índices y el grafo, y abre la ventana.
 * La carga lleva unos segundos: el avance se informa por consola.
 */
public class PrincipalIntegrador {

    public static void main(String[] args) throws IOException {
        Path archivo = UbicacionDelArchivo.getArchivoDeEjemplo();
        if (!Files.exists(archivo)) {
            System.out.println("No está el archivo de ejemplo: se genera (tarda unos 20 segundos)...");
            PrincipalGenerarArchivo.main(new String[0]);
        }

        System.out.println("Leyendo " + archivo.toAbsolutePath() + " ...");
        ArchivoDePersonas.ResultadoDeCarga carga = ArchivoDePersonas.leer(archivo);
        List<Persona> personas = carga.personas();
        System.out.println("   " + personas.size() + " personas; " + carga.errores().size() + " filas descartadas");
        carga.errores().stream().limit(10).forEach(error -> System.out.println("   " + error));

        System.out.println("Armando y midiendo los nueve índices...");
        CatalogoDeIndices catalogo = new CatalogoDeIndices(personas);
        System.out.println("Armando el grafo familiar...");
        GrafoFamiliar grafo = new GrafoFamiliar(personas);

        SwingUtilities.invokeLater(() -> new VentanaPrincipal(personas, catalogo, grafo).setVisible(true));
    }
}
