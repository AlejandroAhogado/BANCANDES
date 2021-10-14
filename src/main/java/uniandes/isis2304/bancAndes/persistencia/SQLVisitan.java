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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Visitan;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VISITAN de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLVisitan 
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
	public SQLVisitan (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un VISITAN a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @param fecha - La fecha en que se realizó la visita
	 * @param horario - EL horario en que se realizó la visita (DIURNO, NOCTURNO, TODOS)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarVisitan (PersistenceManager pm, long idBebedor, long idBar, Timestamp fecha, String horario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVisitan () + "(idbebedor, idbar, fechavisita, horario) values (?, ?, ?, ?)");
        q.setParameters(idBebedor, idBar, fecha, horario);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS VISITAS de la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVisitan (PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitan ());
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN VISITAN de la base de datos de Parranderos, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVisitan (PersistenceManager pm, long idBebedor, long idBar) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitan () + " WHERE idbebedor = ? AND idbar = ?");
        q.setParameters(idBebedor, idBar);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para ELIMINAR TODAS LAS VISITAS DE UN BEBEDOR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVisitanPorIdBebedor (PersistenceManager pm, long idBebedor) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitan () + " WHERE idbebedor = ?");
        q.setParameters(idBebedor);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para ELIMINAR TODAS LAS VISITAS HECHAS A UN BAR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVisitanPorIdBar (PersistenceManager pm, long idBar) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitan () + " WHERE idBar = ?");
        q.setParameters(idBar);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los VISITAN de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VISITAN
	 */
	public List<Visitan> darVisitan (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVisitan ());
		q.setResultClass(Visitan.class);
		return (List<Visitan>) q.execute();
	}

	/* ****************************************************************
	 * 			Versión larga, a lo JDBC
	 *****************************************************************/
  	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los VISITAN de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VISITAN
	 */
	private List<Visitan> darVisitan_V2 (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT idBebedor, idBar, fechaVisita, horario FROM " + pp.darTablaVisitan ());
		List<Visitan> resp = new LinkedList<>();
		List results = q.executeList();
		for (Object obj : results)
		{
			Object [] datos = (Object []) obj;
			long idBebedor =  ((BigDecimal) datos [0]).longValue ();
			long idBar = ((BigDecimal) datos [1]).longValue();
			Timestamp fecha = (Timestamp) datos [2];
			String horario = (String) datos [3];
			resp.add (new Visitan (idBebedor, idBar, fecha, horario));
		}
		return resp;		
	}
		 	
}
