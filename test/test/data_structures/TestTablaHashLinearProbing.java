package test.data_structures;

import model.data_structures.TablaHashLinearProbing;

public class TestTablaHashLinearProbing 
{

	TablaHashLinearProbing<String,Integer> hashtable;
	
	
	public void setUpEscenario0()
	{
		hashtable= new TablaHashLinearProbing<String, Integer>(10);
		
	}
	public void setUpEscenario1() {
		setUpEscenario0();
		hashtable.put("Antonio", 10000000);
		hashtable.put("Cesar", 20000000);
		hashtable.put("Alberto", 30000000);
		hashtable.put("Daniel", 40000000);
		hashtable.put("Ricardo", 50000000);
	}
	
	public void setUpEscenario2() {
		hashtable = new TablaHashLinearProbing<String, Integer>(5);

	}  
	public void testPut()
	{
setUpEscenario0();
		
		hashtable.put("Antonio", 10000000);
		hashtable.put("Cesar", 20000000);
		hashtable.put("Alberto", 30000000);
		hashtable.put("Daniel", 40000000);
		hashtable.put("Ricardo", 50000000);
	

	}
	
	
	public void testDelete()
	{
setUpEscenario1();
		
		int int1 = hashtable.delete("Antonio");
		
		assertTrue(int1 == 10000000);
		
		assertTrue(hashtable.size() == 0);

	}
	
	
	public void testRehash()
	{
		setUpEscenario2();
		assertTrue(hashtable.size() == 5); 

		hashtable.put("Bayona", 4000);
		hashtable.put("Carlos", 6000);
		hashtable.put("Andres", 1000);
		hashtable.put("Andrea", 7000);
		hashtable.put("Camilo", 2000);
		hashtable.put("Santiago", 8000);



		assertTrue(hashtable.size() == 10);
	}
	
	private void assertTrue(boolean b) {
		
	}
	
}
