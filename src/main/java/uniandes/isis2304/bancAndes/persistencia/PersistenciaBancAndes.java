package uniandes.isis2304.bancAndes.persistencia;


import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.negocio.Asociacion;
import uniandes.isis2304.bancAndes.negocio.AsociacionCuentasEmpleados;
import uniandes.isis2304.bancAndes.negocio.Cajero;
import uniandes.isis2304.bancAndes.negocio.CajeroAutomatico;
import uniandes.isis2304.bancAndes.negocio.Cliente;
import uniandes.isis2304.bancAndes.negocio.ClienteProducto;
import uniandes.isis2304.bancAndes.negocio.Cuenta;
import uniandes.isis2304.bancAndes.negocio.Empleado;
import uniandes.isis2304.bancAndes.negocio.GerenteDeOficina;
import uniandes.isis2304.bancAndes.negocio.GerenteGeneral;
import uniandes.isis2304.bancAndes.negocio.Oficina;
import uniandes.isis2304.bancAndes.negocio.OperacionBancaria;
import uniandes.isis2304.bancAndes.negocio.Prestamo;
import uniandes.isis2304.bancAndes.negocio.Producto;
import uniandes.isis2304.bancAndes.negocio.PuestoAtencionOficina;
import uniandes.isis2304.bancAndes.negocio.PuestoAtencionTipoOperacion;
import uniandes.isis2304.bancAndes.negocio.PuestoDeAtencion;
import uniandes.isis2304.bancAndes.negocio.PuestoDigital;
import uniandes.isis2304.bancAndes.negocio.Usuario;
import uniandes.isis2304.bancAndes.negocio.UsuarioTipoOperacion;

public class PersistenciaBancAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaBancAndes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaBancAndes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla ACCIONES de la base de datos
	 */
	private SQLAccion sqlAccion;
	/**
	 * Atributo para el acceso a la tabla CAJERO de la base de datos
	 */
	private SQLCajero sqlCajero;
	/**
	 * Atributo para el acceso a la tabla CAJEROSAUTOMATICOS de la base de datos
	 */
	private SQLCajeroAutomatico sqlCajeroAutomatico;
	/**
	 * Atributo para el acceso a la tabla CLIENTES de la base de datos
	 */
	private SQLCliente sqlCliente;
	/**
	 * Atributo para el acceso a la tabla CLIENTESPRODUCTOS de la base de datos
	 */
	private SQLClienteProducto sqlClienteProducto;
	/**
	 * Atributo para el acceso a la tabla CUENTA de la base de datos
	 */
	private SQLCuenta sqlCuenta;
	/**
	 * Atributo para el acceso a la tabla DEPOSITOSINVERSION de la base de datos
	 */
	private SQLDepositoInversion sqlDepositoInversion;
	/**
	 * Atributo para el acceso a la tabla EMPLEADOS de la base de datos
	 */
	private SQLEmpleado sqlEmpleado;
	/**
	 * Atributo para el acceso a la tabla GERENTESDEOFICINA de la base de datos
	 */
	private SQLGerenteDeOficina sqlGerenteDeOficina;
	/**
	 * Atributo para el acceso a la tabla GERENTEGENERAL de la base de datos
	 */
	private SQLGerenteGeneral sqlGerenteGeneral;
	/**
	 * Atributo para el acceso a la tabla OFICINAS de la base de datos
	 */
	private SQLOficina sqlOficina;
	/**
	 * Atributo para el acceso a la tabla OPERACIONESBANCARIAS de la base de datos
	 */
	private SQLOperacionBancaria sqlOperacionBancaria;
	/**
	 * Atributo para el acceso a la tabla PRESTAMOS de la base de datos
	 */
	private SQLPrestamo sqlPrestamo;
	/**
	 * Atributo para el acceso a la tabla PRODUCTOS de la base de datos
	 */
	private SQLProducto sqlProducto;
	/**
	 * Atributo para el acceso a la tabla PUESTOSATENCIONOFICINA de la base de datos
	 */
	private SQLPuestoAtencionOficina sqlPuestoAtencionOficina;
	/**
	 * Atributo para el acceso a la tabla PUESTOSATENCIONTIPOSOPERACION de la base de datos
	 */
	private SQLPuestoAtencionTipoOperacion sqlPuestoAtencionTipoOperacion;
	/**
	 * Atributo para el acceso a la tabla PUESTOSDEATENCION de la base de datos
	 */
	private SQLPuestoDeAtencion sqlPuestoDeAtencion;
	/**
	 * Atributo para el acceso a la tabla PUESTOSDIGITALES de la base de datos
	 */
	private SQLPuestoDigital sqlPuestoDigital;
	/**
	 * Atributo para el acceso a la tabla TIPOSOPERACION de la base de datos
	 */
	private SQLTipoOperacion sqlTipoOperacion;
	/**
	 * Atributo para el acceso a la tabla USUARIOS de la base de datos
	 */
	private SQLUsuario sqlUsuario;
	/**
	 * Atributo para el acceso a la tabla USUARIOSTIPOSOPERACION de la base de datos
	 */
	private SQLUsuarioTipoOperacion sqlUsuarioTipoOperacion;

	/**
	 * Atributo para el acceso a la tabla ASOCIACIONES de la base de datos
	 */
	private SQLAsociacion sqlAsociacion;


	/**
	 * Atributo para el acceso a la tabla ASOCIACIONCUENTASEMPLEADOS de la base de datos
	 */
	private SQLAsociacionCuentasEmpleados sqlAsociacionCuentasEmpleados;



	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaBancAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("BancAndes");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("BANCANDES_SEQUENCE");
		tablas.add ("USUARIOS");
		tablas.add ("EMPLEADOS");
		tablas.add ("TIPOSOPERACION");
		tablas.add ("PRODUCTOS");
		tablas.add ("PUESTOSDEATENCION");
		tablas.add ("CLIENTES");
		tablas.add ("CAJEROS");
		tablas.add ("GERENTESDEOFICINA");
		tablas.add ("GERENTEGENERAL");
		tablas.add ("OFICINAS");
		tablas.add ("PUESTOSATENCIONOFICINA");
		tablas.add ("PUESTOSDIGITALES");
		tablas.add ("CAJEROSAUTOMATICOS");
		tablas.add ("OPERACIONESBANCARIAS");
		tablas.add ("PRESTAMOS");
		tablas.add ("CUENTAS");
		tablas.add ("ACCIONES");
		tablas.add ("DEPOSITOSINVERSION");
		tablas.add ("USUARIOSTIPOSOPERACION");
		tablas.add ("PUESTOSATENCIONTIPOSOPERACION");
		tablas.add ("CLIENTESPRODUCTOS");
		tablas.add("ASOCIACIONES");
		tablas.add("ASOCIACIONCUENTASEMPLEADOS");
	}


	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaBancAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaBancAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaBancAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaBancAndes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaBancAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaBancAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaBancAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL() {
		sqlUtil = new SQLUtil(this);
		sqlAccion = new SQLAccion(this);
		sqlCajero = new SQLCajero(this);
		sqlCajeroAutomatico = new SQLCajeroAutomatico(this);
		sqlCliente = new SQLCliente(this);
		sqlClienteProducto = new SQLClienteProducto(this);
		sqlCuenta = new SQLCuenta(this);
		sqlDepositoInversion = new SQLDepositoInversion(this);
		sqlEmpleado = new SQLEmpleado(this);
		sqlGerenteDeOficina = new SQLGerenteDeOficina(this);
		sqlGerenteGeneral = new SQLGerenteGeneral(this);
		sqlOficina = new SQLOficina(this);
		sqlOperacionBancaria = new SQLOperacionBancaria(this);
		sqlPrestamo = new SQLPrestamo(this);
		sqlProducto = new SQLProducto(this);
		sqlPuestoAtencionOficina = new SQLPuestoAtencionOficina(this);
		sqlPuestoAtencionTipoOperacion = new SQLPuestoAtencionTipoOperacion(this);
		sqlPuestoDeAtencion = new SQLPuestoDeAtencion(this);
		sqlPuestoDigital = new SQLPuestoDigital(this);
		sqlTipoOperacion = new SQLTipoOperacion(this);
		sqlUsuario = new SQLUsuario(this);
		sqlUsuarioTipoOperacion = new SQLUsuarioTipoOperacion(this);
		sqlAsociacion = new SQLAsociacion(this);
		sqlAsociacionCuentasEmpleados = new SQLAsociacionCuentasEmpleados(this);

	}


	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de BancAndes
	 */
	public String darSeqBancAndes()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Usuarios de BancAndes
	 */
	public String darTablaUsuarios ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Empleados de BancAndes
	 */
	public String darTablaEmpleados()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla TiposOperacion de BancAndes
	 */
	public String darTablaTiposOperacion ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Productos de BancAndes
	 */
	public String darTablaProductos ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PuestosDeAtencion de BancAndes
	 */
	public String darTablaPuestosDeAtencion ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Clientes de BancAndes
	 */
	public String darTablaClientes()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Cajeros de BancAndes
	 */
	public String darTablaCajeros ()
	{
		return tablas.get (7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla GerentesDeOficina de BancAndes
	 */
	public String darTablaGerentesDeOficina ()
	{
		return tablas.get (8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla GerenteGeneral de BancAndes
	 */
	public String darTablaGerenteGeneral ()
	{
		return tablas.get (9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Oficinas de BancAndes
	 */
	public String darTablaOficinas ()
	{
		return tablas.get (10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PuestoAtencionOficina de BancAndes
	 */
	public String darTablaPuestosAtencionOficina ()
	{
		return tablas.get (11);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PuestosDigitales de BancAndes
	 */
	public String darTablaPuestosDigitales ()
	{
		return tablas.get (12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla CajerosAutomaticos de BancAndes
	 */
	public String darTablaCajerosAutomaticos()
	{
		return tablas.get (13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla OperacionesBancarias de BancAndes
	 */
	public String darTablaOperacionesBancarias ()
	{
		return tablas.get (14);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Prestamos de BancAndes
	 */
	public String darTablaPrestamos ()
	{
		return tablas.get (15);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Cuentas de BancAndes
	 */
	public String darTablaCuentas ()
	{
		return tablas.get (16);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Acciones de BancAndes
	 */
	public String darTablaAcciones ()
	{
		return tablas.get (17);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla DepositosInversion de BancAndes
	 */
	public String darTablaDepositosInversion ()
	{
		return tablas.get (18);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla UsuariosTiposOperacion de BancAndes
	 */
	public String darTablaUsuariosTiposOperacion ()
	{
		return tablas.get (19);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla PuestosAtencionTiposOperacion de BancAndes
	 */
	public String darTablaPuestosAtencionTiposOperacion()
	{
		return tablas.get (20);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla ClientesProductos de BancAndes
	 */
	public String darTablaClientesProductos ()
	{
		return tablas.get (21);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla Asociaciones de BancAndes
	 */
	public String darTablaAsociaciones ()
	{
		return tablas.get (22);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla AsociacioCuentasEmpleados de BancAndes
	 */
	public String darTablaAsociacionCuentasEmpleados() {
		return tablas.get(23);

	}


	/**
	 * Transacción para el generador de secuencia de BancAndes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de BancAndes
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}




	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/

	public Usuario adicionarUsuario(String login) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlUsuario.adicionarUsuario(pm,login);
			tx.commit();

			log.trace ("Inserción de usuario: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Usuario (login);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Usuario, dado el identificador del Usuario
	 * Adiciona entradas al log de la aplicación
	 * @param login - El login del usuario
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarUsuario (String login) 
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			//falla
			long resp = sqlUsuario.eliminarUsuario(pm, login);

			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/

	public Cliente adicionarCliente(String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String tipo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCliente.adicionarCliente(pm,tipoDocumento, numeroDocumento, departamento, codigopostal,
					nacionalidad, nombre, direccion, login, contrasena, correo,
					telefono, ciudad, tipo);
			tx.commit();

			log.trace ("Inserción de cliente: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cliente (tipoDocumento, numeroDocumento, departamento, codigopostal,
					nacionalidad, nombre, direccion, login, contrasena, correo,
					telefono, ciudad, tipo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Cliente> darClientes() {
		return sqlCliente.darClientes (pmf.getPersistenceManager());
	}

	public Cliente darClientePorLogin(String login) {
		return  sqlCliente.darClientePorLogin (pmf.getPersistenceManager(), login);
	}

	/* ****************************************************************
	 * 			Métodos para manejar los EMPLEADOS
	 *****************************************************************/

	public Empleado adicionarEmpleado(String login) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlEmpleado.adicionarEmpleado(pm,login);
			tx.commit();

			log.trace ("Inserción de empleado: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Empleado (login);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/* ****************************************************************
	 * 			Métodos para manejar GERENTEGENERAL
	 *****************************************************************/

	public GerenteGeneral adicionarGerenteGeneral(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador, long oficina) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlGerenteGeneral.adicionarGerenteGeneral(pm, tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador, oficina);
			tx.commit();

			log.trace ("Inserción de gerente general: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new GerenteGeneral (tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador, oficina);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public GerenteGeneral darGerenteGeneralPorLogin(String login) {
		return  sqlGerenteGeneral.darGerenteGeneralPorLogin (pmf.getPersistenceManager(), login);
	}

	/* ****************************************************************
	 * 			Métodos para manejar CAJERO
	 *****************************************************************/
	public Cajero adicionarCajero(String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String administrador, long puestoAtencionoficina, long oficina) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlCajero.adicionarCajero(pm, tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador, puestoAtencionoficina,oficina);
			tx.commit();

			log.trace ("Inserción de cajero: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cajero (tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador,puestoAtencionoficina, oficina);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public Cajero darCajeroPorLogin(String login) {
		return  sqlCajero.darCajeroPorLogin (pmf.getPersistenceManager(), login);
	}


	/**
	 * @param idBebedor
	 * @param ciudad
	 * @return
	 */
	public long asociarPuestoDeAtencionOficinaCajero (long idPuesto, String login)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCajero.asociarPuestoDeAtencionOficinaCajero (pm, idPuesto, login);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			Métodos para manejar GERENTEDEOFICINA
	 *****************************************************************/


	public GerenteDeOficina adicionarGerenteDeOficina(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlGerenteDeOficina.adicionarGerenteDeOficina(pm, tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador);
			tx.commit();

			log.trace ("Inserción de gerente de oficina: " + login + ": " + tuplasInsertadas + " tuplas insertadas");

			return new GerenteDeOficina (tipoDocumento, numeroDocumento, departamento,
					codigopostal, nacionalidad, nombre, direccion, login, contrasena,
					correo, telefono, ciudad, administrador);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public GerenteDeOficina darGerenteDeOficinaPorLogin(String login) {
		return  sqlGerenteDeOficina.darGerenteDeOficinaPorLogin (pmf.getPersistenceManager(), login);
	}


	/* ****************************************************************
	 * 			Métodos para manejar OPERACIONBANCARIA
	 *****************************************************************/

	public OperacionBancaria adicionarOperacionBancaria( float valor, Date fecha, String cliente, long productoOrigen,long productoDestino,
			String tipoOperacion, long puestoAtencion, String empleado) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlOperacionBancaria.adicionarOperacionBancaria(pm, id, valor, fecha, cliente, productoOrigen,productoDestino,
					tipoOperacion, puestoAtencion, empleado);
			tx.commit();

			log.trace ("Inserción de operacion bancaria: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new OperacionBancaria ( id, valor, fecha, cliente, productoOrigen,productoDestino,
					tipoOperacion, puestoAtencion, empleado);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	/* ****************************************************************
	 * 			Métodos para manejar CUENTA
	 *****************************************************************/
	/**
	 * @param id
	 * @return
	 */
	public Cuenta darCuentaPorId(long id) {
		Cuenta cuenta=null;
		try {
			cuenta = sqlCuenta.darCuentaPorId (pmf.getPersistenceManager(), id);
		}catch (Exception e) {
			e.printStackTrace();
		}

		return cuenta;
	}

	public Cuenta darCuentaPorNumero(int numero) {
		return sqlCuenta.darCuentaPorNumero (pmf.getPersistenceManager(), numero);
	}

	public Cuenta adicionarCuenta( long id, int numeroCuenta, String estado, String tipo, float saldo,
			Date fechaCreacion, Date dechaVencimiento, float tasaRendimiento, long oficina, String corporativo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long tuplasInsertadas = sqlCuenta.adicionarCuenta(pm, id, numeroCuenta, estado, tipo, saldo,
					fechaCreacion, dechaVencimiento, tasaRendimiento, oficina, corporativo);
			tx.commit();

			log.trace ("Inserción de cuenta: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cuenta ( id, numeroCuenta, estado, tipo, saldo,
					fechaCreacion, dechaVencimiento, tasaRendimiento, oficina, corporativo);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public long cerrarCuenta(long idCuenta) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCuenta.cerrarCuenta (pm, idCuenta);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long cambiarActividadCuenta(long idCuenta, String estado) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCuenta.cambiarActividadCuenta (pm, idCuenta, estado);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public long actualizarSaldoCuenta(long idCuenta, float cambioSaldo) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCuenta.actualizarSaldoCuenta (pm, idCuenta, cambioSaldo);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<Cuenta> darCuentas() {
		return sqlCuenta.darCuentas (pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar PRESTAMO
	 *****************************************************************/

	public Prestamo adicionarPrestamo(long id, float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long tuplasInsertadas = sqlPrestamo.adicionarPrestamo(pm, id, monto, saldoPendiente, interes, numeroCuotas,
					diaPago, valorCuotaMinima, fechaPrestamo, cerrado);
			tx.commit();

			log.trace ("Inserción de prestamo: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Prestamo ( id, monto, saldoPendiente, interes, numeroCuotas,
					diaPago, valorCuotaMinima, fechaPrestamo, cerrado);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long cerrarPrestamo(long idPrestamo) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPrestamo.cerrarPrestamo (pm, idPrestamo);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long realizarPago(long idPrestamo, float montoPago) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPrestamo.realizarPago (pm, idPrestamo, montoPago);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Prestamo darPrestamoPorId(long id) {
		return  sqlPrestamo.darPrestamoPorId (pmf.getPersistenceManager(), id);
	}



	/* ****************************************************************
	 * 			Métodos para manejar PUESTODEATENCION
	 *****************************************************************/


	public PuestoDeAtencion adicionarPuestoDeAtencion() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlPuestoDeAtencion.adicionarPuestoDeAtencion(pm,id);
			tx.commit();

			log.trace ("Inserción de puesto de atencion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new PuestoDeAtencion (id);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla puesto de atencion, dado el id del puesto de atencion
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id del puesto de atencion
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPuestoDeAtencion(long id) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();


			long resp = sqlPuestoDeAtencion.eliminarPuestoDeAtencion(pm, id);

			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			Métodos para manejar PUESTOATENCIONOFICINA
	 *****************************************************************/

	public PuestoAtencionOficina adicionarPuestoAtencionOficina( long id, int telefono, String localizacion,
			long oficina) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlPuestoAtencionOficina.adicionarPuestoAtencionOficina(pm, id, telefono, localizacion,
					oficina);
			tx.commit();

			log.trace ("Insercion de puesto atencion oficina: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new PuestoAtencionOficina ( id, telefono, localizacion, oficina);
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			Métodos para manejar PUESTODIGITAL
	 *****************************************************************/
	public PuestoDigital adicionarPuestoDigital( long id,int telefono, String tipo, String url) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlPuestoDigital.adicionarPuestoDigital(pm, id, telefono, tipo, url);
			tx.commit();

			log.trace ("Insercion de puesto digital: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new PuestoDigital ( id, telefono, tipo, url);
		}
		catch (Exception e)
		{
			//     e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			Métodos para manejar OFICINA
	 *****************************************************************/
	public Oficina adicionarOficina( String nombre, String direccion, int puestosPosibles,
			String gerenteLogin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlOficina.adicionarOficina(pm, id, nombre, direccion, puestosPosibles,
					gerenteLogin);
			tx.commit();

			log.trace ("Inserción de oficina: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Oficina ( id, nombre, direccion, puestosPosibles,
					gerenteLogin);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * @param id
	 * @return
	 */
	public Oficina darOficinaPorId(long id) {
		return sqlOficina.darOficinaPorId (pmf.getPersistenceManager(), id);
	}


	/**
	 * @param gerenteLogin
	 * @return
	 */
	public Oficina darOficinaPorGerenteDeOficina(String gerenteLogin) {
		return sqlOficina.darOficinaPorGerenteDeOficina (pmf.getPersistenceManager(), gerenteLogin);
	}


	/* ****************************************************************
	 * 			Métodos para manejar CAJEROAUTOMATICO
	 *****************************************************************/
	public CajeroAutomatico adicionarCajeroAutomatico( long id, int telefono, String localizacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			long tuplasInsertadas = sqlCajeroAutomatico.adicionarCajeroAutomatico(pm, id, telefono, localizacion);
			tx.commit();

			log.trace ("Inserción de Cajero automatico: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new CajeroAutomatico (id, telefono, localizacion);
		}
		catch (Exception e)
		{
			//    e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}



	/* ****************************************************************
	 * 			Métodos para manejar PRODUCTO
	 *****************************************************************/
	public Producto adicionarProducto() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = nextval ();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm,id);
			tx.commit();

			log.trace ("Inserción de producto: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Producto (id);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Producto, dado el id del Producto
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id del producto
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProducto(long id) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

			//falla
			long resp = sqlProducto.eliminarProducto(pm, id);

			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/* ****************************************************************
	 * 			Métodos para manejar CLIENTEPRODUCTO
	 *****************************************************************/
	public ClienteProducto adicionarClienteProducto(long id, String login) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlClienteProducto.adicionarClienteProducto(pm,id, login);
			tx.commit();

			log.trace ("Inserción de producto id:" + id + " cliente : " + login + ": "+ tuplasInsertadas + " tuplas insertadas");

			return new ClienteProducto (id, login);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<ClienteProducto> darClienteProductoPorCliente(String login) {
		return sqlClienteProducto.darClienteProductoPorCliente (pmf.getPersistenceManager(), login);
	}


	public List<ClienteProducto> darClienteProductoPorProducto(long id) {
		return sqlClienteProducto.darClienteProductoPorProducto (pmf.getPersistenceManager(), id);
	}

	public ClienteProducto darClienteProducto(long producto, String cliente) {
		return sqlClienteProducto.darClienteProducto (pmf.getPersistenceManager(), producto, cliente);
	}


	/* ****************************************************************
	 * 			Métodos para manejar USUARIOTIPOOPERACION
	 *****************************************************************/
	public UsuarioTipoOperacion adicionarUsuarioTipoOperacion(String tipoOperacion, String usuario) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlUsuarioTipoOperacion.adicionarUsuarioTipoOperacion(pm,tipoOperacion, usuario);
			tx.commit();

			log.trace ("Insercion de tipo de operacion:" + tipoOperacion + " usuario : " + usuario + ": "+ tuplasInsertadas + " tuplas insertadas");

			return new UsuarioTipoOperacion (tipoOperacion, usuario);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorUsuario(String usuario) {
		return sqlUsuarioTipoOperacion.darUsuarioTipoOperacionPorUsuario (pmf.getPersistenceManager(), usuario);
	}


	public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorTipo(String tipoOperacion) {
		return sqlUsuarioTipoOperacion.darUsuarioTipoOperacionPorTipo (pmf.getPersistenceManager(), tipoOperacion);
	}

	public UsuarioTipoOperacion darUsuarioTipoOperacion(String usuario, String tipoOperacion) {
		return sqlUsuarioTipoOperacion.darUsuarioTipoOperacion (pmf.getPersistenceManager(), usuario, tipoOperacion);
	}

	/* ****************************************************************
	 * 			Métodos para manejar PUESTOATENCIONTIPOOPERACION
	 *****************************************************************/
	public PuestoAtencionTipoOperacion adicionarPuestoAtencionTipoOperacion(String tipoOperacion, long puesto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlPuestoAtencionTipoOperacion.adicionarPuestoAtencionTipoOperacion(pm,tipoOperacion, puesto);
			tx.commit();

			log.trace ("Insercion de tipo de operacion:" + tipoOperacion + " puesto : " + puesto + ": "+ tuplasInsertadas + " tuplas insertadas");

			return new PuestoAtencionTipoOperacion (tipoOperacion, puesto);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorPuesto(long puesto) {
		return sqlPuestoAtencionTipoOperacion.darPuestoAtencionTipoOperacionPorPuesto (pmf.getPersistenceManager(), puesto);
	}


	public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorTipo(String tipoOperacion) {
		return sqlPuestoAtencionTipoOperacion.darPuestoAtencionTipoOperacionPorTipo (pmf.getPersistenceManager(), tipoOperacion);
	}

	public PuestoAtencionTipoOperacion darPuestoAtencionTipoOperacion(long puesto, String tipoOperacion) {
		return sqlPuestoAtencionTipoOperacion.darPuestoAtencionTipoOperacion (pmf.getPersistenceManager(), puesto, tipoOperacion);
	}


	public PuestoDigital darPuestoDigitalPorId(long id) {
		return sqlPuestoDigital.darPuestoDigitalPorId (pmf.getPersistenceManager(), id);
	}


	public CajeroAutomatico darCajeroAutomaticoPorId(long id) {
		return sqlCajeroAutomatico.darCajeroAutomaticoPorId (pmf.getPersistenceManager(), id);
	}






	/* ****************************************************************
	 * 			Métodos para manejar Asociacion
	 *****************************************************************/

	/**
	 * @param id
	 * @param valor
	 * @param frecuencia
	 * @param cuentaCorporativo
	 * @return
	 */
	public Asociacion adicionarAsociacion( float valor, String frecuencia,  int cuentaCorporativo) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long id = nextval();
			long tuplasInsertadas = sqlAsociacion.adicionarAsociacion(pm, id, valor, frecuencia, cuentaCorporativo);
			tx.commit();

			log.trace ("Insercion de Asociacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Asociacion ( id, valor, frecuencia, cuentaCorporativo);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}



	/**
	 * @param idAsociacion
	 * @param numeroCuenta
	 * @return
	 */
	public long actualizarCuentaAsociacion(long idAsociacion, int numeroCuenta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlAsociacion.actualizarCuentaAsociacion (pm, idAsociacion, numeroCuenta);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}




	/**
	 * @param id
	 * @return
	 */
	public long eliminarAsociacion(long id) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();

		
			long resp = sqlAsociacion.eliminarAsociacion(pm, id);

			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}





	/* ****************************************************************
	 * 			Métodos para manejar Asociacion cuenta empleados
	 *****************************************************************/


	/**
	 * @param asociacion
	 * @param cuentaEmpleado
	 * @return
	 */
	public AsociacionCuentasEmpleados adicionarAsociacionCuentasEmpleados(long asociacion, long cuentaEmpleado) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlAsociacionCuentasEmpleados.adicionarAsociacionCuentasEmpleados(pm,asociacion, cuentaEmpleado);
			tx.commit();

			log.trace ("Insercion de la asociacion id:" + asociacion + " cuenta empleado : " + cuentaEmpleado + ": "+ tuplasInsertadas + " tuplas insertadas");

			return new AsociacionCuentasEmpleados (asociacion, cuentaEmpleado);
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}







	//******************************************METODOS DE LOS REQUERIMIENTOS DE CONSULTA*************************************************************************
	/**
	 * @param id
	 * @param criterio1p
	 * @param signo1
	 * @param filtro1p
	 * @param criterio2p
	 * @param signo2
	 * @param filtro2p
	 * @param ordenamiento
	 * @param tipoOrdenamiento
	 * @return
	 */
	public List<Object[]> consultarCuentasGerenteOficina(String id, String criterio1p, String signo1, String filtro1p,
			String criterio2p, String signo2, String filtro2p, String ordenamiento, String tipoOrdenamiento) {
		return sqlCuenta.consultarCuentasGerenteOficina(pmf.getPersistenceManager(), id, criterio1p, signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrdenamiento);
	}


	public List<Object[]> consultarCuentasGerenteOficinaAgrupamiento(String agrupamiento, String id, String criterio1p,
			String signo1, String filtro1p, String criterio2p, String signo2, String filtro2p, String ordenamiento,
			String tipoOrdenamiento) {
		return sqlCuenta.consultarCuentasGerenteOficinaAgrupamiento(pmf.getPersistenceManager(), agrupamiento, id, criterio1p,signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrdenamiento);
	}


	public List<Object[]> consultarCuentasGerenteGeneral(String criterio1p, String signo1, String filtro1p,
			String criterio2p, String signo2, String filtro2p, String ordenamiento, String tipoOrden) {
		return sqlCuenta.consultarCuentasGerenteGeneral(pmf.getPersistenceManager(), criterio1p, signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrden);

	}


	public List<Object[]> consultarCuentasGerenteGeneralAgrupamiento(String agrupamiento, String criterio1p,
			String signo1, String filtro1p, String criterio2p, String signo2, String filtro2p, String ordenamiento,
			String tipoOrden) {
		return sqlCuenta.consultarCuentasGerenteGeneralAgrupamiento(pmf.getPersistenceManager(), agrupamiento, criterio1p,signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrden);

	}


	public List<Object[]> consultarCuentasCliente(String login, String criterio1p, String signo1, String filtro1p,
			String criterio2p, String signo2, String filtro2p, String ordenamiento, String tipoOrden) {
		return sqlCuenta.consultarCuentasCliente(pmf.getPersistenceManager(), login, criterio1p, signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrden);

	}


	public List<Object[]> consultarCuentasClienteAgrupamiento(String agrupamiento, String login, String criterio1p,
			String signo1, String filtro1p, String criterio2p, String signo2, String filtro2p, String ordenamiento,
			String tipoOrden) {
		return sqlCuenta.consultarCuentasClienteAgrupamiento(pmf.getPersistenceManager(), agrupamiento, login, criterio1p,signo1, filtro1p, criterio2p, signo2, filtro2p, ordenamiento, tipoOrden);

	}

	//***************************************************RFC3************************************************************



	public List<Object[]> consultar10OperacionesGG(String fechaInicio, String fechaFin) {
		return sqlOperacionBancaria.consultar10OperacionesGG(pmf.getPersistenceManager(), fechaInicio, fechaFin);

	}


	public List<Object[]> consultar10OperacionesGOf(String fechaInicio, String fechaFin, long idOficina) {
		return sqlOperacionBancaria.consultar10OperacionesGOf(pmf.getPersistenceManager(), fechaInicio, fechaFin, idOficina);

	}


	//***************************************************RFC4************************************************************
	public List<Object[]> obtenerUsuarioMasActivoValorGG(String tipoUsuario, float valor) {
		return sqlOperacionBancaria.obtenerUsuarioMasActivoValorGG(pmf.getPersistenceManager(), tipoUsuario, valor);

	}

	public List<Object[]> obtenerUsuarioMasActivoTipoOpGG(String tipoUsuario, String tipoOperacion) {
		return sqlOperacionBancaria.obtenerUsuarioMasActivoTipoOpGG(pmf.getPersistenceManager(), tipoUsuario, tipoOperacion);

	}

	public List<Object[]> obtenerUsuarioMasActivoValorGOf(String tipoUsuario, float valor, long idOficina) {
		return sqlOperacionBancaria.obtenerUsuarioMasActivoValorGOf(pmf.getPersistenceManager(), tipoUsuario, valor, idOficina);

	}


	public List<Object[]> obtenerUsuarioMasActivoTipoOpGOf(String tipoUsuario, String tipoOperacion, long idOficina) {
		return sqlOperacionBancaria.obtenerUsuarioMasActivoTipoOpGOf(pmf.getPersistenceManager(), tipoUsuario, tipoOperacion, idOficina);

	}







}
