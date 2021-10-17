package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.GerenteDeOficina;

public class SQLGerenteDeOficina {

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
	public SQLGerenteDeOficina (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarGerenteDeOficina(PersistenceManager pm, String tipoDocumento, int numeroDocumento,
			String departamento, int codigopostal, String nacionalidad, String nombre, String direccion, String login,
			String contrasena, String correo, int telefono, String ciudad, String administrador) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaGerentesDeOficina () + "(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena,correo, telefono, ciudad, administrador) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena,correo, telefono, ciudad, administrador);
		return (long) q.executeUnique();
	}

	public GerenteDeOficina darGerenteDeOficinaPorLogin(PersistenceManager pm, String login) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaGerentesDeOficina() + " WHERE login = ?");
		q.setResultClass(GerenteDeOficina.class);
		q.setParameters(login);
		return (GerenteDeOficina) q.executeUnique();
	}

	
	
}
