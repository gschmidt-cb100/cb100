# Juegos por turnos: diseño común y cuatro juegos

Cuatro juegos construidos sobre un mismo núcleo (`commons`):

| Juego | ¿Tablero? | ¿Fichas? | Movimiento |
|---|---|---|---|
| Tateti | sí | sí | una `Posicion` |
| Reversi | sí | sí | una `Posicion` |
| Memotest | sí | no (cartas) | una `Posicion` |
| Casita robada | **no** | no (baraja española) | un `Tiro`: carta de la mano + adónde va |

La idea de la clase: todo lo que no depende de las reglas de un juego se
escribe **una sola vez**, y cada juego sólo agrega lo suyo. Cada juego nuevo
**pone a prueba el diseño**: el memotest mostró que no todo tiene fichas, y la
casita robada que no todo tiene tablero. Por eso `commons` tiene **dos niveles**:
lo que es de cualquier juego por turnos, y lo que es de un juego de tablero.

```
juego/
├── commons/
│   ├── datos/          TDAs de datos: guardan información y cuidan sus invariantes
│   │   Jugador ─► JugadorConFicha<F>
│   │   Jugada<J> ─► JugadaEnTablero<J> ─► JugadaConFicha<F>
│   │   Partida<J> ─► PartidaDeTablero<T, J> ─► PartidaConFicha<F>
│   │   Ficha (interfaz), Posicion, Direccion, Tablero<T> (interfaz), TableroMatriz<T>,
│   │   Pila<T>, EstadoDePartida
│   ├── funcional/      TDAs funcionales: aplican reglas y modifican los datos
│   │   AdministradorDePartida<J, M, P> ─► AdministradorDePartidaDeTablero<T, J, P>
│   │                                     ─► AdministradorDePartidaConFicha<F, P>
│   └── presentacion/   pantalla, teclado y sonido
│       InterfazDeJuego ─► InterfazDeTablero<T>                     (interfaces)
│       InterfazGrafica ─► InterfazGraficaDeTablero<T> ─► InterfazGraficaConFicha<F>
│       ControladorDeJuego<M> ─► ControladorDeJuegoDeTablero<T>
│       Sonido (interfaz) ── SonidoDeLaCatedra / SinSonido
│       Accion, Cursor, Recursos
├── tateti/  reversi/  memotest/  casitarobada/
│   cada uno con datos/, funcional/, (presentacion/), imagenes/, Principal y README con el enunciado
└── generar-imagenes.py
```

## 1. Datos y funcionalidad, separados

| TDA de datos | TDA funcional |
|---|---|
| `Partida`: jugadores, turno, historial, estado, ganador | `AdministradorDePartida`: si un movimiento es válido, qué hace, quién gana |
| No conoce ninguna regla. Sí cuida sus invariantes. | No guarda la información de la partida: la lee y la modifica a través de la `Partida`. |

## 2. Herencia en niveles

```
Partida<J>                         turnos, historial, ganador
├── PartidaDeCasitaRobada          + mazo, mesa, manos, casitas
└── PartidaDeTablero<T, J>         + tablero; el historial son JugadaEnTablero
    ├── PartidaDeMemotest          + cartas dadas vuelta, pares
    └── PartidaConFicha<F>         + fichas distintas, getJugadorCon(ficha)
        ├── PartidaDeTateti        + cuántas en línea ganan
        └── PartidaDeReversi       + quién pasó

AdministradorDePartida<J, M, P>                la plantilla jugar(M); el movimiento M es genérico
├── AdministradorDePartidaDeCasitaRobada       M = Tiro
└── AdministradorDePartidaDeTablero<T, J, P>   M = Posicion: dentro del tablero, jugadas válidas
    ├── AdministradorDePartidaDeMemotest
    └── AdministradorDePartidaConFicha<F, P>   válida = celda vacía, aplicar = poner la ficha
        ├── AdministradorDePartidaDeTateti     usa esos pasos tal cual
        └── AdministradorDePartidaDeReversi    los extiende con super
```

Cada nivel agrega algo concreto. Si un nivel quedara vacío, sobraría.

## 3. Método plantilla

`AdministradorDePartida.jugar(movimiento)` es `final` e igual para los cuatro juegos:

```
en curso? → validarMovimiento → esJugadaValida? → crearJugada (antes de aplicar: describe lo que va a pasar)
          → aplicarJugada → registrar → buscarGanador / esEmpate → pasarAlSiguienteTurno
```

| Paso | De tablero | ConFicha | Tateti | Reversi | Memotest | Casita robada |
|---|---|---|---|---|---|---|
| `validarMovimiento` | dentro del tablero | | | | | (ninguna) |
| `esJugadaValida` | — | celda vacía | (hereda) | `super` + encierra | carta boca abajo | carta en la mano; robar: casita ajena con el mismo número |
| `crearJugada` | `JugadaEnTablero` | `JugadaConFicha` | | | | `JugadaDeCasitaRobada` (con el resultado) |
| `aplicarJugada` | — | poner la ficha | (hereda) | `super` + dar vuelta | dar vuelta; par → encontradas | robar / levantar / dejar; repartir si hace falta |
| `prepararPartida` | → `prepararTablero` | | | 4 fichas al centro | repartir cartas | 4 a la mesa, 3 a cada uno |
| `pasarAlSiguienteTurno` | | | | si no puede, pasa | sólo si no hubo pareja | (por defecto) |
| ganador / empate | | | k en línea / lleno | más fichas | más pares | casita más grande |

## 4. Genéricos

- `Partida<J>`, `Jugada<J>`: el tipo de jugador (con o sin ficha).
- `PartidaDeTablero<T, J>`, `Tablero<T>`: lo que hay en cada celda (ficha, carta).
- `AdministradorDePartida<J, M, P>`: el **movimiento** `M` es lo que se juega en un turno.
  Es lo que permitió agregar la casita robada sin tocar la plantilla.
- La presentación es genérica en lo que hay en la celda, no en "fichas": no hacen falta
  `ControladorDeJuegoConFicha` ni `InterfazDeJuegoConFicha` (habrían sido clases vacías).

## 5. Presentación en dos niveles

- `ControladorDeJuego<M>`: el ciclo (teclas, sonido, mensajes, nueva partida). Lo
  abstracto es **cómo se elige un movimiento**:
  - `ControladorDeJuegoDeTablero<T>`: el movimiento es la posición del cursor.
  - `ControladorDeCasitaRobada`: dos pasos, elegir una carta de la mano y después la mesa o una casita.
- `InterfazGrafica`: la única clase que conoce `JuegoVisual`. Resuelve ventana,
  teclado y apariencias. `InterfazGraficaDeTablero<T>` dibuja un tablero;
  `InterfazGraficaDeCasitaRobada` dibuja mesa, casitas, mazo y mano según la
  `DisposicionDeCasitaRobada` (la traducción entre "lugares del juego" y "celdas de la grilla").

## Un error que encontró el cuarto juego

`jugar` validaba con `validarFalso(!esJugadaValida(m), explicarJugadaInvalida(m))`.
Los argumentos se evalúan **siempre**, así que el mensaje de error se armaba
aunque la jugada fuera válida. En los juegos de tablero no se notaba; en la
casita robada, con un tiro a la mesa, `explicarJugadaInvalida` daba
NullPointerException. Se cambió por un `if`. Buena pregunta para la clase: ¿por qué?

## Teclas (las mismas en los cuatro)

| Tecla | Acción |
|---|---|
| Flechas o W A S D | mover el cursor |
| Enter o Espacio | jugar / elegir (con la partida terminada: otra partida) |
| Z o Retroceso | nueva partida |
| Esc | salir |

## Para practicar

1. Agregar `InterfazDeConsola` para el tateti y jugarlo en la terminal.
2. **Conecta 4**: ¿de qué hereda? ¿Qué pasos reutiliza y cuál redefine?
3. **Escoba de 15** (o chinchón): ¿de qué nivel hereda? ¿Qué TDAs de la casita robada reutiliza?
4. Deshacer la última jugada: ¿alcanza el historial? ¿En qué juego es más difícil?
5. Un `TableroVector<T>` y usarlo en el memotest cambiando una sola línea.
