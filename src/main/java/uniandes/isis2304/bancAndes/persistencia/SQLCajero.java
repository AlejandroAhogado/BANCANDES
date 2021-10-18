package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Cajero;
import uniandes.isis2304.bancAndes.negocio.ClienteProducto;

public class SQLCajero {

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
	public SQLCajero (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	/**
	 * @param pm
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param departamento
	 * @param codigopostal
	 * @param nacionalidad
	 * @param nombre
	 * @param direccion
	 * @param login
	 * @param contrasena
	 * @param correo
	 * @param telefono
	 * @param ciudad
	 * @param administrador
	 * @param puestoAtencionoficina
	 * @param oficina
	 * @return
	 */
	public long adicionarCajero(PersistenceManager pm, String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador, long puestoAtencionoficina,
			long oficina) {
		
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaCajeros() + "(tipoDocumento, numeroDocumento, departamento, codigopostal, nacionalidad, nombre, direccion, login, contrasena, correo, telefono, ciudad, administrador, puestoAtencionoficina,oficina) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(tipoDocumento, numeroDocumento, departamento,
       			 codigopostal, nacionalidad, nombre, direccion, login, contrasena,
       			 correo, telefono, ciudad, administrador, puestoAtencionoficina,oficina);
	        return (long) q.executeUnique();
		
	}
	
	public Cajero darCajeroPorLogin(PersistenceManager pm, String login) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCajeros () + " WHERE login = ?");
		q.setResultClass(Cajero.class);
		q.setParameters(login);
		return (Cajero) q.executeUnique();
	}

	public long asociarPuestoDeAtencionOficinaCajero (PersistenceManager pm, long id, String login)
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCajeros () + " SET puestoAtencionOficina = ?  WHERE login = ?");
		q.setParameters(id,login);
		return (long) q.executeUnique();
	}


	
}
