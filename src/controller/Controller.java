package controller;

import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.ListaEncadenada;
import model.logic.MVCModelo;
import model.logic.TravelTime;

import view.MVCView;

public class Controller {



    /* Instancia del Modelo*/
    private MVCModelo modelo;

    /* Instancia de la Vista*/
    private MVCView view;

    /**
     * Crear la vista y el modelo del proyecto
     */
    public Controller() {
        view = new MVCView();
        modelo = new MVCModelo();
    }

    public void run() {
        Scanner lector = new Scanner(System.in);
        boolean fin = false;


        int trimestre = -1;
        int origen = -1;
        int destino = -1;


        while (!fin) {
            view.printMenu();

            int option = lector.nextInt();
            switch (option) {
                case 1:
                System.out.println(modelo.loadTravelTimes());


                    break;


                case 2:
                    try {

                        System.out.println(" \n Ingresar trimestre: \n");

                        try {
                            trimestre = lector.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un n�mero");
                        }
                        System.out.println(" \n Ingresar origen: \n");

                        try {
                            origen = lector.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un n�mero");
                        }

                        System.out.println(" \n Ingresar destino: \n");

                        try {
                            destino = lector.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un n�mero");
                        }



                        System.out.println("Se hallaron los siguientes viajes en la Tabla Hash usando Linear Probing: ");
                        printList(modelo.buscarPorTrimestreOrigenYDestinoLP(trimestre,origen, destino));
                        System.out.println("Se hallaron los siguientes viajes en la Tabla Hash usando Separate Chaining: ");
                        printList(modelo.buscarPorTrimestreOrigenYDestinoSC(trimestre,origen, destino));


                    } catch (Exception e) {

                        e.printStackTrace();
                        System.out.print("No se han cargado los datos.\n");
                    }

                    break;

                case 3:

                    System.out.println("Se hallaron los siguientes tiempos  usando Linear Probing para calcular 10000 consultas aleatoreas: ");

                   System.out.println(modelo.tiemposGetLP());
                    System.out.println();
                    System.out.println("Se hallaron los siguientes tiempos  usando Separate Chaining para calcular 10000 consultas aleatoreas: ");

                   System.out.println(modelo.tiemposGetSC());
                    System.out.println();
                    break;




                default:
                    System.out.println("--------- \n Opcion Invalida !! \n---------");
                    break;
            }
        }

    }

    private void printList(ListaEncadenada<TravelTime> lista) {
        TravelTime actual;
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%10s %10s  %20s  %20s %20s", "Trimestre", "Origen", "Destino", "Dia", "Tiempo promedio");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");

        Iterator<TravelTime> iter = null;
        if (lista != null) {

            iter = lista.iterator();

            while (iter.hasNext()) {
                actual = iter.next();
                System.out.format("%10s %10s  %20s  %20s %20s", actual.darTimestre(), actual.darSourceId(), actual.darDistId(), actual.darDow(), actual.darMeanTravelTime());
                System.out.println();
            }
        }
    }
}
