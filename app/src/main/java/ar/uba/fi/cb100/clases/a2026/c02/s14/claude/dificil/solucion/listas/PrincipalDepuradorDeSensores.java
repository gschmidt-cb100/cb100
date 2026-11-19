package ar.uba.fi.cb100.clases.a2026.c02.s14.claude.dificil.solucion.listas;

public class PrincipalDepuradorDeSensores {

    public static void main(String[] args) {
        Registro registro = new Registro("Termómetro del laboratorio");
        int[] valores = {20, 21, 22, 90, 22, 22, 23, -40, 23};
        for (int valor : valores) {
            registro.agregarLectura(new Lectura(valor, 10));
        }
        System.out.println(registro);

        DepuradorDeSensores depurador = new DepuradorDeSensores();
        int corregidas = depurador.depurar(registro, 5, 3);

        // Paso a paso, con ventana 3 y tolerancia 5:
        //   20, 21, 22    -> las tres primeras no se corrigen
        //   90            -> mediana(20, 21, 22) = 21; |90 - 21| > 5: se corrige a 21
        //   22            -> mediana(21, 22, 21) = 21; queda 22
        //   22            -> mediana(22, 21, 22) = 22; queda 22, repite a la anterior: se comprime (x20)
        //   23            -> mediana(21, 22, 22) = 22; queda 23
        //   -40           -> mediana(22, 22, 23) = 22; se corrige a 22
        //   23            -> mediana(22, 23, 22) = 22; queda 23
        System.out.println("Corregidas: " + corregidas);                 // 2
        System.out.println(registro);                                    // 20, 21, 22, 21, 22 x20, 23, 22, 23
    }
}
