package model.logic;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.opencsv.CSVReader;

import model.data_structures.Cola;
import model.data_structures.IColaIterador;
import model.data_structures.Pila;


/**
 * Definicion del modelo del mundo
 *
 */
public class MVCModelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private Pila<Viaje> datosPila;
	
	private Cola<Viaje> datosCola;
	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public MVCModelo()
	{
		datosPila = new Pila();
		datosCola = new Cola();
	}
	
	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	
	
	public Cola<Viaje> clusterMayor(int hora)
	{
		Cola<Viaje> temp = null;
		Cola<Viaje> mayor = null;
		
		while(datosCola.darPrimero() != null)
		{
		if(datosCola.darPrimero().valor().darHora() < hora)
		{
			datosCola.dequeue();
			
		}
		else{
			temp.enqueu(datosCola.dequeue());
			while (temp.darUltimo().valor().darHora() < datosCola.darPrimero().valor().darHora() ) {

				temp.enqueu(datosCola.dequeue());
			}
			
			if (temp.tamano() > mayor.tamano()) {
				mayor = temp;
			}
			
			
		
		}
		
		
		}
		 return mayor;
	}
	
	public Cola<Viaje> viajesHoraDada(int n, int hora)
	{
		int cant = 0;
		Cola<Viaje> temp = null;
		while(datosPila.tamano() >0 && cant< n)
		{
			if(datosPila.darPrimero().valor().darHora() == hora)
			{
				temp.enqueu(datosPila.darPrimero().valor());
				cant++;
			}
		}
		return temp;
		
	}
	
	
	
	public void cargarDatos(String ruta){
		CSVReader reader = null;
		try {

			reader = new CSVReader(new FileReader(ruta));

			Iterator iter = reader.iterator();

			iter.next();

			while (iter.hasNext()){

				String[] parametros = (String[]) iter.next();

				Viaje v = crearViaje(parametros);

				agregar(v);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally{
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
			
			/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Viaje dato)
	{	
		datosPila.agregar(dato);
		datosCola.enqueu(dato);
	}

			public Viaje crearViaje(String[] datos )
			{
				return new Viaje(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Double.valueOf(datos[3]),Double.valueOf(datos[4]), Double.valueOf(datos[5]), Double.valueOf(datos[6]));
			}
			
			public Pila<Viaje> darDatosPila()
			{
				return datosPila;
			}

			public Cola<Viaje> darDatosCola()
			{
				return datosCola;
			}
	




}
