package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.ClienteProducto;
import uniandes.isis2304.bancAndes.negocio.Cuenta;

public class SQLClienteProducto {


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
	public SQLClienteProducto (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarClienteProducto(PersistenceManager pm, long id, String login) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaProductos () + "(id, login) values (?, ?)");
        q.setParameters(id, login);
        return (long) q.executeUnique();
	}

	public List<ClienteProducto> darClienteProductoPorCliente(PersistenceManager pm, String login) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientesProductos () + " WHERE cliente = ?");
		q.setParameters(login);
		q.setResultClass(ClienteProducto.class);
		return (List<ClienteProducto>) q.executeList();
	}

	public List<ClienteProducto> darClienteProductoPorProducto(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientesProductos () + " WHERE producto = ?");
		q.setParameters(id);
		q.setResultClass(ClienteProducto.class);
		return (List<ClienteProducto>) q.executeList();
	}
	
}
