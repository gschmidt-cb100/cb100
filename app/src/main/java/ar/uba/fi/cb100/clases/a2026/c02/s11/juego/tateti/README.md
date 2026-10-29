# Enunciado: Tateti

Implementar el **tateti** para dos jugadores, reutilizando el núcleo común de
`juego/commons`.

## Reglas

- Se juega en un tablero de 3×3, inicialmente vacío.
- Juegan dos jugadores: uno con **X** y otro con **O**. Empieza el primero (X).
- En su turno, cada jugador pone su ficha en una celda **vacía**.
- Gana quien forma **3 fichas propias en línea**: horizontal, vertical o diagonal.
- Si el tablero se llena sin que nadie gane, es **empate**.
- Terminada la partida, no se puede seguir jugando; se puede empezar otra con
  los mismos jugadores, que conservan sus victorias.

### Generalización

El tablero puede ser de *n×m* con *k* en línea para ganar
(por ejemplo, 5×5 con 4 en línea, o 15×15 con 5: el *gomoku*).

## Lo que se pide

1. `FichaDeTateti`: enum X, O que implementa `Ficha`.
2. `PartidaDeTateti extends PartidaConFicha<FichaDeTateti>`: agrega la cantidad de fichas en línea para ganar.
3. `AdministradorDePartidaDeTateti extends AdministradorDePartidaConFicha<FichaDeTateti, PartidaDeTateti>`:
   - `esJugadaValida` (celda vacía) y `aplicarJugada` (poner la ficha) **se heredan**: no se escriben.
   - `buscarGanador`: mirar **sólo las líneas que pasan por la última jugada**
     (hacia una dirección y hacia su opuesta), no todo el tablero.
   - `esEmpate`: tablero lleno.
4. `PrincipalDeTateti`: crear los `JugadorConFicha`, el administrador, la
   `InterfazGraficaConFicha` con una imagen para cada ficha, el sonido, y jugar
   con el `ControladorDeJuegoDeTablero`.

## Restricciones

- No repetir nada que ya esté en commons (turnos, historial, ciclo del juego, teclado).
- Toda jugada inválida lanza una excepción con un mensaje que explique el motivo;
  la interfaz lo muestra y suena el error.
- Filas y columnas van de 1 a n.
