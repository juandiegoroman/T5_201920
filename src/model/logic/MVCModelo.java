package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import com.opencsv.CSVReader;

import model.data_structures.IListaIterador;
import model.data_structures.ListaEncadenada;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;


/**
 * Definicion del modelo del mundo
 */
public class MVCModelo {
    /**
     * Atributos del modelo del mundo
     */

    private ListaEncadenada<TravelTime> datos;
    private MaxColaCP<TravelTime> colaCP;
    private MaxHeapCP<TravelTime> heapCP;

    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public MVCModelo() {

        datos = new ListaEncadenada<TravelTime>();
    }




    public void loadTravelTimes(String ruta, int semestre) {
        CSVReader reader = null;
        try {

            reader = new CSVReader(new FileReader(ruta));

            Iterator iter = reader.iterator();

            iter.next();

            while (iter.hasNext()) {

                String[] parametros = (String[]) iter.next();

                TravelTime v = crearViaje(parametros, semestre);

                agregar(v);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public  TravelTime[] generarMuestraAleatorea(int n){

        TravelTime[] a = new TravelTime[n];

        datos.ordenarPorMergeSort(new ComparadorTiempoPromedio());

        IListaIterador<TravelTime> iter = datos.iterador();
        TravelTime temp = iter.siguiente();


        for (int i = 0; i < n; i++) {

            while (temp.darMeanTravelTime() == datos.darSiguiente().darMeanTravelTime()){
                temp = iter.siguiente();
            }
            a[i] = temp;
        }

        mezclarAleatoreamente(a);

        return a;
    }

    private static void mezclarAleatoreamente(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {

            Random random = new Random();
            int r = i + random.nextInt(n - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    private class ComparadorTiempoPromedio implements Comparator<TravelTime> {
        @Override
        public int compare(TravelTime o1, TravelTime o2) {
            if (o1.darMeanTravelTime() < o2.darMeanTravelTime()) return -1;
            if (o1.darMeanTravelTime() > o2.darMeanTravelTime()) return 1;
            else return 0;
        }
    }

    /**
     * Requerimiento de agregar dato
     *
     * @param dato
     */
    public void agregar(TravelTime dato) {
        datos.insertarFinal(dato);
    }

    public TravelTime crearViaje(String[] datos, int semestre) {
        return new TravelTime(semestre, Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Double.valueOf(datos[3]), Double.valueOf(datos[4]));
    }



    public ListaEncadenada<TravelTime> darDatos() {
        return datos;
    }

    public double tiempoPromedioAgregarImplementacionHeap(){

        TravelTime[] a = generarMuestraAleatorea(200000);
        Contador cont = new Contador();

        for (TravelTime tiempoDeViaje: a) {
            heapCP.agregar(tiempoDeViaje);
        }
        double total = cont.duracion();

        return total / 200000;
    }

    public double tiempoPromedioSacarMaxImplementacionHeap(){

        TravelTime[] a = generarMuestraAleatorea(200000);

        for (TravelTime tiempoDeViaje: a) {
            heapCP.agregar(tiempoDeViaje);
        }


        Contador cont = new Contador();

        while (!heapCP.esVacia()){
            heapCP.sacarMax();
        }
        double total = cont.duracion();

        return total / 200000;
    }


    public double tiempoPromedioAgregarImplementacionCola(){

        TravelTime[] a = generarMuestraAleatorea(200000);
        Contador cont = new Contador();

        for (TravelTime tiempoDeViaje: a) {
            colaCP.agregar(tiempoDeViaje);
        }
        double total = cont.duracion();

        return total / 200000;
    }

    public double tiempoPromedioSacarMaxImplementacionCola(){

        TravelTime[] a = generarMuestraAleatorea(200000);

        for (TravelTime tiempoDeViaje: a) {
            colaCP.agregar(tiempoDeViaje);
        }

        Contador cont = new Contador();

        while (!colaCP.esVacia()){
            colaCP.sacarMax();
        }

        double total = cont.duracion();

        return total / 200000;
    }

    public class Contador
    {
        private final long inicio;
        public Contador()
        { inicio = System.currentTimeMillis(); }
        public double duracion()
        {
            long actual = System.currentTimeMillis();
            return (actual - inicio) ;
        }
    }
}
