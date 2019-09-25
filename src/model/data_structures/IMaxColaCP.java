package model.data_structures;

public interface IMaxColaCP<Key extends IComparable<Key>>
{
	void agregar(Key v) ;
	Key darMax() ;
	Key sacarMax() ;
	boolean esVacia() ;
	int darNumElementos();
}
