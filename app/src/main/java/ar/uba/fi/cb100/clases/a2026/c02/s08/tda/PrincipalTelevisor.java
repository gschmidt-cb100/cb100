package ar.uba.fi.cb100.clases.a2026.c02.s08.tda;

public class PrincipalTelevisor {

    public static void main(String[] args) {
        Televisor televisor = new Televisor(2, 140);
        televisor.activarEntradaDeCable();
        televisor.subirCanalDeCable();
        televisor.bajarCanalDeCable();
        televisor.seleccionarCanalDeCable(10);

        televisor.seleccionarEntradaHDMI(1);
        televisor.activarEntradaHDMI(1);

        System.out.println("Cantidad de usos de la entrada de cable: " + televisor.getEntradaMasUtilizada().getCantidadDeUsos());
    }
}
