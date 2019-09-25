package model.data_structures;

public interface IMaxCP<Key extends Comparable<Key>>
{
	void agregar(Key v) ;
	Key darMax() ;
	Key sacarMax() ;
	boolean esVacia() ;
	int darNumElementos();
}
