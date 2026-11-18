# Ejercicio integrador — solución

Sistema que usa un Excel como base de datos de personas y permite indexar,
filtrar, ver fichas, recorrer el grafo familiar, graficar los costos de los
índices y abrir las ubicaciones en Google Maps. El enunciado está en
[enunciado.md](enunciado.md).

## Cómo ejecutarlo en clase

Los tres programas están en este paquete y se ejecutan desde IntelliJ con el
triángulo verde:

| Programa | Qué hace |
|---|---|
| `PrincipalIntegrador` | El sistema con pantalla. Tarda unos 10 segundos en abrir: lee las 50.000 filas y arma y mide los nueve índices. |
| `PrincipalDeConsola` | El mismo recorrido por los ocho puntos, impreso en la consola. Útil para mostrar los números sin la pantalla. |
| `PrincipalGenerarArchivo` | Punto 6: regenera `archivos/personas.xlsx`. Sin argumentos, 50.000 personas con semilla 2026. Acepta cantidad y semilla: `1000 7`. Tarda unos 20 segundos. |

El archivo `archivos/personas.xlsx` ya está generado, con 50.000 filas. Si se
borra, los otros dos programas lo vuelven a crear solos.

## Dónde está cada punto del enunciado

| Punto | Dónde | Estructura o técnica del apunte |
|---|---|---|
| 1. Índices por vector, lista y árbol, con memoria y tiempo | `indices/` y `funcional/CatalogoDeIndices` | `ArrayList` ordenado + búsqueda binaria (U4, U5); `LinkedList` con iterador (U5); `TreeMap` con consulta por rango (U8) |
| 2. Grilla con DNI, nombre y apellido | `vista/VentanaPrincipal`, `vista/ModeloDeGrilla` | separación entre datos y vista (U13) |
| 3. Filtro con índices, y por provincia y país por fuerza bruta | `CatalogoDeIndices.buscar`, `funcional/FiltroPorFuerzaBruta` | streams: `filter`, `sorted`, `toList`, `groupingBy` (U12) |
| 4. Ficha de una persona | `vista/FichaDePersona` | |
| 5. Grafo de padres e hijos, dibujado | `grafo/GrafoFamiliar`, `vista/PanelDeGrafo`, `vista/VentanaDeGrafo` | listas de adyacencia en `HashMap` (U7, U11) |
| 5.1 Profundidad y anchura | `GrafoFamiliar.recorrerEnProfundidad` / `recorrerEnAnchura` | pila y cola con `ArrayDeque` (U5, U11) |
| 5.2 Orden topológico | `GrafoFamiliar.ordenarTopologicamente` | algoritmo de Kahn con `PriorityQueue` (U9, U11) |
| 6. Archivo de ejemplo | `funcional/GeneradorDePersonas`, `PrincipalGenerarArchivo` | reproducible por semilla |
| 7. Gráfica | `vista/PanelDeGrafica` | barras de memoria, tiempo de armado y tiempo de búsqueda |
| 8. Google Maps | `funcional/EnlaceDeMapas` | |

## Organización

- `datos/`: lo que representa algo. `Persona` y `Geocodificacion` son records
  inmutables que validan en el constructor; `CampoDeBusqueda` es un enum que
  sabe extraer la clave de cada campo.
- `indices/`: una interfaz, `IndiceDePersonas`, y tres implementaciones. El
  enum `TipoDeIndice` es la fábrica: nadie de afuera nombra las clases concretas.
- `funcional/`: lo que hace cosas con los datos. Leer y escribir el Excel,
  generar el archivo, armar y medir los índices, filtrar, armar el enlace.
- `grafo/`: el grafo familiar y sus recorridos.
- `vista/`: Swing. No tiene lógica: cada botón le pide el trabajo a una clase
  funcional. Por eso el sistema corre igual desde la consola.

## Hipótesis (lo que el enunciado no dice)

- **H1.** Se agrega la columna **Provincia**. El punto 3 pide filtrar por
  provincia y la lista de columnas no la traía.
- **H2.** La geocodificación viaja en **una celda**, como `latitud,longitud`
  con punto decimal y seis decimales.
- **H3.** Los DNI de padre y madre **pueden estar vacíos** (no se conocen), y
  pueden nombrar a alguien que no está en el archivo: esa arista se ignora.
- **H4.** Los índices buscan por **comienzo** del campo y sin distinguir
  mayúsculas: "schm" encuentra "Schmidt". Es la búsqueda que las tres
  estructuras pueden resolver y donde se nota la diferencia. La fuerza bruta
  busca el texto en **cualquier parte**, que es lo que un índice ordenado no puede.
- **H5.** "Árbol binario" es el `TreeMap` de la API (rojo-negro), siguiendo la
  regla de usar la API primero.
- **H6.** La **memoria** de cada índice se estima como la diferencia de
  memoria usada antes y después de armarlo, pidiendo recolección de basura.
  Java no informa el tamaño de un objeto: es una aproximación válida para
  comparar las tres estructuras, no una medida exacta.
- **H7.** El **tiempo de búsqueda** de la gráfica es el promedio de 200
  búsquedas de claves completas tomadas al azar. El de la pantalla es el de
  la búsqueda que se acaba de hacer.
- **H8.** El grafo completo tiene 50.000 nodos y no se puede dibujar. Se
  dibuja la **familia de la persona elegida**: dos generaciones hacia arriba y
  dos hacia abajo, con un tope de 24 personas. Los recorridos en profundidad
  y en anchura salen de esa persona y bajan por los hijos; el topológico
  ordena la familia dibujada (el de todo el grafo lo muestra la consola).
- **H9.** No encontré en el repo "la gráfica de ejemplo que dimos en clase".
  La gráfica del punto 7 compara los nueve índices en memoria, tiempo de
  armado y tiempo de búsqueda.
- **H10.** Google Maps admite pocos puntos en una URL: con una persona se
  abre el punto, con 2 a 10 un recorrido que pasa por todos.
- **H11.** Una fila mal formada del Excel no aborta la carga: se descarta y
  se informa con su número de fila.

## Qué mirar en clase

- El índice por **lista** tarda unas 100 veces más que los otros dos: tiene el
  mismo orden, pero sin acceso directo no puede saltar.
- El **árbol** sobre nombre y apellido ocupa muy poca memoria porque hay pocas
  claves distintas (30 nombres, 80 apellidos): cada clave guarda la lista de
  personas que la tienen. Sobre DNI, donde cada clave es única, es el que más ocupa.
- La **fuerza bruta** sobre 50.000 filas tarda unos 10 ms: para un filtro
  ocasional alcanza. Un índice se justifica cuando la búsqueda se repite.
- Los datos son inventados. Con la misma semilla sale siempre el mismo archivo.

## Tests

`IntegradorTest` (18 tests): las tres estructuras devuelven lo mismo y en el
mismo orden, los recorridos del grafo sobre una familia chica conocida, el
orden topológico respeta la precedencia sobre 5.000 personas, un ciclo se
detecta, y lo que se escribe en el Excel se lee igual.
