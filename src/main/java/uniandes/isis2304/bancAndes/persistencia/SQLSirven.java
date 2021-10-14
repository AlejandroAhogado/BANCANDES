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

import uniandes.isis2304.bancAndes.negocio.Sirven;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SIRVEN de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLSirven 
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
	public SQLSirven (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SIRVEN a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @param horario - El horario en que el bar sirve la bebida (DIURNO, NOCTURNO, TDOOS)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarSirven (PersistenceManager pm, long idBar, long idBebida, String horario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSirven () + "(idbar, idbebida, horario) values (?, ?, ?)");
        q.setParameters(idBar, idBebida, horario);
        return (long)q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN SIRVEN de la base de datos de Parranderos, por sus identificador
	 * @param pm - El manejador de persistencia
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSirven (PersistenceManager pm, long idBar, long idBebida) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSirven () + " WHERE idbar = ? AND idbebida = ?");
        q.setParameters(idBar, idBebida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los SIRVEN de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SIRVEN
	 */
	public List<Sirven> darSirven (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSirven ());
		q.setResultClass(Sirven.class);
		return (List<Sirven>) q.execute();
	}
 
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar el identificador y el número de bebidas que sirven los bares de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de parejas de objetos, el primer elemento de cada pareja representa el identificador de un bar,
	 * 	el segundo elemento representa el número de bebidas que sirve (Una bebida que se sirve en dos horarios cuenta dos veces)
	 */
	public List<Object []> darBaresYCantidadBebidasSirven (PersistenceManager pm)
	{
        String sql = "SELECT idBar, count (*) as numBebidas";
        sql += " FROM " + pp.darTablaSirven ();
       	sql	+= " GROUP BY idBar";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}

}
