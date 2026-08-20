package ar.uba.fi.cb100.material.i01_intro;

/**
 * Clase mínima para mostrar la SINTAXIS de una clase con estado (campos) y
 * comportamiento (métodos).
 * <p>
 * Por simplicidad los campos son públicos y no hay constructor. En la Unidad 3
 * vamos a ver por qué exponer los campos NO es una buena práctica
 * (encapsulamiento) y cómo se modela bien con constructores e interfaces.
 */
public class Rectangulo {

    public double base;      // estado: los datos del objeto
    public double altura;

    public double area() {           // comportamiento: qué sabe hacer
        return base * altura;
    }

    public double perimetro() {
        return 2 * (base + altura);
    }

    public static void main(String[] args) {
        Rectangulo r = new Rectangulo();   // se crea el objeto con new
        r.base = 3;                        // se asignan sus campos
        r.altura = 4;
        System.out.println("área = " + r.area());
        System.out.println("perímetro = " + r.perimetro());
    }
}
