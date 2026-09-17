# -*- coding: utf-8 -*-
"""Genera el PDF del enunciado del TP 2 (CB100, 2C 2026) con reportlab.
Mismo formato que el TP 1, con figuras vectoriales (nunca JPG).

Uso:  python generar-pdf.py [ruta-de-salida.pdf]
Ojo: si el PDF está abierto en Acrobat, la escritura falla con PermissionError.
"""

import math
import sys

from reportlab.graphics.shapes import Drawing, Group, Line, Polygon, Rect, String, Circle
from reportlab.lib import colors
from reportlab.lib.enums import TA_JUSTIFY
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import mm
from reportlab.platypus import (KeepTogether, PageBreak, Paragraph, Preformatted,
                                SimpleDocTemplate, Spacer, Table, TableStyle)

SALIDA = sys.argv[1] if len(sys.argv) > 1 else (
    r"C:\na\dvlp\wks-spring\cb100\app\src\main\java\ar\uba\fi\cb100"
    r"\clases\a2026\c02\tps\tp2\TP 2 - 2026 2C.pdf")

ENCABEZADO_1 = "CB100 - Algoritmos y Estructuras de Datos"
ENCABEZADO_2 = "Catedra Ing. Gustavo Schmidt - 2do cuatrimestre 2026"
PIE = "Trabajo Practico 2 - La fuga de la mazmorra"

# ==========================================================================
#  Estilos (los mismos del TP 1)
# ==========================================================================
ss = getSampleStyleSheet()
NEGRO = colors.HexColor('#1a1a1a')

TITULO = ParagraphStyle('TituloTP', parent=ss['Title'], fontName='Helvetica-Bold',
                        fontSize=17, leading=21, spaceBefore=6, spaceAfter=4, textColor=NEGRO)
SUBTITULO = ParagraphStyle('Sub', parent=ss['Normal'], fontName='Helvetica-Bold',
                           fontSize=10, leading=13, alignment=1, spaceAfter=14,
                           textColor=colors.HexColor('#555555'))
H1 = ParagraphStyle('H1', parent=ss['Heading1'], fontName='Helvetica-Bold',
                    fontSize=13, leading=16, spaceBefore=14, spaceAfter=6, textColor=NEGRO)
H2 = ParagraphStyle('H2', parent=ss['Heading2'], fontName='Helvetica-Bold',
                    fontSize=10.5, leading=13, spaceBefore=10, spaceAfter=4,
                    textColor=colors.HexColor('#333333'))
P = ParagraphStyle('P', parent=ss['BodyText'], fontName='Helvetica',
                   fontSize=9.5, leading=13.5, spaceAfter=6, alignment=TA_JUSTIFY)
BULLET = ParagraphStyle('Bullet', parent=P, leftIndent=14, bulletIndent=4,
                        spaceAfter=2, alignment=0,
                        bulletFontName='ZapfDingbats', bulletFontSize=5.5)
NUM = ParagraphStyle('Num', parent=P, leftIndent=16, bulletIndent=2, spaceAfter=3, alignment=0)
SUBNUM = ParagraphStyle('SubNum', parent=P, leftIndent=32, bulletIndent=18, spaceAfter=3, alignment=0)
CODE = ParagraphStyle('Code', parent=ss['Code'], fontName='Courier', fontSize=7.4,
                      leading=9.4, leftIndent=8, spaceBefore=3, spaceAfter=8,
                      backColor=colors.HexColor('#f4f4f4'),
                      borderColor=colors.HexColor('#d8d8d8'), borderWidth=0.5, borderPadding=5)
NOTA = ParagraphStyle('Nota', parent=P, fontSize=9, leading=12.5, leftIndent=10,
                      rightIndent=6, spaceBefore=4, spaceAfter=8,
                      backColor=colors.HexColor('#fbf7e8'),
                      borderColor=colors.HexColor('#e0d7b0'), borderWidth=0.5,
                      borderPadding=6, alignment=0)
EPIGRAFE = ParagraphStyle('Epi', parent=P, fontSize=8, leading=10.5, alignment=1,
                          textColor=colors.HexColor('#555555'), spaceBefore=2, spaceAfter=10,
                          fontName='Helvetica-Oblique')


def p(txt, estilo=P):
    return Paragraph(txt, estilo)


def vinetas(items, estilo=BULLET):
    # Con ZapfDingbats, U+25CF se mapea al circulo relleno. No usar el bullet
    # comun: las fuentes base-14 no lo tienen y sale invisible.
    return [Paragraph(t, estilo, bulletText='\u25cf') for t in items]


def numerados(items, estilo=NUM, desde=1):
    return [Paragraph(t, estilo, bulletText='%d)' % (i + desde)) for i, t in enumerate(items)]


def codigo(txt):
    return Preformatted(txt.strip('\n'), CODE)


def tabla(datos, anchos, tam=8.5):
    t = Table(datos, colWidths=anchos, hAlign='LEFT', repeatRows=1)
    t.setStyle(TableStyle([
        ('FONT', (0, 0), (-1, 0), 'Helvetica-Bold', tam),
        ('FONT', (0, 1), (-1, -1), 'Helvetica', tam),
        ('TEXTCOLOR', (0, 0), (-1, 0), colors.white),
        ('BACKGROUND', (0, 0), (-1, 0), colors.HexColor('#4a4a4a')),
        ('ROWBACKGROUNDS', (0, 1), (-1, -1), [colors.white, colors.HexColor('#f4f4f4')]),
        ('GRID', (0, 0), (-1, -1), 0.4, colors.HexColor('#bbbbbb')),
        ('VALIGN', (0, 0), (-1, -1), 'TOP'),
        ('LEFTPADDING', (0, 0), (-1, -1), 5), ('RIGHTPADDING', (0, 0), (-1, -1), 5),
        ('TOPPADDING', (0, 0), (-1, -1), 3.5), ('BOTTOMPADDING', (0, 0), (-1, -1), 3.5),
    ]))
    return t


def cp(txt, tam=8.5):
    """Celda de tabla con ajuste de linea."""
    return Paragraph(txt, ParagraphStyle('c', parent=P, fontSize=tam, leading=tam + 2.5,
                                         alignment=0, spaceAfter=0))


def decorar(canvas, doc):
    canvas.saveState()
    an, al = A4
    canvas.setFont('Helvetica-Bold', 8.5)
    canvas.setFillColor(colors.HexColor('#333333'))
    canvas.drawString(20 * mm, al - 13 * mm, ENCABEZADO_1)
    canvas.setFont('Helvetica', 8.5)
    canvas.setFillColor(colors.HexColor('#666666'))
    canvas.drawString(20 * mm, al - 17 * mm, ENCABEZADO_2)
    canvas.setStrokeColor(colors.HexColor('#cccccc'))
    canvas.setLineWidth(0.5)
    canvas.line(20 * mm, al - 19.5 * mm, an - 20 * mm, al - 19.5 * mm)
    canvas.setFont('Helvetica', 8)
    canvas.setFillColor(colors.HexColor('#888888'))
    canvas.drawString(20 * mm, 12 * mm, PIE)
    canvas.drawRightString(an - 20 * mm, 12 * mm, "Pagina %d" % doc.page)
    canvas.restoreState()


# ==========================================================================
#  Figuras vectoriales
# ==========================================================================
NIVEL = [
    "##########",
    "#H..~~..K#",
    "#.##.#.#.#",
    "#.#M..T..#",
    "#.#.###.##",
    "#...#..P.S",
    "##########",
]

COLOR_TILE = {
    '#': colors.HexColor('#4a4a4a'), '.': colors.HexColor('#f3ead7'), '~': colors.HexColor('#9ed0f0'),
    'T': colors.HexColor('#f2c14e'), 'K': colors.HexColor('#fff176'), 'P': colors.HexColor('#a1887f'),
    'S': colors.HexColor('#66bb6a'), 'H': colors.HexColor('#f3ead7'), 'M': colors.HexColor('#f3ead7'),
}
COLOR_ENTIDAD = {'H': colors.HexColor('#2f6fd1'), 'M': colors.HexColor('#c62828')}
GRIS = colors.HexColor('#9a9a9a')


def texto(g, x, y, txt, tam=8, color=NEGRO, fuente='Helvetica', anclaje='start'):
    s = String(x, y, txt, fontName=fuente, fontSize=tam, fillColor=color)
    s.textAnchor = anclaje
    g.add(s)


def flecha(g, x1, y1, x2, y2, color=NEGRO, grosor=1.0, doble=False):
    g.add(Line(x1, y1, x2, y2, strokeColor=color, strokeWidth=grosor))
    for (ax, ay, bx, by) in ([(x1, y1, x2, y2)] + ([(x2, y2, x1, y1)] if doble else [])):
        ang = math.atan2(by - ay, bx - ax)
        largo, ancho = 6.0, 3.0
        px, py = bx - largo * math.cos(ang), by - largo * math.sin(ang)
        g.add(Polygon([bx, by, px - ancho * math.sin(ang), py + ancho * math.cos(ang),
                       px + ancho * math.sin(ang), py - ancho * math.cos(ang)],
                      fillColor=color, strokeColor=color))


def caja(g, x, y, w, h, titulo, lineas=(), color=NEGRO, relleno=colors.white, tam=7.5):
    g.add(Rect(x, y, w, h, strokeColor=color, fillColor=relleno, strokeWidth=1.2))
    texto(g, x + w / 2, y + h - 11, titulo, tam=tam + 0.5, fuente='Helvetica-Bold',
          color=color, anclaje='middle')
    if lineas:
        g.add(Line(x, y + h - 15, x + w, y + h - 15, strokeColor=color, strokeWidth=0.5))
    yy = y + h - 26
    for l in lineas:
        texto(g, x + w / 2, yy, l, tam=tam, fuente='Courier', anclaje='middle')
        yy -= 10


def figura_nivel(grilla=NIVEL, celda=24, con_grafo=False):
    filas, cols = len(grilla), len(grilla[0])
    izq, arriba = 16, 14
    W, H = izq + cols * celda + 4, arriba + filas * celda + 4
    d = Drawing(W, H)
    g = Group()
    for c in range(cols):
        texto(g, izq + c * celda + celda / 2, H - 10, str(c + 1), tam=7, anclaje='middle', color=GRIS)
    centros = {}
    for f in range(filas):
        y = H - arriba - (f + 1) * celda
        texto(g, izq - 4, y + celda / 2 - 2.5, str(f + 1), tam=7, anclaje='end', color=GRIS)
        for c in range(cols):
            ch = grilla[f][c]
            x = izq + c * celda
            g.add(Rect(x, y, celda, celda, fillColor=COLOR_TILE[ch],
                       strokeColor=colors.HexColor('#cfcfcf'), strokeWidth=0.4))
            cx, cy = x + celda / 2, y + celda / 2
            centros[(f, c)] = (cx, cy)
            if ch in COLOR_ENTIDAD:
                g.add(Circle(cx, cy, celda * 0.34, fillColor=COLOR_ENTIDAD[ch], strokeColor=colors.white))
                texto(g, cx, cy - 3, ch, tam=8, fuente='Helvetica-Bold', color=colors.white, anclaje='middle')
            elif ch in 'TKPS':
                texto(g, cx, cy - 3, ch, tam=8, fuente='Helvetica-Bold', anclaje='middle')
    if con_grafo:
        azul = colors.HexColor('#1f5fa8')
        for (f, c), (cx, cy) in centros.items():
            if grilla[f][c] == '#':
                continue
            for df, dc in ((0, 1), (1, 0)):
                f2, c2 = f + df, c + dc
                if (f2, c2) in centros and grilla[f2][c2] != '#':
                    x2, y2 = centros[(f2, c2)]
                    al_agua = grilla[f][c] == '~' or grilla[f2][c2] == '~'
                    g.add(Line(cx, cy, x2, y2, strokeColor=azul if al_agua else colors.HexColor('#555555'),
                               strokeWidth=2.2 if al_agua else 1.0))
        for (f, c), (cx, cy) in centros.items():
            if grilla[f][c] != '#':
                g.add(Circle(cx, cy, 3.2, fillColor=colors.HexColor('#333333'), strokeColor=colors.white,
                             strokeWidth=0.6))
    d.add(g)
    return d


def figura_leyenda():
    items = [('#', 'pared'), ('.', 'piso (1)'), ('~', 'agua (3)'), ('H', 'heroe'), ('M', 'monstruo'),
             ('T', 'cofre'), ('K', 'llave'), ('P', 'puerta'), ('S', 'salida')]
    d = Drawing(480, 22)
    g = Group()
    x = 2
    for ch, nombre in items:
        g.add(Rect(x, 5, 12, 12, fillColor=COLOR_TILE[ch], strokeColor=colors.HexColor('#bbbbbb'), strokeWidth=0.4))
        if ch in COLOR_ENTIDAD:
            g.add(Circle(x + 6, 11, 4, fillColor=COLOR_ENTIDAD[ch], strokeColor=colors.white))
        texto(g, x + 16, 8, nombre, tam=7.5)
        x += 16 + 6.5 * len(nombre) + 14
    d.add(g)
    return d


def _mini_plano(g, x, y, w, h, color, salida=False, inicio=False):
    g.add(Rect(x, y, w, h, strokeColor=color, fillColor=colors.HexColor('#faf6ee'), strokeWidth=1.1))
    paso = 7
    for i in range(1, int(w / paso)):
        g.add(Line(x + i * paso, y, x + i * paso, y + h, strokeColor=colors.HexColor('#e0d8c8'), strokeWidth=0.4))
    for j in range(1, int(h / paso)):
        g.add(Line(x, y + j * paso, x + w, y + j * paso, strokeColor=colors.HexColor('#e0d8c8'), strokeWidth=0.4))
    if inicio:
        g.add(Circle(x + 7, y + h - 7, 3.5, fillColor=colors.HexColor('#2f6fd1'), strokeColor=colors.white))
    if salida:
        g.add(Rect(x + w - 11, y + 4, 8, 8, fillColor=colors.HexColor('#66bb6a'), strokeColor=colors.white))


def _tablero_de_tres_pisos(g, x, y, w, color, etiqueta, inicio=False, salida=False):
    """Tres planos apilados con escaleras entre ellos. Devuelve el centro (cx, cy)."""
    alto, sep = 20, 9
    for i in range(3):
        yy = y + i * (alto + sep)
        _mini_plano(g, x, yy, w, alto, color, inicio=(inicio and i == 0), salida=(salida and i == 2))
        if i < 2:
            flecha(g, x + w / 2, yy + alto + 1, x + w / 2, yy + alto + sep - 1, color=color, doble=True)
    texto(g, x + w / 2, y - 9, etiqueta, tam=6.5, anclaje='middle', color=color, fuente='Helvetica-Bold')
    return (x + w / 2, y + (3 * alto + 2 * sep) / 2)


def figura_dificultades():
    d = Drawing(480, 160)
    g = Group()
    verde, azul, rojo = colors.HexColor('#178c17'), colors.HexColor('#1f5fa8'), colors.HexColor('#c62828')
    # FACIL: un plano grande
    _mini_plano(g, 8, 40, 120, 88, verde, salida=True, inicio=True)
    texto(g, 68, 132, "1 tablero, 1 piso, 25 x 40", tam=7, anclaje='middle', color=verde, fuente='Helvetica-Bold')
    texto(g, 68, 142, "12 cofres, 4 monstruos", tam=7, anclaje='middle', color=GRIS)
    texto(g, 68, 10, "FACIL", tam=9, fuente='Helvetica-Bold', anclaje='middle', color=verde)
    # MEDIO: un tablero de tres pisos
    _tablero_de_tres_pisos(g, 165, 36, 80, azul, "3 pisos, escaleras", inicio=True, salida=True)
    texto(g, 205, 132, "1 tablero, 3 pisos, 15 x 20", tam=7, anclaje='middle', color=azul, fuente='Helvetica-Bold')
    texto(g, 205, 142, "6 cofres, 6 monstruos", tam=7, anclaje='middle', color=GRIS)
    texto(g, 205, 10, "MEDIO", tam=9, fuente='Helvetica-Bold', anclaje='middle', color=azul)
    # DIFICIL: tres tableros de tres pisos, en red con pasillos
    c1 = _tablero_de_tres_pisos(g, 290, 36, 44, rojo, "tablero 1", inicio=True)
    c2 = _tablero_de_tres_pisos(g, 362, 36, 44, rojo, "tablero 2")
    c3 = _tablero_de_tres_pisos(g, 434, 36, 44, rojo, "tablero 3", salida=True)
    flecha(g, 335, c1[1], 361, c2[1], color=rojo, doble=True)
    flecha(g, 407, c2[1], 433, c3[1], color=rojo, doble=True)
    # pasillo que cierra el ciclo 1 - 3, por abajo
    g.add(Line(312, 34, 312, 26, strokeColor=rojo, strokeWidth=1))
    g.add(Line(312, 26, 456, 26, strokeColor=rojo, strokeWidth=1))
    flecha(g, 456, 26, 456, 34, color=rojo)
    texto(g, 384, 18, "pasillos entre tableros (con ciclo)", tam=6, anclaje='middle', color=rojo)
    texto(g, 384, 132, "3 tableros en red, 3 pisos cada uno: 9 planos", tam=7, anclaje='middle', color=rojo,
          fuente='Helvetica-Bold')
    texto(g, 384, 142, "3 cofres, 10 monstruos", tam=7, anclaje='middle', color=GRIS)
    texto(g, 384, 6, "DIFICIL", tam=9, fuente='Helvetica-Bold', anclaje='middle', color=rojo)
    d.add(g)
    return d


def figura_arquitectura():
    d = Drawing(480, 190)
    g = Group()
    azul, verde, gris = colors.HexColor('#1f5fa8'), colors.HexColor('#178c17'), colors.HexColor('#666666')
    caja(g, 165, 70, 150, 100, "MODELO", ["Mazmorra  Mundo  Plano", "Heroe  Monstruo*5", "Cofre*10  Agenda",
                                          "Validador  Grafo", "Lista Pila Cola Heap", "Hash ABB"])
    texto(g, 240, 58, "no importa ImageIO, JuegoVisual ni JSON", tam=7, fuente='Helvetica-Oblique',
          anclaje='middle', color=gris)
    caja(g, 10, 120, 110, 40, "<<interface>> Vista", [], color=azul)
    caja(g, 10, 62, 50, 34, "VistaBmp", ["obligatoria"], color=azul, tam=6.5)
    caja(g, 70, 62, 50, 34, "VistaGrafica", ["opcional"], color=azul, tam=6.2)
    flecha(g, 35, 96, 55, 120, color=azul)
    flecha(g, 95, 96, 75, 120, color=azul)
    flecha(g, 165, 130, 122, 140, color=azul)
    texto(g, 143, 145, "usa", tam=7, anclaje='middle', color=azul)
    caja(g, 360, 120, 110, 40, "<<interface>>", ["GeneradorDeLaberinto"], color=verde, tam=6.8)
    caja(g, 360, 62, 50, 34, "Generador", ["propio"], color=verde, tam=6.5)
    caja(g, 420, 62, 50, 34, "Generador", ["de otro grupo"], color=verde, tam=6.5)
    flecha(g, 385, 96, 405, 120, color=verde)
    flecha(g, 445, 96, 425, 120, color=verde)
    flecha(g, 315, 130, 358, 140, color=verde)
    texto(g, 337, 145, "usa", tam=7, anclaje='middle', color=verde)
    caja(g, 165, 8, 150, 36, "PERSISTENCIA (JSON)", ["configuracion partidas ranking"], color=gris, tam=6.5)
    flecha(g, 240, 70, 240, 46, color=gris)
    d.add(g)
    return d


def figura_intercambio():
    d = Drawing(480, 140)
    g = Group()
    a, b, cat = colors.HexColor('#1f5fa8'), colors.HexColor('#c62828'), colors.HexColor('#178c17')
    caja(g, 10, 60, 130, 70, "GRUPO A", ["juego A", "GeneradorA.java"], color=a)
    caja(g, 340, 60, 130, 70, "GRUPO B", ["juego B", "GeneradorB.java"], color=b)
    flecha(g, 140, 108, 340, 82, color=a, grosor=1.6)
    flecha(g, 340, 108, 140, 82, color=b, grosor=1.6)
    texto(g, 240, 120, "semana 7: el generador de A produce los planos del juego B, y el de B los del juego A",
          tam=7.2, anclaje='middle', fuente='Helvetica-Oblique')
    texto(g, 240, 68, "sin cambiar una linea de codigo", tam=7.5, anclaje='middle', fuente='Helvetica-Oblique')
    caja(g, 150, 6, 180, 40, "VALIDADOR", ["bordes  conexo  bifurcaciones  proporcion"], color=cat, tam=6.5)
    flecha(g, 75, 60, 200, 46, color=a)
    flecha(g, 405, 60, 280, 46, color=b)
    texto(g, 240, 52, "todo plano ajeno pasa por el validador antes de jugarse", tam=6.5, anclaje='middle',
          color=cat)
    d.add(g)
    return d


def figura_hitos():
    d = Drawing(480, 118)
    g = Group()
    x0, x1, y = 30, 450, 60
    g.add(Line(x0, y, x1, y, strokeColor=NEGRO, strokeWidth=1.4))
    semanas = list(range(8, 17))
    paso = (x1 - x0) / (len(semanas) - 1)
    hitos = {10: ("Hito 1", "Facil con BMP"), 12: ("Hito 2", "Medio, tipos, JSON"),
             14: ("Hito 3", "Dificil, grafo, heap"), 15: ("Intercambio", "generador ajeno"),
             16: ("Entrega", "defensa")}
    for i, s in enumerate(semanas):
        x = x0 + i * paso
        g.add(Line(x, y - 4, x, y + 4, strokeColor=NEGRO, strokeWidth=1))
        texto(g, x, y - 16, "sem %d" % s, tam=6.5, anclaje='middle', color=GRIS)
        if s in hitos:
            titulo, det = hitos[s]
            arriba = (s in (10, 14, 16))
            yy = y + 18 if arriba else y - 44
            g.add(Circle(x, y, 4.5, fillColor=colors.HexColor('#c62828'), strokeColor=colors.white))
            g.add(Line(x, y + (5 if arriba else -5), x, yy + (0 if arriba else 22),
                       strokeColor=colors.HexColor('#c62828'), strokeWidth=0.8))
            g.add(Rect(x - 40, yy, 80, 24, fillColor=colors.HexColor('#fdecea'),
                       strokeColor=colors.HexColor('#c62828'), strokeWidth=0.8))
            texto(g, x, yy + 13, titulo, tam=7.5, fuente='Helvetica-Bold', anclaje='middle')
            texto(g, x, yy + 4, det, tam=6.5, anclaje='middle')
    texto(g, x0, y + 46, "semana 8: se publica el TP", tam=7, fuente='Helvetica-Oblique', color=GRIS)
    d.add(g)
    return d


# ==========================================================================
#  Contenido
# ==========================================================================
s = []
s.append(p("Trabajo Práctico 2: La fuga de la mazmorra", TITULO))
s.append(p("Trabajo práctico grupal · 6 integrantes · 8 semanas", SUBTITULO))

# ---- Objetivo
s.append(p("Objetivo", H1))
s.append(p("El objetivo de este trabajo práctico es:"))
s += vinetas([
    "Construir un <b>juego completo</b> aplicando todo lo visto en la materia.",
    "Usar <b>todas las estructuras de datos</b> del curso donde cada una hace falta de verdad: pila, "
    "cola, lista, cola de prioridad, tabla hash, árbol binario de búsqueda y grafo.",
    "Programar <b>100% orientado a objetos</b>: 5 tipos de monstruo y 10 tipos de cofre por polimorfismo.",
    "Generar los tableros <b>al azar</b> con un <b>generador de laberintos</b> configurado desde JSON, "
    "diseñado contra una interfaz para que el generador de <b>otro grupo</b> encaje sin cambiar una línea.",
    "Leer y escribir <b>archivos</b>: configuración y partidas en JSON, ranking, y la salida gráfica en <b>BMP</b>.",
    "Trabajar <b>en equipo</b> durante dos meses con Git, roles e hitos.",
])

# ---- El juego
s.append(p("El juego", H1))
s.append(p(
    "Una <b>mazmorra</b> es un conjunto de <b>planos</b>: grillas de filas × columnas donde cada celda es "
    "piso, pared, agua, lava, cofre, escalera, pasillo o salida. Adentro se mueven el <b>héroe</b> y los "
    "<b>monstruos</b>. El héroe avanza <b>de a pasos</b>: cada acción suya es un paso, y después de cada "
    "paso se mueven los monstruos. Al iniciar, el jugador elige una <b>dificultad</b>, y los planos "
    "<b>se generan al azar</b> con el generador de laberintos, según un archivo de configuración."))
s.append(KeepTogether([
    p("Un plano de ejemplo", H2),
    figura_nivel(),
    figura_leyenda(),
    p("Figura 1. Un plano chico de 7 filas por 10 columnas, con el costo de energía de cada terreno entre "
      "paréntesis. Los planos del juego son más grandes y los produce el generador.", EPIGRAFE),
]))

s.append(p("Las reglas", H2))
s += numerados([
    "El héroe empieza con la <b>energía inicial</b> de la configuración (100 por defecto) y el inventario vacío.",
    "En cada paso hace <b>una</b> acción: <b>moverse</b> una celda (arriba, abajo, izquierda o derecha), "
    "<b>usar</b> un objeto del inventario, o <b>esperar</b>.",
    "Cada movimiento cuesta energía según el terreno al que entra: <b>piso 1, agua 3, lava 8</b>, "
    "configurables. Una pared no se puede pisar: no es una acción válida y no cuenta como paso.",
    "Pisar un <b>cofre</b> lo abre: su contenido va al inventario o actúa de inmediato, según el tipo. "
    "Un cofre abierto suma 100 puntos y desaparece.",
    "Pisar una <b>escalera</b> lleva al héroe al piso de arriba o de abajo del mismo tablero. Pisar un "
    "<b>pasillo</b> lo lleva a otro tablero. En los dos casos aparece en la celda de destino de la "
    "conexión. Los monstruos <b>nunca</b> cambian de plano.",
    "Los <b>monstruos</b> tienen comportamientos distintos según su tipo. Los dos básicos: los "
    "<b>errantes</b> se mueven al azar; los <b>cazadores</b> avanzan una celda hacia el héroe <b>por el "
    "camino más corto</b>, respetando el costo del terreno. Si un monstruo alcanza al héroe hace lo que su "
    "tipo indica; el básico descuenta 30 de energía y vuelve a su posición inicial.",
    "Hay una <b>agenda</b>: cosas que pasan en un paso futuro. Efectos de cofres que vencen, trampas que "
    "se rearman, monstruos que despiertan. Al empezar cada paso se ejecutan los eventos cuyo momento "
    "llegó, en orden.",
    "Los movimientos <b>no se deshacen</b>. Lo que se pisó, se pisó.",
    "El juego se <b>gana</b> al pisar la salida, que está en un solo plano. Se <b>pierde</b> si la energía "
    "llega a 0. Puntaje: <b>cofres abiertos × 100 + energía restante × 2 - pasos</b>.",
    "Una partida se puede <b>guardar</b> en cualquier momento y <b>retomar</b> después exactamente igual: "
    "todos los planos, el plano actual, héroe, inventario, efectos activos, monstruos y agenda.",
    "Al terminar, el puntaje entra al <b>ranking</b>, que se conserva entre ejecuciones y muestra el "
    "top 10 con la dificultad.",
])
s.append(p(
    "<b>Sobre el ritmo.</b> Con entrada por consola, el juego avanza naturalmente de a pasos. Un grupo "
    "que use la ventana gráfica puede, si quiere, mover a los monstruos <b>por tiempo</b> con un "
    "temporizador, y contar la agenda en milisegundos en vez de pasos. Las reglas son las mismas; sólo "
    "cambia quién marca el ritmo.", NOTA))

s.append(KeepTogether([
    p("Las tres dificultades", H2),
    figura_dificultades(),
    p("Figura 2. Fácil: un plano grande. Medio: un tablero de tres pisos unidos por escaleras. Difícil: "
      "tres tableros de tres pisos, unidos entre sí por pasillos que forman un ciclo. El punto azul es el "
      "inicio; el cuadrado verde, la salida.", EPIGRAFE),
]))
dif = [
    [cp(""), cp("<b>Fácil</b>"), cp("<b>Medio</b>"), cp("<b>Difícil</b>")],
    [cp("Tableros"), cp("1"), cp("1"), cp("<b>3, en red</b>")],
    [cp("Pisos por tablero"), cp("1"), cp("<b>3</b>"), cp("<b>3</b>")],
    [cp("Planos en total"), cp("1, grande (25 × 40)"), cp("3 (15 × 20)"), cp("9 (15 × 20)")],
    [cp("Conexiones"), cp("ninguna"), cp("<b>escaleras</b> entre pisos"), cp("escaleras entre pisos <b>y pasillos</b> entre tableros")],
    [cp("Cofres"), cp("muchos (12)"), cp("6"), cp("pocos (3)")],
    [cp("Monstruos"), cp("4"), cp("6"), cp("10")],
    [cp("La salida"), cp("en el único plano"), cp("en el piso 3"), cp("en cualquier plano")],
]
s.append(tabla(dif, [30 * mm, 40 * mm, 44 * mm, 52 * mm]))
s.append(Spacer(1, 6))
s.append(p(
    "Una escalera y un pasillo son lo mismo para el modelo: una <font face='Courier'>Conexion</font> de "
    "una celda de un plano a una celda de otro. Lo que cambia es la geometría. La escalera lleva a la "
    "<b>misma posición</b> del piso de arriba o de abajo. El pasillo está en el <b>borde</b> de un tablero "
    "y lleva al borde del tablero vecino. En Difícil, los tres tableros forman un <b>grafo</b> que puede "
    "tener ciclos, y cada tablero es a su vez una pila de tres pisos: nueve planos para recorrer, y una "
    "sola salida."))

# ---- Configuracion y generacion
s.append(p("Configuración y generación", H1))
s.append(p("El archivo de configuración", H2))
s.append(p("<font face='Courier'>configuracion.json</font> se lee al iniciar. Si falta, se rechaza con un "
           "mensaje claro: no hay valores escondidos en el código."))
s.append(codigo('''
{
  "energiaInicial": 100,
  "costos": { "piso": 1, "agua": 3, "lava": 8 },
  "semilla": null,
  "dificultades": {
    "FACIL":   { "tableros": 1, "pisos": 1, "filas": 25, "columnas": 40, "cofres": 12, "monstruos": 4  },
    "MEDIO":   { "tableros": 1, "pisos": 3, "filas": 15, "columnas": 20, "cofres": 6,  "monstruos": 6  },
    "DIFICIL": { "tableros": 3, "pisos": 3, "filas": 15, "columnas": 20, "cofres": 3,  "monstruos": 10 }
  }
}
'''))
s.append(p(
    "La <b>semilla</b> manda: con <font face='Courier'>null</font> cada partida es distinta; con un "
    "número, la misma semilla genera exactamente el mismo mundo. Así se puede reproducir un bug, "
    "escribir un test, y jugar todos el mismo nivel.", NOTA))

s.append(p("El generador de laberintos: la única pieza que se intercambia", H2))
s.append(p("Un método, texto de entrada, texto de salida. Ninguna clase compartida:"))
s.append(codigo('''
public interface GeneradorDeLaberinto {
    String generar(String parametrosJson);
}
'''))
s.append(p(
    "Recibe un JSON con dificultad, filas, columnas, semilla, cofres, monstruos y "
    "<font face='Courier'>conSalida</font>. Devuelve un JSON con el plano como cadenas de texto, una por "
    "fila, con <b>ocho símbolos fijos</b>: <font face='Courier'>#</font> pared, "
    "<font face='Courier'>.</font> piso, <font face='Courier'>~</font> agua, "
    "<font face='Courier'>^</font> lava, <font face='Courier'>C</font> cofre, "
    "<font face='Courier'>M</font> monstruo, <font face='Courier'>E</font> entrada, "
    "<font face='Courier'>S</font> salida."))
s.append(p(
    "El generador decide <b>dónde</b> va cada cosa; el juego que lo carga decide <b>qué tipo</b> de cofre "
    "o de monstruo es, sorteándolo de su propio catálogo. Por eso un generador ajeno no necesita conocer "
    "los tipos del grupo que lo usa. Las escaleras y los pasillos <b>no</b> los pone el generador: dependen "
    "de los planos vecinos, y los coloca el juego después."))
s.append(p(
    "Un plano es <b>válido</b> si cumple <b>once reglas</b>: borde de pared, conexo, con bifurcaciones, "
    "proporción de piso, cantidades exactas de cofres y monstruos, salida en la celda más lejana a la "
    "entrada, y otras. El juego valida todo plano antes de usarlo, propio o ajeno. La especificación "
    "completa, los dos formatos JSON con un ejemplo verificado, las once reglas y la lista de control del "
    "intercambio están en el <b>Anexo B</b>.", NOTA))
s.append(p("El algoritmo recomendado es el de <b>backtracking con pila</b>:"))
s += numerados([
    "Todas las celdas empiezan como pared. Se elige una celda de piso inicial y se apila.",
    "Mientras la pila no esté vacía: se mira la celda del tope; se eligen al azar sus vecinas a distancia 2 "
    "que todavía sean pared; si hay alguna, se abre el camino hasta ella (la celda de en medio pasa a piso) "
    "y se apila; si no hay ninguna, se desapila.",
    "Cuando la pila queda vacía el laberinto es conexo por construcción. Para las <b>bifurcaciones</b>, se "
    "abren después algunas paredes elegidas al azar entre dos celdas de piso: cada una crea un ciclo y un "
    "camino alternativo.",
    "Se decora: agua y lava, cofres y monstruos sobre piso libre, la entrada al azar, y la salida en la "
    "celda <b>más lejana</b> a la entrada por BFS.",
])
s.append(p(
    "La pila tiene que ser <b>la del grupo</b>, no la recursión de Java: en un plano de 25 × 40 la "
    "recursión llega a 500 niveles, y la pregunta 5 del cuestionario pide explicar por qué eso importa.",
    NOTA))

s.append(KeepTogether([
    p("El intercambio", H2),
    figura_intercambio(),
    p("Figura 3. En la semana 7 cada grupo enchufa el generador de otro grupo en su juego. Todo plano ajeno "
      "pasa por el validador antes de jugarse.", EPIGRAFE),
]))
s.append(p(
    "En la <b>semana 7</b>, cada grupo publica su clase <font face='Courier'>GeneradorGrupoNN.java</font>, "
    "que implementa la interfaz y <b>no depende de ninguna otra clase del grupo</b>, más tres JSON de "
    "salida de ejemplo, uno por dificultad. Recibe los de <b>otro grupo</b>, valida los JSON con su "
    "validador, reemplaza <b>una línea</b> en su juego (<font face='Courier'>new GeneradorGrupoNN()</font> "
    "donde estaba el propio) y juega una partida en cada dificultad. Si algo falla, el informe dice qué "
    "regla, y de quién es la falla: del generador, del validador o del contrato. La lista de control "
    "completa está en el Anexo B."))

s.append(p("Partida guardada, ranking y records", H2))
s.append(p(
    "<font face='Courier'>guardar()</font> escribe un JSON con <b>todo</b> el estado (regla 10) y "
    "<font face='Courier'>cargar()</font> lo reconstruye. <font face='Courier'>ranking.json</font> guarda "
    "nombre, puntaje, dificultad y fecha, y se carga al iniciar en el ABB. Todo lo que es un <b>valor</b> va "
    "en un <font face='Courier'>record</font>: <font face='Courier'>Posicion</font>, "
    "<font face='Courier'>Ubicacion</font> (tablero, piso, posición), <font face='Courier'>Conexion</font>, "
    "<font face='Courier'>EventoProgramado</font>, <font face='Courier'>Puntaje</font>, "
    "<font face='Courier'>EntradaDeRanking</font> y la configuración leída. Para JSON se puede usar "
    "<b>Gson</b> o <b>Jackson</b>: es la única librería externa permitida en el modelo. "
    "<font face='Courier'>java.util.Random</font> está permitido para el generador."))

# ---- Salida grafica
s.append(p("La salida gráfica: BMP obligatorio, ventana opcional", H1))
s.append(p("Lo obligatorio", H2))
s.append(p(
    "La acción del héroe se lee por <b>consola</b> (<font face='Courier'>w a s d</font> para moverse, "
    "<font face='Courier'>u</font> para usar, <font face='Courier'>e</font> para esperar, "
    "<font face='Courier'>g</font> para guardar, <font face='Courier'>q</font> para salir). Después de "
    "<b>cada paso</b>, el juego escribe el plano actual en un archivo <b>BMP</b>: "
    "<font face='Courier'>salida/paso-0001.bmp</font>, <font face='Courier'>paso-0002.bmp</font>, y así. "
    "Cada celda es un cuadrado de N píxeles con un color por símbolo; el héroe y los monstruos, un cuadrado "
    "o un círculo de otro color encima. Abajo o al costado, con rectángulos, la energía. Al terminar, los "
    "archivos son la <b>película de la partida</b>."))
s.append(p(
    "Se hace con <font face='Courier'>BufferedImage</font> e "
    "<font face='Courier'>ImageIO.write(imagen, \"bmp\", archivo)</font>, que son parte del JDK: no es una "
    "librería externa. El BMP no se comprime, y por eso la pregunta 6 del cuestionario pide calcular cuánto "
    "pesa."))
s.append(p("Lo opcional, que suma puntos", H2))
s.append(p(
    "La cátedra provee <font face='Courier'>JuegoVisual</font> (una ventana con el tablero, sprites y "
    "teclado) y <font face='Courier'>ReproductorDeSonido</font> (efectos y música en MP3), en "
    "<font face='Courier'>librerias/</font>. Están pensadas para reemplazar la vista BMP <b>en unas pocas "
    "líneas</b>: donde la vista obligatoria escribe un archivo, la gráfica llama a "
    "<font face='Courier'>setCelda</font> y <font face='Courier'>moverPersonaje</font>; donde lee un "
    "<font face='Courier'>char</font> de la consola, llama a <font face='Courier'>esperarTecla()</font>. "
    "Con <font face='Courier'>leerTecla()</font>, que no bloquea, los monstruos pueden moverse por tiempo."))
s.append(p(
    "Si el grupo quiere <b>subir el nivel</b>, es por acá, y vale hasta <b>10 puntos de bonus</b>. Pero "
    "<b>primero tiene que andar con BMP</b>. Para que el reemplazo sea de verdad de pocas líneas, hay una "
    "interfaz <font face='Courier'>Vista</font> en el diseño del grupo, con "
    "<font face='Courier'>VistaBmp</font> obligatoria y <font face='Courier'>VistaGrafica</font> opcional. La "
    "pregunta 8 del cuestionario pregunta qué hubo que tocar en el modelo para agregar la gráfica: la "
    "respuesta correcta es <i>nada</i>.", NOTA))
s.append(KeepTogether([
    figura_arquitectura(),
    p("Figura 4. El modelo en el centro, sin saber quién lo dibuja, quién genera los planos ni cómo se "
      "guarda. Las dos interfaces que lo rodean son las que hacen intercambiable cada pieza.", EPIGRAFE),
]))

# ---- Monstruos y cofres
s.append(p("Monstruos y cofres: el polimorfismo del grupo", H1))
s.append(p("Cinco tipos de monstruo", H2))
s.append(p(
    "<font face='Courier'>Monstruo</font> es abstracta. Cada tipo redefine <b>cómo decide moverse</b> y "
    "<b>qué pasa al alcanzar al héroe</b>. La cátedra define dos; el grupo agrega hasta llegar a "
    "<b>cinco</b>:"))
s.append(codigo('''
public abstract class Monstruo {
    protected abstract Posicion decidirMovimiento(Plano plano, Posicion heroe);
    protected abstract void alAlcanzarAlHeroe(Heroe heroe, Agenda agenda);
}
'''))
mons = [
    [cp("<b>Tipo</b>"), cp("<b>Se mueve</b>"), cp("<b>Al alcanzar al héroe</b>"), cp("<b>Lo define</b>")],
    [cp("ERRANTE"), cp("al azar"), cp("-30 de energía, vuelve a su posición inicial"), cp("cátedra")],
    [cp("CAZADOR"), cp("una celda por el camino más corto (Dijkstra)"), cp("ídem"), cp("cátedra")],
    [cp("..."), cp("..."), cp("..."), cp("el grupo, 3 más")],
]
s.append(tabla(mons, [28 * mm, 52 * mm, 58 * mm, 28 * mm]))
s.append(Spacer(1, 6))
s.append(p(
    "Ideas, no obligatorias: uno que <b>duerme</b> hasta que el héroe se acerca a distancia 3 y ahí caza "
    "(despierta por la agenda); uno que <b>custodia</b> un cofre y sólo se mueve en un radio; uno que "
    "<b>roba</b> un objeto del inventario en vez de quitar energía; uno que <b>atraviesa paredes</b> pero "
    "avanza cada dos pasos."))
s.append(p(
    "<b>Qué cuenta como tipo distinto</b>: distinta decisión de movimiento, o distinto efecto al alcanzar. "
    "Dos monstruos que sólo difieren en cuánta energía quitan son <b>el mismo tipo</b> con distinto "
    "número.", NOTA))

s.append(p("Diez tipos de cofre", H2))
s.append(p(
    "<font face='Courier'>Cofre</font> es abstracta con <font face='Courier'>abrir(Heroe heroe, Agenda "
    "agenda)</font>. La cátedra define dos; el grupo agrega hasta <b>diez</b>:"))
cof = [
    [cp("<b>Tipo</b>"), cp("<b>Qué hace</b>"), cp("<b>Lo define</b>")],
    [cp("COFRE_DE_PUNTOS"), cp("suma los 100 puntos, nada más"), cp("cátedra")],
    [cp("ZAPATOS_DE_AGUA"), cp("va al inventario; al usarlo, durante 20 pasos el agua cuesta como piso"), cp("cátedra")],
    [cp("..."), cp("..."), cp("el grupo, 8 más")],
]
s.append(tabla(cof, [38 * mm, 100 * mm, 28 * mm]))
s.append(Spacer(1, 6))
s.append(p("Condiciones sobre los diez:"))
s += vinetas([
    "Al menos <b>3</b> tienen <b>efecto temporal</b>: vencen en un paso futuro, programado en la agenda (heap).",
    "Al menos <b>2</b> cambian la relación del héroe con el <b>terreno</b> (agua, lava, paredes).",
    "Al menos <b>1</b> es una <b>trampa</b>: efecto negativo.",
    "Al menos <b>1</b> afecta a los <b>monstruos</b>: los duerme, los aleja, los revela.",
    "El resto, libre. Ideas: una brújula que muestra la dirección a la salida, una poción que devuelve "
    "energía, un mapa que marca el plano como visitado, una llave que abre un pasillo cerrado.",
])
s.append(p(
    "<b>Qué cuenta como tipo distinto</b>: distinto efecto. Dos pociones que curan distinta cantidad son "
    "<b>el mismo tipo</b>. Cofres y monstruos se ubican <b>al azar</b> en celdas de piso libres, y su tipo "
    "también se sortea entre los del catálogo.", NOTA))

# ---- Estructuras
s.append(p("Dónde va cada estructura", H1))
s.append(p("Esta tabla es <b>obligatoria</b>, con la implementación <b>propia de la materia</b>. "
           "<font face='Courier'>java.util</font> queda prohibido en el modelo."))
estructuras = [
    [cp("<b>Estructura</b>"), cp("<b>Uso obligatorio</b>"), cp("<b>Por qué esa y no otra</b>")],
    [cp("Pila"), cp("<b>Generador de laberinto</b> (backtracking)"), cp("Lo último que se abrió es lo primero que se retrocede")],
    [cp("Cola"), cp("<b>BFS</b>: salida más lejana, alcanzabilidad, validación de conexidad de un plano ajeno"),
     cp("El BFS visita por capas: la primera vez que llega a una celda es por el camino más corto")],
    [cp("Lista"), cp("Entidades de cada plano; log de la partida"), cp("Se recorren y modifican en cualquier posición")],
    [cp("Cola de prioridad (heap)"), cp("Agenda de eventos y <b>efectos temporales</b> de los cofres; Dijkstra"),
     cp("Siempre se necesita <i>el próximo que vence</i>, nunca la lista entera ordenada")],
    [cp("Tabla hash"), cp("Inventario (id -> cantidad); celdas visitadas por plano; <b>catálogo de tipos</b> de monstruo y cofre (nombre -> fábrica)"),
     cp("Búsqueda por clave en O(1), decenas de veces por paso")],
    [cp("ABB o AVL"), cp("Ranking de puntajes, recorrido en orden para el top 10"), cp("Mantiene el orden mientras se inserta")],
    [cp("Grafo con pesos"), cp("Cada plano: celda transitable = vértice, movimiento = arista con costo. Y el <b>mundo</b>: plano = vértice, conexión = arista"),
     cp("Dijkstra de los cazadores (dentro de un plano); alcanzabilidad de la salida a través de conexiones")],
]
s.append(tabla(estructuras, [30 * mm, 68 * mm, 68 * mm]))
s.append(Spacer(1, 8))
s.append(KeepTogether([
    p("El plano visto como grafo", H2),
    figura_nivel(con_grafo=True),
    p("Figura 5. El plano de la figura 1 con sus vértices y aristas. Las aristas gruesas azules entran o "
      "salen del agua: cuestan 3; las demás, 1. El camino más corto de un cazador es el de menor costo "
      "total, no el de menos pasos.", EPIGRAFE),
]))
s.append(p("Regla de oro: <b>el modelo no sabe cómo se dibuja ni cómo se guarda</b>. Ninguna clase del modelo "
           "importa <font face='Courier'>ImageIO</font>, <font face='Courier'>JuegoVisual</font>, Swing ni "
           "JSON.", NOTA))

# ---- Restricciones
s.append(p("Restricciones", H1))
s += vinetas([
    "<b>Prohibido java.util en el modelo</b>: ArrayList, HashMap, PriorityQueue, Stack, LinkedList, TreeMap "
    "y la API de Streams. Se usan las estructuras propias. Excepciones: java.util.Random para el generador, "
    "y java.util en las vistas y en el código de JSON, que son capas de borde.",
    "Sin hilos, salvo el temporizador de Swing si el grupo eligió tiempo real en la vista gráfica opcional.",
    "Ninguna clase del modelo importa ImageIO, JuegoVisual, Swing, Gson ni Jackson.",
    "Toda estructura propia tiene su <b>test</b> de unidad, aparte de los tests del juego.",
])

# ---- Equipo
s.append(p("Organización del equipo", H1))
s.append(p(
    "Seis personas y ocho semanas se desperdician sin roles. Cada grupo asigna <b>un responsable</b> por "
    "área. Responsable no es <i>el que lo hace solo</i>: es el que responde por que esté hecho y probado."))
roles = [
    ["Área", "Responsable de"],
    ["Modelo", "Mazmorra, Heroe, reglas, agenda, conexiones entre planos"],
    ["Estructuras", "Lista, pila, cola, heap, hash, ABB, con sus tests"],
    ["Generación y grafo", "Generador de laberinto, validador, BFS, alcanzabilidad, Dijkstra"],
    ["Monstruos y cofres", "Las jerarquías, los 5 y los 10 tipos, el catálogo, sus tests"],
    ["Persistencia y vista", "Configuración, guardado, ranking, VistaBmp, y la gráfica si la hacen"],
    ["Integración", "main, tests de punta a punta, Git, informe"],
]
s.append(tabla(roles, [36 * mm, 130 * mm]))
s.append(Spacer(1, 8))
s.append(KeepTogether([
    p("Los hitos", H2),
    p("Cada dos semanas hay una <b>demo de 10 minutos</b> frente a la cátedra. No es optativa y no se "
      "recupera. Siguen el orden en que la materia da cada estructura."),
    figura_hitos(),
    p("Figura 6. Las semanas son las del cuatrimestre. El TP se publica en la semana 8 y se defiende en la 16.",
      EPIGRAFE),
]))
hitos = [
    [cp("<b>Sem. TP</b>"), cp("<b>Sem. materia</b>"), cp("<b>Hito</b>"), cp("<b>Se tiene que poder ver</b>")],
    [cp("2"), cp("10"), cp("Fácil con BMP"),
     cp("configuracion.json leído, laberinto generado <b>con pila</b> y semilla, validador de planos, el héroe se mueve por consola, gana y pierde, errantes, cofre de puntos, y <b>un BMP por paso</b>.")],
    [cp("4"), cp("12"), cp("Medio, persistencia y tipos"),
     cp("Escaleras entre 3 pisos, guardar y retomar, ranking en ABB, inventario en hash, <b>los 5 monstruos y los 10 cofres</b> (los efectos temporales pueden faltar).")],
    [cp("6"), cp("14"), cp("Difícil, grafo y heap"),
     cp("3 tableros en red de 3 pisos, salida por BFS más lejana, alcanzabilidad a través de conexiones, cazadores con Dijkstra, agenda y efectos temporales en heap.")],
    [cp("7"), cp("15"), cp("Intercambio"), cp("El generador de otro grupo generando los planos de su juego.")],
    [cp("8"), cp("16"), cp("Entrega y defensa"), cp("Informe, ventana gráfica y sonido si los hicieron, defensa oral.")],
]
s.append(tabla(hitos, [16 * mm, 20 * mm, 36 * mm, 94 * mm]))

# ---- Tests
s.append(p("Tests", H1))
s.append(p("Se entregan como mínimo:"))
s += vinetas([
    "<b>Un test por estructura</b>, sola, con Integer o String, sin nada del juego.",
    "Tests de reglas: cada regla numerada de la sección <i>El juego</i> tiene al menos uno.",
    "<b>Generador y validador</b>: la misma semilla genera el mismo plano; todo plano generado cumple las "
    "once reglas del Anexo B; un plano hecho a mano que viola cada regla es rechazado por el validador, uno por uno.",
    "<b>Mundo</b>: la salida es alcanzable desde el inicio en las tres dificultades, atravesando escaleras y pasillos.",
    "<b>Guardar y retomar</b>: los dos estados son iguales, plano actual incluido.",
    "<b>Cada tipo</b> de monstruo y de cofre tiene un test de su comportamiento.",
    "<b>BMP</b>: después de un paso existe el archivo, y tiene el tamaño en píxeles que corresponde al plano.",
])
s.append(p("Se corren con <font face='Courier'>./gradlew test</font> y tienen que pasar en verde en la entrega."))

# ---- Cuestionario
s.append(p("Cuestionario", H1))
s.append(p("Responder el siguiente cuestionario en el informe:"))
s += numerados([
    "¿Por qué la agenda es una cola de prioridad y no una lista ordenada? Comparen insertar y sacar el "
    "próximo en cada caso.",
    "¿Qué diferencia de costo hay entre buscar en el inventario si es lista y si es tabla hash? ¿Cuántas "
    "veces por paso se consulta?",
    "¿Por qué el ranking es un ABB y no un arreglo ordenado? ¿Qué pasa si los puntajes llegan en orden "
    "creciente, y cómo lo resuelve el AVL?",
    "Dijkstra en un plano de 25 × 40: ¿cuántos vértices y aristas tiene el grafo? ¿Cuál es el costo de un "
    "camino mínimo?",
    "El generador usa <b>su</b> pila y no la recursión de Java. ¿Qué profundidad alcanza la recursión en un "
    "plano de 25 × 40, y qué pasaría con la pila de la JVM? ¿Por qué el laberinto resultante es conexo, y "
    "por qué hay que abrir paredes después para que tenga bifurcaciones?",
    "Un BMP de un plano de 25 × 40 con celdas de 16 píxeles: ¿cuántos bytes ocupa? ¿Y la película de una "
    "partida de 300 pasos? Comparen con lo que ocuparía en PNG, y expliquen la diferencia con lo que vimos "
    "entre WAV y MP3.",
    "El generador de otro grupo corrió en su juego. ¿Pasó el validador a la primera? Si no, ¿qué regla "
    "falló y por qué la interfaz no la pudo impedir?",
    "Si hicieron la ventana gráfica: ¿qué tuvieron que cambiar en el modelo? Si la respuesta no es "
    "<i>nada</i>, ¿qué estaba mal acoplado?",
])

# ---- Normas de entrega
s.append(p("Normas de entrega", H1))
s.append(p("Trabajo práctico <b>grupal</b>: 6 integrantes."))
s.append(p("Reglas generales: respetar el <b>Apéndice A</b>."))
s += vinetas([
    "<b>Repositorio Git</b> por grupo, con la cátedra como colaboradora desde la semana 1. El código se "
    "entrega en el repositorio: se evalúan los commits, y <b>cada integrante</b> tiene que tener commits "
    "propios en su área a lo largo de las 8 semanas, no un volcado final.",
    "<b>Informe</b> en PDF, dentro del repositorio y subido al campus: decisiones de diseño, diagrama de "
    "clases con las dos jerarquías, respuestas al cuestionario, manual de usuario, manual del programador, "
    "y una sección por integrante con lo que hizo. Nombre: <font face='Courier'>GrupoNN-TP2.pdf</font>.",
    "<b>La clase del generador</b>, <font face='Courier'>configuracion.json</font> y una carpeta "
    "<font face='Courier'>salida/</font> con los BMP de una partida de ejemplo, en el repositorio.",
    "<b>Defensa oral</b> en la semana de defensas grupales: cada integrante explica su área y responde "
    "sobre cualquier otra.",
])
s.append(p("La fecha de entrega vence el día <b>&lt;&lt; COMPLETAR: DD/MM/26 &gt;&gt;</b> a las 23.59 hs. "
           "La defensa es en la semana 16 del cuatrimestre.", NOTA))
s.append(p(
    "Se evaluará: funcionalidad, eficiencia, algoritmos utilizados, buenas prácticas de programación, "
    "modularización, documentación, gestión de memoria y estructuras de datos."))
s.append(p("Rúbrica", H2))
rubrica = [
    ["Criterio", "Peso"],
    ["Las 7 estructuras usadas donde corresponde, con sus tests", "15"],
    ["Reglas del juego completas y verificadas por tests", "10"],
    ["Generador con pila y semilla, validador, configuración, tres dificultades", "20"],
    ["5 monstruos y 10 cofres con polimorfismo real", "15"],
    ["Grafo: alcanzabilidad a través de conexiones, salida por BFS, Dijkstra", "10"],
    ["Persistencia: guardado exacto, ranking, BMP por paso", "10"],
    ["Intercambio: el generador ajeno corre sin cambios, y el propio pasa el validador ajeno", "10"],
    ["Hitos cumplidos en fecha", "5"],
    ["Informe, cuestionario y defensa", "5"],
    ["Ventana gráfica con JuegoVisual y sonido, sin tocar el modelo", "bonus +10"],
]
s.append(tabla(rubrica, [116 * mm, 50 * mm]))
s.append(Spacer(1, 6))
s.append(p("La nota es <b>individual</b>: parte de la grupal y se ajusta con los commits, la defensa y la "
           "sección personal del informe."))


# ---- Anexo B: el generador
s.append(PageBreak())
s.append(p("Anexo B — El generador de laberintos: especificación exacta", H1))
s.append(p(
    "El generador es la única pieza del TP que se intercambia con otro grupo. Para que el generador ajeno "
    "funcione en su juego <b>sin cambiar una línea</b>, el contrato está reducido a lo mínimo: <b>un "
    "método, texto de entrada, texto de salida</b>. No hay ninguna clase que compartir."))

s.append(p("B.1 La interfaz", H2))
s.append(codigo('''
package ar.uba.fi.cb100.tp2.generador;

/**
 * Genera un plano de la mazmorra a partir de los parametros que recibe.
 * Recibe un JSON con el formato de B.2 y devuelve un JSON con el formato
 * de B.3. Con el mismo JSON de entrada devuelve siempre el mismo plano.
 * Lanza IllegalArgumentException si la entrada es invalida: un campo que
 * falta, filas o columnas menores a 5, cofres o monstruos negativos, o mas
 * cofres y monstruos de los que entran en el plano.
 */
public interface GeneradorDeLaberinto {
    String generar(String parametrosJson);
}
'''))
s.append(p(
    "Por qué <font face='Courier'>String</font> y no un record compartido: para que otro grupo use su "
    "generador sólo tiene que copiar <b>un archivo .java</b> que implementa esta interfaz. Si el método "
    "recibiera un objeto, habría que ponerse de acuerdo en la clase de ese objeto, su paquete y sus campos. "
    "Con JSON, el acuerdo es este anexo."))

s.append(p("B.2 El JSON de entrada", H2))
s.append(codigo('''
{
  "version": 1,
  "dificultad": "MEDIO",
  "filas": 15,
  "columnas": 20,
  "semilla": 42,
  "cofres": 2,
  "monstruos": 2,
  "conSalida": false
}
'''))
entrada = [
    [cp("<b>Campo</b>"), cp("<b>Tipo</b>"), cp("<b>Significado</b>")],
    [cp("version"), cp("entero"), cp("Siempre 1. Si algún día cambia el formato, cambia este número.")],
    [cp("dificultad"), cp("FACIL, MEDIO o DIFICIL"), cp("El generador puede usarla para decidir cuánta agua y lava pone. Otro grupo puede interpretarla distinto: es lo único del contrato que queda a criterio.")],
    [cp("filas, columnas"), cp("entero ≥ 5"), cp("Tamaño del plano, <b>incluido</b> el borde de pared.")],
    [cp("semilla"), cp("entero largo"), cp("La misma semilla con los mismos parámetros produce el mismo plano.")],
    [cp("cofres, monstruos"), cp("entero ≥ 0"), cp("Cuántos poner <b>en este plano</b>. El juego reparte los totales de la configuración entre los planos.")],
    [cp("conSalida"), cp("booleano"), cp("Sólo un plano de todo el mundo tiene la salida.")],
]
s.append(tabla(entrada, [32 * mm, 34 * mm, 100 * mm]))

s.append(p("B.3 El JSON de salida", H2))
s.append(codigo('''
{
  "version": 1,
  "generador": "Grupo 03",
  "parametros": { "version": 1, "dificultad": "MEDIO", "filas": 9, "columnas": 12,
                  "semilla": 7, "cofres": 1, "monstruos": 1, "conSalida": true },
  "entrada": { "fila": 2, "columna": 2 },
  "salida":  { "fila": 8, "columna": 10 },
  "plano": [
    "############",
    "#E..#......#",
    "#.#.#.##.#.#",
    "#.#...#..~.#",
    "#.###.#.##.#",
    "#...#...C..#",
    "#.#.#.#.##.#",
    "#.M.....#S.#",
    "############"
  ]
}
'''))
salida = [
    [cp("<b>Campo</b>"), cp("<b>Significado</b>")],
    [cp("generador"), cp("Nombre del grupo. Para saber de quién es el plano cuando algo falla.")],
    [cp("parametros"), cp("Copia exacta de la entrada. Así el archivo se explica solo y se puede regenerar.")],
    [cp("entrada"), cp("Dónde aparece el héroe. Fila y columna <b>de 1 a n</b>. Coincide con la E del plano.")],
    [cp("salida"), cp("Dónde está la salida, o null si conSalida era false. Coincide con la S.")],
    [cp("plano"), cp("Una cadena por fila, todas del mismo largo. Es la única fuente de verdad: entrada y salida son redundantes a propósito, para poder verificar que coinciden.")],
]
s.append(tabla(salida, [30 * mm, 136 * mm]))
s.append(Spacer(1, 6))
s.append(p("<b>Los símbolos</b> son fijos: los define este anexo, no cada grupo."))
simbolos = [
    [cp("<b>Símbolo</b>"), cp("<b>Significa</b>"), cp("<b>Quién decide qué es</b>")],
    [cp("#"), cp("pared"), cp("—")],
    [cp("."), cp("piso"), cp("—")],
    [cp("~"), cp("agua"), cp("—")],
    [cp("^"), cp("lava"), cp("—")],
    [cp("C"), cp("un cofre, parado sobre piso"), cp("<b>el juego que lo carga</b> sortea el tipo de su propio catálogo")],
    [cp("M"), cp("un monstruo, parado sobre piso"), cp("<b>el juego que lo carga</b> sortea el tipo de su propio catálogo")],
    [cp("E"), cp("la entrada: donde aparece el héroe"), cp("—")],
    [cp("S"), cp("la salida"), cp("—")],
]
s.append(tabla(simbolos, [20 * mm, 60 * mm, 86 * mm]))
s.append(Spacer(1, 6))
s.append(p(
    "Esto es lo que hace posible el intercambio: el generador dice <b>dónde</b>, el juego dice <b>qué</b>. "
    "Un generador ajeno no necesita conocer los cinco monstruos ni los diez cofres del grupo que lo usa. "
    "<b>No aparecen en el plano</b> las escaleras ni los pasillos: dependen de los planos vecinos, y eso el "
    "generador no lo sabe. Los pone el juego después (B.6).", NOTA))

s.append(p("B.4 Las reglas de un plano válido", H2))
s.append(p("El juego <b>valida todo plano antes de usarlo</b>, propio o ajeno, con estas once reglas. Si "
           "alguna falla, se rechaza con un mensaje que dice cuál."))
reglas = [
    [cp("<b>#</b>"), cp("<b>Regla</b>"), cp("<b>Cómo se verifica</b>")],
    [cp("R1"), cp("<b>Dimensiones</b>: plano tiene exactamente filas cadenas de exactamente columnas caracteres, y ambas son ≥ 5."), cp("Contar.")],
    [cp("R2"), cp("<b>Sólo los ocho símbolos</b> de B.3."), cp("Recorrer.")],
    [cp("R3"), cp("<b>Borde de pared</b>: la primera y última fila, y la primera y última columna, son todas #."), cp("Recorrer el borde.")],
    [cp("R4"), cp("<b>Una entrada, y la salida que corresponde</b>: exactamente una E; exactamente una S si conSalida, ninguna si no. Los campos entrada y salida coinciden con ellas."), cp("Contar y comparar.")],
    [cp("R5"), cp("<b>Conexo</b>: desde E se llega a <b>toda</b> celda transitable. Transitable es todo lo que no es #."), cp("<b>BFS con cola</b> desde E: las celdas alcanzadas son tantas como las transitables.")],
    [cp("R6"), cp("<b>Con bifurcaciones</b>: al menos el <b>10 %</b> de las celdas transitables tienen <b>3 o más</b> vecinos transitables."), cp("Contar vecinos de cada celda.")],
    [cp("R7"), cp("<b>Proporción</b>: entre el <b>30 % y el 60 %</b> de las celdas del plano son transitables."), cp("Contar.")],
    [cp("R8"), cp("<b>Cantidades exactas</b>: tantas C como cofres y tantas M como monstruos."), cp("Contar.")],
    [cp("R9"), cp("<b>La salida es la más lejana</b>: la distancia de S a E en el BFS es la máxima del plano. Si hay empate, cualquiera de las empatadas vale."), cp("Con el mismo BFS de R5.")],
    [cp("R10"), cp("<b>Agua y lava con tope</b>: entre las dos, como mucho el <b>25 %</b> de las transitables."), cp("Contar.")],
    [cp("R11"), cp("<b>Ningún monstruo pegado a la entrada</b>: toda M está a distancia Manhattan ≥ 3 de E."), cp("Restar coordenadas.")],
]
s.append(tabla(reglas, [12 * mm, 104 * mm, 50 * mm], tam=8))
s.append(Spacer(1, 6))
s.append(p(
    "Las once reglas se comprueban con <b>recorridos, conteos y un BFS</b>. Nada más. Un validador completo "
    "son unas 80 líneas. <b>Determinismo</b>: no es una regla del validador porque no se puede verificar con "
    "un solo plano, pero es parte del contrato: dos llamadas con el mismo JSON de entrada devuelven el mismo "
    "plano. Se prueba con un test."))

s.append(KeepTogether([
    p("B.5 El plano de ejemplo, verificado", H2),
    p("El de B.3 pasa las once reglas. Los números:"),
    tabla([
        ["Celdas", "9 × 12 = 108"],
        ["Transitables", "49 (45 %): R7 cumple"],
        ["Con 3 o más vecinos", "10 de 49 (20 %): R6 cumple"],
        ["Distancia máxima desde E", "16, y S está a 16: R9 cumple"],
        ["M en (8,3), E en (2,2)", "distancia 7: R11 cumple"],
        ["Agua", "1 celda (2 %): R10 cumple"],
    ], [50 * mm, 80 * mm]),
    Spacer(1, 6),
    p("Un detalle que muestra por qué R9 vale la pena: en un primer borrador de este mismo plano, la S "
      "estaba en (8,11), a distancia 15. La celda más lejana era (8,10), una al lado. El validador lo "
      "rechazó. A ojo, nadie lo hubiera visto.", NOTA),
]))

s.append(p("B.6 Qué hace el juego con el plano", H2))
s.append(p("Una vez validado:"))
s += numerados([
    "Por cada C, sortea un tipo de cofre <b>de su catálogo</b> y crea el objeto en esa celda. Por cada M, "
    "lo mismo con un monstruo.",
    "Reemplaza C, M, E y S por piso en su grilla interna: son entidades y posiciones, no terreno. La "
    "salida queda registrada como posición.",
    "Coloca las <b>conexiones</b>, que el generador no conoce. <b>Escalera</b> entre el piso k y el k+1 "
    "de un tablero: elige una celda transitable en los dos planos, que no sea E, S, C ni M en ninguno, y "
    "la marca en ambos; si no hay ninguna, abre una. <b>Pasillo</b> entre dos tableros: elige una celda "
    "del borde de cada uno cuya vecina interior sea transitable, y las une.",
    "Verifica con el <b>grafo del mundo</b> (plano = vértice, conexión = arista) que la salida es alcanzable "
    "desde la entrada del primer plano.",
])
s.append(p("Nada de esto toca al generador: por eso el generador ajeno simplemente anda."))

s.append(p("B.7 Los tests que entrega el grupo", H2))
s.append(p("Sobre <b>su</b> generador:"))
s += vinetas([
    "<b>Determinismo</b>: dos llamadas con el mismo JSON dan el mismo plano.",
    "<b>Distintas semillas, distintos planos</b>: al menos en un caso.",
    "<b>Válido en las tres dificultades</b>: con los tamaños de la configuración, el plano pasa las once "
    "reglas. Repetido con 20 semillas distintas, para que no sea casualidad.",
    "<b>Entrada inválida</b>: un JSON sin filas, con filas 3, con cofres -1, o con más cofres que celdas, "
    "lanza IllegalArgumentException.",
])
s.append(p("Sobre <b>su</b> validador: un plano hecho a mano por cada regla, que la viola y sólo a ella, y "
           "que el validador rechaza nombrándola. Once tests. Y la mitad del test del intercambio se puede "
           "escribir <b>antes</b> de recibir el generador ajeno: dado un JSON válido de B.3, el juego lo carga "
           "y arranca una partida."))

s.append(p("B.8 Lista de control para el día del intercambio", H2))
s.append(p("<b>Lo que el grupo entrega:</b>"))
s += vinetas([
    "Un único archivo GeneradorGrupoNN.java que implementa GeneradorDeLaberinto y <b>no depende de ninguna "
    "otra clase del grupo</b>. Puede usar Gson o Jackson y java.util.Random. Su pila propia, si es una clase "
    "aparte, va adentro como clase anidada.",
    "Tres JSON de salida de ejemplo, uno por dificultad, generados con semilla 1, para que el otro grupo los "
    "valide antes de compilar nada.",
    "El nombre del grupo en el campo generador.",
])
s.append(p("<b>Lo que el grupo hace con lo que recibe:</b>"))
s += vinetas([
    "Validar los tres JSON de ejemplo con su validador. Si alguno falla, anotar qué regla, antes de tocar código.",
    "Copiar el .java al paquete generador de su proyecto y reemplazar <b>una línea</b>: new GeneradorGrupoNN() "
    "donde antes había el propio.",
    "Jugar una partida en cada dificultad.",
    "En el informe: qué pasó a la primera, qué regla falló si falló, y de quién era la falla: del generador, "
    "del validador, o del contrato.",
])

# ---- Apendice A
s.append(p("Apéndice A", H1))
s.append(Paragraph("1) Usar las siguientes convenciones para nombrar identificadores.", NUM, bulletText=''))
s += [
    Paragraph("<b>Clases:</b> Los nombres de clases siempre deben comenzar con la primera letra en "
              "mayúscula en cada palabra, deben ser simples y descriptivos. Se concatenan todas las "
              "palabras. Ejemplo: Coche, Vehiculo, CentralTelefonica.", SUBNUM, bulletText='a)'),
    Paragraph("<b>Métodos:</b> Deben comenzar con letra minúscula, y si está compuesta por 2 o más "
              "palabras, la primera letra de la segunda palabra debe comenzar con mayúscula. De "
              "preferencia que sean verbos. Ejemplo: arrancarCoche(), sumar().", SUBNUM, bulletText='b)'),
    Paragraph("<b>Variables y objetos:</b> las variables siguen la misma convención que los métodos. "
              "Por ejemplo: alumno, padronElectoral.", SUBNUM, bulletText='c)'),
    Paragraph("<b>Constantes:</b> Las variables constantes o finales, las cuales no cambian su valor "
              "durante todo el programa, se deben escribir en mayúsculas, concatenadas por \"_\". "
              "Ejemplo: ANCHO, VACIO, COLOR_BASE.", SUBNUM, bulletText='d)'),
]
s += numerados([
    "Si el trabajo práctico requiere archivos para procesar, entregar los archivos de prueba en la "
    "entrega del TP. Utilizar siempre <b>rutas relativas</b> y no absolutas.",
    "Entregar el informe explicando el TP realizado, manual de usuario y manual del programador en un "
    "mismo PDF dentro del repositorio.",
    "Comentar el código. Todos los tipos, métodos y funciones deberían tener sus comentarios.",
    "Modularizar el código. No entregar 1 o 2 archivos, separar cada clase.",
    "Si cualquier estructura de control tiene 1 línea, utilizar <b>{ }</b> siempre, por ejemplo:",
], desde=2)
s.append(codigo('''
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}
'''))

# ==========================================================================
doc = SimpleDocTemplate(SALIDA, pagesize=A4,
                        leftMargin=20 * mm, rightMargin=20 * mm,
                        topMargin=24 * mm, bottomMargin=18 * mm,
                        title="TP 2 - CB100 - 2C 2026",
                        author="Catedra Ing. Gustavo Schmidt",
                        subject="Trabajo Practico 2: La fuga de la mazmorra")
doc.build(s, onFirstPage=decorar, onLaterPages=decorar)
print("OK")
