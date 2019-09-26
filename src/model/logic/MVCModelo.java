package model.logic;


import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.opencsv.CSVReader;

import controller.Controller;
import model.data_structures.MaxColaCP;
import model.data_structures.IColaIterador;



/**
 * Definicion del modelo del mundo
 */
public class MVCModelo {
    /**
     * Atributos del modelo del mundo
     */


    private MaxColaCP<Viaje> datosCola;

    /**
     * Constructor del modelo del mundo con capacidad predefinida
     */
    public MVCModelo() {

        datosCola = new MaxColaCP();
    }

    /**
     * Constructor del modelo del mundo con capacidad dada
     *
     * @param tamano
     */


    public MaxColaCP<Viaje> clusterMayor(int hora) {
        MaxColaCP<Viaje> temp = new MaxColaCP<>();
        MaxColaCP<Viaje> mayor = new MaxColaCP<>();

        while (datosCola.darNumElementos() > 0) {
            if (datosCola.darPrimero().valor().darHora() < hora) {
                datosCola.sacarMax();

            } else {

                temp.agregar(datosCola.sacarMax());

                while (datosCola.darNumElementos() > 0 && temp.darUltimo().valor().darHora() < datosCola.darPrimero().valor().darHora()) {

                    temp.agregar(datosCola.sacarMax());
                }

                if (temp.darNumElementos() > mayor.darNumElementos()) {
                    mayor = temp;
                }

            }

        }
        return mayor;
    }




    public void cargarDatos(String ruta) {
        CSVReader reader = null;
        try {

            reader = new CSVReader(new FileReader(ruta));

            Iterator iter = reader.iterator();

            iter.next();

            while (iter.hasNext()) {

                String[] parametros = (String[]) iter.next();

                Viaje v = crearViaje(parametros);

                agregar(v);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    
    public void generarMuestra(int cantidad)
    {
    	int inicial=0;
    	double List[] = new double [cantidad];
    	List[inicial] = datosCola.darPrimero().valor().darTiempoPromedio();
    	for(int i= 1; i<cantidad; i++)
    	{
    		List[i]= datosCola.darPrimero().siguiente().valor().darTiempoPromedio();
    		for(int j=0; j<1; j++)
    		{
    			if(List[i] == List[j])
    			{
    				i--;
    			}
    		}
    	}
    }

    /**
     * Requerimiento de agregar dato
     *
     * @param dato
     */
    public void agregar(Viaje dato) {
        datosCola.agregar(dato);
    }

    public Viaje crearViaje(String[] datos) {
        return new Viaje(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Double.valueOf(datos[3]), Double.valueOf(datos[4]), Double.valueOf(datos[5]), Double.valueOf(datos[6]));
    }



    public MaxColaCP<Viaje> darDatosCola() {
        return datosCola;
    }

    public static void main(String[] args)
    {
        MVCModelo m = new MVCModelo();
        m.cargarDatos(Controller.DATOS_PRIMER_SEMESTRE);
    }

}
