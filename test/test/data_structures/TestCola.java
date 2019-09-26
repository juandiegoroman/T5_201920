package test.data_structures;

import model.data_structures.MaxColaCP;
import model.data_structures.IColaIterador;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestCola {

	private MaxColaCP<String> lista;

	@Before
	public void setUp1() {
		lista = new MaxColaCP<String>();
	}

	public void setUp2() {
		for(int i =0; i< 10; i++){
			lista.enqueu(""+i);
		}
	}

	@Test
	public void testListaEncadenada() {
		assertTrue(lista!=null);
		assertEquals(0, lista.tamano());
	}

	@Test
	public void testBuscar()  {
		setUp2();
		assertEquals("0", lista.buscar(0));
		assertEquals("2", lista.buscar(2));
		assertEquals(""+(lista.tamano() - 1), lista.buscar(lista.tamano() - 1));

		try {
			lista.buscar(lista.tamano() + 1);
			fail();
		}

		catch(Exception e) {

		}
	}

	@Test
	public void testTamano()  {
		assertEquals(0, lista.tamano());
		setUp2();
		assertEquals(10, lista.tamano());

	}

	@Test
	public void testInsertarFinal()  {
		setUp2();
		lista.enqueu(""+10);
		assertEquals("10", lista.buscar(10));

	}

	
	@Test
	public void testIterador() {
		setUp2();
		IColaIterador<String> iter = lista.iterador();
		
		String actual = "";
		for (int i = 0; i < 10; i++) {
			actual = iter.siguiente();
			assertEquals(actual, lista.buscar(i));
		}		
	}
	
	@Test
	public void testEliminar()  {
		setUp2();
		lista.dequeue();
		assertEquals(9, lista.tamano());
		assertEquals("1", lista.buscar(0));
		

	}
	
	
}
