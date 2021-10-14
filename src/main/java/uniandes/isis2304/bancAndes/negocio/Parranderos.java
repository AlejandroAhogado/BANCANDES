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

package uniandes.isis2304.bancAndes.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public TipoBebida adicionarTipoBebida (String nombre)
	{
        log.info ("Adicionando Tipo de bebida: " + nombre);
        TipoBebida tipoBebida = pp.adicionarTipoBebida (nombre);		
        log.info ("Adicionando Tipo de bebida: " + tipoBebida);
        return tipoBebida;
	}
	
	/**
	 * Elimina un tipo de bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorNombre (String nombre)
	{
		log.info ("Eliminando Tipo de bebida por nombre: " + nombre);
        long resp = pp.eliminarTipoBebidaPorNombre (nombre);		
        log.info ("Eliminando Tipo de bebida por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorId (long idTipoBebida)
	{
		log.info ("Eliminando Tipo de bebida por id: " + idTipoBebida);
        long resp = pp.eliminarTipoBebidaPorId (idTipoBebida);		
        log.info ("Eliminando Tipo de bebida por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<TipoBebida> darTiposBebida ()
	{
		log.info ("Consultando Tipos de bebida");
        List<TipoBebida> tiposBebida = pp.darTiposBebida ();	
        log.info ("Consultando Tipos de bebida: " + tiposBebida.size() + " existentes");
        return tiposBebida;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOTipoBebida> darVOTiposBebida ()
	{
		log.info ("Generando los VO de Tipos de bebida");        
        List<VOTipoBebida> voTipos = new LinkedList<VOTipoBebida> ();
        for (TipoBebida tb : pp.darTiposBebida ())
        {
        	voTipos.add (tb);
        }
        log.info ("Generando los VO de Tipos de bebida: " + voTipos.size() + " existentes");
        return voTipos;
	}

	/**
	 * Encuentra el tipos de bebida en Parranderos con el nombre solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @return Un objeto TipoBebida con el tipos de bebida de ese nombre que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public TipoBebida darTipoBebidaPorNombre (String nombre)
	{
		log.info ("Buscando Tipo de bebida por nombre: " + nombre);
		List<TipoBebida> tb = pp.darTipoBebidaPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Bebida adicionarBebida (String nombre, long idTipoBebida, int gradoAlcohol)
	{
		log.info ("Adicionando bebida " + nombre);
		Bebida bebida = pp.adicionarBebida (nombre, idTipoBebida, gradoAlcohol);
        log.info ("Adicionando bebida: " + bebida);
        return bebida;
	}
	
	/**
	 * Elimina una bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebidaPorNombre (String nombre)
	{
        log.info ("Eliminando bebida por nombre: " + nombre);
        long resp = pp.eliminarBebidaPorNombre (nombre);
        log.info ("Eliminando bebida por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarBebidaPorId (long idBebida)
	{
        log.info ("Eliminando bebida por id: " + idBebida);
        long resp = pp.eliminarBebidaPorId (idBebida);
        log.info ("Eliminando bebida por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todas las bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<Bebida> darBebidas ()
	{
        log.info ("Consultando Bebidas");
        List<Bebida> bebidas = pp.darBebidas ();	
        log.info ("Consultando Bebidas: " + bebidas.size() + " bebidas existentes");
        return bebidas;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBebida> darVOBebidas ()
	{
		log.info ("Generando los VO de las bebidas");       
        List<VOBebida> voBebidas = new LinkedList<VOBebida> ();
        for (Bebida beb : pp.darBebidas ())
        {
        	voBebidas.add (beb);
        }
        log.info ("Generando los VO de las bebidas: " + voBebidas.size() + " existentes");
        return voBebidas;
	}

	/**
	 * Elimina las bebidas que no son servidas en ningún bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicación
	 * @return El número de bebidas eliminadas
	 */
	public long eliminarBebidasNoServidas ()
	{
        log.info ("Borrando bebidas no servidas");
        long resp = pp.eliminarBebidasNoServidas ();
        log.info ("Borrando bebidas no servidas: " + resp + " bebidas eliminadas");
        return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Bebedor adicionarBebedor (String nombre, String presupuesto, String ciudad)
	{
        log.info ("Adicionando bebedor: " + nombre);
        Bebedor bebedor = pp.adicionarBebedor (nombre, presupuesto, ciudad);
        log.info ("Adicionando bebedor: " + bebedor);
        return bebedor;
	}

	/**
	 * Elimina un bebedor por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebedorPorNombre (String nombre)
	{
        log.info ("Eliminando bebedor por nombre: " + nombre);
        long resp = pp.eliminarBebedorPorNombre (nombre);
        log.info ("Eliminando bebedor por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebedorPorId (long idBebedor)
	{
        log.info ("Eliminando bebedor por id: " + idBebedor);
        long resp = pp.eliminarBebedorPorId (idBebedor);
        log.info ("Eliminando bebedor por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Bebedor darBebedorPorId (long idBebedor)
	{
        log.info ("Dar información de un bebedor por id: " + idBebedor);
        Bebedor bebedor = pp.darBebedorPorId (idBebedor);
        log.info ("Buscando bebedor por Id: " + bebedor != null ? bebedor : "NO EXISTE");
        return bebedor;
	}

	/**
	 * Encuentra la información básica de los bebedores, según su nombre
	 * @param nombre - El nombre de bebedor a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen bebedores con ese nombre
	 */
	public List<Bebedor> darBebedoresPorNombre (String nombre)
	{
        log.info ("Dar información de bebedores por nombre: " + nombre);
        List<Bebedor> bebedores = pp.darBebedoresPorNombre (nombre);
        log.info ("Dar información de Bebedores por nombre: " + bebedores.size() + " bebedores con ese nombre existentes");
        return bebedores;
 	}

	/**
	 * Encuentra la información básica de los bebedores, según su nombre y los devuelve como VO
	 * @param nombre - El nombre de bebedor a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen bebedores con ese nombre
	 */
	public List<VOBebedor> darVOBebedoresPorNombre (String nombre)
	{
        log.info ("Generando VO de bebedores por nombre: " + nombre);
        List<VOBebedor> voBebedores = new LinkedList<VOBebedor> ();
       for (Bebedor bdor : pp.darBebedoresPorNombre (nombre))
       {
          	voBebedores.add (bdor);
       }
       log.info ("Generando los VO de Bebedores: " + voBebedores.size() + " bebedores existentes");
      return voBebedores;
 	}

	/**
	 * Encuentra un bebedor, su información básica y los bares y las bebidas 
	 * con las que está directamente relacionado, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica y 
	 * 		los bares y bebidas con los que está directamente relacionado<br>
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Bebedor darBebedorCompleto (long idBebedor)
	{
        log.info ("Dar información COMPLETA de un bebedor por id: " + idBebedor);
        Bebedor bebedor = pp.darBebedorCompleto (idBebedor);
        log.info ("Buscando bebedor por Id: " + bebedor.toStringCompleto() != null ? bebedor : "NO EXISTE");
        return bebedor;
	}

	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Bebedor> darBebedores ()
	{
        log.info ("Listando Bebedores");
        List<Bebedor> bebedores = pp.darBebedores ();	
        log.info ("Listando Bebedores: " + bebedores.size() + " bebedores existentes");
        return bebedores;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBebedor> darVOBebedores ()
	{
        log.info ("Generando los VO de Bebedores");
         List<VOBebedor> voBebedores = new LinkedList<VOBebedor> ();
        for (Bebedor bdor : pp.darBebedores ())
        {
        	voBebedores.add (bdor);
        }
        log.info ("Generando los VO de Bebedores: " + voBebedores.size() + " bebedores existentes");
       return voBebedores;
	}
	
	/**
	 * Encuentra todos los bebedores que conoce la aplicación y el número visitas realizadas por cada uno
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de parejas [Bebedor, numVisitas]
	 */
	public List<Object []> darBebedoresYNumVisitasRealizadas ()
	{
        log.info ("Listando Bebedores y cuántas visitas ha realizado");
        List<Object []> tuplas = pp.darBebedoresYNumVisitasRealizadas ();
        log.info ("Listando Bebedores y cuántas visitas ha realizado: Listo!");
        return tuplas;
	}
	
	/**
	 * Dado el nombre de una ciudad, encuentra el número de bebedores de esa ciudad que han realizado por lo menos una visita a un bar
	 * Adiciona entradas al log de la aplicación
	 * @param ciudad - La ciudad de interés
	 * @return Un número que representa el número de bebedores de esa ciudad que hab realizado por lo menos una visita a un bar
	 */
	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
	{
        log.info ("Calculando cuántos Bebedores de una ciudad visitan bares");
        long resp = pp.darCantidadBebedoresCiudadVisitanBares (ciudad);
        log.info ("Calculando cuántos Bebedores de una ciudad visitan bares de " + ciudad +": " + resp);
        return resp;
	}
	
	/**
	 * Cambia la ciudad de un bebedor dado su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor que va a cambiar de ciudad
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que un bebedor con ese identificador no existe
	 */
	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
	{
        log.info ("Cambiando ciudad de bebedor: " + idBebedor);
        long cambios = pp.cambiarCiudadBebedor (idBebedor, ciudad);
        return cambios;
	}
	
	/**
	 * Elimina un bebedor y las visitas a bares que haya realizado v1: 
	 * En caso que el bebedor esté referenciado por otra relación, NO SE BORRA NI EL BEBEDOR, NI SUS VISITAS
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarBebedorYVisitas_v1 (long idBebedor)
	{
        log.info ("Eliminando bebedor con sus visitas v1: " + idBebedor);
        long [] resp = pp.eliminarBebedorYVisitas_v1 (idBebedor);
        log.info ("Eliminando bebedor con sus visitas v1: " + resp [0] + " bebedor y " + resp [1] + " visitas");
        return resp;
	}

	/**
	 * Elimina un bebedor y las visitas a bares que haya realizado v2
	 * En caso que el bebedor esté referenciado por otra relación, EL BEBEDOR NO SE BORRA. PERO SUS VISITAS SÍ
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
	{
        log.info ("Eliminando bebedor con sus visitas v2: " + idBebedor);
        long [] resp = pp.eliminarBebedorYVisitas_v2 (idBebedor);
        log.info ("Eliminando bebedor con sus visitas v2: " + resp [0] + " bebedor y " + resp [1] + " visitas");
        return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los BARES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un bar 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bar
	 * @param sedes - El número de sedes que tiene el bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public Bar adicionarBar (String nombre, String presupuesto, String ciudad, int sedes)
	{
        log.info ("Adicionando bar: " + nombre);
        Bar bar = pp.adicionarBar (nombre, presupuesto, ciudad, sedes);
        log.info ("Adicionando bar: " + bar);
        return bar;
	}
	
	/**
	 * Elimina un bar por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBarPorNombre (String nombre)
	{
        log.info ("Eliminando bar por nombre: " + nombre);
        long resp = pp.eliminarBarPorNombre (nombre);
        log.info ("Eliminando bar: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBarPorId (long idBar)
	{
        log.info ("Eliminando bar por id: " + idBar);
        long resp = pp.eliminarBarPorId (idBar);
        log.info ("Eliminando bar: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los bares en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<Bar> darBares ()
	{
        log.info ("Listando Bares");
        List<Bar> bares = pp.darBares ();	
        log.info ("Listando Bares: " + bares.size() + " bares existentes");
        return bares;
	}

	/**
	 * Encuentra todos los bares en Parranderos y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBar> darVOBares ()
	{
		log.info ("Generando los VO de Bares");
		List<VOBar> voBares = new LinkedList<VOBar> ();
		for (Bar bar: pp.darBares ())
		{
			voBares.add (bar);
		}
		log.info ("Generando los VO de Bares: " + voBares.size () + " bares existentes");
		return voBares;
	}

	/**
	 * Aumenta en 1 el número de sedes de los bares de una ciudad
	 * Adiciona entradas al log de la aplicación
	 * @param ciudad - La ciudad en la cual se aumenta el número de sedes de los bares
	 * @return El número de tuplas actualizadas
	 */
	public long aumentarSedesBaresCiudad (String ciudad)
	{
        log.info ("Aumentando sedes de bares de una ciudad: " + ciudad);
        long resp = pp.aumentarSedesBaresCiudad (ciudad);
        log.info ("Aumentando sedes de bares de una ciudad: " + resp + " tuplas actualizadas");
        return resp;
	}
	
	/**
	 * Encuentra los bares que conoce la aplicación y el número de bebidas que sirve cada uno, 
	 * para aquellos bares que sirven por lo menos una bebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de parejas [IdBar, numBebidas]
	 */
	public List<long []> darBaresYCantidadBebidasSirven ()
	{
        log.info ("Listando Bares y cuántos bebidas sirven");
        List<long []> tuplas = pp.darBaresYCantidadBebidasSirven ();
        log.info ("Listando Bares y cuántos bebidas sirven: Listo!");
        return tuplas;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación GUSTAN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return Un objeto Gustan con los valores dados
	 */
	public Gustan adicionarGustan (long idBebedor, long idBebida)
	{
        log.info ("Adicionando gustan [" + idBebedor + ", " + idBebida + "]");
        Gustan resp = pp.adicionarGustan (idBebedor, idBebida);
        log.info ("Adicionando gustan: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarGustan (long idBebedor, long idBebida)
	{
        log.info ("Eliminando gustan");
        long resp = pp.eliminarGustan (idBebedor, idBebida);
        log.info ("Eliminando gustan: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los gustan en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Gustan> darGustan ()
	{
        log.info ("Listando Gustan");
        List<Gustan> gustan = pp.darGustan ();	
        log.info ("Listando Gustan: " + gustan.size() + " preferencias de gusto existentes");
        return gustan;
	}

	/**
	 * Encuentra todos los gustan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOGustan> darVOGustan ()
	{
		log.info ("Generando los VO de Gustan");
		List<VOGustan> voGustan = new LinkedList<VOGustan> ();
		for (VOGustan bar: pp.darGustan ())
		{
			voGustan.add (bar);
		}
		log.info ("Generando los VO de Gustan: " + voGustan.size () + " Gustan existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Sirven con los valores dados
	 */
	public Sirven adicionarSirven (long idBar, long idBebida, String horario)
	{
        log.info ("Adicionando sirven [" + idBar + ", " + idBebida + "]");
        Sirven resp = pp.adicionarSirven (idBar, idBebida, horario);
        log.info ("Adicionando sirven: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSirven (long idBar, long idBebida)
	{
        log.info ("Eliminando sirven");
        long resp = pp.eliminarSirven (idBar, idBebida);
        log.info ("Eliminando sirven: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los SIRVEN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Sirven> darSirven ()
	{
        log.info ("Listando Sirven");
        List<Sirven> sirven = pp.darSirven ();	
        log.info ("Listando Sirven: " + sirven.size() + " sirven existentes");
        return sirven;
	}

	/**
	 * Encuentra todos los sirven en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los SIRVEN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSirven> darVOSirven ()
	{
		log.info ("Generando los VO de Sirven");
		List<VOSirven> voGustan = new LinkedList<VOSirven> ();
		for (VOSirven sirven: pp.darSirven ())
		{
			voGustan.add (sirven);
		}
		log.info ("Generando los VO de Sirven: " + voGustan.size () + " Sirven existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @param fecha - La fecha en la que se realizó la visita
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Visitan con los valores dados
	 */
	public Visitan adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario)
	{
        log.info ("Adicionando visitan [" + idBebedor + ", " + idBar + "]");
        Visitan resp = pp.adicionarVisitan (idBebedor, idBar, fecha, horario);
        log.info ("Adicionando visitan: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVisitan (long idBebedor, long idBar)
	{
        log.info ("Eliminando visitan");
        long resp = pp.eliminarVisitan (idBebedor, idBar);
        log.info ("Eliminando visitan: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los VISITAN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VISITAN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Visitan> darVisitan ()
	{
        log.info ("Listando Visitan");
        List<Visitan> visitan = pp.darVisitan ();	
        log.info ("Listando Visitan: Listo!");
        return visitan;
	}

	/**
	 * Encuentra todos los visitan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Visitan con todos los Visitan que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVisitan> darVOVisitan ()
	{
		log.info ("Generando los VO de Visitan");
		List<VOVisitan> voGustan = new LinkedList<VOVisitan> ();
		for (VOVisitan vis: pp.darVisitan ())
		{
			voGustan.add (vis);
		}
		log.info ("Generando los VO de Visitan: " + voGustan.size () + " Visitan existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
}
