package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

public class MaxHeapCP<Key extends IComparable<Key>> implements IMaxCP<Key> {

    private Key[] pq;                    // store items at indices 1 to n

    private int n;                       // number of items on priority queue

    private Comparator<Key> comparator;  // optional comparator

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param initCapacity the initial capacity of this priority queue
     */

    public MaxHeapCP(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];

        n = 0;
    }

    /**
     * Initializes an empty priority queue.
     */

    public MaxHeapCP() {

        this(1);

    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * <p>
     * using the given comparator.
     *
     * @param initCapacity the initial capacity of this priority queue
     * @param comparator   the order in which to compare the keys
     */

    public MaxHeapCP(int initCapacity, Comparator<Key> comparator) {

        this.comparator = comparator;

        pq = (Key[]) new Object[initCapacity + 1];

        n = 0;

    }


    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param comparator the order in which to compare the keys
     */

    public MaxHeapCP(Comparator<Key> comparator) {

        this(1, comparator);

    }


    /**
     * Initializes a priority queue from the array of keys.
     * <p>
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param keys the array of keys
     */

    public MaxHeapCP(Key[] keys) {

        n = keys.length;

        pq = (Key[]) new Object[keys.length + 1];

        for (int i = 0; i < n; i++)

            pq[i + 1] = keys[i];

        for (int k = n / 2; k >= 1; k--)

            sink(k);

        assert isMaxHeap();

    }


    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     * <p>
     * {@code false} otherwise
     */

    public boolean esVacia() {

        return n == 0;

    }


    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */

    public int darNumElementos() {

        return n;

    }



    public Key darMax() {

        if (esVacia()) throw new RuntimeException();

        return pq[1];

    }


    // helper function to double the size of the heap array

    private void resize(int capacity) {

        assert capacity > n;

        Key[] temp = (Key[]) new Object[capacity];

        for (int i = 1; i <= n; i++) {

            temp[i] = pq[i];

        }
        pq = temp;
    }


    /**
     * Adds a new key to this priority queue.
     *
     * @param x the new key to add to this priority queue
     */

    public void agregar(Key x) {

        if (n == pq.length - 1) resize(2 * pq.length);

        pq[++n] = x;

        swim(n);

        assert isMaxHeap();

    }


    /**
     * Removes and returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue

     */

    public Key sacarMax() {

        if (esVacia()) throw new RuntimeException("Priority queue underflow");

        Key max = pq[1];

        exch(1, n--);

        sink(1);

        pq[n + 1] = null;     // to avoid loiteing and help with garbage collection

        if ((n > 0) && (n == (pq.length - 1) / 4)) resize(pq.length / 2);

        assert isMaxHeap();

        return max;

    }


    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/


    private void swim(int k) {

        while (k > 1 && less(k / 2, k)) {

            exch(k, k / 2);

            k = k / 2;

        }

    }


    private void sink(int k) {

        while (2 * k <= n) {

            int j = 2 * k;

            if (j < n && less(j, j + 1)) j++;

            if (!less(k, j)) break;

            exch(k, j);

            k = j;

        }

    }


    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/

    private boolean less(int i, int j) {

        if (comparator == null) {

            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;

        } else {

            return comparator.compare(pq[i], pq[j]) < 0;

        }

    }


    private void exch(int i, int j) {

        Key swap = pq[i];

        pq[i] = pq[j];

        pq[j] = swap;

    }


    // is pq[1..N] a max heap?

    private boolean isMaxHeap() {

        return isMaxHeap(1);

    }


    // is subtree of pq[1..n] rooted at k a max heap?

    private boolean isMaxHeap(int k) {

        if (k > n) return true;

        int left = 2 * k;

        int right = 2 * k + 1;

        if (left <= n && less(k, left)) return false;

        if (right <= n && less(k, right)) return false;

        return isMaxHeap(left) && isMaxHeap(right);

    }


    /***************************************************************************
     * Iterator.
     ***************************************************************************/


    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * <p>
     * in descending order.
     * <p>
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in descending order
     */

    public Iterator<Key> iterator() {

        return new HeapIterator();

    }


    private class HeapIterator implements Iterator<Key> {


        // create a new pq

        private MaxHeapCP<Key> copy;


        // add all items to copy of heap

        // takes linear time since already in heap order so no keys move

        public HeapIterator() {

            if (comparator == null) copy = new MaxHeapCP<Key>(darNumElementos());

            else copy = new MaxHeapCP<Key>(darNumElementos(), comparator);

            for (int i = 1; i <= n; i++)

                copy.agregar(pq[i]);

        }


        public boolean hasNext() {
            return !copy.esVacia();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }


        public Key next() {

            if (!hasNext()) throw new RuntimeException();

            return copy.sacarMax();
        }

    }

}
