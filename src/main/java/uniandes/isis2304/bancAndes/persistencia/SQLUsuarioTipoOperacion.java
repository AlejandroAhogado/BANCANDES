package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.ClienteProducto;
import uniandes.isis2304.bancAndes.negocio.UsuarioTipoOperacion;

public class SQLUsuarioTipoOperacion {

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
	public SQLUsuarioTipoOperacion (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarUsuarioTipoOperacion(PersistenceManager pm, String tipoOperacion, String usuario) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaUsuariosTiposOperacion () + "(tipoOperacion, usuario) values (?, ?)");
        q.setParameters(tipoOperacion, usuario);
        return (long) q.executeUnique();
	}

	public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorUsuario(PersistenceManager pm,String usuario) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaUsuariosTiposOperacion () + " WHERE usuario = ?");
		q.setParameters(usuario);
		q.setResultClass(UsuarioTipoOperacion.class);
		return (List<UsuarioTipoOperacion>) q.executeList();
	}

	public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorTipo(PersistenceManager pm,String tipoOperacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaUsuariosTiposOperacion () + " WHERE tipoOperacion = ?");
		q.setParameters(tipoOperacion);
		q.setResultClass(UsuarioTipoOperacion.class);
		return (List<UsuarioTipoOperacion>) q.executeList();
	}
	
	public UsuarioTipoOperacion darUsuarioTipoOperacion(PersistenceManager pm, String usuario, String tipoOperacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaUsuariosTiposOperacion () + " WHERE usuario = ? AND tipoOperacion = ?");
		q.setParameters(usuario, tipoOperacion);
		q.setResultClass(UsuarioTipoOperacion.class);
		return (UsuarioTipoOperacion) q.executeUnique();
	}
	
}
