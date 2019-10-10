package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import com.opencsv.CSVReader;

import model.data_structures.*;


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
    public final static int CANTIDAD_EXISTENTE = 30;
    public final static int CANTIDAD_NO_EXISTENTE = 10;

    private ListaEncadenada<TravelTime> datos;
    private TablaHashSeparateChaining<String, ListaEncadenada<TravelTime>> tablaHashSC;
    private TablaHashLinearProbing<String, ListaEncadenada<TravelTime>> tablaHashLP;


    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public MVCModelo() {

        datos = new ListaEncadenada<TravelTime>();
    }


    public String loadTravelTimes(){

        String info =   cargar(DATOS_PRIMER_SEMESTRE, 1) + cargar(DATOS_SEGUNDO_SEMESTRE, 2)+ cargar(DATOS_TERCER_SEMESTRE, 3) + cargar(DATOS_CUARTO_SEMESTRE, 4);

        info+=  "Linear Probing: "+cargarTablaLinearProbing() + "\n";
       info+= "Separate chaining: "+cargarTablaHashSeparateChaining() + "\n";

        return info;
    }

    public String cargar(String ruta, int trimestre) {

       TravelTime primero = null;
       int cant = datos.tamano();

        CSVReader reader = null;
        try {

            reader = new CSVReader(new FileReader(ruta));

            Iterator iter = reader.iterator();

            iter.next();

            while (iter.hasNext()) {

                String[] parametros = (String[]) iter.next();

                TravelTime v = crearViaje(parametros, trimestre);

                if (primero == null) primero =v;

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

        return  "Para el  trimestre " + trimestre + " del 2018 se tiene la siguiente informacion: \n" + "Primer viaje: Origen: " +primero.darSourceId()+ ", Destino: " + primero.darDistId() + ", Dia: " + primero.darDow() + ", Tiempo promedio: " + primero.darMeanTravelTime() + "\n" +

          "Ultimo viaje: Origen: " + datos.ultimo().darSourceId() + ", Destino: " + datos.ultimo().darDistId() + ", Dia: " + datos.ultimo().darDow() + ", Tiempo promedio: " + datos.ultimo().darMeanTravelTime() + "\n " +
               "Total de viajes leidos: " + (datos.tamano() - cant) + "\n \n";

    }


    public  TravelTime[] generarMuestraAleatorea(){

        TravelTime[] arr = convertirAArreglo(datos);
        TravelTime[] arr2 = new TravelTime[CANTIDAD_EXISTENTE + CANTIDAD_NO_EXISTENTE];

        mezclarAleatoreamente(arr);

        int cont = 0;

        for (int i = 0; i < CANTIDAD_EXISTENTE; i++) {
            arr2[i] = arr[i];
        }

        for (int i = CANTIDAD_EXISTENTE; i < CANTIDAD_NO_EXISTENTE + CANTIDAD_EXISTENTE; i++) {
            arr2[i] = new TravelTime(-10 *(int) Math.random(), -10 * (int)Math.random(),-10 *(int) Math.random(),-10 * (int)Math.random(),-10 * Math.random(),-10 * Math.random());
        }

        mezclarAleatoreamente(arr2);

        return arr2;
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

        Iterator<TravelTime> iter = lista.iterator();

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


    public String cargarTablaHashSeparateChaining(){

        String res = "";

        tablaHashSC = new TablaHashSeparateChaining<>(100);

        int tamanoInicial = tablaHashSC.size();
        int numRehashes = 0;

        Iterator<TravelTime> iter = datos.iterator();

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

            int tamanoTemp = tablaHashSC.size();

            tablaHashSC.put(darLlave(actual), lista);

            if (tamanoTemp != tablaHashSC.size())  numRehashes++;
        }

        res = "N: " + tablaHashSC.numKeys() + ", M Inicial: " + tamanoInicial + ", M Final: " + tablaHashSC.size() + ", N/M: " + (double)tablaHashSC.numKeys()/ (double)tablaHashSC.size() + ", Num Rehashes: " + numRehashes;

        return  res;


    }

    public String cargarTablaLinearProbing(){

        String res = "";

        tablaHashLP = new TablaHashLinearProbing<>(100);

        int tamanoInicial = tablaHashLP.size();
        int numRehashes = 0;

        Iterator<TravelTime> iter = datos.iterator();

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

            int tamanoTemp = tablaHashLP.size();

            tablaHashLP.put(darLlave(actual), lista);

            if (tamanoTemp != tablaHashLP.size())  numRehashes++;
        }

        res = "N: " + tablaHashLP.numKeys() + ", M Inicial: " + tamanoInicial + ", M Final: " + tablaHashLP.size() + ", N/M: " + (double)tablaHashLP.numKeys()/ (double)tablaHashLP.size() + ", Num Rehashes: " + numRehashes;

   return  res;
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
        if (lista != null) lista.ordenarPorMergeSort(new ComparadorPorDia());

        return lista;

    }


    public ListaEncadenada<TravelTime> buscarPorTrimestreOrigenYDestinoLP(int tri, int org, int dst) {

        ListaEncadenada<TravelTime> lista = tablaHashLP.get(tri + "-" + org + "-" + dst);
        if (lista != null) lista.ordenarPorMergeSort(new ComparadorPorDia());

        return lista;
    }






    public ListaEncadenada<TravelTime> darDatos() {
        return datos;
    }




    public String tiemposGetSC(){

        TravelTime[] muestraAleatorea = generarMuestraAleatorea();

        double max = 0;
        double min = Double.MAX_VALUE;
        double total = 0;
        double promedio = 0;

        for (TravelTime tiempoDeViaje: muestraAleatorea) {

            Contador cont = new Contador();
            tablaHashSC.get(darLlave(tiempoDeViaje));
            double contTemp = cont.duracion();

            if (contTemp > max) max = contTemp;

            if (contTemp < min) min = contTemp;

            total+= contTemp;

        }

        promedio = total / (CANTIDAD_NO_EXISTENTE + CANTIDAD_EXISTENTE);

        return "Max: " + max + " ms,  Min: " + min + " ms, Prom: " + promedio +" ms";
    }

    public String tiemposGetLP(){

        TravelTime[] muestraAleatorea = generarMuestraAleatorea();

        double max = 0;
        double min = Double.MAX_VALUE;
        double total = 0;
        double promedio = 0;

        for (TravelTime tiempoDeViaje: muestraAleatorea) {

            Contador cont = new Contador();
            tablaHashLP.get(darLlave(tiempoDeViaje));
            double contTemp = cont.duracion();

            if (contTemp > max) max = contTemp;

            if (contTemp < min) min = contTemp;

            total+= contTemp;

        }

        promedio = total / (CANTIDAD_NO_EXISTENTE + CANTIDAD_EXISTENTE);

        return "Max: " + max + " ms,  Min: " + min + " ms, Prom: " + promedio +" ms";
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
            modelo.cargarTablaHashSeparateChaining();
            modelo.cargarTablaLinearProbing();
            ListaEncadenada lista = modelo.buscarPorTrimestreOrigenYDestinoLP(1,1,2);
            System.out.println("Tiempo de agregar para SC Hash Table: " +modelo.tiemposGetSC());
            System.out.println("Tiempo de agregar para LC Hash Table: " +modelo.tiemposGetLP());

        }

}
