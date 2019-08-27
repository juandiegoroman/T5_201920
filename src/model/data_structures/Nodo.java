package model.data_structures;

public class Nodo<E> {

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

    public void insertar(Nodo<E> nodo)
    {
        siguiente = nodo;
    }
    
    

    public boolean haySiguiente(){

        return siguiente != null;
    }







}