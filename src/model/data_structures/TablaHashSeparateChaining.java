
package model.data_structures;

import java.util.Iterator;

public class TablaHashSeparateChaining<Key, Value> implements ITablasHash<Key,Value>
{

    private static final int INITIAL_CAP = 4;

    private int size;

    private int keysNumber;

    private LinkedKeyList<Key, Value>[] values;



    public TablaHashSeparateChaining() {

        this(INITIAL_CAP);

    }



    public TablaHashSeparateChaining(int keysNumber) {

        this.keysNumber = keysNumber;

        values = (LinkedKeyList<Key, Value>[]) new LinkedKeyList[keysNumber];

        for (int i = 0; i < keysNumber; i++)

            values[i] = new LinkedKeyList<Key, Value>();

    }


    private void resize(int chains) {

        TablaHashSeparateChaining<Key, Value> temp = new TablaHashSeparateChaining<Key, Value>(chains);

        for (int i = 0; i < keysNumber; i++) {


            Iterator<Key> iter = values[i].keys().iterator();

            while (iter.hasNext()) {
                Key key = iter.next();
                temp.put(key, values[i].get(key));
            }

        }

        this.keysNumber = temp.keysNumber;

        this.size = temp.size;

        this.values = temp.values;

    }



    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % keysNumber;

    }


    public int size() {

        return size;

    }




    public boolean isEmpty() {

        return size() == 0;

    }


    public boolean contains(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to contains() is null");

        return get(key) != null;

    }



    public Value get(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to get() is null");

        int i = hash(key);

        return values[i].get(key);

    }




    public void put(Key key, Value val) {

        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {

            delete(key);

            return;

        }


        if (size >= 5*keysNumber) resize(2* keysNumber);



        int i = hash(key);

        if (!values[i].contains(key)) size++;

        values[i].put(key, val);

    }


    public Value delete(Key key) {

    	Value rta= null;
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");



        int i = hash(key);

        if (values[i].contains(key)) size--;
        rta= values[i].get(key);
        values[i].delete(key);


        if (keysNumber > INITIAL_CAP && size <= 2* keysNumber) resize(keysNumber /2);

        
        
        return rta;
    }


    public Iterator<Key> keys() {

        ListaEncadenada<Key> lista = new ListaEncadenada<>();

        for (int i = 0; i < keysNumber; i++) {

            Iterator<Key> iter = values[i].keys().iterator();

            while (iter.hasNext()) {
                Key key = iter.next();
                lista.insertarFinal(key);
            }
        }

        return (Iterator<Key>) lista;

    }

}

