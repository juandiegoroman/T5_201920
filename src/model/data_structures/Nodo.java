package model.data_structures;

public class Nodo<E extends Comparable<E>>
{

    private E valor;

    private Nodo<E> siguiente;
    
    private Nodo<E> anterior;

    Nodo(E elemento, Nodo<E> siguiente, Nodo<E> anterior) {
        this.valor = elemento;
        this.siguiente = siguiente;
        this.anterior= anterior;
    }


    public Nodo<E> siguiente() {
        return siguiente;
    }

    public Nodo<E> anterior()
    {
    return anterior;	
    }
    
    public E valor() {
        return valor;
    }

    public void insertarSiguiente(Nodo<E> nodo)
    {
        siguiente = nodo;
    }
    public void insertarAnterior(Nodo<E> nodo)
    {
    	anterior = nodo;
    }
    
    

    public boolean haySiguiente(){

        return siguiente != null;
    }







}