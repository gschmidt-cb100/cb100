# -*- coding: utf-8 -*-
"""
Sintetiza los sonidos de prueba de la cátedra y los guarda como MP3 en esta
misma carpeta. No hace falta bajar nada: todo se fabrica con matemática.

    paso.mp3     golpe seco y grave (0,12 s)      -> cada movimiento del héroe
    agua.mp3     chapoteo (0,35 s)                -> entrar a una celda de agua
    tesoro.mp3   arpegio ascendente (0,4 s)       -> levantar un objeto
    musica.mp3   loop de 16 s a 120 bpm           -> música de fondo

Requiere el codificador LAME para Python:   pip install lameenc
Uso:                                         python generar-sonidos.py
"""

import math
import os
import random
import struct
import wave

import lameenc

TASA = 44100          # muestras por segundo
AMPLITUD = 20000      # de 32767: deja margen para no saturar
CARPETA = os.path.dirname(os.path.abspath(__file__))


# --------------------------------------------------------------------------
#  Generadores básicos: devuelven listas de floats entre -1 y 1
# --------------------------------------------------------------------------
def silencio(segundos):
    return [0.0] * int(TASA * segundos)


def seno(frecuencia, segundos, decaimiento=0.0):
    """Tono puro. decaimiento > 0 lo apaga exponencialmente (percusivo)."""
    n = int(TASA * segundos)
    return [math.sin(2 * math.pi * frecuencia * i / TASA) * math.exp(-decaimiento * i / TASA)
            for i in range(n)]


def triangulo(frecuencia, segundos, decaimiento=0.0):
    """Onda triangular: más 'de juego' que el seno, menos áspera que la cuadrada."""
    n = int(TASA * segundos)
    return [(2 / math.pi) * math.asin(math.sin(2 * math.pi * frecuencia * i / TASA))
            * math.exp(-decaimiento * i / TASA) for i in range(n)]


def cuadrada(frecuencia, segundos, decaimiento=0.0):
    n = int(TASA * segundos)
    return [(1.0 if math.sin(2 * math.pi * frecuencia * i / TASA) >= 0 else -1.0)
            * math.exp(-decaimiento * i / TASA) for i in range(n)]


def ruido(segundos, decaimiento=0.0, suavizado=1):
    """Ruido blanco. suavizado > 1 lo vuelve más grave (promedio móvil)."""
    n = int(TASA * segundos)
    crudo = [random.uniform(-1, 1) for _ in range(n + suavizado)]
    return [sum(crudo[i:i + suavizado]) / suavizado * math.exp(-decaimiento * i / TASA)
            for i in range(n)]


def barrido(desde, hasta, segundos, decaimiento=0.0):
    """Seno cuya frecuencia baja (o sube) linealmente."""
    n = int(TASA * segundos)
    salida, fase = [], 0.0
    for i in range(n):
        f = desde + (hasta - desde) * i / n
        fase += 2 * math.pi * f / TASA
        salida.append(math.sin(fase) * math.exp(-decaimiento * i / TASA))
    return salida


def mezclar(*pistas):
    """Suma muestra a muestra; la más larga manda."""
    largo = max(len(p) for p in pistas)
    return [sum(p[i] if i < len(p) else 0.0 for p in pistas) for i in range(largo)]


def escalar(pista, factor):
    return [m * factor for m in pista]


def concatenar(*pistas):
    salida = []
    for p in pistas:
        salida.extend(p)
    return salida


def suavizar_bordes(pista, milisegundos=5):
    """Fade in/out cortito para que no haga 'click' al empezar y terminar."""
    n = int(TASA * milisegundos / 1000)
    for i in range(min(n, len(pista))):
        pista[i] *= i / n
        pista[-1 - i] *= i / n
    return pista


# --------------------------------------------------------------------------
#  Los cuatro sonidos
# --------------------------------------------------------------------------
def paso():
    golpe = seno(90, 0.12, decaimiento=35)          # grave y seco
    roce = escalar(ruido(0.03, decaimiento=120, suavizado=4), 0.35)
    return suavizar_bordes(mezclar(golpe, roce))


def agua():
    chapoteo = escalar(ruido(0.35, decaimiento=12, suavizado=6), 0.8)
    burbuja = escalar(barrido(400, 150, 0.25, decaimiento=10), 0.5)
    return suavizar_bordes(mezclar(chapoteo, burbuja))


def tesoro():
    notas = [1047, 1319, 1568, 2093]                # C6 E6 G6 C7: arpegio mayor
    partes = [escalar(triangulo(f, 0.10, decaimiento=6), 0.7) for f in notas]
    cola = escalar(triangulo(2093, 0.15, decaimiento=12), 0.7)
    return suavizar_bordes(concatenar(*partes, cola))


# Notas en Hz (cuarta y quinta octava)
NOTA = {'A3': 220.0, 'C4': 261.6, 'D4': 293.7, 'E4': 329.6, 'F4': 349.2, 'G4': 392.0,
        'A4': 440.0, 'B4': 493.9, 'C5': 523.3, 'D5': 587.3, 'E5': 659.3, 'F3': 174.6,
        'G3': 196.0, 'C3': 130.8, 'E3': 164.8}


def musica():
    """16 segundos a 120 bpm: dos vueltas de Am - F - C - G. Cierra en bucle."""
    corchea = 0.25                                  # a 120 bpm, una corchea dura 0,25 s
    acordes = [('A3', ['A4', 'C5', 'E5', 'C5']),    # bajo, y 4 notas de melodía por compás
               ('F3', ['F4', 'A4', 'C5', 'A4']),
               ('C3', ['C4', 'E4', 'G4', 'E4']),
               ('G3', ['G4', 'B4', 'D5', 'B4'])]
    melodia, bajo = [], []
    for _ in range(2):                              # dos vueltas
        for raiz, notas in acordes:
            for nota in notas:                      # 4 corcheas de melodía...
                melodia.extend(escalar(triangulo(NOTA[nota], corchea, decaimiento=3), 0.45))
                melodia.extend(escalar(triangulo(NOTA[nota] * 2, corchea, decaimiento=8), 0.12))
            for _ in range(4):                      # ...y el bajo en negras
                bajo.extend(escalar(cuadrada(NOTA[raiz], corchea * 2, decaimiento=2), 0.18))
    return suavizar_bordes(mezclar(melodia, bajo), milisegundos=15)


# --------------------------------------------------------------------------
#  Codificación a MP3
# --------------------------------------------------------------------------
def a_pcm16(pista):
    return b''.join(struct.pack('<h', int(max(-1.0, min(1.0, m)) * AMPLITUD)) for m in pista)


def guardar_mp3(nombre, pista, kbps):
    codificador = lameenc.Encoder()
    codificador.set_bit_rate(kbps)
    codificador.set_in_sample_rate(TASA)
    codificador.set_channels(1)
    codificador.set_quality(2)                      # 2 = alta calidad, más lento
    datos = codificador.encode(a_pcm16(pista)) + codificador.flush()
    destino = os.path.join(CARPETA, nombre)
    with open(destino, 'wb') as f:
        f.write(datos)
    return len(datos)


def guardar_wav(nombre, pista):
    """WAV = las muestras PCM tal cual, sin comprimir, más 44 bytes de cabecera."""
    destino = os.path.join(CARPETA, nombre)
    with wave.open(destino, 'wb') as w:
        w.setnchannels(1)
        w.setsampwidth(2)                           # 16 bits
        w.setframerate(TASA)
        w.writeframes(a_pcm16(pista))
    return os.path.getsize(destino)


def guardar(nombre, pista, kbps):
    """El mismo sonido en los dos formatos, para comparar el peso."""
    mp3 = guardar_mp3(nombre + '.mp3', pista, kbps)
    wav = guardar_wav(nombre + '.wav', pista)
    print("%-8s %5.2f s   mp3 %7.1f KB   wav %7.1f KB   (x%.0f)"
          % (nombre, len(pista) / TASA, mp3 / 1024, wav / 1024, wav / mp3))


if __name__ == '__main__':
    random.seed(100)                                # mismos sonidos en cada corrida
    guardar('paso', paso(), 64)
    guardar('agua', agua(), 64)
    guardar('tesoro', tesoro(), 64)
    guardar('musica', musica(), 96)
