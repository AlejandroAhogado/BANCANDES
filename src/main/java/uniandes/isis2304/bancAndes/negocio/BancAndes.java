package uniandes.isis2304.bancAndes.negocio;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.persistencia.PersistenciaBancAndes;
import uniandes.isis2304.bancAndes.persistencia.PersistenciaParranderos;

public class BancAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(BancAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaBancAndes pba;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public BancAndes ()
	{
		pba = PersistenciaBancAndes.getInstance ();
	}
	
	/**
	 * El constructor que recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public BancAndes (JsonObject tableConfig)
	{
		pba = PersistenciaBancAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pba.cerrarUnidadPersistencia ();
	}
	
	/**
	 * MODIFICACION
	 * Registrar usuario
	 * Registrar oficina
	 * Registrar punto de atencion
	 * Registrar cuenta
	 * Cerrar cuenta
	 * Registrar operación sobre cuenta
	 * Registrar prestamo
	 * registrar operacion sobre prestamo
	 * cerrar prestamo
	 * 
	 * CONSULTA
	 * consultar cuentas (todas)
	 * consultar clientes
	 * consultar operaciones
	 * consultar cliente por login
	 * 
	 */
	
	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un usuario
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del usuario
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public TipoBebida adicionarTipoBebida (String nombre)
	{
        log.info ("Adicionando Tipo de bebida: " + nombre);
        TipoBebida tipoBebida = pp.adicionarTipoBebida (nombre);		
        log.info ("Adicionando Tipo de bebida: " + tipoBebida);
        return tipoBebida;
	}
}
