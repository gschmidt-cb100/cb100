# JuegoVisual — una ventana con tablero, personajes y teclado

Librería de la cátedra para que un juego de grilla por turnos tenga
gráficos **sin saber Swing**. Un tablero de celdas, cada una con un símbolo
que se dibuja con un color o una imagen, personajes que se mueven por encima,
una barra de mensajes, y el teclado listo para leer.

## En tres pasos

```java
String[] nivel = {
        "##########",
        "#H..~~..K#",
        "#........S",
        "##########",
};

try (JuegoVisual juego = new JuegoVisual("Mazmorra", nivel)) {
    juego.agregarPersonaje("heroe", Path.of(".../imagenes/heroe.png"), 2, 2);
    juego.mostrar();

    int fila = 2, columna = 2;
    while (juego.estaAbierta()) {
        Tecla tecla = juego.esperarTecla();                       // 1. esperar
        if (tecla == Tecla.ESCAPE || tecla == Tecla.CERRAR) {
            break;
        }
        if (tecla.esDireccion()) {                                // 2. decidir
            int f = fila + tecla.deltaFila();
            int c = columna + tecla.deltaColumna();
            if (juego.estaDentro(f, c) && juego.getCelda(f, c) != '#') {
                fila = f;
                columna = c;
                juego.moverPersonaje("heroe", fila, columna);     // 3. mover
            }
        }
    }
}
```

No hay `KeyListener`, ni `repaint()`, ni hilos: **`esperarTecla()` se queda
esperando** hasta que el usuario apriete algo, y todo lo que cambia el
tablero se redibuja solo. Como el juego es por turnos, un ciclo común
alcanza.

## La API

| Método | Qué hace |
|---|---|
| `new JuegoVisual(titulo, grilla)` / `(titulo, filas, columnas)` | Crea la ventana, todavía oculta. Opcional: tamaño de celda en píxeles (48 por defecto). |
| `mostrar()` | La muestra. Antes de eso se puede armar todo el tablero. |
| `definirCelda(char, Color)` / `definirCelda(char, Path)` | Con qué se dibuja un símbolo. `# . ~ ^ S T K P X` ya tienen color. |
| `setCelda(f, c, char)` / `getCelda(f, c)` | Leer y cambiar una celda. **De 1 a n.** |
| `cargar(grilla)` | Reemplazar la grilla entera. |
| `estaDentro(f, c)` | Si la posición existe, para no salirse del tablero. |
| `agregarPersonaje(nombre, Path o Color, f, c)` | Un sprite con nombre. Con `Color`, es un círculo con la inicial. |
| `moverPersonaje(nombre, f, c)` | Lo mueve. La celda de abajo no cambia. |
| `getFilaDe(nombre)` / `getColumnaDe(nombre)` / `quitarPersonaje` | Consultar y sacar. |
| `mostrarMensaje(texto)` | La barra de abajo: energía, turno, avisos. |
| `esperarTecla()` | **Bloquea** hasta una tecla. Devuelve `Tecla.CERRAR` si cerraron la ventana. |
| `leerTecla()` | No bloquea: la próxima tecla, o `null`. |
| `cerrar()` | Cierra. También se cierra sola al salir de un `try (...)`. |

`Tecla` es un enum: `ARRIBA ABAJO IZQUIERDA DERECHA ESPACIO ENTER ESCAPE
RETROCESO OTRA CERRAR`. Las flechas y W A S D son lo mismo. Las direcciones
tienen `deltaFila()` y `deltaColumna()` (−1, 0 o 1), así el movimiento es
una suma y no cuatro `if`.

## Imágenes

PNG con transparencia o JPG, de cualquier tamaño: se agrandan a la celda
**sin suavizar**, para que el pixel-art quede nítido. En `imagenes/` hay
sprites de prueba de 64×64 hechos por la cátedra: héroe, monstruo, pared,
piso, agua, tesoro, llave, puerta, salida. Se regeneran o se cambian con
`imagenes/generar-imagenes.py` (requiere `pip install pillow`): cada sprite
es un dibujo de 16 líneas de 16 letras, una letra por color.

Un símbolo **sin apariencia definida** se dibuja en **magenta con el
símbolo adentro**: si ven eso, falta un `definirCelda`.

## Sonido

`EjemploDeJuegoVisual` muestra cómo combinarlo con `ReproductorDeSonido`:
un `reproducirEfecto("paso")` por movimiento, y la música de fondo al 30 %.

## Límites, a propósito

No hay animaciones, ni tiempo real, ni mouse. Es una librería para un juego
por turnos: lo que entra por el teclado, sale por el tablero. Si un grupo
quiere más, la clase `PanelDelTablero` es un `JPanel` común y se puede
extender.
