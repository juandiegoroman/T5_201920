package model.data_structures;


public class LinkedKeyList<Key, Value> {

    private int n;

    private Node first;





    private class Node {

        private Key key;

        private Value val;

        private Node next;



        public Node(Key key, Value val, Node next)  {

            this.key  = key;

            this.val  = val;

            this.next = next;

        }

    }




    public LinkedKeyList() {

    }




    public int size() {

        return n;

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

        for (Node x = first; x != null; x = x.next) {

            if (key.equals(x.key))

                return x.val;

        }

        return null;

    }




    public void put(Key key, Value val) {

        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {

            delete(key);

            return;

        }



        for (Node x = first; x != null; x = x.next) {

            if (key.equals(x.key)) {

                x.val = val;

                return;

            }

        }

        first = new Node(key, val, first);

        n++;

    }




    public void delete(Key key) {

        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        first = delete(first, key);

    }



    private Node delete(Node x, Key key) {

        if (x == null) return null;

        if (key.equals(x.key)) {

            n--;

            return x.next;

        }

        x.next = delete(x.next, key);

        return x;

    }





    public Iterable<Key> keys()  {

        ListaEncadenada<Key> lista = new ListaEncadenada<>();

        for (Node x = first; x != null; x = x.next)

            lista.insertarFinal(x.key);

        return lista;

    }

}

