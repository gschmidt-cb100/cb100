# Enunciado: Casita robada

Implementar la **casita robada** para 2 a 4 jugadores, reutilizando el núcleo
común de `juego/commons`. Es un juego **sin tablero**: no se juega en
posiciones, sino tirando cartas.

## Reglas

- Se juega con la baraja española de **40 cartas** (sin 8 ni 9).
- Al empezar se mezcla, se ponen **4 cartas boca arriba en la mesa** y se dan
  **3 a cada jugador**.
- En su turno, el jugador tira **una** carta de su mano:
  - **a la casita de otro jugador**: si la de arriba tiene el **mismo número**, se
    la roba **entera** y la pone en su casita, con la carta tirada arriba;
  - **a la mesa**: si hay cartas con el mismo número, las levanta **todas** y las
    pone en su casita, con la tirada arriba; si no hay, la carta queda en la mesa.
- Si una carta sirve para robar y también para levantar, **elige el jugador**.
- Se puede robar la casita de **cualquier** otro jugador, no la propia.
- Cuando nadie tiene cartas en la mano, se reparten 3 más a cada uno (a la mesa ya no).
- Cuando se acaba el mazo y nadie tiene cartas, gana quien tiene la **casita más grande**.
  Si hay empate en el máximo, es empate. Las cartas que quedan en la mesa no son de nadie.

## Lo que se pide

### Datos (`datos/`)

| TDA | Qué es | Estructura |
|---|---|---|
| `Palo`, `CartaEspanola` | la carta: número y palo, inmutable | |
| `Mazo` | de donde se reparte | **Pila** |
| `Casita` | lo ganado por un jugador: sólo importa la de arriba | **Pila** |
| `Mano` | hasta 3 cartas para tirar | lista |
| `Mesa` | las cartas a la vista | lista |
| `Tiro` | el **movimiento**: una carta y adónde va (mesa o casita de alguien) | |
| `ResultadoDeTiro`, `JugadaDeCasitaRobada extends Jugada<Jugador>` | qué pasó, para el historial | |
| `PartidaDeCasitaRobada extends Partida<Jugador>` | mazo, mesa, una mano y una casita por jugador | |

### Reglas (`funcional/`)

`AdministradorDePartidaDeCasitaRobada extends AdministradorDePartida<Jugador, Tiro, PartidaDeCasitaRobada>`:
hereda **directamente** del nivel de "juego por turnos", no del de tablero.
- `prepararPartida`: mezclar y repartir.
- `esJugadaValida(Tiro)` y `explicarJugadaInvalida(Tiro)`.
- `crearJugada`: se llama **antes** de aplicar el tiro, así puede describir qué va a pasar.
- `aplicarJugada`: robar, levantar o dejar; y repartir si las manos quedaron vacías.
- `buscarGanador` y `esEmpate`.

### Presentación (`presentacion/`)

- `DisposicionDeCasitaRobada`: dónde va cada cosa en la grilla de la ventana.
- `InterfazDeCasitaRobada extends InterfazDeJuego` e `InterfazGraficaDeCasitaRobada extends InterfazGrafica`.
- `ControladorDeCasitaRobada extends ControladorDeJuego<Tiro>`: se elige el tiro en
  dos pasos (Enter sobre una carta de la mano; Enter sobre la mesa o una casita).

## Preguntas

1. ¿Por qué `Casita` y `Mazo` son pilas y `Mesa` es una lista?
2. ¿Por qué `PartidaDeCasitaRobada` no hereda de `PartidaDeTablero`? ¿Qué habría que inventar si lo hiciera?
3. Después del reparto inicial, ¿puede haber en la mesa dos cartas del mismo número? ¿Por qué?
4. Agregar este juego obligó a separar `commons` en dos niveles. ¿Qué clases de los otros tres juegos cambiaron, y cuánto?
