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

import uniandes.isis2304.bancAndes.negocio.Bebida;
import uniandes.isis2304.bancAndes.negocio.TipoBebida;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BEBIDA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLBebida 
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
	public SQLBebida (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una BEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la bebida
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarBebida (PersistenceManager pm, long idBebida, String nombre, long idTipoBebida, int gradoAlcohol) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaBebida () + "(id, nombre, idTipoBebida, gradoalcohol) values (?, ?, ?, ?)");
        q.setParameters(idBebida, nombre, idTipoBebida, gradoAlcohol);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BEBIDAS de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBebida - El nombre de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBebidaPorNombre (PersistenceManager pm, String nombreBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebida () + " WHERE nombre = ?");
        q.setParameters(nombreBebida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BEBIDAS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la bebida
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBebidaPorId (PersistenceManager pm, long idBebida)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebida () + " WHERE id = ?");
        q.setParameters(idBebida);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA BEBIDA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebida - El identificador de la bebida
	 * @return El objeto BEBIDA que tiene el identificador dado
	 */
	public Bebida darTipoBebidaPorId (PersistenceManager pm, long idBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebida () + " WHERE id = ?");
		q.setResultClass(TipoBebida.class);
		q.setParameters(idBebida);
		return (Bebida) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de BEBIDAS de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBebida - El nombre de la bebida
	 * @return Una lista de objetos BEBIDA que tienen el nombre dado
	 */
	public List<Bebida> darBebidasPorNombre (PersistenceManager pm, String nombreBebida) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebida () + " WHERE nombre = ?");
		q.setResultClass(Bebida.class);
		q.setParameters(nombreBebida);
		return (List<Bebida>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS BEBIDAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BEBIDA
	 */
	public List<Bebida> darBebidas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebida ());
		q.setResultClass(Bebida.class);
		return (List<Bebida>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar LAS BEBIDAS que no son servidas de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebidasNoServidas (PersistenceManager pm)
	{
        String q2Str = "SELECT idBebida FROM " + pp.darTablaSirven ();
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebida () + " WHERE id NOT IN (" + q2Str + ")");
        return (long) q.executeUnique();            
    }

}
