# -*- coding: utf-8 -*-
"""
Dibuja los sprites de prueba en pixel-art (16x16, guardados a 64x64 para que
se vean nítidos) y los guarda como PNG con transparencia en la carpeta que se
pase por parámetro. Cada sprite es un dibujo de 16 líneas de 16 caracteres;
cada letra es un color de la paleta. El punto es un pixel transparente.

Requiere Pillow:   pip install pillow
Uso:               python generar-imagenes.py <carpeta-destino>
"""

import os
import sys

from PIL import Image

ESCALA = 4          # 16 px de dibujo -> 64 px de archivo

PALETA = {
    '.': (0, 0, 0, 0),              # transparente
    'k': (30, 30, 30, 255),         # contorno
    'p': (245, 205, 170, 255),      # piel
    'a': (47, 111, 209, 255),       # azul (ropa del heroe)
    'A': (30, 75, 150, 255),        # azul oscuro
    'm': (140, 80, 40, 255),        # marron (pelo, cuero)
    'r': (198, 40, 40, 255),        # rojo (monstruo)
    'R': (130, 20, 20, 255),        # rojo oscuro
    'y': (242, 193, 78, 255),       # dorado
    'Y': (255, 236, 150, 255),      # brillo
    'w': (255, 255, 255, 255),      # blanco
    'g': (74, 74, 74, 255),         # gris pared
    'G': (110, 110, 110, 255),      # gris claro
    'q': (158, 208, 240, 255),      # agua
    'Q': (120, 175, 220, 255),      # agua oscura
    'f': (243, 234, 215, 255),      # piso
    'F': (225, 214, 190, 255),      # piso, mota
    'v': (102, 187, 106, 255),      # verde salida
    'V': (60, 140, 65, 255),        # verde oscuro
    'b': (161, 136, 127, 255),      # madera puerta
    'B': (110, 85, 75, 255),        # madera oscura
}

SPRITES = {
    'heroe': [
        "................",
        ".....kkkkkk.....",
        "....kmmmmmmk....",
        "....kmpppppk....",
        "....kpkppkpk....",
        "....kpppppppk...",
        ".....kppppk.....",
        "....kkaaaakk....",
        "...kpkaaaakpk...",
        "...kpkaAAakpk...",
        "....kkaaaakk....",
        ".....kAAAAk.....",
        ".....kAkkAk.....",
        ".....kmk.kmk....",
        ".....kkk.kkk....",
        "................",
    ],
    'monstruo': [
        "................",
        "...kk......kk...",
        "..kRRk....kRRk..",
        "..kRrrkkkkrrRk..",
        "...krrrrrrrrk...",
        "..krrwkrrkwrrk..",
        "..krrkkrrkkrrk..",
        "..krrrrrrrrrrk..",
        "..krrkwwwwkrrk..",
        "..krrrkwwkrrrk..",
        "...krrrkkrrrk...",
        "....kRRRRRRk....",
        "...kRRkRRkRRk...",
        "...kkk.kk.kkk...",
        "................",
        "................",
    ],
    'pared': [
        "gggggggkgggggggg",
        "gGGGGGGkgGGGGGGg",
        "gGGGGGGkgGGGGGGg",
        "kkkkkkkkkkkkkkkk",
        "gggkgggggggggkgg",
        "gGGkgGGGGGGGGkGg",
        "gGGkgGGGGGGGGkGg",
        "kkkkkkkkkkkkkkkk",
        "gggggggkgggggggg",
        "gGGGGGGkgGGGGGGg",
        "gGGGGGGkgGGGGGGg",
        "kkkkkkkkkkkkkkkk",
        "gggkgggggggggkgg",
        "gGGkgGGGGGGGGkGg",
        "gGGkgGGGGGGGGkGg",
        "kkkkkkkkkkkkkkkk",
    ],
    'piso': [
        "ffffffffffffffff",
        "ffffffffffFfffff",
        "ffFfffffffffffff",
        "ffffffffffffffff",
        "ffffffffffffffff",
        "fffffffFffffffff",
        "ffffffffffffffFf",
        "ffffffffffffffff",
        "fFffffffffffffff",
        "ffffffffffffffff",
        "ffffffffFfffffff",
        "ffffffffffffffff",
        "ffffffffffffffff",
        "fffFffffffffffff",
        "ffffffffffffFfff",
        "ffffffffffffffff",
    ],
    'agua': [
        "qqqqqqqqqqqqqqqq",
        "qqQQqqqqqqQQqqqq",
        "qQqqQqqqqQqqQqqq",
        "qqqqqqqqqqqqqqqq",
        "qqqqqqqqqqqqqqqq",
        "qqqqqqQQqqqqqqqq",
        "qqqqqQqqQqqqqqqq",
        "qqqqqqqqqqqqqqqq",
        "qqqqqqqqqqqqqqqq",
        "qQQqqqqqqqqQQqqq",
        "Qqqqqqqqqqqqqqqq",
        "qqqqqqqqqqqqqqqq",
        "qqqqqqqqqqqqqqqq",
        "qqqqqqQQqqqqqqqq",
        "qqqqqQqqQqqqqqqq",
        "qqqqqqqqqqqqqqqq",
    ],
    'tesoro': [
        "................",
        "................",
        "....kkkkkkkk....",
        "...kyyyyyyyyk...",
        "..kyYyyyyyyyyk..",
        "..kyyyyyyyyyyk..",
        "..kkkkkkkkkkkk..",
        "..kyyyykkyyyyk..",
        "..kyyyykkyyyyk..",
        "..kyyyyyyyyyyk..",
        "..kyyyyyyyyyyk..",
        "..kyyyyyyyyyyk..",
        "..kkkkkkkkkkkk..",
        "................",
        "................",
        "................",
    ],
    'llave': [
        "................",
        "................",
        "......kkkk......",
        ".....kyyyyk.....",
        ".....kykkyk.....",
        ".....kyyyyk.....",
        "......kyyk......",
        ".......kyk......",
        ".......kyk......",
        ".......kyk......",
        ".......kyyk.....",
        ".......kykk.....",
        ".......kyyk.....",
        ".......kkkk.....",
        "................",
        "................",
    ],
    'puerta': [
        "kkkkkkkkkkkkkkkk",
        "kbbbbbbbbbbbbbbk",
        "kbBBBBBBBBBBBBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbyybBbk",
        "kbBbbbbbbbyybBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBbbbbbbbbbbBbk",
        "kbBBBBBBBBBBBBbk",
        "kbbbbbbbbbbbbbbk",
        "kkkkkkkkkkkkkkkk",
    ],
    'salida': [
        "vvvvvvvvvvvvvvvv",
        "vVVVVVVVVVVVVVVv",
        "vVvvvvvvvvvvvvVv",
        "vVvvvvvkkvvvvvVv",
        "vVvvvvkwwkvvvvVv",
        "vVvvvkwwwwkvvvVv",
        "vVvvkwwwwwwkvvVv",
        "vVvkkkkwwkkkkvVv",
        "vVvvvvkwwkvvvvVv",
        "vVvvvvkwwkvvvvVv",
        "vVvvvvkwwkvvvvVv",
        "vVvvvvkkkkvvvvVv",
        "vVvvvvvvvvvvvvVv",
        "vVVVVVVVVVVVVVVv",
        "vvvvvvvvvvvvvvvv",
        "vvvvvvvvvvvvvvvv",
    ],
}


def dibujar(nombre, filas, carpeta):
    assert len(filas) == 16 and all(len(f) == 16 for f in filas), nombre
    imagen = Image.new('RGBA', (16, 16))
    for y, fila in enumerate(filas):
        for x, letra in enumerate(fila):
            imagen.putpixel((x, y), PALETA[letra])
    grande = imagen.resize((16 * ESCALA, 16 * ESCALA), Image.NEAREST)   # sin suavizar: pixel-art
    destino = os.path.join(carpeta, nombre + '.png')
    grande.save(destino)
    print("%-10s %s  %d bytes" % (nombre, destino, os.path.getsize(destino)))


if __name__ == '__main__':
    carpeta = sys.argv[1] if len(sys.argv) > 1 else '.'
    os.makedirs(carpeta, exist_ok=True)
    for nombre, filas in SPRITES.items():
        dibujar(nombre, filas, carpeta)
