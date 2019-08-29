package controller;

import java.util.Scanner;

import model.data_structures.Cola;
import model.data_structures.IColaIterador;
import model.logic.MVCModelo;
import model.logic.Viaje;
import view.MVCView;

public class Controller {

    public final static String DATOS_PRIMER_SEMESTRE = "./data/bogota-cadastral-2018-1-All-HourlyAggregate.csv";

    /* Instancia del Modelo*/
    private MVCModelo modelo;

    /* Instancia de la Vista*/
    private MVCView view;

    /**
     * Crear la vista y el modelo del proyecto
     *
     * @param capacidad tamaNo inicial del arreglo
     */
    public Controller() {
        view = new MVCView();
        modelo = new MVCModelo();
    }

    public void run() {
        Scanner lector = new Scanner(System.in);
        boolean fin = false;
        String dato = "";
        String respuesta = "";

        int horaConsulta = -1;
        int nViajes = -1;

        while (!fin) {
            view.printMenu();

            int option = lector.nextInt();
            switch (option) {
                case 1:
                    modelo.cargarDatos(DATOS_PRIMER_SEMESTRE);

                    System.out.println("Para el primer semestre del 2018 se leyeron las siguientes cantidades de viajes por hora: " + modelo.darDatosCola().tamano());
                    System.out.println();
                    System.out.println("Primer viaje: Origen: " + modelo.darDatosCola().darPrimero().valor().darIdOrigen() + ", Destino: " + modelo.darDatosCola().darPrimero().valor().darIdDestino() + ", Hora: " + modelo.darDatosCola().darPrimero().valor().darHora() + ", Tiempo promedio: " + modelo.darDatosCola().darPrimero().valor().darTiempoPromedio());
                    System.out.println();
                    System.out.println("Ultimo viaje: Origen: " + modelo.darDatosCola().darUltimo().valor().darIdOrigen() + ", Destino: " + modelo.darDatosCola().darUltimo().valor().darIdDestino() + ", Hora: " + modelo.darDatosCola().darUltimo().valor().darHora() + ", Tiempo promedio: " + modelo.darDatosCola().darUltimo().valor().darTiempoPromedio());
                    System.out.println();
                    break;


                case 2:
                    try {

                        System.out.println("--------- \nIngresar el n�mero de la hora de consulta: ");

                        try {
                            horaConsulta = lector.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un numero");
                        }

                        System.out.println("La cantidad de viajes del grupo resultante es:" + modelo.clusterMayor(horaConsulta).tamano() + "\n");

                        Cola<Viaje> cola = modelo.darDatosCola();

                        IColaIterador<Viaje> iter = cola.iterador();

                        if (cola.tamano() == 0) {

                            System.out.println("No se encontraron resultados con los par�metros dados. \n");
                        } else {

                            printArray(iter);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                        System.out.print("No se han cargado los datos.\n");
                    }

                    break;

                case 3:
                    try {

                        System.out.println("--------- \nIngresar el n�mero de la hora de consulta: ");

                        try {
                            nViajes = lector.nextInt();
                        } catch (Exception e) {
                            System.out.println("Debe ingresar un numero");
                        }
                        System.out.println("La cantidad de viajes del grupo resultante es:" + modelo.viajesHoraDada(nViajes, horaConsulta).tamano() + "\n");
                        Cola<Viaje> cola = modelo.darDatosCola();
                        IColaIterador<Viaje> iter = cola.iterador();

                        Viaje actual = iter.siguiente();

                        if (cola.tamano() == 0) {
                            System.out.println("No se encontraron resultados. \n");
                        } else {
                            printArray(iter);
                        }

                    } catch (Exception e) {
                        System.out.print("No se han cargado los datos.\n");
                    }

                    break;

                default:
                    System.out.println("--------- \n Opcion Invalida !! \n---------");
                    break;
            }
        }

    }

    private void printArray(IColaIterador<Viaje> iter) {
        Viaje actual;
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %10s  %20s  %20s", "Hora", "Origen", "Destino", "Tiempo promedio");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        while (iter.haySiguiente()) {
            actual = iter.siguiente();
            System.out.format("%10s %10s  %20s  %20s", actual.darHora(), actual.darIdOrigen(), actual.darIdDestino(), actual.darTiempoPromedio());
            System.out.println();
        }
    }
}
