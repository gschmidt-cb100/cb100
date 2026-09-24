package ar.uba.fi.cb100.clases.a2026.c02.s05.tarea;

// Torres de Hanoi. long movimientos(int n) (con 𝑇 (𝑛) = 2𝑇 (𝑛 − 1) + 1 ⇒ 2𝑛 − 1); test.
public class TorresDeHanoi {
    
    public long movimientos(int n){
        if (n == 0){
            return 0;
        }
        if (n == 1){
            return 1;
        } // Casos base

        long sumaMovimientos = movimientos(n - 1) + 1 + movimientos(n - 1); //Procedimiento y llamada recursiva

        return sumaMovimientos; // final
    }
}
        // Tenemos que a = 2 (llamadas recursivas) y b = 1.  ->  T(n) = 2T(n - 1) + O(1)  (Teorema maestro)
        // De acá saco que n^k = 1  ->  k = 0
        // T(n) = O(n^k . a^(n/b))  ->  T(n) = O(1 . 2^n) = O(2^n)  (El teorema maestro por fórmula no contempla el - 1)
