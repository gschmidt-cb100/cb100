package ar.uba.fi.cb100.clases.a2026.c02.s03.herencia;

import org.apache.poi.extractor.MainExtractorFactory;

public class Principal03 {

    public static void main() {
//        Animal animal = new Animal("Animal");
//        System.out.println(animal.sonido());

        {
            Perro perro = new Perro("Perro");
            perro.setCantidadDePatas(4);
            System.out.println(perro.sonido());

            Gato gato = new Gato("Gato");
            gato.setCantidadDePatas(4);
            System.out.println(gato.sonido());

            //Version 1
            hacerSonido(perro);
            hacerSonido(gato);

            //Version 2
            Animal animal1 = perro;
            hacerSonido(animal1);

            animal1 = gato;
            hacerSonido(animal1);
        }
        {
            Animal animal1 = new Perro("Perro");
            System.out.println(animal1.sonido());

            Animal animal2 = new Gato("Gato");
            System.out.println(animal2.sonido());

            animal1 = animal2;
            System.out.println(animal1.sonido());  //Miau

            Animal[] animales = {animal1, animal2};
            animales[0] = animal1;
            animales[1] = animal2;
        }



    }

    public static void hacerSonido(Animal animal) {
        System.out.println(animal.sonido());
    }


    public static void testCasa() {
        Casa casa = new Casa(3);

        casa.adoptarMascota(new Perro("Perro"));
        casa.adoptarMascota(new Gato("Gato"));
        casa.adoptarMascota(new Gallina("Gallina"));
    }
}
