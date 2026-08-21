package ar.uba.fi.cb100.material.i01_intro;

/**
 * Un {@code record} con VARIOS atributos. Por cada componente, Java genera un
 * <b>accesor</b> con el mismo nombre: {@code nombre()}, {@code padron()},
 * {@code promedio()}. No hay setters: el record es inmutable — para "cambiar"
 * un dato se crea otro record.
 */
public record FichaDeAlumno(String nombre, int padron, double promedio) {

    // Constructor compacto: valida TODOS los componentes al crear.
    public FichaDeAlumno {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("el nombre no puede estar vacío");
        }
        if (padron <= 0) {
            throw new IllegalArgumentException("el padrón debe ser positivo: " + padron);
        }
        if (promedio < 0 || promedio > 10) {
            throw new IllegalArgumentException("el promedio va de 0 a 10: " + promedio);
        }
    }

    /** Los métodos pueden combinar los atributos como quieran. */
    public boolean promociona() {
        return promedio >= 7.0;
    }

    /** "Modificar" un record = crear otro con el dato nuevo. */
    public FichaDeAlumno conPromedio(double nuevoPromedio) {
        return new FichaDeAlumno(nombre, padron, nuevoPromedio);
    }

    public static void main(String[] args) {
        FichaDeAlumno ana = new FichaDeAlumno("Ana Gómez", 111234, 8.5);
        FichaDeAlumno beto = new FichaDeAlumno("Beto Díaz", 109876, 6.1);

        // Leer cada atributo con su ACCESOR (mismo nombre que el componente):
        System.out.println(ana.nombre());          // Ana Gómez
        System.out.println(ana.padron());          // 111234
        System.out.println(ana.promedio());        // 8.5

        // Los accesores se usan como cualquier expresión:
        System.out.println(ana.nombre() + " (" + ana.padron() + ")");   // Ana Gómez (111234)
        double diferencia = ana.promedio() - beto.promedio();
        System.out.println(diferencia);            // 2.4000000000000004 (¡el punto flotante!)

        // toString, equals y hashCode vienen gratis:
        System.out.println(beto);                  // FichaDeAlumno[nombre=Beto Díaz, padron=109876, promedio=6.1]
        FichaDeAlumno copia = new FichaDeAlumno("Ana Gómez", 111234, 8.5);
        System.out.println(ana.equals(copia));     // true: igualdad por CONTENIDO

        // Inmutable: no existe ana.setPromedio(9.0). Se crea OTRO record:
        FichaDeAlumno anaMejorada = ana.conPromedio(9.0);
        System.out.println(anaMejorada.promedio()); // 9.0
        System.out.println(ana.promedio());         // 8.5: la original no cambió

        System.out.println(ana.promociona());       // true
        System.out.println(beto.promociona());      // false
    }
}
