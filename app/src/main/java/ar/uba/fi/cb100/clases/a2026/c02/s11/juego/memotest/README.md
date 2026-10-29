# Enunciado: Memotest

Implementar el **memotest** para dos jugadores, reutilizando el núcleo común de
`juego/commons`. Es un juego **sin fichas**: el tablero tiene cartas y los
jugadores no juegan con ninguna ficha propia.

## Reglas

- Hay 16 cartas (tablero de 4×4): 8 figuras, dos cartas de cada una.
  Se mezclan y se ponen boca abajo.
- En su turno, el jugador da vuelta **dos** cartas, de a una.
  - Si son **pareja** (misma figura), quedan a la vista para siempre, el
    jugador suma un par y **vuelve a jugar**.
  - Si no, quedan a la vista hasta que el siguiente jugador elija su primera
    carta (así todos pueden memorizarlas), y el turno pasa.
- No se puede elegir una carta que ya está a la vista.
- Cuando se encontraron todos los pares, gana quien tiene más; con la misma
  cantidad, empate.

## Lo que se pide

1. `FiguraDeCarta` y `EstadoDeCarta` (enums).
2. `Carta`: TDA de datos con figura y estado. Los cambios de estado se validan:
   ```
   BOCA_ABAJO ──darVuelta──► BOCA_ARRIBA ──marcarEncontrada──► ENCONTRADA
        ▲                         │
        └─────────tapar───────────┘
   ```
3. `PartidaDeMemotest extends PartidaDeTablero<Carta, Jugador>` (**no** PartidaConFicha):
   guarda las cartas dadas vuelta en el turno y los pares de cada jugador.
4. `AdministradorDePartidaDeMemotest extends AdministradorDePartidaDeTablero<Carta, Jugador, PartidaDeMemotest>`:
   - Una **jugada** es elegir **una** carta: el turno de un jugador son dos o más jugadas.
   - `prepararTablero` (gancho): repartir las cartas mezcladas.
   - `pasarAlSiguienteTurno` (gancho): el turno sólo pasa si las dos cartas no eran pareja.
5. `InterfazGraficaDeMemotest extends InterfazGraficaDeTablero<Carta>`: decide la
   apariencia de una carta según su estado (dorso, figura o figura encontrada).
6. `PrincipalDeMemotest`: igual a los otros dos, con `Jugador` comunes.

## Preguntas

1. ¿Por qué `Carta` no redefine `equals`? ¿Qué pasaría con `Tablero.contar` si lo hiciera por figura?
2. El tateti y el reversi heredan de `AdministradorDePartidaConFicha`, el memotest no.
   ¿Qué tendría que hacer el memotest si heredara de ella?
3. ¿Qué parte de `commons` no se modificó para agregar este juego?
