# TDA Tostadora (borrador)

## EstadoTostadora.java

```java
public enum EstadoTostadora {
    ENCENDIDA
    APAGADA
}
// este enum hace que la tostadora tenga estado ENCENDIDA y APAGADA
```

## Tostadora.java

```java
public class Tostadora {

    // atributos
    private int NivelC;
    private int NRanuras;
    private EstadoTostadora estado;

    // constructor para la tostadora
    public Tostadora(int NivelC, int NRanuras, EstadoTostadora estado) {
        this.NRanuras = NRanuras;
        this.NivelC = NivelC;
        this.estado = estado.ENCENDIDA;   // preguntar
    }

    // metodos: IniciarTostado, FinalizarTostado, IndicarNivel,
    // ContarRanurasLibres, RanuraMasPan
    // IniciarTostado: void / FinalizarTostado: void / IndicarNivel: no void
    // ContarRanurasLibres: no void / RanuraMasPan: no void

    public void IniciarTostado(int nRanura) {
        // este metodo no devuelve nada
        if (this.estado == estado.ENCENDIDA) {
            this.ComienzaRanura();
        }
        else {   // preguntar
            // excepcion
        }

    public void FinalizarTostado(Tostadora tostadora) {

    public int IndicarNivel
        // este metodo retorna el nivel calentamiento de la tostadora
        return this.NivelC

    public int ContarRanurasLibres
        // este metodo devuelve cuantas ranuras libres hay
        // tiene que iterar en las ranuras y si
        // estador es libre suma uno al contador luego
        // return contador
```

## EstadoRanura.java

```java
public enum EstadoRanura {
    LIBRE
    OCUPADA
}
```

## Ranura.java

```java
public class Ranura {

    // atributos
    private int NPTost;
    private EstadoRanura estador;

    // constructor
    // public Ranura   // public o private?
    public Ranura(int NPTost, EstadoRanura estador) {
        this.NPTost = NPTost;
        this.estador = estador.LIBRE;
    }

    // metodos: Ranura con mas Pan (RMPan)
    public int RMPan() {
        // tostadora.NRanuras es una lista/arreglo -> busco el max?
        if (this.estador == estado.LIBRE) {
```
