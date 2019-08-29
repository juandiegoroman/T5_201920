package test.data_structures;

import model.data_structures.Cola;
import model.data_structures.IColaIterador;
import model.data_structures.IPilaIterador;
import model.data_structures.Pila;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestPila{

	private Pila<String> lista;

	@Before
	public void setUp1() {
		lista = new Pila<String>();
	}

	public void setUp2() {
		for(int i =0; i< 10; i++){
			lista.agregar(""+i);
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

	public void testInsertarFinal()  {
		setUp2();
		lista.agregar(""+10);
		assertEquals("10", lista.buscar(10));

	}

	
	@Test
	public void testIterador() {
		setUp2();
		IPilaIterador<String> iter = lista.iterador();
		
		String actual = "";
		for (int i = 0; i < 10; i++) {
			actual = iter.siguiente();
			assertEquals(actual, lista.buscar(i));
		}		
	}
	
	@Test
	public void testEliminar()  {
		setUp2();
		lista.sacar();
		assertEquals(9, lista.tamano());
		assertEquals("8", lista.buscar(8));
		

	}
	
	
}
