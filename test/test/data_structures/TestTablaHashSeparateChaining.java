package test.data_structures;

import model.data_structures.TablaHashSeparateChaining;

public class TestTablaHashSeparateChaining 
{
	TablaHashSeparateChaining<String, Integer> hashTable;
	
	public void setUpEscenario0() {
		hashTable = new TablaHashSeparateChaining<String, Integer>(10);
	}  
	
	public void setUpEscenario1() {
		setUpEscenario0();
		hashTable.put("Antonio", 10000000);
		hashTable.put("Cesar", 20000000);
		hashTable.put("Alberto", 30000000);
		hashTable.put("Daniel", 40000000);
		hashTable.put("Ricardo", 50000000);
	}
	public void setUpEscenario2() {
		hashTable = new TablaHashSeparateChaining<String, Integer>(2);


	}
	
	public void testPut()
	{
setUpEscenario0();
		
		hashTable.put("Antonio", 10000000);
		hashTable.put("Cesar", 20000000);
		hashTable.put("Alberto", 30000000);
		hashTable.put("Daniel", 40000000);
		hashTable.put("Ricardo", 50000000);
	

	}
	public void testGet() {
		setUpEscenario1();
		
		int int1 = hashTable.get("Antonio");
	
		
		
		assertTrue(int1 == 10000000);
		

	}
	public void testDelete() {
		setUpEscenario1();
		
		int int1 = hashTable.delete("Antonio");
		
		
		assertTrue(int1 == 10000000);
		
		
		assertTrue(hashTable.size() == 0);

	}
	public void testRehash() {
		setUpEscenario2();
	
		assertTrue(hashTable.size() == 2); 

		
		hashTable.put("Antonia", 6000);
		hashTable.put("Ximena", 7000);
		hashTable.put("Andrea", 2000);
		hashTable.put("Camilo", 7000);
		hashTable.put("Esteban", 8000);
		hashTable.put("Ricardo", 8000);
		hashTable.put("Jorge", 8000);
		hashTable.put("Claudia", 8000);
		hashTable.put("Sofia", 8000);
		hashTable.put("Alejandro", 10000000);
		hashTable.put("Andres", 30000000);
		hashTable.put("Daniel", 40000000);
		hashTable.put("Duvan", 50000000);


		//Ahora eltamaño maximo debe ser el doble del original: 4
		System.out.println(hashTable.size());
		assertTrue(hashTable.size() == 4);
	}
	private void assertTrue(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	
}
