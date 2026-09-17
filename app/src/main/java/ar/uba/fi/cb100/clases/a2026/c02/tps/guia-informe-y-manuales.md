# Guía: el informe, el manual de usuario y el manual del programador

**CB100 — Algoritmos y Estructuras de Datos**

Todo trabajo práctico se entrega con un único PDF que contiene tres
documentos, en este orden: el **informe**, el **manual de usuario** y el
**manual del programador**. Son tres documentos distintos porque tienen
**tres lectores distintos**, y cada lector hace una pregunta distinta.

| Documento | Lo escribe | Lo lee | La pregunta que responde | Largo orientativo |
|---|---|---|---|---|
| **Informe** | el alumno | el profesor | *¿Qué hicieron, por qué lo hicieron así, y qué aprendieron?* | 4 a 8 páginas |
| **Manual de usuario** | el programador | alguien que **usa** el programa y no sabe programar | *¿Cómo lo uso?* | 2 a 4 páginas, con pantallas |
| **Manual del programador** | el programador | otro programador que **tiene que modificarlo** | *¿Cómo está hecho y cómo lo cambio?* | 3 a 6 páginas |

La prueba de fuego de cada uno es simple: **el lector correcto tiene que
poder leerlo solo, sin los otros dos, y sin preguntarle nada al autor.**

---

## 1. El informe: del alumno al profesor

El profesor ya conoce el enunciado y ya va a leer el código. Lo que **no**
puede saber leyendo el código es qué pensaron ustedes: por qué eligieron
una estructura y no otra, qué alternativas descartaron, dónde se
equivocaron y cómo lo arreglaron. **Eso** es el informe. Un informe que
describe lo que hace cada clase no aporta nada, porque el código ya lo dice.

### Secciones

1. **Carátula.** Materia, número de TP, integrantes con padrón, fecha, y el
   link al repositorio.
2. **Objetivo.** Dos o tres líneas, con sus palabras, de qué había que hacer.
   No copien el enunciado.
3. **Diseño.** La sección que más pesa. Un **diagrama de clases** con los
   paquetes y las jerarquías, y después, para cada decisión importante:
   qué eligieron, **por qué**, y qué alternativa descartaron. Ejemplos de
   decisiones que valen la pena: por qué el ranking es un ABB y no una lista;
   por qué `Cofre` es abstracta; cómo hicieron para que la vista no toque el
   modelo; por qué el generador usa una pila propia.
4. **Estructuras de datos.** Una tabla: estructura, dónde se usa, por qué esa
   y no otra, y qué costo tiene la operación que más se ejecuta.
5. **Pruebas.** Qué probaron, cuántos tests hay, cómo se corren, y una
   captura de `gradlew test` en verde. Si un test falla y saben por qué,
   díganlo: vale más que esconderlo.
6. **Problemas encontrados.** Los bugs que más les costaron, qué los causaba
   y cómo los resolvieron. Las decisiones que cambiaron a mitad de camino y
   por qué. Esta sección es la que más muestra que aprendieron.
7. **Respuestas al cuestionario** del enunciado, numeradas igual.
8. **Limitaciones.** Lo que quedó afuera o funciona a medias. Decirlo
   descuenta menos que ser descubierto.
9. **Sección por integrante** (en los trabajos grupales). Qué hizo cada uno,
   con referencia a sus commits. Cada integrante escribe la suya.
10. **Conclusiones.** Qué aprendieron y qué harían distinto. Concreto: "no
    volveríamos a poner la lógica de puntaje en la vista" sirve; "aprendimos
    mucho" no.

### Lo que no va

- Código pegado. Como máximo, cinco o diez líneas cuando hacen falta para
  explicar una decisión. Las clases completas están en el repositorio.
- El enunciado copiado.
- Descripción clase por clase de qué hace cada método. Eso es el javadoc.
- Relleno para llegar a un largo. No hay largo mínimo; hay contenido mínimo.

### Errores típicos

- Contar **qué** hace el código en vez de **por qué** está hecho así.
- "Funciona todo" sin una sola evidencia.
- Diagrama de clases sacado automáticamente con 80 cajas ilegibles. Un
  diagrama a mano con las 10 clases que importan vale más.
- Escribirlo la última noche. El informe se escribe **durante** el TP: cada
  hito agrega una sección.

---

## 2. El manual de usuario: del programador a quien usa el programa

El lector **no sabe programar** y no le interesa. Quiere jugar, cargar un
archivo, ver el ranking. No existen para él las clases, los paquetes ni
Java: existen **pantallas, teclas, menús, archivos y mensajes**.

### Secciones

1. **Qué es el programa.** Un párrafo: qué hace y para qué sirve.
2. **Requisitos e instalación.** Qué necesita tener instalado (Java 25) y
   cómo se ejecuta, paso a paso, con el comando exacto o el archivo al que
   hay que hacer doble click. Si hay que abrir una terminal, decir cómo.
3. **Las pantallas.** Por cada pantalla, menú o estado del programa: una
   **captura numerada**, qué se ve en ella, qué puede hacer el usuario y qué
   pasa con cada opción o tecla. En un programa de consola, la "pantalla" es
   la terminal y los archivos que genera. En el TP 2: el menú de dificultad,
   el tablero, los mensajes, el guardado, el ranking, la carpeta con los BMP.
4. **Tareas frecuentes, paso a paso.** "Cómo guardar una partida", "cómo
   retomarla", "cómo ver el ranking", "cómo cambiar la dificultad". Cada una,
   una lista numerada de pasos que se puede seguir con el dedo.
5. **Mensajes y errores.** Cada mensaje que el usuario puede llegar a ver,
   qué significa y qué tiene que hacer. "No se encontró configuracion.json":
   qué es ese archivo, dónde tiene que estar.
6. **Archivos que usa el programa.** Cuáles son, dónde están y qué contienen,
   en lenguaje de usuario: "el ranking se guarda en `ranking.json`, al lado
   del programa; si lo borrás, el ranking empieza de cero".

### Las capturas

- Numeradas y con epígrafe: "Figura 3. El menú de dificultad."
- Recortadas al contenido y legibles. Si hay que hacer zoom para leer el
  texto, no sirve.
- De consola: captura de la terminal, no foto del monitor con el celular.
- Una por cada estado distinto del programa. No hace falta una por cada
  paso de una partida.

### La prueba

Dénselo a alguien de otra carrera. Si tiene que abrir el código, o
preguntarles algo, o adivinar, el manual está incompleto ahí.

---

## 3. El manual del programador: de un programador a otro

El lector es un programador que **entra al proyecto mañana** y tiene que
agregar un tipo de cofre nuevo sin preguntarle a nadie. Sabe Java. No sabe
nada del proyecto. El manual le tiene que dar el mapa y las recetas.

### Secciones

1. **Cómo armar el entorno.** JDK, IDE, clonar, compilar, correr los tests,
   correr el programa. Comandos exactos. Si algo suele fallar (el encoding,
   la ruta relativa, el working directory de `gradlew run`), decirlo acá.
2. **Estructura del proyecto.** El árbol de paquetes y qué hay en cada uno.
   Qué es modelo, qué es vista, qué es persistencia, qué son las librerías
   de la cátedra.
3. **Arquitectura.** El diagrama de clases (puede ser el mismo del informe)
   y **el recorrido de una acción de punta a punta**: el usuario aprieta
   `w`, ¿qué clases se llaman, en qué orden, y quién decide qué? Un diagrama
   de secuencia a mano, o una lista numerada, alcanza.
4. **Las interfaces y sus contratos.** `Vista`, `GeneradorDeLaberinto`,
   `Monstruo`, `Cofre`: qué tiene que cumplir quien implemente cada una, y
   qué puede suponer. Es lo que le permite a otro extender el programa sin
   romperlo.
5. **Recetas: "cómo hago para..."** Agregar un tipo de monstruo. Agregar un
   cofre. Cambiar el costo del agua. Agregar una dificultad. Cada receta:
   qué archivos tocar, en qué orden, y qué test agregar. Es la sección más
   útil del documento.
6. **Formatos de archivo.** Cada JSON y cada archivo que el programa lee o
   escribe: campos, tipos, qué significa cada uno, un ejemplo.
7. **Decisiones y trampas.** Lo que no es obvio leyendo el código: las
   posiciones van de 1 a n; el modelo nunca imprime; por qué no se usa
   `java.util`; por qué el generador usa una pila propia; el `-1` de
   `split`. Cada trampa que les hizo perder una tarde, acá.
8. **Cómo probar.** Convenciones de los tests, cómo correr uno solo, qué
   tiene que tener un test nuevo para que lo acepten.
9. **Pendientes y bugs conocidos.** Con la mayor precisión posible.

### Lo que no va

- Repetir el javadoc clase por clase. Está en el código, donde corresponde.
- Explicar Java. El lector lo sabe.
- El manual de usuario disfrazado. Al programador no le interesa qué tecla
  aprieta el usuario; le interesa qué clase la recibe.

### La prueba

Alguien que no escribió el código, siguiendo sólo el manual, agrega un cofre
nuevo con su test en **30 minutos**. Si no puede, falta una receta o una
trampa.

---

## 4. Lo que vale para los tres

- **Un solo PDF**, con índice y páginas numeradas, con el nombre de archivo
  que pide el enunciado. Los tres documentos separados por un título de
  primer nivel cada uno.
- **Cada figura, tabla y captura tiene número y epígrafe**, y se la menciona
  en el texto. Una figura que nadie menciona, sobra.
- **Cada lector, su vocabulario.** Si en el manual de usuario aparece la
  palabra "clase" o en el informe aparece "apretá la tecla w", algo se
  mezcló.
- **Se escribe durante el trabajo.** El informe crece con cada hito; las
  recetas del manual del programador se escriben cuando agregan la primera
  pieza de cada tipo, que es cuando todavía se acuerdan de qué les costó.
- **En los grupos, se reparte.** Cada integrante escribe la sección del
  manual del programador de su área y su sección del informe. Uno integra,
  revisa que los tres suenen a un solo documento, y arma el PDF.
- **Corregir antes de entregar.** Un documento con errores de ortografía o
  con "TODO" adentro dice que nadie lo leyó antes de mandarlo.

## 5. Cómo se evalúa la documentación

Se lee con la pregunta de cada lector en la mano. Del informe: ¿me
convence de que entendieron lo que hicieron y por qué? Del manual de
usuario: ¿podría usar el programa alguien que nunca lo vio? Del manual del
programador: ¿podría extenderlo alguien que nunca vio el código? Un
documento largo que no responde su pregunta vale menos que uno corto que
sí.
