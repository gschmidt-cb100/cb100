# Enunciado: Reversi (Othello)

Implementar el **reversi** para dos jugadores, reutilizando el núcleo común de
`juego/commons`.

## Reglas

- Se juega en un tablero de 8×8. Al empezar hay 4 fichas en el centro, en
  diagonal: blancas en (4,4) y (5,5), negras en (4,5) y (5,4).
- Juegan dos jugadores: **negras** y **blancas**. Empiezan las negras.
- En su turno, un jugador pone una ficha en una celda vacía de forma que
  **encierre** una o más fichas contrarias en línea recta (horizontal, vertical
  o diagonal) entre la ficha nueva y otra propia, sin huecos.
- Todas las fichas encerradas, en **todas** las direcciones, se dan vuelta y pasan
  a ser del jugador.
- Una jugada que no encierra nada **no es válida**.
- Si un jugador no tiene ninguna jugada válida, **pasa** y vuelve a jugar el otro.
- La partida termina cuando **ninguno de los dos** puede jugar (o el tablero se llenó).
  Gana quien tiene más fichas; con la misma cantidad, es empate.

## Lo que se pide

1. `FichaDeReversi`: enum NEGRA, BLANCA que implementa `Ficha` y sabe su contraria.
2. `PartidaDeReversi extends PartidaConFicha<FichaDeReversi>`: tablero de lado par; recuerda quién pasó el último turno.
3. `AdministradorDePartidaDeReversi extends AdministradorDePartidaConFicha<FichaDeReversi, PartidaDeReversi>`:
   - `esJugadaValida` y `aplicarJugada` **extienden** los heredados con `super`:
     celda vacía *y además* encierra fichas; poner la ficha *y además* dar vuelta las encerradas,
   - los métodos abstractos (`buscarGanador`, `esEmpate`, `crearPartida`),
   - y redefinir los **ganchos** que hagan falta: `prepararTablero` (las 4 fichas iniciales),
     `pasarAlSiguienteTurno` (el pase), `debeMarcarJugadasValidas` (mostrar dónde se puede jugar)
     y `describirSituacion` (cuántas fichas tiene cada uno).
4. `PrincipalDeReversi`: igual al del tateti, con otras fichas e imágenes.

## Pista

Buscar las fichas encerradas en una dirección es: avanzar mientras haya fichas
contrarias; si al final hay una propia, las recorridas quedan encerradas; si hay
borde o celda vacía, no se encierra nada. Con `Direccion.values()` se recorren las 8.

## Pregunta

Comparar `AdministradorDePartidaDeReversi` con el del tateti. ¿Qué parte del
ciclo de una jugada no tuvieron que escribir en ninguno de los dos? ¿Por qué el
`ControladorDeJuegoDeTablero` no necesitó ni un `if` para distinguir los juegos?
