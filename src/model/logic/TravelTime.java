package model.logic;

public class TravelTime implements Comparable <TravelTime>
{

	private int trimestre;
	
	private int sourceId;
	
	private int distId;
	
	private int hod;
	
	private double mean_travel_time;
	
	private double standard_deviation_travel_time;

	public TravelTime(int trimestre, int sourceId, int distId, int hod, double mean_travel_time, double standard_deviation_travel_time) {
		this.trimestre = trimestre;
		this.sourceId = sourceId;
		this.distId = distId;
		this.hod = hod;
		this.mean_travel_time = mean_travel_time;
		this.standard_deviation_travel_time = standard_deviation_travel_time;
	}

	public int darTimestre()
	{
		return trimestre;
	}
	public int darSourceId()
	{
		return sourceId;
	}
	public int darDistId()
	{
		return distId;
	}
	public int darHod()
	{
		return hod;
	}
	public double darMeanTravelTime()
	{
		return mean_travel_time;
	}
	public double darStandardDeviationTravelTIme()
	{
		return standard_deviation_travel_time;
	}

	@Override
	public int compareTo(TravelTime o) 
	{
		int rta = 0;
		
		if(mean_travel_time > o.darMeanTravelTime())
		{
			rta = 1;
		}
		else if (mean_travel_time < o.darMeanTravelTime())
		{
			rta = -1;
		}		
		return rta;
	}
	
	
}
