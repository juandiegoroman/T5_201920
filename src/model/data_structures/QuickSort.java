package model.data_structures;



import java.util.Random;

public class QuickSort {


    private static void mezclar(Object[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {

            Random random = new Random();
            int r = i + random.nextInt(n - i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }


    private static int dividir(Comparable[] a, int inferior, int superior)
    {
        int i = inferior, j = superior + 1;
        Comparable v = a[inferior];
        while (true)
        {
            while (esMenor(a[++i], v)) if (i == superior) break;
            while (esMenor(v, a[--j])) if (j == inferior) break;
            if (i >= j) break;
            intercambiar(a, i, j);
        }
        intercambiar(a, inferior, j);
        return j;
    }

    private static void intercambiar(Comparable[] arreglo, int i, int j) {
        Comparable temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    private static boolean esMenor(Comparable elemento1, Comparable elemento2){
        return elemento1.compareTo(elemento2) < 0;
    }


    private static void ordenarAux(Comparable[] arr, int inf, int sup) {
        if (inf < sup) {
            int pi = dividir(arr, inf, sup);
            ordenarAux(arr, inf, pi - 1);
            ordenarAux(arr, pi + 1, sup);
        }

    }

    public static void ordenar(Comparable[] arr){
        mezclar(arr);
        ordenarAux(arr, 0, arr.length -1);
    }
}
