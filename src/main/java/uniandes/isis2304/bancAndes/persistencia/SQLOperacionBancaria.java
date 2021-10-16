package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLOperacionBancaria {

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
	public SQLOperacionBancaria (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarOperacionBancaria(PersistenceManager pm, long id, float valor, Date fecha, String cliente,
			long producto, String tipoOperacion, long puestoAtencion, String empleado) {
		
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, producto,tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?)");
	        q.setParameters(id, valor, fecha, cliente, producto,tipoOperacion, puestoAtencion, empleado);
	        return (long) q.executeUnique();
	}
	
}
