
package model.data_structures;



public class TablaHashSeparateChaining<Key, Value> {

    private static final int CAPACIDAD_INICIAL = 4;

    private int n;

    private int m;

    private ArrListas<Key, Value>[] st;



    public TablaHashSeparateChaining() {

        this(CAPACIDAD_INICIAL);

    }



    public TablaHashSeparateChaining(int m) {

        this.m = m;

        st = (ArrListas<Key, Value>[]) new ArrListas[m];

        for (int i = 0; i < m; i++)

            st[i] = new ArrListas<Key, Value>();

    }

    
    private void resize(int chains) {

        TablaHashSeparateChaining<Key, Value> temp = new TablaHashSeparateChaining<Key, Value>(chains);

        for (int i = 0; i < m; i++) {

            for (Key key : st[i].keys()) {

                temp.put(key, st[i].get(key));

            }

        }

        this.m  = temp.m;

        this.n  = temp.n;

        this.st = temp.st;

    }



    // hash value between 0 and m-1

    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % m;

    }



    /**

     * Returns the number of key-value pairs in this symbol table.

     *

     * @return the number of key-value pairs in this symbol table

     */

    public int size() {

        return n;

    }



    /**

     * Returns true if this symbol table is empty.

     *

     * @return {@code true} if this symbol table is empty;

     *         {@code false} otherwise

     */

    public boolean isEmpty() {

        return size() == 0;

    }



    /**

     * Returns true if this symbol table contains the specified key.

     *

     * @param  key the key

     * @return {@code true} if this symbol table contains {@code key};

     *         {@code false} otherwise

     * @throws IllegalArgumentException if {@code key} is {@code null}

     */

    public boolean contains(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to contains() is null");

        return get(key) != null;

    }



    /**

     * Returns the value associated with the specified key in this symbol table.

     *

     * @param  key the key

     * @return the value associated with {@code key} in the symbol table;

     *         {@code null} if no such value

     * @throws IllegalArgumentException if {@code key} is {@code null}

     */

    public Value get(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to get() is null");

        int i = hash(key);

        return st[i].get(key);

    }



    /**

     * Inserts the specified key-value pair into the symbol table, overwriting the old

     * value with the new value if the symbol table already contains the specified key.

     * Deletes the specified key (and its associated value) from this symbol table

     * if the specified value is {@code null}.

     *

     * @param  key the key

     * @param  val the value

     * @throws IllegalArgumentException if {@code key} is {@code null}

     */

    public void put(Key key, Value val) {

        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {

            delete(key);

            return;

        }



        // double table size if average length of list >= 10

        if (n >= 10*m) resize(2*m);



        int i = hash(key);

        if (!st[i].contains(key)) n++;

        st[i].put(key, val);

    }



    /**

     * Removes the specified key and its associated value from this symbol table

     * (if the key is in this symbol table).

     *

     * @param  key the key

     * @throws IllegalArgumentException if {@code key} is {@code null}

     */

    public void delete(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to delete() is null");



        int i = hash(key);

        if (st[i].contains(key)) n--;

        st[i].delete(key);



        // halve table size if average length of list <= 2

        if (m > CAPACIDAD_INICIAL && n <= 2*m) resize(m/2);

    }



    // return keys in symbol table as an Iterable

    public Iterable<Key> keys() {

        ListaEncadenada<Key> lista = new ListaEncadenada<>();

        for (int i = 0; i < m; i++) {

            for (Key key : st[i].keys())

                lista.insertarFinal(key);

        }

        return lista;

    }

}

