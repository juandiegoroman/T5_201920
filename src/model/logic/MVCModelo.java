package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import com.opencsv.CSVReader;

import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;
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
    public final static int CANTIDADE_EXISTENTE = 8000;
    public final static int CANTIDAD_NO_EXISTENTE = 2000;

    private ListaEncadenada<TravelTime> datos;
    private TravelTime[] muestraAleatorea;

    private TablaHashSeparateChaining<String, ListaEncadenada<TravelTime>> tablaHashSC;
    private TablaHashLinearProbing<String, ListaEncadenada<TravelTime>> tablaHashLP;


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


    public  TravelTime[] generarMuestraAleatorea(){

        TravelTime[] arr = convertirAArreglo(datos);
        TravelTime[] arr2 = new TravelTime[CANTIDADE_EXISTENTE + CANTIDAD_NO_EXISTENTE];

        mezclarAleatoreamente(arr);

        int cont = 0;

        for (int i = 0; i < CANTIDADE_EXISTENTE ; i++) {

        }



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








    private TravelTime[] convertirAArreglo(ListaEncadenada<TravelTime> lista) {

        TravelTime[] arr = new TravelTime[lista.tamano()];

        int cont = 0;

        Iterator<TravelTime> iter = lista.iterador();

        while(iter.hasNext())
        {
            TravelTime actual = iter.next();
            arr[cont] =  actual;
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

    public String darLlave(TravelTime viaje){

        return viaje.darTimestre() + "-" + viaje.darSourceId() + "-" + viaje.darDistId();
    }


    public void cargarTablaHashSeparateChaining(){

        tablaHashSC = new TablaHashSeparateChaining<>();


        Iterator<TravelTime> iter = datos.iterador();

        while(iter.hasNext())
        {
            TravelTime actual = iter.next();

            ListaEncadenada<TravelTime> lista = tablaHashSC.get(darLlave(actual));

            if (lista == null) {
                lista = new ListaEncadenada<>();
                lista.insertarFinal(actual);
            }
            else {
                lista.insertarFinal(actual);
            }

            tablaHashSC.put(darLlave(actual), lista);
        }

    }

    public void cargarTablaLinearProbing(){

        tablaHashLP = new TablaHashLinearProbing<>();

        Iterator<TravelTime> iter = datos.iterador();

        while(iter.hasNext())
        {
            TravelTime actual = iter.next();

            ListaEncadenada<TravelTime> lista = tablaHashLP.get(darLlave(actual));

            if (lista == null) {
                lista = new ListaEncadenada<>();
                lista.insertarFinal(actual);
            }
            else {
                lista.insertarFinal(actual);
            }

            tablaHashLP.put(darLlave(actual), lista);
        }

    }


    private class ComparadorPorDia implements Comparator<TravelTime> {
        @Override
        public int compare(TravelTime o1, TravelTime o2) {
            if (o1.darDow() < o2.darDow()) return -1;
            if (o1.darDow() > o2.darDow()) return 1;
            else return 0;
        }
    }


    public ListaEncadenada<TravelTime> buscarPorTrimestreOrigenYDestinoSC(int tri, int org, int dst) {

        ListaEncadenada<TravelTime> lista = tablaHashSC.get(tri + "-" + org + "-" + dst);
        lista.ordenarPorMergeSort(new ComparadorPorDia());

        return lista;

    }


    public ListaEncadenada<TravelTime> buscarPorTrimestreOrigenYDestinoLP(int tri, int org, int dst) {

        ListaEncadenada<TravelTime> lista = tablaHashLP.get(tri + "-" + org + "-" + dst);
        lista.ordenarPorMergeSort(new ComparadorPorDia());

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
