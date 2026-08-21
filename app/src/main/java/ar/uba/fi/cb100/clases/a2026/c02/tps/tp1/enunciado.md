# TP 1 — Registro de préstamos de la biblioteca

**CB100 — Algoritmos y Estructuras de Datos · 2026, 2º cuatrimestre**

Unidades involucradas: `i01_intro` (sintaxis, archivos), `i02_memoria`
(referencias, arreglos) e `i03_poo` (TDA, encapsulamiento, invariantes).

---

## 1. Qué se evalúa

Este TP **no** busca que resuelvan un algoritmo difícil. Busca que escriban un
programa completo y prolijo con lo visto hasta ahora:

| Tema | Dónde aparece en el TP |
|---|---|
| Sintaxis básica, `String`, ciclos, arreglos | parseo de cada línea |
| Archivos (`java.nio.file.Files`) | lectura de la entrada, escritura de reportes |
| TDA: contrato separado de la implementación | `RegistroDePrestamos` (interfaz) + implementación sobre arreglo |
| Invariantes y validación en el constructor | `Prestamo` |
| Excepciones propias | `LineaInvalidaException` |
| Tests con JUnit 5 | Parte E, obligatoria |

---

## 2. El problema

La biblioteca de la facultad les pasa un archivo de texto con los préstamos del
cuatrimestre. Hay que leerlo, validarlo y producir **el reporte de multas por
socio** y **el ranking de títulos más pedidos**.

### Reglas del negocio

1. El plazo de préstamo es de **14 días corridos** desde el retiro.
2. Un préstamo **sin fecha de devolución** todavía está en poder del socio.
3. Los **días de atraso** se cuentan desde el vencimiento (`retiro + 14 días`)
   hasta la fecha de devolución; si el préstamo está pendiente, hasta la
   **fecha de corte** que recibe el programa como parámetro.
   Si no hay atraso, son **0 días** (nunca negativo).
4. La **multa** es de **$150 por día de atraso**, con un **tope de $3000 por
   préstamo**.
5. Un socio está `CON_DEUDA` si su multa acumulada es mayor a 0; si no,
   `AL_DIA`.

---

## 3. Formato de entrada

Archivo de texto, un préstamo por línea, campos separados por **punto y coma**:

```
fechaRetiro;padron;socio;isbn;titulo;fechaDevolucion
```

- Las fechas van en formato **ISO** (`AAAA-MM-DD`), que es lo que
  `LocalDate.parse` entiende sin configurar nada.
- El campo `fechaDevolucion` **puede venir vacío** (préstamo pendiente).
- Las líneas **en blanco** y las que empiezan con `#` se ignoran (no cuentan
  como error).
- El separador es `;` y no `,` **a propósito**: hay títulos que contienen comas
  (`Java, como programar`).

> **Sobre los acentos:** el archivo de ejemplo viene sin acentos a propósito,
> para que el TP no se trabe en problemas de codificación. El manejo serio de
> encodings lo vemos en `i12_profesional`.

### Archivo de ejemplo

Está en [`app/src/main/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp1/datos/prestamos.csv`](datos/prestamos.csv). Primeras líneas:

```
# Biblioteca FIUBA - registro de prestamos
# formato: fechaRetiro;padron;socio;isbn;titulo;fechaDevolucion
# la fecha de devolucion vacia significa "todavia no lo devolvio"

2026-03-02;41234;Ana Gomez;9789871234567;Estructuras de Datos;2026-03-16
2026-03-05;39876;Bruno Ferrari;9780262033848;Introduction to Algorithms;2026-03-30
2026-03-09;42001;Carla Nunez;9789871234567;Estructuras de Datos;2026-03-20
2026-03-11;40555;Diego Ruiz;9788478290499;El Lenguaje de Programacion C;
```

Al final trae **4 líneas deliberadamente inválidas**, una de cada tipo de error
que tienen que detectar:

| Línea | Problema |
|---|---|
| `2026-04-30;41234;Ana Gomez;9789871234567` | faltan campos |
| `2026-13-02;39876;Bruno Ferrari;9780132350884;Clean Code;` | mes 13 |
| `2026-04-18;CUARENTA;Carla Nunez;...` | padrón no numérico |
| `2026-04-15;43310;Elena Sosa;...;2026-04-01` | devolución anterior al retiro |

**El programa no debe abortar cuando encuentra una línea inválida**: la
descarta, guarda el motivo con el número de línea, y sigue con las demás. Al
final informa cuántas descartó y por qué.

---

## 4. Qué hay que construir

Todo en el paquete `ar.uba.fi.cb100.clases.a2026.c02.tps.tp1`
(el mismo donde esta este enunciado).

### Parte A — El TDA `Prestamo` (clase de valor)

```java
public record Prestamo(LocalDate retiro, int padron, String socio,
                       String isbn, String titulo, LocalDate devolucion) {

    // Valida en el constructor compacto. Un Prestamo mal formado NO debe existir.
    // - socio, isbn y titulo no pueden ser null ni vacios
    // - padron debe ser positivo
    // - devolucion puede ser null (pendiente), pero si no lo es,
    //   no puede ser anterior a retiro

    public boolean estaPendiente() { ... }
    public LocalDate vencimiento() { ... }           // retiro + 14 dias
    public int diasDeAtraso(LocalDate corte) { ... } // >= 0
    public int multa(LocalDate corte) { ... }        // 150/dia, tope 3000
}
```

**Por qué un `record`:** un préstamo es un *valor* (dos préstamos con los mismos
datos son el mismo préstamo). El `record` les da `equals`, `hashCode` y
`toString` gratis, y el constructor compacto es el lugar natural para la
invariante. Miren `Temperatura.java` y `Fraccion.java` del material.

> Ojo con `diasDeAtraso`: usen `ChronoUnit.DAYS.between(...)` y acoten con
> `Math.max(0, ...)`. Un préstamo devuelto en fecha da 0, no un número negativo.

### Parte B — El TDA `RegistroDePrestamos` (contrato + implementación)

Primero **el contrato**, sin decir cómo se guarda nada:

```java
public interface RegistroDePrestamos {
    void registrar(Prestamo p);
    int cantidad();
    Prestamo obtener(int i);              // IndexOutOfBoundsException si i es invalido
    int[] padrones();                     // sin repetidos, en orden de aparicion
    Prestamo[] prestamosDe(int padron);   // arreglo vacio si no hay ninguno
    String[] titulosMasPedidos(int n);    // los n mas pedidos, desempate alfabetico
}
```

Después **la implementación**, `RegistroSobreArreglo`, con un arreglo interno
que **ustedes hacen crecer a mano**:

- arrancan con capacidad 8;
- cuando se llena, crean uno del **doble** y copian (`Arrays.copyOf` está
  permitido);
- `cantidad()` es la cantidad de elementos **usados**, no la capacidad.

> Este es el corazón del TP. Es, en chiquito, el `VectorDinamico` que vamos a ver
> en `i05_lineales`. Si lo resuelven con `ArrayList`, el TP no cumple su objetivo.

### Parte C — Lectura del archivo

```java
public class LineaInvalidaException extends RuntimeException {
    public LineaInvalidaException(int numeroDeLinea, String motivo) { ... }
    public int numeroDeLinea() { ... }
}

public record ResultadoDeCarga(RegistroDePrestamos registro,
                               String[] errores,
                               int lineasDeDatos) { }

public class LectorDePrestamos {
    public static ResultadoDeCarga cargar(Path archivo) throws IOException { ... }
}
```

`cargar` lee con `Files.readAllLines`, ignora blancos y comentarios, arma un
`Prestamo` por línea válida, y por cada línea inválida agrega un texto a
`errores` con **el número de línea real del archivo** (contando comentarios y
blancos: si el error está en la línea 28 del archivo, dice 28).

### Parte D — Reportes y exportación

```java
public record FilaDeSocio(int padron, String socio, int prestamos,
                          int diasDeAtraso, int multa, String estado) { }

public class Reporteador {
    public static FilaDeSocio[] porSocio(RegistroDePrestamos r, LocalDate corte) { ... }
    public static String[] ranking(RegistroDePrestamos r, int n) { ... }
}
```

Las filas van ordenadas por **multa descendente**, y a igual multa, por **nombre
de socio alfabético**.

Y acá vuelve a aparecer la idea de TDA, ahora del lado de la salida:

```java
public interface ExportadorDeReporte {
    void exportar(FilaDeSocio[] filas, Path destino) throws IOException;
    String extension();   // "txt", "csv", ...
}
```

Implementaciones obligatorias: **`ExportadorTxt`** y **`ExportadorCsv`**. El
`main` no debe saber cuál está usando: recibe un `ExportadorDeReporte` y listo.
(Cambiar el formato de salida sin tocar el resto del programa es exactamente
para lo que sirve separar contrato de implementación.)

### Parte E — El programa

```java
public class Tp1 {

    // Valores por defecto, para que `./gradlew run` sin argumentos
    // reproduzca exactamente la salida de la seccion 5.
    private static final String ENTRADA_POR_DEFECTO =
            "app/src/main/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp1/datos/prestamos.csv";
    private static final LocalDate CORTE_POR_DEFECTO = LocalDate.parse("2026-05-04");

    public static void main(String[] args) throws IOException {
        // args[0] = archivo de entrada   (opcional)
        // args[1] = fecha de corte ISO   (opcional)
    }
}
```

Se ejecuta así:

```bash
./gradlew run
```

o, pasándole otro archivo y otra fecha de corte:

```bash
./gradlew run --args="otra/ruta/prestamos.csv 2026-06-01"
```

> **Cuidado con el directorio de trabajo:** en un proyecto Gradle multi-módulo,
> `run` ejecuta parado en `app/`, no en la raíz, así que una ruta relativa a la
> raíz no se encuentra sola. Agreguen esto **una sola vez** a `app/build.gradle`:
>
> ```groovy
> run { workingDir = rootProject.projectDir }
> ```
>
> Si igual les tira `NoSuchFileException`, impriman
> `Path.of("").toAbsolutePath()` para ver desde dónde esta corriendo el programa.

> **Por que el archivo de datos vive adentro de `src/main/java`:** es la misma
> convencion que ya usamos con los teoricos (`guia/.../tNN.md`). Gradle ignora
> todo lo que no sea `.java` al compilar, asi que no molesta. Eso si: **no esta
> en el classpath**, o sea que se lee con `Files`/`Path` como cualquier archivo
> del disco, no con `getResourceAsStream`.

---

## 5. Salida esperada

Con el archivo de ejemplo y **fecha de corte `2026-05-04`**, estos son los
valores exactos. Si les da distinto, algo está mal.

### 5.1 Resumen de carga (por consola)

```
Lineas de datos: 22 | validas: 18 | descartadas: 4
  linea 28: se esperaban 6 campos y llegaron 4
  linea 29: fecha invalida: 2026-13-02
  linea 30: padron no numerico: CUARENTA
  linea 31: la devolucion (2026-04-01) es anterior al retiro (2026-04-15)
```

El texto del motivo puede variar; el **número de línea y la cantidad, no**.

### 5.2 `reporte.txt`

```
BIBLIOTECA FIUBA - REPORTE DE MULTAS
Fecha de corte: 2026-05-04

Padron   Socio              Prestamos  DiasAtraso     Multa  Estado
--------------------------------------------------------------------
39876    Bruno Ferrari              4          24      3600  CON_DEUDA
41234    Ana Gomez                  4          23      3450  CON_DEUDA
40555    Diego Ruiz                 3          42      3300  CON_DEUDA
43310    Elena Sosa                 3          10      1500  CON_DEUDA
42001    Carla Nunez                4           0         0  AL_DIA
--------------------------------------------------------------------
TOTALES                            18          99     11850

TITULOS MAS PEDIDOS
  1. Estructuras de Datos             6
  2. Clean Code                       3
  3. Introduction to Algorithms       3
```

Fíjense en el desempate: `Clean Code` e `Introduction to Algorithms` tienen 3
préstamos cada uno, y va primero el alfabéticamente menor.

Para el formato de columnas usen `String.format` con anchos fijos (`%-8s`,
`%9d`), como en `BoletinDeNotas.java` del material.

### 5.3 `reporte.csv`

```
padron;socio;prestamos;dias_atraso;multa;estado
39876;Bruno Ferrari;4;24;3600;CON_DEUDA
41234;Ana Gomez;4;23;3450;CON_DEUDA
40555;Diego Ruiz;3;42;3300;CON_DEUDA
43310;Elena Sosa;3;10;1500;CON_DEUDA
42001;Carla Nunez;4;0;0;AL_DIA
```

Este archivo lo abre Excel con doble click. Usamos `;` y no `,` porque en la
configuración regional de Argentina, Excel espera `;` como separador de campos
(la coma es el separador decimal).

---

## 6. Restricciones

Para que el TP tenga sentido, **está prohibido** usar:

- `ArrayList`, `HashMap`, `HashSet` ni ninguna colección de `java.util`. Todo
  con **arreglos**. (Sí pueden usar `Arrays.copyOf`, `Arrays.sort` y
  `Arrays.fill`.)
- La API de Streams (`.stream()`, `.map()`, `.collect()`).
- Librerías externas, salvo el bonus de la sección 8.

Sí pueden y deben usar: `String` y sus métodos, `StringBuilder`, `LocalDate`,
`ChronoUnit`, `Files`, `Path`, `Math`, `String.format`, `record`, `switch`.

---

## 7. Tests (obligatorio)

En `app/src/test/java/ar/uba/fi/cb100/clases/a2026/c02/tps/tp1/` van **como mínimo 12 tests** que
cubran:

- `Prestamo`: cada validación del constructor rechaza (usen `assertThrows`),
  cálculo de vencimiento, atraso 0 cuando se devolvió en fecha, atraso de un
  préstamo pendiente, tope de multa en 3000.
- `RegistroSobreArreglo`: que crezca más allá de la capacidad inicial (registren
  20 préstamos y verifiquen `cantidad() == 20`), `padrones()` sin repetidos,
  `prestamosDe` con un padrón inexistente devuelve arreglo vacío, el desempate
  alfabético de `titulosMasPedidos`.
- `LectorDePrestamos`: sobre el archivo de ejemplo, `18` válidas y `4` errores.
- `ExportadorCsv`: escribe a un archivo temporal (`Files.createTempFile`) y lo
  vuelven a leer con `Files.readAllLines` para comparar contra el contenido
  esperado.

Se corren con:

```bash
./gradlew test
```

---

## 8. Bonus opcional (+1 punto): exportar a Excel de verdad

Implementar `ExportadorXlsx implements ExportadorDeReporte` usando **Apache
POI**, que genera un `.xlsx` nativo (con encabezado en negrita y columnas
autoajustadas).

Es opcional **a propósito**: agrega ~19 MB y 16 jars al proyecto, y el valor
educativo es "sé usar una librería", no algoritmos. Si lo hacen, que sea
*además* del CSV, nunca en lugar de.

En `app/build.gradle`:

```groovy
dependencies {
    // ... lo que ya estaba ...
    implementation 'org.apache.poi:poi-ooxml:5.5.1'

    // Sin estas dos lineas, POI imprime al arrancar:
    // "ERROR Log4j API could not find a logging provider"
    runtimeOnly 'org.apache.logging.log4j:log4j-to-slf4j:2.24.3'
    runtimeOnly 'org.slf4j:slf4j-nop:2.0.16'
}
```

Y el esqueleto:

```java
try (Workbook libro = new XSSFWorkbook()) {
    Sheet hoja = libro.createSheet("Multas");

    CellStyle negrita = libro.createCellStyle();
    Font f = libro.createFont();
    f.setBold(true);
    negrita.setFont(f);

    String[] encabezados = {"Padron", "Socio", "Prestamos", "DiasAtraso", "Multa", "Estado"};
    Row cab = hoja.createRow(0);
    for (int c = 0; c < encabezados.length; c++) {
        Cell celda = cab.createCell(c);
        celda.setCellValue(encabezados[c]);
        celda.setCellStyle(negrita);
    }

    // ... una fila por cada FilaDeSocio, con hoja.createRow(i) ...

    for (int c = 0; c < encabezados.length; c++) hoja.autoSizeColumn(c);

    try (FileOutputStream out = new FileOutputStream(destino.toFile())) {
        libro.write(out);
    }
}
```

---

## 9. Entrega

- Rama `tp1` en su repositorio, con **commits incrementales** (no un único
  commit "TP1"). Queremos ver el proceso.
- El `README.md` del repo con una sección "TP 1" que explique cómo correrlo.
- `./gradlew build` tiene que pasar **en verde**. Un TP que no compila no se
  corrige.

### Rúbrica

| Criterio | Puntos |
|---|---|
| `Prestamo` con invariantes validadas en el constructor | 15 |
| Interfaz `RegistroDePrestamos` bien separada de la implementación | 15 |
| Crecimiento manual del arreglo, correcto | 15 |
| Lectura tolerante a errores, con número de línea | 15 |
| Reportes con los valores exactos de la sección 5 | 15 |
| Los dos exportadores detrás del TDA `ExportadorDeReporte` | 10 |
| Tests (mínimo 12, que pasen y que prueben algo real) | 15 |
| **Bonus: exportar a Excel (.xlsx)** | +10 (equivale a **1 punto** en la nota final) |

Descuentan: usar colecciones o streams (−20), métodos de más de 30 líneas
(−5 c/u), atributos públicos sin encapsular (−10).

---

## 10. Ayudas

- **Nunca escriban una fecha "a mano"**: `LocalDate.parse("2026-03-02")`. Si el
  formato es inválido tira `DateTimeParseException`, que es justo la que tienen
  que atrapar y convertir en `LineaInvalidaException`.
- `linea.split(";", -1)` — **el `-1` importa**. Sin él, Java descarta los campos
  vacíos del final, y todas las líneas con devolución pendiente les van a llegar
  con 5 campos en vez de 6.
- `strip()` en cada campo antes de parsear.
- Empiecen por la Parte A y sus tests. Con `Prestamo` andando y probado, el
  resto es acomodar datos.
