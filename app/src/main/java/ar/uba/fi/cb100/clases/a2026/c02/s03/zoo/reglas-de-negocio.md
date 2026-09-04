# Zoológico — reglas de negocio y qué enseña cada clase

Ejemplo integrador de la semana 3: **TDA, POO, herencia, polimorfismo y
buenas prácticas** en un solo dominio. Se corre con `ZoologicoMain` y se
prueba con `ZooTest`.

## El dominio

Un zoológico tiene jaulas. Cada jaula tiene capacidad fija, aloja animales y
está a cargo de un cuidador. Los cuidadores están **especializados por
especie** y cada uno tiene su propia rutina de alimentación. El administrador
recorre las jaulas y hace que cada cuidador alimente a sus animales.

```
            Animal (abstracta)                    Cuidador (abstracta)
              |                                     |
      +-------+-------+-------+              +------+------+------+
      |               |       |              |             |      |
   Felino          Jirafa   Oveja     CuidadorDeLeon  CuidadorDe  CuidadorDe
  (abstracta)                                          Jirafa      Oveja
      |
    Leon

   Zoologico  1 --- *  Jaula  * --- *  Animal
                        |
                        1 Cuidador
```

## Las reglas

| # | Regla | Dónde vive |
|---|---|---|
| R1 | La energía de un animal está siempre entre 0 y 100. Nace con 60. | `Animal` — constantes y `registrarRacion` / `comenzarNuevoDia` |
| R2 | Un animal está **bien alimentado** si su energía es ≥ 50. | `Animal.estaBienAlimentado()` |
| R3 | Al comenzar cada día, todo animal pierde su **desgaste diario**: León 35, Jirafa 25, Oveja 18. Nunca baja de 0. | `Animal.comenzarNuevoDia()` + `desgasteDiario()` en cada especie |
| R4 | Cada especie come una cantidad fija de veces por día: León 1, Jirafa 2, Oveja 3. | `comidasPorDia()` en cada `Cuidador` |
| R5 | Si el animal ya recibió todas sus comidas del día, el cuidador lo **saltea**. No es un error: queda en el acta como `SALTEADO_POR_FRECUENCIA`. | `Cuidador.alimentar()` |
| R6 | Un cuidador **sólo atiende su especialidad**. Si le toca otro animal, no lo toca: queda como `FUERA_DE_ESPECIALIDAD`. | `Cuidador.alimentar()` + `puedeAtender()` en cada subclase |
| R7 | Cada ración aporta energía, con tope en 100: León +45, Jirafa +20, Oveja +15. | `Animal.registrarRacion()` + `prepararRacion()` en cada `Cuidador` |
| R8 | Una jaula vacía se saltea sin generar actas. | `AdministradorDeZoologico.hacerRondaDeAlimentacion()` |
| R9 | Una jaula tiene capacidad fija y no admite el mismo animal dos veces. | `Jaula.alojar()` |
| R10 | Una jaula **con animales y sin cuidador** es un error de configuración: la ronda lanza `IllegalStateException`. | `AdministradorDeZoologico.hacerRondaDeAlimentacion()` |
| R11 | Un felino con agresividad mayor a 3 necesita **encierro** para alimentarlo: el cuidador usa una rutina distinta. | `Felino.necesitaEncierroParaAlimentar()` + `CuidadorDeLeon.prepararRacion()` |
| R12 | La rutina de la jirafa cuelga el comedero **40 cm por debajo de su altura**. | `CuidadorDeJirafa.prepararRacion()` |

Fijate la diferencia entre R5/R6 y R10. Las dos primeras son **situaciones
normales del negocio** — pasan todos los días — y por eso se devuelven como
resultado en el acta. R10 es un **error de quien armó el zoológico**, y por
eso es una excepción. Esa distinción es una decisión de diseño, no un detalle.

## Verificá los números

Con el `main`, día 1, tres rondas. Todos arrancan en 60 y el día les descuenta
el desgaste:

| Animal | Arranca | Tras el desgaste | Ronda 1 | Ronda 2 | Ronda 3 |
|---|---|---|---|---|---|
| Simba (León) | 60 | 25 | +45 → 70 | salteado | salteado |
| Melman (Jirafa) | 60 | 35 | +20 → 55 | +20 → 75 | salteado |
| Dolly (Oveja) | 60 | 42 | +15 → 57 | +15 → 72 | +15 → 87 |
| Perdida (Oveja, jaula de leones) | 60 | 42 | fuera de especialidad | ídem | ídem |

Perdida termina el día con 42: **nadie la alimentó**, y el acta lo dice tres
veces. Es el ejemplo de por qué R6 devuelve un resultado y no una excepción:
el zoológico sigue funcionando, pero queda constancia.

Son exactamente los números que imprime `ZoologicoMain`. (El bloque previo de
"polimorfismo a la vista" usa animales de muestra que no están en el
zoológico, justamente para no alterarlos.)

## Qué muestra cada concepto

### TDA

Cada clase expone **qué se puede hacer**, nunca **cómo está guardado**.
`Jaula` tiene un arreglo adentro, pero nadie lo ve: se consulta con
`cantidadDeAnimales()` y `animal(i)`. Si mañana cambia a otra estructura,
ningún usuario de `Jaula` se entera.

Los TDA de **datos** (`Zoologico`, `Jaula`, `Animal` y sus hijas) están
separados de los TDA de **funcionalidad** (`AdministradorDeZoologico`,
`Cuidador` y sus hijas). Cada clase tiene una sola razón para cambiar.

### POO y encapsulamiento

- Todos los atributos son `private`. No hay setters: el estado se modifica
  sólo por operaciones con nombre (`alojar`, `registrarRacion`), que validan.
- Las **invariantes se validan en el constructor**. Un `Leon` con agresividad
  15 o una `Jirafa` de 50 cm no pueden existir.
- `Animal.registrarRacion()` es **package-private**: sólo el paquete `zoo`
  puede alimentar. Desde afuera, hay que pasar por un `Cuidador`.
  Encapsular *quién puede llamar* es tan importante como encapsular datos.

### Herencia

- `Animal` → `Felino` → `Leon`: tres niveles. `Felino` resuelve
  `esCarnivoro()` **una sola vez** para toda la rama, y lo marca `final`
  para que ninguna subclase lo contradiga.
- `Jirafa` y `Oveja` cuelgan directo de `Animal`. La jerarquía **no tiene por
  qué ser simétrica**: se agrega un nivel intermedio cuando hay comportamiento
  para compartir, no antes.
- Una subclase puede agregar **estado**, no sólo métodos: `Felino` tiene
  `nivelDeAgresividad`; `Jirafa`, `alturaEnCm`.

### Polimorfismo

Está en una sola línea de `AdministradorDeZoologico`:

```java
actas[cantidad] = jaula.cuidador().alimentar(animal);
```

El administrador no sabe qué cuidador es. Es el objeto, según su tipo real,
el que ejecuta su rutina. Por eso **agregar una especie nueva no toca al
administrador**: se agregan una subclase de `Animal` y una de `Cuidador`, y
listo.

`Cuidador.alimentar()` es un **método plantilla**: es `final`, fija el
procedimiento (especialidad → frecuencia → ración → energía → acta) y deja
tres huecos abstractos para que las subclases completen. Ninguna subclase
puede olvidarse de actualizar la energía, porque no es ella la que la
actualiza.

### Buenas prácticas

- **Sin números mágicos**: `ENERGIA_MAXIMA`, `COMIDAS_POR_DIA`,
  `KILOS_DE_CARNE`. Cada número tiene nombre y vive en un solo lugar.
- **`@Override` siempre.** Si te equivocás en la firma, el compilador avisa.
- **El modelo no imprime.** Devuelve `ActaDeAlimentacion` y el `main` decide
  cómo mostrarlo. Por eso `ZooTest` puede verificar cada regla sin consola.
- **`Racion` y `ActaDeAlimentacion` son `record`**: valores inmutables con
  `equals`, `hashCode` y `toString` gratis.
- **Resultados vs excepciones**: lo que es parte del negocio se devuelve
  (`ResultadoDeAlimentacion`); lo que es un error de programación se lanza.
- **Cast seguro por contrato**: `CuidadorDeLeon.prepararRacion()` hace
  `(Felino) animal`, y es seguro porque la plantilla ya verificó
  `puedeAtender`. El comentario lo dice. Un cast sin esa garantía sería un
  bug esperando a pasar.

## El costo del diseño, para decirlo en clase

`Cuidador` con un hijo por especie, en paralelo a `Animal` con un hijo por
especie, son **jerarquías paralelas**: cada especie nueva obliga a crear dos
clases. Acá se justifica porque los cuidadores de un zoológico *están*
especializados. Pero si las rutinas fueran intercambiables, sería mejor una
sola clase `Cuidador` que reciba la rutina como objeto. Esa es la
conversación siguiente, cuando veamos interfaces y composición.
