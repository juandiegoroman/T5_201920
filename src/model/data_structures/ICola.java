package model.data_structures;

public interface ICola<E>
{

		void enqueu(E valor);
		
		

		E buscar(int indice) throws Exception;
		
		E dequeue();

		int tamano();

		boolean estaVacia();

}
