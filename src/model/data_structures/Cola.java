package model.data_structures;

public class Cola<E extends Comparable<E>> implements IMaxCP<E>, IColaIterable<E> 
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
	
	
	
	
	
	//Agregar al final
	@Override
	public void agregar(E valor) 
	{
		if (esVacia()) 
		{
			ultimo = new Nodo(valor, null,null);
			primero = ultimo;
		} 
		else if(ultimo.valor().compareTo(valor) > 0)
		{

			Nodo<E> temp = ultimo;

			ultimo = new Nodo(valor, null, null);
			
			temp.insertarSiguiente(ultimo);
		}
		else
		{
			Nodo<E> temp = ultimo.anterior();
			while(temp.valor().compareTo(valor) < 0)
			{
				temp =temp.anterior();
				
			}
			
			
			Nodo<E> nuevo = new Nodo(valor,temp.siguiente(),temp);
			
			temp.insertarSiguiente(nuevo);
			nuevo.siguiente().insertarAnterior(nuevo);
		}

		tamano++;
	}

	
	// Sacar primero
	@Override
	public E sacarMax() 
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
	public int darNumElementos() {
		return tamano;
	}

	@Override
	public boolean esVacia() {
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


	@Override
	public E darMax() {
		
		return ultimo.valor();
	}


	
}
