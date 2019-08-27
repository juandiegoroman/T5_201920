package model.logic;

public class Viaje implements Comparable<Viaje>
{

	private int idOrigen;

    private int idDestino;

    private int mes;
    
    private int hora;

    private double tiempoPromedio;

    private double desviacionEstandar;

    private double promedioGeometrico;

    private double desviacionGeometrica;

    public Viaje(int idOrigen, int idDestino, int mes,int hora, double tiempoPromedio, double desviacionEstandar, double promedioGeometrico, double desviacionGeometrica) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.mes = mes;
        this.hora = hora;
        this.tiempoPromedio = tiempoPromedio;
        this.desviacionEstandar = desviacionEstandar;
        this.promedioGeometrico = promedioGeometrico;
        this.desviacionGeometrica = desviacionGeometrica;
    }
    
    
    public int darMes()
    {
    	return mes;
    }
    public int darHora()
    {
    	return hora;
    }

    public int darIdOrigen()
    {
    	return idOrigen;
    }

    public int darIdDestino() {
        return idDestino;
    }

    public double darTiempoPromedio() {
        return tiempoPromedio;
    }

    public double darDesviacionEstandar() {
        return desviacionEstandar;
    }

    public double darPromedioGeometrico() {
        return promedioGeometrico;
    }

    public double darDesviacionGeometrica() {
        return desviacionGeometrica;
    }
	
	
	@Override
	public int compareTo(Viaje v) {

		return 0;
	}

	   public String toString(){

	        return "" + idOrigen;
	    }
}
