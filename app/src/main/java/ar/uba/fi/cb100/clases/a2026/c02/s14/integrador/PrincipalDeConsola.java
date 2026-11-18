package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.CampoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ArchivoDePersonas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.CatalogoDeIndices;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.EnlaceDeMapas;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.FiltroPorFuerzaBruta;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.Medicion;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.ResultadoDeBusqueda;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional.UbicacionDelArchivo;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.grafo.GrafoFamiliar;

/**
 * El mismo sistema, sin ventanas: recorre los puntos del enunciado e imprime
 * los resultados. Sirve para ver los números en clase sin distraerse con la
 * pantalla, y muestra que toda la lógica vive fuera de la vista.
 */
public class PrincipalDeConsola {

    private static final int FILAS_A_MOSTRAR = 5;

    public static void main(String[] args) throws IOException {
        Path archivo = UbicacionDelArchivo.getArchivoDeEjemplo();
        if (!Files.exists(archivo)) {
            PrincipalGenerarArchivo.main(new String[0]);
        }

        long inicio = System.currentTimeMillis();
        ArchivoDePersonas.ResultadoDeCarga carga = ArchivoDePersonas.leer(archivo);
        List<Persona> personas = carga.personas();
        System.out.printf("Leídas %d personas (%d filas descartadas) en %.1f s%n%n", personas.size(),
                carga.errores().size(), (System.currentTimeMillis() - inicio) / 1000.0);

        System.out.println("1) ÍNDICES: memoria, tiempo de armado y tiempo promedio de búsqueda");
        CatalogoDeIndices catalogo = new CatalogoDeIndices(personas);
        System.out.printf("   %-8s %-9s %12s %14s %18s%n", "índice", "campo", "memoria KB", "armado ms", "búsqueda µs");
        for (Medicion m : catalogo.getMediciones()) {
            System.out.printf("   %-8s %-9s %12.0f %14.1f %18.1f%n", m.tipo(), m.campo(),
                    m.getKilobytes(), m.getMilisegundosDeConstruccion(), m.getMicrosegundosPorBusqueda());
        }

        System.out.println();
        System.out.println("2 y 3) FILTRO con los índices: apellido que empieza con \"schm\"");
        for (ResultadoDeBusqueda r : catalogo.buscarConTodos(CampoDeBusqueda.APELLIDO, "schm")) {
            System.out.printf("   %-34s %6d coincidencias en %9.3f ms%n", r.metodo(), r.getCantidad(), r.getMilisegundos());
        }
        ResultadoDeBusqueda porApellido = catalogo.buscarConTodos(CampoDeBusqueda.APELLIDO, "schm").get(0);
        mostrarGrilla(porApellido.personas());

        System.out.println();
        System.out.println("3) FILTRO por fuerza bruta");
        for (FiltroPorFuerzaBruta.Campo campo : FiltroPorFuerzaBruta.Campo.values()) {
            String texto = campo == FiltroPorFuerzaBruta.Campo.PAIS ? "uruguay" : "mendoza";
            ResultadoDeBusqueda r = FiltroPorFuerzaBruta.filtrar(personas, campo, texto);
            System.out.printf("   %-28s \"%s\": %6d coincidencias en %9.3f ms%n", r.metodo(), texto,
                    r.getCantidad(), r.getMilisegundos());
        }
        System.out.println("   personas por país: " + FiltroPorFuerzaBruta.contarPor(personas, FiltroPorFuerzaBruta.Campo.PAIS));

        System.out.println();
        Persona elegida = personas.stream()
                .filter(p -> p.dniDelPadre() != null && p.dniDeLaMadre() != null && p.edad() >= 25 && p.edad() < 50)
                .findFirst().orElse(personas.get(0));
        System.out.println("4) FICHA de " + elegida.getNombreCompleto());
        System.out.println("   " + elegida);

        System.out.println();
        GrafoFamiliar grafo = new GrafoFamiliar(personas);
        System.out.printf("5) GRAFO: %d personas y %d aristas padre→hijo%n", grafo.getCantidadDeVertices(),
                grafo.getCantidadDeAristas());
        long raiz = grafo.getPadresDe(elegida.dni()).get(0);
        System.out.println("   desde " + nombre(grafo, raiz) + " (padre de la persona elegida):");
        System.out.println("   5.1 en profundidad: " + nombres(grafo, grafo.recorrerEnProfundidad(raiz)));
        System.out.println("   5.1 en anchura:     " + nombres(grafo, grafo.recorrerEnAnchura(raiz)));
        System.out.println("   5.2 topológico de la familia: "
                + nombres(grafo, grafo.ordenarTopologicamente(grafo.getFamiliaDe(elegida.dni(), 2, 12))));
        inicio = System.currentTimeMillis();
        int ordenados = grafo.ordenarTopologicamente().size();
        System.out.printf("   5.2 topológico de TODO el grafo: %d personas ordenadas en %d ms%n", ordenados,
                System.currentTimeMillis() - inicio);

        System.out.println();
        System.out.println("8) GOOGLE MAPS de la persona y sus padres:");
        List<Persona> paraElMapa = new java.util.ArrayList<>(List.of(elegida));
        grafo.getPadresDe(elegida.dni()).forEach(dni -> paraElMapa.add(grafo.getPersona(dni)));
        System.out.println("   " + EnlaceDeMapas.crear(paraElMapa));
    }

    private static void mostrarGrilla(List<Persona> personas) {
        System.out.printf("   %-10s %-14s %-14s%n", "DNI", "Nombre", "Apellido");
        personas.stream().limit(FILAS_A_MOSTRAR)
                .forEach(p -> System.out.printf("   %-10d %-14s %-14s%n", p.dni(), p.nombre(), p.apellido()));
        if (personas.size() > FILAS_A_MOSTRAR) {
            System.out.println("   ... y " + (personas.size() - FILAS_A_MOSTRAR) + " más");
        }
    }

    private static String nombre(GrafoFamiliar grafo, long dni) {
        Persona persona = grafo.getPersona(dni);
        return persona.nombre() + " " + persona.apellido();
    }

    private static String nombres(GrafoFamiliar grafo, List<Long> dnis) {
        return dnis.stream().limit(12).map(dni -> nombre(grafo, dni)).collect(Collectors.joining(" → "))
                + (dnis.size() > 12 ? " → ... (" + dnis.size() + " en total)" : "");
    }
}
