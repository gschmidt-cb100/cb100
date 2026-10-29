"""
Genera las imágenes del tateti, del reversi, del memotest y de la casita robada
(requiere: pip install pillow).

Cada apariencia sale dos veces: "x.png" y "x-cursor.png" (la misma con un
borde amarillo). InterfazGrafica usa la segunda para la celda del cursor.

Uso, desde esta carpeta:  python generar-imagenes.py
"""
from pathlib import Path
import math

from PIL import Image, ImageDraw, ImageFont

AQUI = Path(__file__).resolve().parent
ESCALA = 4                      # se dibuja 4 veces más grande y se achica: bordes suaves
AMARILLO = (255, 193, 7)


def lienzo(lado, fondo, borde, ancho_borde):
    grande = lado * ESCALA
    img = Image.new("RGB", (grande, grande), fondo)
    d = ImageDraw.Draw(img)
    a = ancho_borde * ESCALA
    d.rectangle([0, 0, grande - 1, grande - 1], outline=borde, width=a)
    return img, d, grande


def con_cursor(img, lado):
    copia = img.copy()
    d = ImageDraw.Draw(copia)
    grande = lado * ESCALA
    a = max(4, lado // 14) * ESCALA
    d.rectangle([a // 2, a // 2, grande - 1 - a // 2, grande - 1 - a // 2], outline=AMARILLO, width=a)
    return copia


def guardar(img, lado, carpeta, nombre):
    carpeta.mkdir(parents=True, exist_ok=True)
    chica = img.resize((lado, lado), Image.LANCZOS)
    chica.save(carpeta / f"{nombre}.png")
    con_cursor(img, lado).resize((lado, lado), Image.LANCZOS).save(carpeta / f"{nombre}-cursor.png")


# ------------------------------------------------------------------ tateti
def tateti(lado=140):
    carpeta = AQUI / "tateti" / "imagenes"
    fondo, borde = (245, 240, 225), (90, 90, 90)

    img, d, g = lienzo(lado, fondo, borde, 2)
    guardar(img, lado, carpeta, "vacio")

    img, d, g = lienzo(lado, fondo, borde, 2)
    m, w = g * 0.26, int(g * 0.11)
    d.line([m, m, g - m, g - m], fill=(211, 47, 47), width=w)
    d.line([m, g - m, g - m, m], fill=(211, 47, 47), width=w)
    guardar(img, lado, carpeta, "x")

    img, d, g = lienzo(lado, fondo, borde, 2)
    m, w = g * 0.24, int(g * 0.10)
    d.ellipse([m, m, g - m, g - m], outline=(25, 118, 210), width=w)
    guardar(img, lado, carpeta, "o")


# ------------------------------------------------------------------ reversi
def reversi(lado=72):
    carpeta = AQUI / "reversi" / "imagenes"
    fondo, borde = (46, 125, 50), (27, 94, 32)

    img, d, g = lienzo(lado, fondo, borde, 2)
    guardar(img, lado, carpeta, "vacio")

    img, d, g = lienzo(lado, fondo, borde, 2)
    r = g * 0.12
    d.ellipse([g / 2 - r, g / 2 - r, g / 2 + r, g / 2 + r], fill=(129, 199, 132))
    guardar(img, lado, carpeta, "marcada")

    for nombre, relleno, contorno, brillo in (
            ("negra", (33, 33, 33), (0, 0, 0), (90, 90, 90)),
            ("blanca", (250, 250, 250), (158, 158, 158), (255, 255, 255))):
        img, d, g = lienzo(lado, fondo, borde, 2)
        m = g * 0.12
        d.ellipse([m, m + g * 0.03, g - m, g - m + g * 0.03], fill=(20, 70, 25))         # sombra
        d.ellipse([m, m, g - m, g - m], fill=relleno, outline=contorno, width=int(g * 0.02))
        b = g * 0.30
        d.ellipse([b, b, b + g * 0.16, b + g * 0.16], fill=brillo)                          # reflejo
        guardar(img, lado, carpeta, nombre)


# ------------------------------------------------------------------ memotest
COLORES = {
    "sol": (249, 168, 37), "luna": (120, 144, 156), "estrella": (255, 193, 7), "corazon": (229, 57, 53),
    "rombo": (30, 136, 229), "triangulo": (67, 160, 71), "cuadrado": (142, 36, 170), "cruz": (244, 81, 30),
}


def poligono_regular(cx, cy, r, lados, giro=-90):
    return [(cx + r * math.cos(math.radians(giro + 360 * i / lados)),
             cy + r * math.sin(math.radians(giro + 360 * i / lados))) for i in range(lados)]


def dibujar_figura(d, nombre, g, color, fondo):
    c, r = g / 2, g * 0.30
    if nombre == "sol":
        for i in range(12):
            a = math.radians(30 * i)
            d.line([c + r * 0.9 * math.cos(a), c + r * 0.9 * math.sin(a),
                    c + r * 1.35 * math.cos(a), c + r * 1.35 * math.sin(a)], fill=color, width=int(g * 0.04))
        d.ellipse([c - r * 0.75, c - r * 0.75, c + r * 0.75, c + r * 0.75], fill=color)
    elif nombre == "luna":
        d.ellipse([c - r, c - r, c + r, c + r], fill=color)
        d.ellipse([c - r * 0.45, c - r * 1.15, c + r * 1.25, c + r * 0.55], fill=fondo)
    elif nombre == "estrella":
        puntos = []
        for i in range(10):
            radio = r * 1.15 if i % 2 == 0 else r * 0.48
            a = math.radians(-90 + 36 * i)
            puntos.append((c + radio * math.cos(a), c + radio * math.sin(a)))
        d.polygon(puntos, fill=color)
    elif nombre == "corazon":
        d.ellipse([c - r, c - r * 0.75, c, c + r * 0.25], fill=color)
        d.ellipse([c, c - r * 0.75, c + r, c + r * 0.25], fill=color)
        d.polygon([(c - r * 0.97, c - r * 0.15), (c + r * 0.97, c - r * 0.15), (c, c + r * 1.05)], fill=color)
    elif nombre == "rombo":
        d.polygon(poligono_regular(c, c, r * 1.15, 4), fill=color)
    elif nombre == "triangulo":
        d.polygon(poligono_regular(c, c + r * 0.15, r * 1.15, 3), fill=color)
    elif nombre == "cuadrado":
        d.rectangle([c - r * 0.85, c - r * 0.85, c + r * 0.85, c + r * 0.85], fill=color)
    elif nombre == "cruz":
        a = r * 0.35
        d.rectangle([c - a, c - r, c + a, c + r], fill=color)
        d.rectangle([c - r, c - a, c + r, c + a], fill=color)


def aclarar(color, cuanto=0.55):
    return tuple(int(v + (255 - v) * cuanto) for v in color)


def memotest(lado=130):
    carpeta = AQUI / "memotest" / "imagenes"
    mesa = (38, 50, 56)

    img, d, g = lienzo(lado, mesa, mesa, 1)                       # dorso: carta azul con rombos
    m = g * 0.07
    d.rounded_rectangle([m, m, g - m, g - m], radius=g * 0.08, fill=(92, 107, 192), outline=(255, 255, 255), width=int(g * 0.025))
    for i in range(3):
        for j in range(3):
            cx, cy = g * (0.3 + 0.2 * i), g * (0.3 + 0.2 * j)
            d.polygon(poligono_regular(cx, cy, g * 0.06, 4), fill=(159, 168, 218))
    guardar(img, lado, carpeta, "dorso")

    for nombre, color in COLORES.items():
        for encontrada in (False, True):
            img, d, g = lienzo(lado, mesa, mesa, 1)
            fondo = (200, 230, 201) if encontrada else (255, 255, 255)
            d.rounded_rectangle([m, m, g - m, g - m], radius=g * 0.08, fill=fondo)
            dibujar_figura(d, nombre, g, aclarar(color) if encontrada else color, fondo)
            guardar(img, lado, carpeta, nombre + ("-encontrada" if encontrada else ""))


# ------------------------------------------------------------------ casita robada
PALOS = {"oros": (230, 160, 0), "copas": (198, 40, 40), "espadas": (21, 101, 192), "bastos": (46, 125, 50)}
NUMEROS = [1, 2, 3, 4, 5, 6, 7, 10, 11, 12]
PANO = (39, 94, 56)


def fuente(tamanio):
    for nombre in ("DejaVuSans-Bold.ttf", "arialbd.ttf", "Arial Bold.ttf"):
        try:
            return ImageFont.truetype(nombre, tamanio)
        except OSError:
            pass
    return ImageFont.load_default(tamanio)


def dibujar_palo(d, palo, cx, cy, r):
    color = PALOS[palo]
    if palo == "oros":
        d.ellipse([cx - r, cy - r, cx + r, cy + r], fill=color, outline=(150, 100, 0), width=max(2, int(r * 0.12)))
        d.ellipse([cx - r * 0.5, cy - r * 0.5, cx + r * 0.5, cy + r * 0.5], outline=(255, 224, 130), width=max(2, int(r * 0.12)))
    elif palo == "copas":
        d.chord([cx - r, cy - r * 1.1, cx + r, cy + r * 0.5], 0, 180, fill=color)
        d.rectangle([cx - r * 0.15, cy + r * 0.1, cx + r * 0.15, cy + r * 0.75], fill=color)
        d.ellipse([cx - r * 0.6, cy + r * 0.6, cx + r * 0.6, cy + r * 1.0], fill=color)
    elif palo == "espadas":
        d.polygon([(cx, cy - r * 1.2), (cx + r * 0.18, cy - r * 0.9), (cx + r * 0.18, cy + r * 0.45),
                   (cx - r * 0.18, cy + r * 0.45), (cx - r * 0.18, cy - r * 0.9)], fill=color)
        d.rectangle([cx - r * 0.65, cy + r * 0.40, cx + r * 0.65, cy + r * 0.58], fill=color)
        d.rectangle([cx - r * 0.13, cy + r * 0.58, cx + r * 0.13, cy + r * 1.1], fill=(120, 80, 40))
    else:
        d.polygon([(cx - r * 0.18, cy + r * 1.15), (cx + r * 0.18, cy + r * 1.15),
                   (cx + r * 0.42, cy - r * 1.0), (cx - r * 0.42, cy - r * 1.0)], fill=color)
        for k, lado in ((-0.6, -1), (-0.1, 1), (0.4, -1)):
            x = cx + lado * r * 0.38
            d.ellipse([x - r * 0.17, cy + r * k - r * 0.15, x + r * 0.17, cy + r * k + r * 0.15], fill=color)


def carta_base(lado, fondo=PANO):
    img, d, g = lienzo(lado, fondo, fondo, 1)
    m = g * 0.06
    return img, d, g, m


def casita(lado=104):
    carpeta = AQUI / "casitarobada" / "imagenes"

    img, d, g, m = carta_base(lado)                                   # paño
    guardar(img, lado, carpeta, "pano")

    img, d, g, m = carta_base(lado)                                   # lugar vacío
    d.rounded_rectangle([m * 2, m, g - m * 2, g - m], radius=g * 0.08, outline=(76, 140, 96), width=int(g * 0.025))
    guardar(img, lado, carpeta, "hueco")

    img, d, g, m = carta_base(lado)                                   # casita vacía: una casita dibujada
    c = g / 2
    d.polygon([(c - g * 0.28, c), (c, c - g * 0.26), (c + g * 0.28, c)], outline=(129, 199, 132), width=int(g * 0.03))
    d.rectangle([c - g * 0.2, c, c + g * 0.2, c + g * 0.26], outline=(129, 199, 132), width=int(g * 0.03))
    guardar(img, lado, carpeta, "casita-vacia")

    img, d, g, m = carta_base(lado)                                   # dorso
    d.rounded_rectangle([m * 2, m, g - m * 2, g - m], radius=g * 0.08, fill=(183, 28, 28), outline=(255, 255, 255), width=int(g * 0.025))
    for i in range(4):
        for j in range(5):
            cx, cy = g * (0.30 + 0.135 * i), g * (0.20 + 0.15 * j)
            d.ellipse([cx - g * 0.03, cy - g * 0.03, cx + g * 0.03, cy + g * 0.03], fill=(239, 154, 154))
    guardar(img, lado, carpeta, "dorso")

    grande = fuente(int(lado * ESCALA * 0.30))
    chica = fuente(int(lado * ESCALA * 0.16))
    for palo, color in PALOS.items():
        for numero in NUMEROS:
            img, d, g, m = carta_base(lado)
            d.rounded_rectangle([m * 2, m, g - m * 2, g - m], radius=g * 0.08, fill=(250, 245, 230), outline=(120, 120, 120), width=int(g * 0.012))
            d.text((m * 3.2, m * 1.6), str(numero), font=chica, fill=color)
            d.text((g * 0.5, g * 0.40), str(numero), font=grande, fill=color, anchor="mm")
            dibujar_palo(d, palo, g * 0.5, g * 0.72, g * 0.13)
            guardar(img, lado, carpeta, f"{numero}-{palo}")


if __name__ == "__main__":
    tateti()
    reversi()
    memotest()
    casita()
    print("Listo.")
