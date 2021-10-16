package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPuestoDigital {

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
	public SQLPuestoDigital (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarPuestoDigital(PersistenceManager pm, long id, int telefono, String tipo, String url) {
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaPuestosDigitales () + "(id, telefono, tipo, url) values (?, ?, ?, ?)");
	        q.setParameters(id, telefono, tipo, url);
	        return (long) q.executeUnique();
	}
	
	
}
