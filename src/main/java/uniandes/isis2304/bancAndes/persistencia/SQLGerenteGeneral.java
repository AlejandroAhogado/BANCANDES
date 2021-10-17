package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.GerenteGeneral;

public class SQLGerenteGeneral {

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
	public SQLGerenteGeneral (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarGerenteGeneral(PersistenceManager pm, String tipoDocumento, int numeroDocumento,
			String departamento, int codigopostal, String nacionalidad, String nombre, String direccion, String login,
			String contrasena, String correo, int telefono, String ciudad, String administrador, long oficina) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaGerenteGeneral () + "(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena,correo, telefono, ciudad, administrador, oficina) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena,correo, telefono, ciudad, administrador, oficina);
		return (long) q.executeUnique();
	}


	public GerenteGeneral darGerenteGeneralPorLogin(PersistenceManager pm, String login) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaGerenteGeneral () + " WHERE login = ?");
       q.setResultClass(GerenteGeneral.class);
       q.setParameters(login);
       return (GerenteGeneral) q.executeUnique();
}

	
	
}
