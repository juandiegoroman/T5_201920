package model.data_structures;

import java.util.Iterator;

public class MaxHeapCP<T extends Comparable<T>> implements IMaxCP<T> {

    private T[] colaPrioridad;

    private int n;


    public MaxHeapCP(int initCapacity) {
        colaPrioridad = (T[]) new Object[initCapacity + 1];

        n = 0;
    }

    public MaxHeapCP() {

        this(1);

    }


    public boolean esVacia() {

        return n == 0;
    }


    public int darNumElementos() {
        return n;
    }

    public T darMax() {

        if (esVacia()) throw new RuntimeException();

        return colaPrioridad[1];

    }

    private void ajustarTamano(int capacidad) {

        assert capacidad > n;

        T[] temp = (T[]) new Object[capacidad];

        for (int i = 1; i <= n; i++) {

            temp[i] = colaPrioridad[i];

        }
        colaPrioridad = temp;
    }


    public void agregar(T x) {

        if (n == colaPrioridad.length - 1) ajustarTamano(2 * colaPrioridad.length);

        colaPrioridad[++n] = x;

        swim(n);

        assert validar();

    }



    public T sacarMax() {

        if (esVacia()) throw new RuntimeException("No hay elementos");

        T max = colaPrioridad[1];

        intercambiar(1, n--);

        sink(1);

        colaPrioridad[n + 1] = null;

        if ((n > 0) && (n == (colaPrioridad.length - 1) / 4)) ajustarTamano(colaPrioridad.length / 2);

        assert validar();

        return max;

    }



    private void swim(int k) {

        while (k > 1 && menor(k / 2, k)) {

            intercambiar(k, k / 2);

            k = k / 2;

        }

    }


    private void sink(int k) {

        while (2 * k <= n) {

            int j = 2 * k;

            if (j < n && menor(j, j + 1)) j++;

            if (!menor(k, j)) break;

            intercambiar(k, j);

            k = j;

        }

    }



    private boolean menor(int i, int j) {

            return ((Comparable<T>) colaPrioridad[i]).compareTo(colaPrioridad[j]) < 0;

    }


    private void intercambiar(int i, int j) {

        T swap = colaPrioridad[i];

        colaPrioridad[i] = colaPrioridad[j];

        colaPrioridad[j] = swap;

    }


    private boolean validar() {

        return validar(1);

    }

    private boolean validar(int k) {

        if (k > n) return true;

        int left = 2 * k;

        int right = 2 * k + 1;

        if (left <= n && menor(k, left)) return false;

        if (right <= n && menor(k, right)) return false;

        return validar(left) && validar(right);

    }


    public Iterator<T> iterador() {

        return new HeapIterator();

    }


    private class HeapIterator implements Iterator<T> {


        // create a new pq

        private MaxHeapCP<T> copia;


        // add all items to copy of heap

        // takes linear time since already in heap order so no keys move

        public HeapIterator() {

             copia = new MaxHeapCP<T>(darNumElementos());

            for (int i = 1; i <= n; i++)

                copia.agregar(colaPrioridad[i]);

        }

        public boolean hasNext() {
            return !copia.esVacia();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }


        public T next() {

            if (!hasNext()) throw new RuntimeException();

            return copia.sacarMax();
        }

    }

}
