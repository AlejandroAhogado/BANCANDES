package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLPuestoDeAtencion {

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
	public SQLPuestoDeAtencion (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

    /**
    * @param pm
    * @param id
    * @return
    */
	public long adicionarPuestoDeAtencion(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaPuestosDeAtencion () + "( id) values (?)");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

	/**
	 * @param pm
	 * @param id
	 * @return
	 */
	public long eliminarPuestoDeAtencion(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "DELETE FROM " + pba.darTablaPuestosDeAtencion () + " WHERE id = ?");
		q.setParameters(id);
		return (long) q.executeUnique();
	}

	
}
