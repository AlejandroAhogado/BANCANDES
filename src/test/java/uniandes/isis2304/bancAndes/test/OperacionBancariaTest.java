package uniandes.isis2304.bancAndes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.bancAndes.negocio.Asociacion;
import uniandes.isis2304.bancAndes.negocio.AsociacionCuentasEmpleados;
import uniandes.isis2304.bancAndes.negocio.BancAndes;
import uniandes.isis2304.bancAndes.negocio.OperacionBancaria;
import uniandes.isis2304.bancAndes.negocio.VOAsociacion;

public class OperacionBancariaTest {


	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(OperacionBancariaTest.class.getName());

	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD tambi�n
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * La clase que se quiere probar
	 */
	private BancAndes bancAndes;

	/* ****************************************************************
	 * 			M�todos de prueba para la tabla OperacionBancaria 	 *****************************************************************/
	/**
	 * M�todo que prueba las operaciones sobre la tabla OperacionBancaria
	 * 1. Registrar operacion sobre cuenta
	 * 2. Actualizar saldo a ambas cuentas
	 * 3. Registrar operacion sobre prestamo
	 */
	@Test
	public void RF11Test() 
	{
		// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre OperacionBancaria");
			bancAndes = new BancAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			log.info ("Prueba de operacionBancaria incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de asociacion incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de bancAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}

		// Ahora si se pueden probar las operaciones
		try
		{
			long hoy=System.currentTimeMillis();

			float saldoAnterior1 = bancAndes.darCuentaPorId(23).getSaldo();
			float saldoAnterior2 = bancAndes.darCuentaPorId(4).getSaldo();
			
			OperacionBancaria opb = bancAndes.adicionarOperacionBancaria(5000, new java.sql.Date(hoy) , "alkosto", 23, 4, "TRANSFERIR", 12, null);
			assertNotNull(opb);
			bancAndes.actualizarSaldoCuenta(23, -5000);
			bancAndes.actualizarSaldoCuenta(4, 5000);

			float saldoDespues1 = bancAndes.darCuentaPorId(23).getSaldo();
			float saldoDespues2 = bancAndes.darCuentaPorId(4).getSaldo();


			assertTrue((saldoAnterior1-5000)==saldoDespues1);
			assertTrue((saldoAnterior2+5000)==saldoDespues2);
			
			bancAndes.eliminarOperacionBancaria(opb.getId());
			bancAndes.actualizarSaldoCuenta(23,5000);
			bancAndes.actualizarSaldoCuenta(4, -5000);


		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla operacionBancaria.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

			fail ("Error en las pruebas sobre la tabla operacionBancaria");
		}
		finally
		{
		
			bancAndes.cerrarUnidadPersistencia ();    		
		}
	}

	@Test
	public void RF12() {
		try
		{
			log.info ("Probando las operaciones sobre OperacionBancaria");
			bancAndes = new BancAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
			//		e.printStackTrace();
			log.info ("Prueba de operacionBancaria incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de asociacion incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de bancAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}

		try
		{
			long hoy=System.currentTimeMillis();

			float saldoAnterior22 = bancAndes.darPrestamoPorId(1).getSaldoPendiente();
			float saldoAnterior11 = bancAndes.darCuentaPorId(23).getSaldo();
			
			OperacionBancaria opb = bancAndes.adicionarOperacionBancaria(1100000, new java.sql.Date(hoy) , "alkosto", 23, 1, "PAGAR_CUOTA_EXTRAORDINARIA", 7, "rom2716");
			assertNotNull(opb);
			bancAndes.realizarPago(1, 1100000);
			bancAndes.actualizarSaldoCuenta(23, -1100000);

			float saldoDespues22 = bancAndes.darPrestamoPorId(1).getSaldoPendiente();
			float saldoDespues11 = bancAndes.darCuentaPorId(23).getSaldo();

			assertTrue((saldoAnterior22-1100000)==saldoDespues22);
			assertTrue((saldoAnterior11-1100000)==saldoDespues11);
			
			bancAndes.eliminarOperacionBancaria(opb.getId());
			bancAndes.actualizarSaldoCuenta(23,1100000);
			bancAndes.realizarPago(1, -1100000);


		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla operacionBancaria.\n";
			msg += "Revise el log de bancandes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

			fail ("Error en las pruebas sobre la tabla operacionBancaria");
		}

		finally
		{
			
			bancAndes.cerrarUnidadPersistencia ();    		
		}
	}

	
@Test
	public void RF13Test() {
		try
		{
			log.info ("Probando las operaciones sobre OperacionBancaria");
			bancAndes = new BancAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
			//		e.printStackTrace();
			log.info ("Prueba de operacionBancaria incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de asociacion incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de bancAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}

		try
		{
			
			List<AsociacionCuentasEmpleados> lista = bancAndes.darAsociacionesCuentasPorAsociacion(24);
			VOAsociacion aso = bancAndes.darAsociacionPorCuenta(25);
			float saldoAnterior22 = bancAndes.darCuentaPorId(3).getSaldo();
			float saldoAnterior11 = bancAndes.darCuentaPorId(25).getSaldo();
			bancAndes.pagarNomina(lista, 25, aso.getValor() , "alkosto", 12, null);
			
			float saldoDespues22 = bancAndes.darCuentaPorId(3).getSaldo();
			float saldoDespues11 = bancAndes.darCuentaPorId(25).getSaldo();

			assertTrue((saldoAnterior22+aso.getValor())==saldoDespues22);
			
			assertTrue((saldoAnterior11-(aso.getValor()*2))==saldoDespues11);

			bancAndes.actualizarSaldoCuenta(25,aso.getValor()*2);
			bancAndes.actualizarSaldoCuenta(3,-aso.getValor());
			bancAndes.actualizarSaldoCuenta(4,-aso.getValor());
			
		}
		catch (Exception e)
		{
			//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla operacionBancaria.\n";
			msg += "Revise el log de bancandes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

			fail ("Error en las pruebas sobre la tabla operacionBancaria");
		}

		finally
		{
			
			bancAndes.cerrarUnidadPersistencia ();    		
		}
	}


@Test
	public void RFC6Test() 
	{
   	// Probar primero la conexi�n a la base de datos
		try
		{
			log.info ("Probando las operaciones sobre Operaciones Bancarias");
			bancAndes = new BancAndes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de Operaciones Bancarias incompleta. No se pudo conectar a la base de datos !!. La excepci�n generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de Operaciones Bancarias incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de bancAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
   	try
		{
   	    		
   		assertNotNull(bancAndes.consultarOperacionesGerenteGeneral("opb.id", ">", "0", "opb.id", ">", "0", "VALOR", "ASC"));
   		    		
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecuci�n de las pruebas de operaciones sobre la tabla Operaciones Bancarias.\n";
			msg += "Revise el log de BancAndes y el de datanucleus para conocer el detalle de la excepci�n";
			System.out.println (msg);

   		fail ("Error en las pruebas sobre la tabla Operaciones Bancarias");
		}
	}

	
	
	/* ****************************************************************
	 * 			M�todos de configuraci�n
	 *****************************************************************/
	/**
	 * Lee datos de configuraci�n para la aplicaci�n, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraci�n deseada
	 * @param archConfig - Archivo Json que contiene la configuraci�n
	 * @return Un objeto JSON con la configuraci�n del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n de tablas v�lido");
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de tablas v�lido: ", "OperacionBancariaTest", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}	



}
