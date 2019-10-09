package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import com.opencsv.CSVReader;

import model.data_structures.*;
import model.data_structures.QuickSort;


/**
 * Definicion del modelo del mundo
 */
public class MVCModelo {
    /**
     * Atributos del modelo del mundo
     */

    public final static String DATOS_PRIMER_SEMESTRE = "./data/bogota-cadastral-2018-1-WeeklyAggregate.csv";
    public final static String DATOS_SEGUNDO_SEMESTRE = "./data/bogota-cadastral-2018-2-WeeklyAggregate.csv";
    public final static String DATOS_TERCER_SEMESTRE = "./data/bogota-cadastral-2018-3-WeeklyAggregate.csv";
    public final static String DATOS_CUARTO_SEMESTRE = "./data/bogota-cadastral-2018-4-WeeklyAggregate.csv";
    public final static int CANTIDAD = 200000;

    private ListaEncadenada<TravelTime> datos;
    private MaxColaCP<TravelTime> colaCP;
    private MaxHeapCP<TravelTime> heapCP;
    private TravelTime[] muestraAleatorea;

    private TablaHashSeparateChaining<String, TravelTime> tablaHashSC;
    private TablaHashLinearProbing<String, TravelTime> tablaHashLP;


    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public MVCModelo() {

        datos = new ListaEncadenada<TravelTime>();
    }


    public void loadTravelTimes(){
        cargar(DATOS_PRIMER_SEMESTRE, 1);
        cargar(DATOS_SEGUNDO_SEMESTRE, 2);
        cargar(DATOS_TERCER_SEMESTRE, 3);
        cargar(DATOS_CUARTO_SEMESTRE, 4);

        muestraAleatorea = generarMuestraAleatorea(CANTIDAD);
    }

    public void cargar(String ruta, int semestre) {
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

        Comparable[] arr = convertirAArreglo(datos);

        QuickSort.ordenar(arr);


        int cont = 0;

        for (int i = 0; i < n; i++) {

            while (arr[cont] == arr[++cont])

            a[i] = (TravelTime) arr[cont];
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

    private Comparable[] convertirAArreglo(ListaEncadenada<TravelTime> lista) {

        Comparable[] arr = new Comparable[lista.tamano()];

        int cont = 0;

        Iterator<TravelTime> iter = lista.iterador();

        while(iter.hasNext())
        {
            Comparable actual = iter.next();
            arr[cont] = (Comparable) actual;
            cont++;
        }
        return arr;
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


    public void cargarTablaHashSeparateChaining(){

        tablaHashSC = new TablaHashSeparateChaining<>();


        Iterator<TravelTime> iter = datos.iterador();

        while(iter.hasNext())
        {
            TravelTime actual = iter.next();
            tablaHashSC.put(actual.darTimestre() + "-" + actual.darSourceId() + "-" + actual.darDistId() + "-" + actual.darDow(), actual);
        }

    }

    public void cargarTablaLinearProbing(){

        tablaHashLP = new TablaHashLinearProbing<>();

        Iterator<TravelTime> iter = datos.iterador();

        while(iter.hasNext())
        {
            TravelTime actual = iter.next();
            tablaHashLP.put(actual.darTimestre() + "-" + actual.darSourceId() + "-" + actual.darDistId() + "-" + actual.darDow(), actual);
        }

    }

    public ListaEncadenada<TravelTime> buscarPorTrimestreOrigenYDestinoSC(int tri, int org, int dst) {

        ListaEncadenada<TravelTime> lista = new ListaEncadenada<>();

        for (int i = 0; i < 7; i++) {
            lista.insertarFinal(tablaHashSC.get(tri + "-" + org + "-" + dst + "-" + (1 + i)));
        }

    return lista;
    }

    public ListaEncadenada<TravelTime> buscarPorTrimestreOrigenYDestinoLP(int tri, int org, int dst) {

        ListaEncadenada<TravelTime> lista = new ListaEncadenada<>();

        for (int i = 0; i < 7; i++) {
            lista.insertarFinal(tablaHashLP.get(tri + "-" + org + "-" + dst + "-" + (1 + i)));
        }
        return lista;
    }











    public ListaEncadenada<TravelTime> darDatos() {
        return datos;
    }

    public double tiempoPromedioAgregarImplementacionHeap(){
        heapCP = new MaxHeapCP<>(CANTIDAD);

        Contador cont = new Contador();

        for (TravelTime tiempoDeViaje: muestraAleatorea) {
            heapCP.agregar(tiempoDeViaje);
        }
        double total = cont.duracion();

        return total / CANTIDAD;
    }

    public double tiempoPromedioSacarMaxImplementacionHeap(){
        heapCP = new MaxHeapCP<>(CANTIDAD);

        for (TravelTime tiempoDeViaje: muestraAleatorea) {
            heapCP.agregar(tiempoDeViaje);
        }

        Contador cont = new Contador();

        while (!heapCP.esVacia()){
            heapCP.sacarMax();
        }
        double total = cont.duracion();

        return total / CANTIDAD;
    }


    public double tiempoPromedioAgregarImplementacionCola(){
        colaCP = new MaxColaCP();

        Contador cont = new Contador();

        for (TravelTime tiempoDeViaje: muestraAleatorea) {
            colaCP.agregar(tiempoDeViaje);
        }
        double total = cont.duracion();

        return total / CANTIDAD;
    }

    public double tiempoPromedioSacarMaxImplementacionCola(){

        colaCP = new MaxColaCP();


        for (TravelTime tiempoDeViaje: muestraAleatorea) {
            colaCP.agregar(tiempoDeViaje);
        }

        Contador cont = new Contador();

        while (!colaCP.esVacia()){
            colaCP.sacarMax();
        }

        double total = cont.duracion();

        return total / CANTIDAD;
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



        public static void main(String[] args)
        {
            MVCModelo modelo = new MVCModelo ();
            modelo.loadTravelTimes();
            System.out.println("Tiempo de agregar para cola: " +modelo.tiempoPromedioAgregarImplementacionCola());
            System.out.println("Tiempo de sacar para cola: " +modelo.tiempoPromedioSacarMaxImplementacionCola());
            System.out.println("Tiempo de agregar para heap: " +modelo.tiempoPromedioAgregarImplementacionHeap());
            System.out.println("Tiempo de sacar para heap: " + modelo.tiempoPromedioSacarMaxImplementacionHeap());

        }

}
