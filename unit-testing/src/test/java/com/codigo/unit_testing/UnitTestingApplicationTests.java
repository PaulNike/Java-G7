package com.codigo.unit_testing;

import com.codigo.unit_testing.aggregates.request.EmpresaRequest;
import com.codigo.unit_testing.aggregates.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnitTestingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testSumaAssertEquals(){
		//ARRANGE: Preparar el ambiente con las variables y/o mocks requeridos
		int resultadoEsperado = 10;
		int actual;
		//ACT: EJecutamos la prueba
		actual = 5 + 5;
		//ASSERT: Afirmar el resultado esperado
		assertEquals(resultadoEsperado,actual, "Los valores deben tanto el esperado como " +
				"el actual deben ser iguales");
	}
	@Test
	void testSumaAssertNotEquals(){
		//ARRANGE: Preparar el ambiente con las variables y/o mocks requeridos
		int resultadoEsperado = 10;
		int actual;
		//ACT: EJecutamos la prueba
		actual = 5 + 6;
		//ASSERT: Afirmar el resultado esperado
		assertNotEquals(resultadoEsperado,actual, "Los valores NO deben de ser iguales");
	}
	@Test
	void testAssertTrue(){
		//Arrange
		boolean condicion;
		//Act
		condicion = 3 > 2;
		//Assert
		assertTrue(condicion, "El primer valor debe ser mayor al segundo");
	}
	@Test
	void testAssertFalse(){
		//Arrange
		boolean condicion;
		//Act
		condicion = 1 > 2;
		//Assert
		assertFalse(condicion, "El primer valor debe ser mayor al segundo");
	}
	@Test
	void testAssertNull(){
		String dato = null;

		assertNull(dato, "El valor esperado no es NUlo");
	}
	@Test
	void testAssertNotNull(){
		String dato = null;

		dato = "Hola";

		assertNotNull(dato, "El valor esperado no es NUlo");
	}

	@Test
	void testAssertEqualsArray(){
		int[] arregloEsperado ={1,2,3};
		int[] actual ={1,2,3};

		assertArrayEquals(arregloEsperado,actual,
				"Los arreglos no son iguales");
	}

	@Test
	void testAssertThrows(){
		assertThrows(ArithmeticException.class, () ->{
			int resultado = 10/0;
		}, "La exepcion generada no es la que se espera");
	}

	@Test
	void testAssertSame(){

		String dato1 = "1";
		String dato2 = "1";
		String dato3 = new String("1");
		assertSame(dato1,dato2);
	}

	@Test
	void testAssertSame1(){
		EmpresaRequest instancia1 = SingleService.getInstance();
		instancia1.setDistrito("SANTA ANITA");
		EmpresaRequest instancia2 = SingleService.getInstance();
		EmpresaRequest instancia3 = new EmpresaRequest();

		assertSame(instancia1,instancia2, "Las instancias no son iguales");

	}

	@Test
	void testAssertSame2(){

		BaseResponse instancia1 = SingleService.getInstanceBaseResponse();
		BaseResponse instancia2 = SingleService.getInstanceBaseResponse();
		BaseResponse instancia3 = new BaseResponse<>();
		BaseResponse instancia4 = new BaseResponse<>();
		assertSame(instancia1,instancia2, "Las instancias no son iguales");

	}
}
