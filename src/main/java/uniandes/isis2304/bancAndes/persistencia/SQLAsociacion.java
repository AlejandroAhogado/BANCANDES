package uniandes.isis2304.bancAndes.persistencia;

import java.sql.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLAsociacion {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaBancAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaBancAndes pba;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLAsociacion (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarAsociacion(PersistenceManager pm, long id, float valor, String frecuencia,  int cuentaCorporativo) {
		
		   Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaAsociaciones () + "(id, valor, frecuencia, cuentaCorporativo) values (?, ?, ?, ?)");
	        q.setParameters(id, valor, frecuencia, cuentaCorporativo);
	        return (long) q.executeUnique();
		
	}

}
