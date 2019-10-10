package view;

import model.logic.MVCModelo;

public class MVCView 
{
	    /**
	     * Metodo constructor
	     */
	    public MVCView()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Cargar los tiempos de viajes");
			System.out.println("2. Buscar por trimestre, origen y destino ");
			System.out.println("3. Dar tiempos de consultas aleatoreas");
		}
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
}
