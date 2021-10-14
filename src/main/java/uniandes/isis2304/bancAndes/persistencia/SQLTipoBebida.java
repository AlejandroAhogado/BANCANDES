/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.TipoBebida;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPO DE BEBIDA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLTipoBebida 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLTipoBebida (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOBEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarTipoBebida (PersistenceManager pm, long idTipoBebida, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaTipoBebida  () + "(id, nombre) values (?, ?)");
        q.setParameters(idTipoBebida, nombre);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreTipoBebida - El nombre del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorNombre (PersistenceManager pm, String nombreTipoBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipoBebida  () + " WHERE nombre = ?");
        q.setParameters(nombreTipoBebida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TIPOS DE BEBIDA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorId (PersistenceManager pm, long idTipoBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTipoBebida  () + " WHERE id = ?");
        q.setParameters(idTipoBebida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TIPOBEBIDA que tiene el identificador dado
	 */
	public TipoBebida darTipoBebidaPorId (PersistenceManager pm, long idTipoBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipoBebida  () + " WHERE id = ?");
		q.setResultClass(TipoBebida.class);
		q.setParameters(idTipoBebida);
		return (TipoBebida) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN TIPO DE BEBIDA de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreTipoBebida - El nombre del tipo de bebida
	 * @return El objeto TIPOBEBIDA que tiene el nombre dado
	 */
	public List<TipoBebida> darTiposBebidaPorNombre (PersistenceManager pm, String nombreTipoBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipoBebida  () + " WHERE nombre = ?");
		q.setResultClass(TipoBebida.class);
		q.setParameters(nombreTipoBebida);
		return (List<TipoBebida>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS TIPOS DE BEBIDA de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos TIPOBEBIDA
	 */
	public List<TipoBebida> darTiposBebida (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTipoBebida  ());
		q.setResultClass(TipoBebida.class);
		return (List<TipoBebida>) q.executeList();
	}

}
