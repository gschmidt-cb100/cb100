package ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.funcional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import ar.uba.fi.cb100.clases.a2026.c02.s04.ValidacionesUtiles;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Geocodificacion;
import ar.uba.fi.cb100.clases.a2026.c02.s14.integrador.datos.Persona;

/**
 * Inventa personas para el archivo de ejemplo. Los datos son ficticios pero
 * coherentes: cuatro generaciones (bisabuelos, abuelos, padres, hijos), cada
 * persona con padre y madre de la generación anterior, el apellido del padre,
 * y una ciudad real con sus coordenadas corridas unos metros.
 * <p>
 * Como los padres siempre son de una generación anterior, el parentesco no
 * tiene ciclos. Con la misma semilla se genera siempre el mismo archivo.
 */
public final class GeneradorDePersonas {

    public static final long SEMILLA_POR_DEFECTO = 2026L;

    /** Qué fracción del total es cada generación, de la más vieja a la más joven. */
    private static final double[] PROPORCION_POR_GENERACION = {0.08, 0.20, 0.32, 0.40};
    private static final int[][] EDADES_POR_GENERACION = {{75, 98}, {50, 74}, {25, 49}, {0, 24}};
    private static final double PROBABILIDAD_DE_PROGENITOR_DESCONOCIDO = 0.08;
    private static final double PROBABILIDAD_DE_VIVIR_DONDE_EL_PADRE = 0.75;
    private static final double DISPERSION_EN_GRADOS = 0.04;
    /** Nadie es padre o madre con menos de esta edad: el hijo tiene al menos esta diferencia con los dos. */
    private static final int EDAD_MINIMA_PARA_TENER_HIJOS = 18;

    private static final String[] NOMBRES_MASCULINOS = {
        "Juan", "Carlos", "Luis", "Jorge", "Miguel", "Pedro", "Diego", "Martín", "Pablo", "Sergio",
        "Lucas", "Mateo", "Nicolás", "Tomás", "Bruno", "Agustín", "Facundo", "Gonzalo", "Federico", "Ramiro",
        "Santiago", "Joaquín", "Ignacio", "Franco", "Emiliano", "Hernán", "Gustavo", "Ricardo", "Daniel", "Andrés"};
    private static final String[] NOMBRES_FEMENINOS = {
        "María", "Ana", "Laura", "Carla", "Lucía", "Sofía", "Julia", "Paula", "Marta", "Elena",
        "Camila", "Valentina", "Florencia", "Micaela", "Rocío", "Agustina", "Daniela", "Carolina", "Victoria", "Abril",
        "Martina", "Julieta", "Milagros", "Antonella", "Brenda", "Silvia", "Patricia", "Mónica", "Graciela", "Noelia"};
    private static final String[] APELLIDOS = {
        "González", "Rodríguez", "Gómez", "Fernández", "López", "Díaz", "Martínez", "Pérez", "García", "Sánchez",
        "Romero", "Sosa", "Álvarez", "Torres", "Ruiz", "Ramírez", "Flores", "Benítez", "Acosta", "Medina",
        "Herrera", "Suárez", "Aguirre", "Giménez", "Gutiérrez", "Pereyra", "Rojas", "Molina", "Castro", "Ortiz",
        "Silva", "Núñez", "Luna", "Juárez", "Cabrera", "Ríos", "Morales", "Godoy", "Moreno", "Ferreyra",
        "Domínguez", "Carrizo", "Peralta", "Castillo", "Ledesma", "Quiroga", "Vega", "Vera", "Muñoz", "Ojeda",
        "Ponce", "Villalba", "Cardozo", "Navarro", "Coronel", "Vázquez", "Ramos", "Vargas", "Cáceres", "Arias",
        "Figueroa", "Córdoba", "Correa", "Maldonado", "Paz", "Rivero", "Miranda", "Mansilla", "Farías", "Roldán",
        "Schmidt", "Ferrari", "Bianchi", "Russo", "Kowalski", "Müller", "Weber", "Costa", "Santos", "Oliveira"};
    private static final String[] CALLES = {
        "San Martín", "Belgrano", "Rivadavia", "Mitre", "Sarmiento", "Moreno", "9 de Julio", "25 de Mayo",
        "Colón", "Urquiza", "Independencia", "Libertad", "Güemes", "Alberdi", "Paso", "Lavalle"};

    /** Una ciudad real: su nombre, provincia o estado, país, código postal, prefijo telefónico y centro. */
    private record Ciudad(String nombre, String provincia, String pais, String codigoPostal, String prefijo,
                          double latitud, double longitud) {
    }

    private static final Ciudad[] CIUDADES = {
        new Ciudad("Buenos Aires", "Ciudad Autónoma de Buenos Aires", "Argentina", "C1000", "+54 11", -34.6037, -58.3816),
        new Ciudad("La Plata", "Buenos Aires", "Argentina", "B1900", "+54 221", -34.9205, -57.9536),
        new Ciudad("Mar del Plata", "Buenos Aires", "Argentina", "B7600", "+54 223", -38.0055, -57.5426),
        new Ciudad("Bahía Blanca", "Buenos Aires", "Argentina", "B8000", "+54 291", -38.7183, -62.2663),
        new Ciudad("Córdoba", "Córdoba", "Argentina", "X5000", "+54 351", -31.4201, -64.1888),
        new Ciudad("Río Cuarto", "Córdoba", "Argentina", "X5800", "+54 358", -33.1232, -64.3493),
        new Ciudad("Rosario", "Santa Fe", "Argentina", "S2000", "+54 341", -32.9442, -60.6505),
        new Ciudad("Santa Fe", "Santa Fe", "Argentina", "S3000", "+54 342", -31.6107, -60.6973),
        new Ciudad("Mendoza", "Mendoza", "Argentina", "M5500", "+54 261", -32.8895, -68.8458),
        new Ciudad("San Miguel de Tucumán", "Tucumán", "Argentina", "T4000", "+54 381", -26.8083, -65.2176),
        new Ciudad("Salta", "Salta", "Argentina", "A4400", "+54 387", -24.7821, -65.4232),
        new Ciudad("Neuquén", "Neuquén", "Argentina", "Q8300", "+54 299", -38.9516, -68.0591),
        new Ciudad("San Carlos de Bariloche", "Río Negro", "Argentina", "R8400", "+54 294", -41.1335, -71.3103),
        new Ciudad("Posadas", "Misiones", "Argentina", "N3300", "+54 376", -27.3621, -55.9009),
        new Ciudad("Ushuaia", "Tierra del Fuego", "Argentina", "V9410", "+54 2901", -54.8019, -68.3030),
        new Ciudad("Montevideo", "Montevideo", "Uruguay", "11000", "+598 2", -34.9011, -56.1645),
        new Ciudad("Punta del Este", "Maldonado", "Uruguay", "20100", "+598 42", -34.9667, -54.9500),
        new Ciudad("Santiago", "Región Metropolitana", "Chile", "8320000", "+56 2", -33.4489, -70.6693),
        new Ciudad("Valparaíso", "Valparaíso", "Chile", "2340000", "+56 32", -33.0472, -71.6127),
        new Ciudad("Asunción", "Distrito Capital", "Paraguay", "1001", "+595 21", -25.2637, -57.5759),
        new Ciudad("São Paulo", "São Paulo", "Brasil", "01000-000", "+55 11", -23.5505, -46.6333),
        new Ciudad("Porto Alegre", "Rio Grande do Sul", "Brasil", "90000-000", "+55 51", -30.0346, -51.2177),
        new Ciudad("La Paz", "La Paz", "Bolivia", "0201", "+591 2", -16.4897, -68.1193),
        new Ciudad("Lima", "Lima", "Perú", "15001", "+51 1", -12.0464, -77.0428)};

    /** Lo que hace falta recordar de alguien ya generado para poder ser padre o madre de otro. */
    private record Progenitor(long dni, String apellido, Ciudad ciudad, int edad) {
    }

    private GeneradorDePersonas() {
    }

    /**
     * pre: {@code cantidad} es mayor que cero.
     * post: devuelve esa cantidad de personas inventadas, con DNI únicos,
     *       siempre las mismas para la misma semilla.
     */
    public static List<Persona> generar(int cantidad, long semilla) {
        ValidacionesUtiles.esMayorQueCero(cantidad, "cantidad de personas");
        Random azar = new Random(semilla);
        Set<Long> dnisUsados = new HashSet<>();
        List<Persona> personas = new ArrayList<>(cantidad);
        List<Progenitor> padresPosibles = new ArrayList<>();
        List<Progenitor> madresPosibles = new ArrayList<>();

        for (int generacion = 0; generacion < PROPORCION_POR_GENERACION.length; generacion++) {
            boolean esLaUltima = generacion == PROPORCION_POR_GENERACION.length - 1;
            int cuantas = esLaUltima ? cantidad - personas.size()
                                     : (int) Math.round(cantidad * PROPORCION_POR_GENERACION[generacion]);
            List<Progenitor> varones = new ArrayList<>();
            List<Progenitor> mujeres = new ArrayList<>();
            for (int i = 0; i < cuantas && personas.size() < cantidad; i++) {
                boolean esVaron = azar.nextBoolean();
                Progenitor padre = elegir(padresPosibles, azar);
                Progenitor madre = elegir(madresPosibles, azar);
                Persona persona = crear(azar, dnisUsados, generacion, esVaron, padre, madre);
                personas.add(persona);
                Progenitor comoProgenitor = new Progenitor(persona.dni(), persona.apellido(),
                        ciudadDe(persona), persona.edad());
                (esVaron ? varones : mujeres).add(comoProgenitor);
            }
            padresPosibles = varones;
            madresPosibles = mujeres;
        }
        return personas;
    }

    private static Persona crear(Random azar, Set<Long> dnisUsados, int generacion, boolean esVaron,
                                 Progenitor padre, Progenitor madre) {
        String nombre = esVaron ? unoDe(NOMBRES_MASCULINOS, azar) : unoDe(NOMBRES_FEMENINOS, azar);
        String apellido = (padre != null) ? padre.apellido() : unoDe(APELLIDOS, azar);
        Ciudad ciudad = (padre != null && azar.nextDouble() < PROBABILIDAD_DE_VIVIR_DONDE_EL_PADRE)
                ? padre.ciudad() : CIUDADES[azar.nextInt(CIUDADES.length)];
        long dni = dniNuevo(azar, dnisUsados, generacion);
        int[] edades = EDADES_POR_GENERACION[generacion];
        int edad = edades[0] + azar.nextInt(edades[1] - edades[0] + 1);
        if (padre != null) {
            edad = Math.min(edad, padre.edad() - EDAD_MINIMA_PARA_TENER_HIJOS);
        }
        if (madre != null) {
            edad = Math.min(edad, madre.edad() - EDAD_MINIMA_PARA_TENER_HIJOS);
        }
        edad = Math.max(0, edad);
        // se redondea a los 6 decimales con que viaja en el Excel: lo que se escribe es lo que se lee
        Geocodificacion punto = Geocodificacion.desdeTexto(new Geocodificacion(
                ciudad.latitud() + (azar.nextDouble() - 0.5) * DISPERSION_EN_GRADOS,
                ciudad.longitud() + (azar.nextDouble() - 0.5) * DISPERSION_EN_GRADOS).aTexto());
        String telefono = ciudad.prefijo() + " " + (4_000_000 + azar.nextInt(5_999_999));
        String email = sinAcentos(nombre + "." + apellido).toLowerCase(Locale.ROOT) + "." + dni + "@ejemplo.com";
        String direccion = unoDe(CALLES, azar) + " " + (100 + azar.nextInt(4900));
        return new Persona(dni, nombre, apellido, edad, ciudad.nombre(), ciudad.provincia(), ciudad.pais(),
                telefono, email, direccion, ciudad.codigoPostal(), punto,
                (padre != null) ? padre.dni() : null, (madre != null) ? madre.dni() : null);
    }

    /** Elige un progenitor de la generación anterior; a veces ninguno (desconocido), y ninguno en la primera. */
    private static Progenitor elegir(List<Progenitor> posibles, Random azar) {
        if (posibles.isEmpty() || azar.nextDouble() < PROBABILIDAD_DE_PROGENITOR_DESCONOCIDO) {
            return null;
        }
        return posibles.get(azar.nextInt(posibles.size()));
    }

    /** Los DNI más bajos son de las generaciones más viejas, como en la realidad. */
    private static long dniNuevo(Random azar, Set<Long> usados, int generacion) {
        long base = 4_000_000L + generacion * 12_000_000L;
        long dni;
        do {
            dni = base + azar.nextInt(12_000_000);
        } while (!usados.add(dni));
        return dni;
    }

    private static Ciudad ciudadDe(Persona persona) {
        for (Ciudad ciudad : CIUDADES) {
            if (ciudad.nombre().equals(persona.ciudad())) {
                return ciudad;
            }
        }
        throw new IllegalStateException("ciudad desconocida: " + persona.ciudad());
    }

    private static String unoDe(String[] opciones, Random azar) {
        return opciones[azar.nextInt(opciones.length)];
    }

    private static String sinAcentos(String texto) {
        return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").replace(" ", "");
    }
}
