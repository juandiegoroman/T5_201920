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
			System.out.println("1. Cargar los viajes agregados por hora de el primer semestre");
			System.out.println("2. Ingresar una hora para obterner el grupo de viajes consecutivo mas grande");
			System.out.println("3. Ingrese una hora para obtener una N cantidad de viajes con dicha hora");
		}
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
}
