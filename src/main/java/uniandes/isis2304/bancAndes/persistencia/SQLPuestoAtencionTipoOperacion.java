package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.PuestoAtencionTipoOperacion;
import uniandes.isis2304.bancAndes.negocio.UsuarioTipoOperacion;

public class SQLPuestoAtencionTipoOperacion {

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
	public SQLPuestoAtencionTipoOperacion (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}
	
	public long adicionarPuestoAtencionTipoOperacion(PersistenceManager pm, String tipoOperacion, long puesto) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaPuestosAtencionTiposOperacion() + "(tipoOperacion, puesto) values (?, ?)");
        q.setParameters(tipoOperacion, puesto);
        return (long) q.executeUnique();
	}

	public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorPuesto(PersistenceManager pm,long puesto) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaPuestosAtencionTiposOperacion () + " WHERE puesto = ?");
		q.setParameters(puesto);
		q.setResultClass(PuestoAtencionTipoOperacion.class);
		return (List<PuestoAtencionTipoOperacion>) q.executeList();
	}

	public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorTipo(PersistenceManager pm,String tipoOperacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaPuestosAtencionTiposOperacion () + " WHERE tipoOperacion = ?");
		q.setParameters(tipoOperacion);
		q.setResultClass(PuestoAtencionTipoOperacion.class);
		return (List<PuestoAtencionTipoOperacion>) q.executeList();
	}
	
	public PuestoAtencionTipoOperacion darPuestoAtencionTipoOperacion(PersistenceManager pm, long puesto, String tipoOperacion) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaPuestosAtencionTiposOperacion () + " WHERE puesto = ? AND tipoOperacion = ?");
		q.setParameters(puesto, tipoOperacion);
		q.setResultClass(PuestoAtencionTipoOperacion.class);
		return (PuestoAtencionTipoOperacion) q.executeUnique();
	}
	
}
