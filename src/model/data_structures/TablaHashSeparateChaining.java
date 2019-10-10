
package model.data_structures;

import java.util.Iterator;

public class TablaHashSeparateChaining<Key, Value> implements ITablasHash<Key,Value>
{

    private static final int INITIAL_CAP = 4;

    private int keysNumber;

    private int size;

    private LinkedKeyList<Key, Value>[] values;



    public TablaHashSeparateChaining() {

        this(INITIAL_CAP);

    }



    public TablaHashSeparateChaining(int size) {

        this.size = size;

        values = (LinkedKeyList<Key, Value>[]) new LinkedKeyList[size];

        for (int i = 0; i < size; i++)

            values[i] = new LinkedKeyList<Key, Value>();

    }


    private void resize(int chains) {

        TablaHashSeparateChaining<Key, Value> temp = new TablaHashSeparateChaining<Key, Value>(chains);

        for (int i = 0; i < size; i++) {


            Iterator<Key> iter = values[i].keys().iterator();

            while (iter.hasNext()) {
                Key key = iter.next();
                temp.put(key, values[i].get(key));
            }

        }

        this.size = temp.size;

        this.keysNumber = temp.keysNumber;

        this.values = temp.values;

    }



    private int hash(Key key) {

        return (key.hashCode() & 0x7fffffff) % size;

    }


    public int size() {

        return size;

    }

    public int numKeys() {
        return keysNumber;
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


        if (keysNumber >= 5* size) resize(2* size);



        int i = hash(key);

        if (!values[i].contains(key)) keysNumber++;

        values[i].put(key, val);

    }


    public Value delete(Key key) {

    	Value rta= null;
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");



        int i = hash(key);

        if (values[i].contains(key)) keysNumber--;
        rta= values[i].get(key);
        values[i].delete(key);


        if (size > INITIAL_CAP && keysNumber <= 2* size) resize(size /2);

        
        
        return rta;
    }


    public Iterator<Key> keys() {

        ListaEncadenada<Key> lista = new ListaEncadenada<>();

        for (int i = 0; i < size; i++) {

            Iterator<Key> iter = values[i].keys().iterator();

            while (iter.hasNext()) {
                Key key = iter.next();
                lista.insertarFinal(key);
            }
        }

        return (Iterator<Key>) lista;

    }

}

