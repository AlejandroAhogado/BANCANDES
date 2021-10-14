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
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Bebedor;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BEBEDOR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLBebedor 
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
	public SQLBebedor (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BEBEDOR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarBebedor (PersistenceManager pm, long idBebedor, String nombre, String ciudad, String presupuesto) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaBebedor () + "(id, nombre, ciudad, presupuesto) values (?, ?, ?, ?)");
        q.setParameters(idBebedor, nombre, ciudad, presupuesto);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar BEBEDORES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del bebedor
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBebedorPorNombre (PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebedor () + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BEBEDOR de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebeodor
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarBebedorPorId (PersistenceManager pm, long idBebedor)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebedor () + " WHERE id = ?");
        q.setParameters(idBebedor);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN BEBEDOR de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR que tiene el identificador dado
	 */
	public Bebedor darBebedorPorId (PersistenceManager pm, long idBebedor) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebedor () + " WHERE id = ?");
		q.setResultClass(Bebedor.class);
		q.setParameters(idBebedor);
		return (Bebedor) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBebedor - El nombre de bebedor buscado
	 * @return Una lista de objetos BEBEDOR que tienen el nombre dado
	 */
	public List<Bebedor> darBebedoresPorNombre (PersistenceManager pm, String nombreBebedor) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebedor () + " WHERE nombre = ?");
		q.setResultClass(Bebedor.class);
		q.setParameters(nombreBebedor);
		return (List<Bebedor>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BEBEDOR
	 */
	public List<Bebedor> darBebedores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBebedor ());
		q.setResultClass(Bebedor.class);
		return (List<Bebedor>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES Y DE SUS VISITAS REALIZADAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return Una lista de arreglos de objetos, de tamaño 7. Los elementos del arreglo corresponden a los datos de 
	 * los bares visitados y los datos propios de la visita:
	 * 		(id, nombre, ciudad, presupuesto, cantsedes) de los bares y (fechavisita, horario) de las visitas
	 */
	public List<Object []> darVisitasRealizadas (PersistenceManager pm, long idBebedor)
	{
        String sql = "SELECT bar.id, bar.nombre, bar.ciudad, bar.presupuesto, bar.cantsedes, vis.fechavisita, vis.horario";
        sql += " FROM ";
        sql += pp.darTablaBebedor () + " bdor, ";
        sql += pp.darTablaVisitan () + " vis, ";
        sql += pp.darTablaBar () + " bar ";
       	sql	+= " WHERE ";
       	sql += "bdor.id = ?";
       	sql += " AND bdor.id = vis.idbebedor";
       	sql += " AND vis.idbar = bar.id";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idBebedor);
		return q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES Y DE LAS BEBIDAS QUE GUSTA de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return Una lista de arreglos de objetos, de tamaño 5. Los elementos del arreglo corresponden a los datos de 
	 * las bebidas (con el nombre del tipo de bebida) que le gustan al bebedor:
	 * 		(id, nombre, idtipobebida, gradoalcohol) de la bebida y el (nombre) del tipo de bebida
	 */
	public List<Object []> darBebidasQueLeGustan (PersistenceManager pm, long idBebedor)
	{
        String sql = "SELECT beb.id, beb.nombre, beb.idtipobebida, beb.gradoalcohol, tb.nombre";
        sql += " FROM ";
        sql += pp.darTablaBebedor () + " bdor, ";
        sql += pp.darTablaGustan () + " g, ";
        sql += pp.darTablaBebida () + " beb, ";
        sql += pp.darTablaTipoBebida () + " tb ";
       	sql	+= " WHERE ";
       	sql += "bdor.id = ?";
       	sql += " AND bdor.id = g.idbebedor";
       	sql += " AND g.idBebida = beb.id";
       	sql += " AND beb.idtipobebida = tb.id";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idBebedor);
		return q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES Y DE CUÁNTAS VISITAS HA REALIZADO de la 
	 * base de datos de Parranderos. Incluye, con 0, los bebedores que no han realizado visitas 
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de objetos, de tamaño 5. Los elementos del arreglo corresponden a los datos del bebedor,
	 * y del número de visitas realizadas:
	 * 		(id, nombre, ciudad, presupuesto) del bebedor y numVisitas
	 */
	public List<Object> darBebedoresYNumVisitasRealizadas (PersistenceManager pm)
	{
	    String sql = "SELECT id, nombre, ciudad, presupuesto, count (idbebedor) as numVisitas";
	    sql += " FROM " + pp.darTablaBebedor ();
	    sql += " LEFT OUTER JOIN " + pp.darTablaVisitan () + " ON id = idbebedor";
	    sql	+= " GROUP BY id, nombre, ciudad, presupuesto";
	    sql	+= " ORDER BY numVisitas";
		
	    Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la cantidad de BEBEDORES de una ciudad que visitan bares de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param ciudad - La ciudad de interés
	 * @return El número de bebedores de la ciudad que son referenciados en VISITAN
	 */
	public long darCantidadBebedoresCiudadVisitanBares (PersistenceManager pm, String ciudad)
	{
        String sql1 = "SELECT UNIQUE ID";
        sql1 += " FROM " + pp.darTablaBebedor ();
        sql1 += " INNER JOIN " + pp.darTablaVisitan () + " ON id = idbebedor";
       	sql1	+= " WHERE ciudad = ?";
       	
       	String sql = "SELECT count (*) FROM (" + sql1 + ")";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(ciudad);
		return ((BigDecimal) q.executeUnique()).longValue ();
	}

	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar la ciudad de un bebedor en la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas
	 */
	public long cambiarCiudadBebedor (PersistenceManager pm, long idBebedor, String ciudad) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaBebedor () + " SET ciudad = ? WHERE id = ?");
	     q.setParameters(ciudad, idBebedor);
	     return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para:
	 * Eliminar un bebedor y las visitas a bares que haya realizado v1: 
	 * En caso que el bebedor esté referenciado por otra relación, NO SE BORRA NI EL BEBEDOR, NI SUS VISITAS
	 * Adiciona entradas al log de la aplicación
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarBebedorYVisitas_v1 (PersistenceManager pm, long idBebedor) 
	{
      Query q1 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVisitan () + " WHERE idBebedor = ?");
      q1.setParameters(idBebedor);
      Query q2 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBebedor () + " WHERE id = ?");
      q2.setParameters(idBebedor);
      
      long visitasEliminadas = (long) q1.executeUnique ();
      long bebedoresEliminads = (long) q2.executeUnique ();
      return new long[] {bebedoresEliminads, visitasEliminadas};
	}

	/* ****************************************************************
	 * 			Métodos de prueba para subtransacciones
	 *****************************************************************/
	//****************** QUITAR EN LA VERSIÓN A ENTREGAR
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES Y DE CUÁNTOS BARES VISITAN de la 
	 * base de datos de Parranderos. Incluye, con 0, los bebedores que no han realizado visitas 
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de objetos, de tamaño 5. Los elementos del arreglo corresponden a los datos del bebedor,
	 * y del número de bares que visita:
	 * 		(id, nombre, ciudad, presupuesto) del bebedor y numbares
	 */
	public List<Object> darBebedoresYCuantosBaresVisitan (PersistenceManager pm)
	{
		// Selecciona las parejas [idBebedor, idBar] únicas de la tabla VISITAN
		String sql0 = "SELECT DISTINCT idbebedor, idBar";
		sql0 += " FROM " + pp.darTablaVisitan ();
		
		// Agrupa las parejas anteriores por idBebedor y cuenta cuántos bares visita cada bebedor
		String sql1 = "SELECT idbebedor, count (*) as numbares";
		sql1 += " FROM " + "(" + sql0 + ")";
		sql1 += " GROUP BY idBebedor";
		
		// Hace join de BEBEDORES con el resultado anterior para asociar la información del bebedor
        String sql = "SELECT id, nombre, ciudad, presupuesto, NVL (numbares, 0)";
        sql += " FROM " + pp.darTablaBebedor () + " LEFT OUTER JOIN (" + sql1 + ")";
        sql += " ON id = idBebedor";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS BEBEDORES Y DE CUÁNTAS VISITAS HA REALIZADO de la 
	 * base de datos de Parranderos. Incluye, con 0, los bebedores que no han realizado visitas 
	 * @param pm - El manejador de persistencia
	 * @return Una lista de arreglos de objetos, de tamaño 5. Los elementos del arreglo corresponden a los datos del bebedor,
	 * y del número de visitas realizadas:
	 * 		(id, nombre, ciudad, presupuesto) del bebedor y numVisitas
	 */
	public List<Object> darBebedoresYNumVisitasRealizadas_v2 (PersistenceManager pm)
	{		
		String sql1 = "SELECT idbebedor, count (*) as numVisitas";
		sql1 += " FROM " + pp.darTablaVisitan ();
		sql1 += " GROUP BY idBebedor";
		
        String sql = "SELECT id, nombre, ciudad, presupuesto, NVL (numVisitas, 0)";
        sql += " FROM " + pp.darTablaBebedor () + " LEFT OUTER JOIN (" + sql1 + ")";
        sql += " ON id = idBebedor";
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}


}
