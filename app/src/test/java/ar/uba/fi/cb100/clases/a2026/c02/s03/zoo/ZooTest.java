package ar.uba.fi.cb100.clases.a2026.c02.s03.zoo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Animal;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Felino;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Jirafa;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Leon;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.animales.Oveja;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ActaDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.Racion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.comida.ResultadoDeAlimentacion;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.Cuidador;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeJirafa;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeLeon;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.cuidadores.CuidadorDeOveja;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Jaula;
import ar.uba.fi.cb100.clases.a2026.c02.s03.zoo.infraestructura.Zoologico;

/**
 * Cada test verifica una regla de {@code reglas-de-negocio.md}. Ninguno mira
 * la consola: el modelo devuelve datos y acá se comparan.
 *
 * <p>Los pesos que aporta cada ración (león 3.0, jirafa 2.0, oveja 0.5) son
 * los de las constantes {@code PESO_DE_LA_RACION} de cada cuidador. Si se
 * ajustan, hay que ajustar acá.
 */
class ZooTest {

    private static final double PESO_RACION_LEON = 3.0;
    private static final double PESO_RACION_JIRAFA = 2.0;
    private static final double PESO_RACION_OVEJA = 0.5;
    private static final double PRECISION = 0.0001;

    // ------------------------------------------------------------ fábricas

    private static Leon leon(String nombre, int agresividad) {
        return new Leon(nombre, 180.0, 190.0, agresividad);
    }

    private static Jirafa jirafa(String nombre) {
        return new Jirafa(nombre, 800.0, 850.0, 500);
    }

    private static Oveja oveja(String nombre) {
        return new Oveja(nombre, 60.0, 65.0);
    }

    private static Racion racionDeAyer() {
        return new Racion(LocalDateTime.now().minusDays(1), new String[]{"ayer"}, 1.0);
    }

    // ------------------------------------------------------------ R7: la ración engorda

    @Test
    @DisplayName("R7: una ración sube el peso en lo que aporta la rutina")
    void laRacionSubeElPeso() {
        Oveja dolly = oveja("Dolly");
        Cuidador lucia = new CuidadorDeOveja("Lucía", 3);

        ActaDeAlimentacion acta = lucia.alimentar(dolly);

        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, acta.resultado());
        assertTrue(acta.seAlimento());
        assertEquals(60.0, acta.pesoAntes(), PRECISION);
        assertEquals(60.0 + PESO_RACION_OVEJA, acta.pesoDespues(), PRECISION);
        assertEquals(PESO_RACION_OVEJA, acta.pesoGanado(), PRECISION);
        assertEquals(60.0 + PESO_RACION_OVEJA, dolly.getPeso(), PRECISION);
        assertEquals(3, acta.pasos().length, "la rutina de la oveja tiene tres pasos");
    }

    // ------------------------------------------------------------ R4 y R5: cuota diaria

    @Test
    @DisplayName("R5: el león come una vez por día; la segunda se saltea")
    void elLeonComeUnaVezPorDia() {
        Leon simba = leon("Simba", 2);
        Cuidador marta = new CuidadorDeLeon("Marta", 1);

        ActaDeAlimentacion primera = marta.alimentar(simba);
        ActaDeAlimentacion segunda = marta.alimentar(simba);

        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, primera.resultado());
        assertEquals(ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA, segunda.resultado());
        assertEquals(0.0, segunda.pesoGanado(), PRECISION, "saltear no cambia el peso");
        assertEquals(0, segunda.pasos().length, "saltear no ejecuta la rutina");
        assertEquals(1, simba.getCantidadDeRaciones(LocalDate.now()));
    }

    @Test
    @DisplayName("R4: la jirafa come dos veces por día y la tercera se saltea")
    void laJirafaComeDosVecesPorDia() {
        Jirafa melman = jirafa("Melman");
        Cuidador pedro = new CuidadorDeJirafa("Pedro", 2);

        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, pedro.alimentar(melman).resultado());
        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, pedro.alimentar(melman).resultado());
        assertEquals(ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA,
                pedro.alimentar(melman).resultado());
        assertEquals(800.0 + 2 * PESO_RACION_JIRAFA, melman.getPeso(), PRECISION);
    }

    @Test
    @DisplayName("R4: la oveja come tres veces por día y la cuarta se saltea")
    void laOvejaComeTresVecesPorDia() {
        Oveja dolly = oveja("Dolly");
        Cuidador lucia = new CuidadorDeOveja("Lucía", 3);

        for (int i = 1; i <= 3; i++) {
            assertEquals(ResultadoDeAlimentacion.ALIMENTADO, lucia.alimentar(dolly).resultado(),
                    "comida " + i);
        }
        assertEquals(ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA,
                lucia.alimentar(dolly).resultado(), "comida 4");
        assertEquals(3, dolly.getCantidadDeRaciones(LocalDate.now()));
    }

    @Test
    @DisplayName("R5: una ración de ayer no cuenta para la cuota de hoy")
    void laRacionDeAyerNoCuentaParaHoy() {
        Leon simba = leon("Simba", 2);
        Cuidador marta = new CuidadorDeLeon("Marta", 1);
        simba.registrarRacion(racionDeAyer());

        assertEquals(1, simba.getCantidadDeRaciones(), "está en el historial");
        assertEquals(0, simba.getCantidadDeRaciones(LocalDate.now()), "pero no es de hoy");
        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, marta.alimentar(simba).resultado(),
                "hoy le toca comer igual");
        assertEquals(ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA,
                marta.alimentar(simba).resultado(), "y la segunda de hoy se saltea");
    }

    // ------------------------------------------------------------ R6: especialidad

    @Test
    @DisplayName("R6: un cuidador de leones no toca a una oveja")
    void elCuidadorRespetaSuEspecialidad() {
        Oveja perdida = oveja("Perdida");
        Cuidador marta = new CuidadorDeLeon("Marta", 1);

        ActaDeAlimentacion acta = marta.alimentar(perdida);

        assertEquals(ResultadoDeAlimentacion.FUERA_DE_ESPECIALIDAD, acta.resultado());
        assertFalse(acta.seAlimento());
        assertEquals(60.0, perdida.getPeso(), PRECISION, "no se le cambió el peso");
        assertEquals(0, perdida.getCantidadDeRaciones(), "no cuenta como comida");
    }

    @Test
    @DisplayName("R6 en la jaula: no se puede alojar un animal que el cuidador no atiende")
    void laJaulaRechazaAnimalesFueraDeLaEspecialidadDelCuidador() {
        Jaula jaula = new Jaula(1, 3);
        jaula.asignarCuidador(new CuidadorDeLeon("Marta", 1));

        assertThrows(IllegalStateException.class, () -> jaula.alojar(oveja("Perdida")));
        assertTrue(jaula.estaVacia(), "no quedó nada alojado");
    }

    @Test
    @DisplayName("R6 en la jaula: no se puede asignar un cuidador que no atiende a los alojados")
    void laJaulaRechazaCuidadoresQueNoAtiendenALosAlojados() {
        Jaula corral = new Jaula(1, 3);
        corral.alojar(oveja("Dolly"));

        assertThrows(IllegalStateException.class,
                () -> corral.asignarCuidador(new CuidadorDeLeon("Marta", 1)));
        assertFalse(corral.tieneCuidador());
    }

    // ------------------------------------------------------------ R11: agresividad

    @Test
    @DisplayName("R11: un león agresivo se alimenta con la rutina de encierro")
    void elLeonAgresivoUsaLaRutinaDeEncierro() {
        Cuidador marta = new CuidadorDeLeon("Marta", 1);
        Leon nala = leon("Nala", 2);
        Leon simba = leon("Simba", 7);

        assertFalse(nala.necesitaEncierroParaAlimentar());
        assertTrue(simba.necesitaEncierroParaAlimentar());

        ActaDeAlimentacion tranquilo = marta.alimentar(nala);
        ActaDeAlimentacion agresivo = marta.alimentar(simba);

        assertTrue(tranquilo.pasos()[0].startsWith("Verificar la reja"));
        assertTrue(agresivo.pasos()[0].startsWith("Activar el cierre"));
        assertEquals(tranquilo.pesoGanado(), agresivo.pesoGanado(), PRECISION,
                "la rutina cambia, el peso que aporta no");
    }

    // ------------------------------------------------------------ R2: bien alimentado

    @Test
    @DisplayName("R2: está bien alimentado desde el 95% del peso óptimo")
    void estaBienAlimentadoAlLlegarAlUmbral() {
        Oveja flaca = new Oveja("Flaca", 58.0, 62.0);          // 58/62 = 0.935: baja
        Cuidador lucia = new CuidadorDeOveja("Lucía", 3);

        assertFalse(flaca.estaBienAlimentado());
        lucia.alimentar(flaca);                                // 58.5: 0.943, sigue baja
        assertFalse(flaca.estaBienAlimentado());
        lucia.alimentar(flaca);                                // 59.0: 0.951, ya no
        assertTrue(flaca.estaBienAlimentado());
    }

    // ------------------------------------------------------------ polimorfismo y herencia

    @Test
    @DisplayName("Polimorfismo: la misma llamada ejecuta tres rutinas distintas")
    void laMismaLlamadaEjecutaRutinasDistintas() {
        Cuidador[] cuidadores = {
                new CuidadorDeLeon("Marta", 1),
                new CuidadorDeJirafa("Pedro", 2),
                new CuidadorDeOveja("Lucía", 3),
        };
        Animal[] animales = {leon("Nala", 2), jirafa("Melman"), oveja("Dolly")};
        double[] pesosEsperados = {PESO_RACION_LEON, PESO_RACION_JIRAFA, PESO_RACION_OVEJA};

        String[] primerPaso = new String[3];
        for (int i = 0; i < cuidadores.length; i++) {
            ActaDeAlimentacion acta = cuidadores[i].alimentar(animales[i]);   // misma línea, x3
            primerPaso[i] = acta.pasos()[0];
            assertEquals(pesosEsperados[i], acta.pesoGanado(), PRECISION);
        }

        assertNotEquals(primerPaso[0], primerPaso[1]);
        assertNotEquals(primerPaso[1], primerPaso[2]);
        assertNotEquals(primerPaso[0], primerPaso[2]);
    }

    @Test
    @DisplayName("Herencia: el león hereda esCarnivoro() de Felino sin escribirlo")
    void elLeonHeredaDeFelino() {
        Animal simba = leon("Simba", 2);
        Animal dolly = oveja("Dolly");

        assertTrue(simba.esCarnivoro());
        assertFalse(dolly.esCarnivoro());
        assertTrue(simba instanceof Felino);
        assertTrue(simba instanceof Animal);
        assertEquals("León", simba.especie());
    }

    // ------------------------------------------------------------ R9: jaula

    @Test
    @DisplayName("R9: una jaula con capacidad rechaza más animales de los que entran")
    void laJaulaRespetaSuCapacidad() {
        Jaula jaula = new Jaula(1, 1);

        assertThrows(IllegalArgumentException.class,
                () -> jaula.alojar(oveja("Dolly"), oveja("Shaun")));
        assertTrue(jaula.estaVacia());
    }

    @Test
    @DisplayName("R9: una jaula sin límite (capacidad null) acepta cualquier cantidad")
    void laJaulaSinLimiteAceptaTodo() {
        Jaula corral = new Jaula(1, null);

        corral.alojar(oveja("A"), oveja("B"), oveja("C"), oveja("D"), oveja("E"));

        assertEquals(5, corral.getCantidadDeAnimales());
    }

    @Test
    @DisplayName("alojar(...) reemplaza a los animales anteriores, no los agrega")
    void alojarReemplazaALosAnteriores() {
        Jaula corral = new Jaula(1, 5);
        Oveja dolly = oveja("Dolly");
        Oveja shaun = oveja("Shaun");

        corral.alojar(dolly);
        corral.alojar(shaun);

        assertEquals(1, corral.getCantidadDeAnimales());
        assertSame(shaun, corral.getAnimales()[0], "quedó sólo la última llamada");
    }

    @Test
    @DisplayName("getAnimales() devuelve una copia: modificarla no afecta a la jaula")
    void losAnimalesQueSeDevuelvenSonUnaCopia() {
        Jaula corral = new Jaula(1, 5);
        corral.alojar(oveja("Dolly"), oveja("Shaun"));

        Animal[] copia = corral.getAnimales();
        copia[0] = null;

        assertEquals("Dolly", corral.getAnimales()[0].getNombre());
    }

    // ------------------------------------------------------------ R8, R10: la ronda

    @Test
    @DisplayName("R8: las jaulas vacías no generan actas")
    void lasJaulasVaciasSeSaltean() {
        Zoologico zoo = new Zoologico("Zoo", 3);   // nace con 3 jaulas vacías

        ActaDeAlimentacion[] actas = new AdministradorDeZoologico(zoo).hacerRondaDeAlimentacion();

        assertEquals(0, actas.length);
    }

    @Test
    @DisplayName("R10: una jaula con animales y sin cuidador es un error de configuración")
    void laJaulaSinCuidadorEsUnError() {
        Zoologico zoo = new Zoologico("Zoo", 1);
        zoo.getJaula(1).alojar(oveja("Dolly"));       // animales, pero nadie a cargo
        AdministradorDeZoologico administrador = new AdministradorDeZoologico(zoo);

        assertThrows(IllegalStateException.class, administrador::hacerRondaDeAlimentacion);
    }

    @Test
    @DisplayName("La ronda recorre todas las jaulas y devuelve un acta por animal, en orden")
    void laRondaDevuelveUnActaPorAnimal() {
        Zoologico zoo = new Zoologico("Zoo", 3);
        Jaula leones = zoo.getJaula(1);
        leones.asignarCuidador(new CuidadorDeLeon("Marta", 1));
        leones.alojar(leon("Simba", 7), leon("Nala", 2));
        Jaula corral = zoo.getJaula(3);                // la 2 queda vacía
        corral.asignarCuidador(new CuidadorDeOveja("Lucía", 3));
        corral.alojar(oveja("Dolly"));

        ActaDeAlimentacion[] actas = new AdministradorDeZoologico(zoo).hacerRondaDeAlimentacion();

        assertEquals(3, actas.length);
        assertArrayEquals(new String[]{"Simba", "Nala", "Dolly"},
                new String[]{actas[0].animal(), actas[1].animal(), actas[2].animal()});
        assertEquals("Marta", actas[0].cuidador());
        assertEquals("Lucía", actas[2].cuidador());
        assertTrue(actas[0].seAlimento() && actas[1].seAlimento() && actas[2].seAlimento());
    }

    @Test
    @DisplayName("Dos rondas el mismo día: la segunda saltea a quien ya cumplió su cuota")
    void laSegundaRondaSalteaAQuienYaComio() {
        Zoologico zoo = new Zoologico("Zoo", 2);
        zoo.getJaula(1).asignarCuidador(new CuidadorDeLeon("Marta", 1));
        zoo.getJaula(1).alojar(leon("Simba", 2));
        zoo.getJaula(2).asignarCuidador(new CuidadorDeOveja("Lucía", 3));
        zoo.getJaula(2).alojar(oveja("Dolly"));
        AdministradorDeZoologico administrador = new AdministradorDeZoologico(zoo);

        administrador.hacerRondaDeAlimentacion();
        ActaDeAlimentacion[] segunda = administrador.hacerRondaDeAlimentacion();

        assertEquals(ResultadoDeAlimentacion.SALTEADO_POR_FRECUENCIA, segunda[0].resultado(),
                "el león ya comió su única vez");
        assertEquals(ResultadoDeAlimentacion.ALIMENTADO, segunda[1].resultado(),
                "a la oveja todavía le quedan comidas");
    }

    @Test
    @DisplayName("animalesConPesoBajo devuelve sólo los que están por debajo del umbral")
    void losAnimalesConPesoBajo() {
        Zoologico zoo = new Zoologico("Zoo", 1);
        Jaula corral = zoo.getJaula(1);
        corral.asignarCuidador(new CuidadorDeOveja("Lucía", 3));
        Oveja gorda = new Oveja("Gorda", 64.0, 65.0);   // 0.985: bien
        Oveja flaca = new Oveja("Flaca", 50.0, 65.0);   // 0.769: baja
        corral.alojar(gorda, flaca);

        Animal[] bajos = new AdministradorDeZoologico(zoo).animalesConPesoBajo();

        assertEquals(1, bajos.length);
        assertSame(flaca, bajos[0]);
    }

    // ------------------------------------------------------------ zoológico

    @Test
    @DisplayName("El zoológico nace con todas sus jaulas, numeradas desde 1")
    void elZoologicoNaceConSusJaulas() {
        Zoologico zoo = new Zoologico("Zoo", 3, 4);

        assertEquals(3, zoo.getCantidadDeJaulas());
        assertEquals(0, zoo.getCantidadDeAnimales());
        assertEquals(1, zoo.getJaula(1).getNumero());
        assertEquals(3, zoo.getJaula(3).getNumero());
        assertTrue(zoo.tieneLimiteDeAnimalesPorJaula());
        assertThrows(IndexOutOfBoundsException.class, () -> zoo.getJaula(0));
        assertThrows(IndexOutOfBoundsException.class, () -> zoo.getJaula(4));
    }

    // ------------------------------------------------------------ invariantes

    @Test
    @DisplayName("Las invariantes se validan en el constructor")
    void lasInvariantesSeValidanAlConstruir() {
        assertThrows(IllegalArgumentException.class, () -> new Leon("", 180, 190, 2));
        assertThrows(IllegalArgumentException.class, () -> new Leon("Simba", 180, 190, 15));
        assertThrows(IllegalArgumentException.class, () -> new Jirafa("Melman", 800, 850, 50));
        assertThrows(IllegalArgumentException.class, () -> new Jaula(0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Jaula(1, 0));
        assertThrows(IllegalArgumentException.class, () -> new CuidadorDeOveja("Lucía", 0));
        assertThrows(IllegalArgumentException.class, () -> new CuidadorDeOveja("", 3));
        assertThrows(IllegalArgumentException.class,
                () -> new Racion(LocalDateTime.now(), new String[0], 1.0));
        assertThrows(IllegalArgumentException.class,
                () -> new Racion(LocalDateTime.now(), new String[]{"x"}, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Zoologico("", 3));
        assertThrows(IllegalArgumentException.class, () -> new Zoologico("Zoo", 0));
        assertThrows(IllegalArgumentException.class, () -> new Zoologico("Zoo", 3, 0));
        assertThrows(IllegalArgumentException.class, () -> new AdministradorDeZoologico(null));
    }
}
