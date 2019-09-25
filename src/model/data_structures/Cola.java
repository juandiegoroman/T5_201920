package model.data_structures;

public class Cola<E> implements ICola<E>, IColaIterable<E>
{
	private Nodo<E> primero;
	private Nodo<E> ultimo;
	private int tamano;
	
	public Cola()
	{
		tamano = 0;
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
	
	
	
	
	@Override
	//Agregar al final
	public void enqueu(E valor) 
	{
		if (estaVacia()) 
		{
			ultimo = new Nodo(valor, null,null);
			primero = ultimo;
		} 
		else 
		{

			Nodo<E> temp = ultimo;

			ultimo = new Nodo(valor, null, null);

			temp.insertarSiguiente(ultimo);
		}

		tamano++;
	}

	@Override
	// Sacar primero
	public E dequeue() 
	{
		Nodo<E> sacar = null;	
		if(tamano > 0)
		{
		sacar = primero;
		primero = primero.siguiente();
		}

		tamano--;

		return (sacar == null) ? null: sacar.valor();
	}

	
	
	@Override
	public int tamano() {
		return tamano;
	}

	@Override
	public boolean estaVacia() {
		return primero== null;
	}
	
	public Nodo<E> darUltimo()
	{
		return ultimo;
	}
	
	public Nodo<E> darPrimero()
	{
		return primero;
	}
	
	private class colaIterador implements IColaIterador<E>
	{

		private Nodo<E> actual;
		
		colaIterador()
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
	public IColaIterador<E> iterador() 
	{
		return new colaIterador();
		
	}
	
}
