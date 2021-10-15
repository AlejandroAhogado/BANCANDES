package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Cliente;

public class SQLCliente {

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
	public SQLCliente (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarCliente(PersistenceManager pm, String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String tipo) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaClientes () + "(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena, correo, telefono, ciudad, tipo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena, correo, telefono, ciudad, tipo);
        return (long) q.executeUnique();
	}

	public List<Cliente> darClientes(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientes ());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}

	public Cliente darClientePorLogin(PersistenceManager pm, String login) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientes () + " WHERE login = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(login);
		return (Cliente) q.executeUnique();
	}
	
	
}
