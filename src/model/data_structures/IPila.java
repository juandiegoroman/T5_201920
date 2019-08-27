package model.data_structures;

public interface IPila<E>
{
	void agregar(E valor);

	
	E sacar();

	int tamano();

	boolean estaVacia();
	
}
