package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLAsociacionCuentasEmpleados {
	
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
	public SQLAsociacionCuentasEmpleados (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}


	public long adicionarAsociacionCuentasEmpleados(PersistenceManager pm, long asociacion, long cuentaEmpleado) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaAsociacionCuentasEmpleados () + "(asociacion, cuentaEmpleado) values (?, ?)");
        q.setParameters(asociacion, cuentaEmpleado);
        return (long) q.executeUnique();
	}

}
