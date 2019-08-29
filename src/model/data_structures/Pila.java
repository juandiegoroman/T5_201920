package model.data_structures;

public class Pila<E> implements IPila<E>, IPilaIterable<E>
{
	private Nodo<E> primero;
	private Nodo<E> ultimo;

	private int tamano;

	public Pila()
	{
		tamano = 0;
	}
	

	@Override
	//Se agrega al final
	public void agregar(E valor)
	{
		if (estaVacia()) 
		{
			ultimo = new Nodo(valor, null, null);
			primero = ultimo;
		} 
		else 
		{

			Nodo<E> temp = ultimo;

			ultimo = new Nodo(valor, null, temp);

			temp.insertarSiguiente(ultimo);
		}

		tamano++;
		
	}
	
	public Nodo<E> darUltimo()
	{
		return ultimo;
	}
	
	public Nodo<E> darPrimero()
	{
		return primero;
	}
	

	@Override
	//Sacar ultimo
	public E sacar() 
	{
		Nodo<E> temp = ultimo;

		if(tamano > 0)
		{
			ultimo = ultimo.anterior();

			if (ultimo != null) ultimo.insertarSiguiente(null);
		}
		tamano--;

		return (temp == null) ? null : temp.valor();
	}

	@Override
	public int tamano() {
		return tamano;
	}

	@Override
	public boolean estaVacia() {
		return primero== null;
	}
	
	private class pilaIterador implements IPilaIterador<E>
	{
		private Nodo<E> actual;
		
		pilaIterador() // constructor
		{
			actual = null;
		}

		@Override
		public boolean haySiguiente() {
			if(primero == null) return false;
			
			return (actual == null) ? true : actual.haySiguiente();

		}

		@Override
		public E siguiente() {
			
			if (actual == null) {
				actual = primero;
			}
	
			else {
	
				if(actual.haySiguiente()) actual = actual.siguiente();
	
			}
	
			return (actual != null) ? actual.valor() : null;
		}
		
	}

	@Override
	public IPilaIterador<E> iterador() {
	
		return new pilaIterador();
	}
	public E buscar(int indice) throws RuntimeException {
		
		int cont = 0;

		if (indice >= tamano)
			throw new RuntimeException("Desborde: " + indice);

		Nodo<E> actual = primero;

		while (cont < indice) {
			actual = actual.siguiente();
			cont++;
		}

		return actual.valor();

	}

}
