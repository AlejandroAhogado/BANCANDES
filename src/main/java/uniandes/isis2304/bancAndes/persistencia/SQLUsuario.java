package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLUsuario {

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
	public SQLUsuario (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	/**
	 * @param pm
	 * @param login
	 * @return
	 */
	public long adicionarUsuario(PersistenceManager pm, String login) {
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaUsuarios () + "(login) values (?)");
	        q.setParameters(login);
	        return (long) q.executeUnique();
	}

	/**
	 * @param pm
	 * @param login
	 * @return
	 */
	public long eliminarUsuario(PersistenceManager pm, String login) {
		
		Query q = pm.newQuery(SQL, "DELETE FROM " + pba.darTablaUsuarios () + " WHERE login = ?");
        q.setParameters(login);
        return (long) q.executeUnique();
	}
	
	
	
}
