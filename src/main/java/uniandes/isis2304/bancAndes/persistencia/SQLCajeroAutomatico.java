package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.CajeroAutomatico;
import uniandes.isis2304.bancAndes.negocio.PuestoDigital;

public class SQLCajeroAutomatico {

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
	public SQLCajeroAutomatico (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	/**
	 * @param pm
	 * @param id
	 * @param telefono
	 * @param localizacion
	 * @return
	 */
	public long adicionarCajeroAutomatico(PersistenceManager pm, long id, int telefono, String localizacion) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaCajerosAutomaticos () + "( id, telefono, localizacion) values (?, ?, ?)");
        q.setParameters(id, telefono, localizacion);
        return (long) q.executeUnique();
	}

	public CajeroAutomatico darCajeroAutomaticoPorId(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCajerosAutomaticos () + " WHERE id = ?");
		q.setResultClass(CajeroAutomatico.class);
		q.setParameters(id);
		return (CajeroAutomatico) q.executeUnique();
	}
	
}
