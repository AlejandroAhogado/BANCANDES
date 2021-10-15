package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
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

import uniandes.isis2304.bancAndes.negocio.CajeroAutomatico;
import uniandes.isis2304.bancAndes.negocio.Cliente;
import uniandes.isis2304.bancAndes.negocio.Cuenta;
import uniandes.isis2304.bancAndes.negocio.Empleado;
import uniandes.isis2304.bancAndes.negocio.GerenteDeOficina;
import uniandes.isis2304.bancAndes.negocio.GerenteGeneral;
import uniandes.isis2304.bancAndes.negocio.Oficina;
import uniandes.isis2304.bancAndes.negocio.OperacionBancaria;
import uniandes.isis2304.bancAndes.negocio.Prestamo;
import uniandes.isis2304.bancAndes.negocio.PuestoAtencionOficina;
import uniandes.isis2304.bancAndes.negocio.PuestoDeAtencion;
import uniandes.isis2304.bancAndes.negocio.PuestoDigital;
import uniandes.isis2304.bancAndes.negocio.Usuario;

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
		sqlUtil = new sqlUtil(this);
		sqlAccion = new sqlAccion(this);
		sqlCajero = new sqlCajero(this);
		sqlCajeroAutomatico = new sqlCajeroAutomatico(this);
		sqlCliente = new sqlCliente(this);
		sqlClienteProducto = new sqlClienteProducto(this);
		sqlCuenta = new sqlCuenta(this);
		sqlDepositoInversion = new sqlDepositoInversion(this);
		sqlEmpleado = new sqlEmpleado(this);
		sqlGerenteDeOficina = new sqlGerenteDeOficina(this);
		sqlGerenteGeneral = new sqlGerenteGeneral(this);
		sqlOficina = new sqlOficina(this);
		sqlOperacionBancaria = new sqlOperacionBancaria(this);
		sqlPrestamo = new sqlPrestamo(this);
		sqlProducto = new sqlProducto(this);
		sqlPuestoAtencionOficina = new sqlPuestoAtencionOficina(this);
		sqlPuestoAtencionTipoOperacion = new sqlPuestoAtencionTipoOperacion(this);
		sqlPuestoDeAtencion = new sqlPuestoDeAtencion(this);
		sqlPuestoDigital = new sqlPuestoDigital(this);
		sqlTipoOperacion = new sqlTipoOperacion(this);
		sqlUsuario = new sqlUsuario(this);
		sqlUsuarioTipoOperacion = new sqlUsuarioTipoOperacion(this);
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
	public String darTablasEmpleados()
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
	public String darTablaPuestoAtencionOficina ()
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

	public List<Cliente> darClientes() {
		return sqlCliente.darClientes (pmf.getPersistenceManager());
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
	
	
//pendiente
	public Cliente darClientePorLogin(String login) {
		return  sqlCliente.darClientePorLogin (pmf.getPersistenceManager(), login);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar OPERACIONBANCARIA
	 *****************************************************************/
	
	public OperacionBancaria adicionarOperacionBancaria( float valor, Date fecha, String cliente, long producto,
			String tipoOperacion, long puestoAtencion, String empleado) {
		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {

	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlOperacionBancaria.adicionarOperacionBancaria(pm, id, valor, fecha, cliente, producto,
	        			 tipoOperacion, puestoAtencion, empleado);
	            tx.commit();
	            
	            log.trace ("Inserción de operacion bancaria: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
	            
	            return new OperacionBancaria ( id, valor, fecha, cliente, producto,
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
	
	public Cuenta adicionarCuenta( int numeroCuenta, String estado, String tipo, float saldo,
			Date fechaCreacion, Date dechaVencimiento, float tasaRendimiento, long oficina) {
		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {

	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlCuenta.adicionarCuenta(pm, id, numeroCuenta, estado, tipo, saldo,
	        			 fechaCreacion, dechaVencimiento, tasaRendimiento, oficina);
	            tx.commit();
	            
	            log.trace ("Inserción de cuenta: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
	            
	            return new Cuenta ( id, numeroCuenta, estado, tipo, saldo,
	        			 fechaCreacion, dechaVencimiento, tasaRendimiento, oficina);
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
		// TODO Auto-generated method stub
		return 0;
	}

	public long actualizarSaldoCuenta(long idCuenta, float cambioSaldo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Cuenta> darCuentas() {
		return sqlCuenta.darCuentas (pmf.getPersistenceManager());
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar PRESTAMO
	 *****************************************************************/

	public Prestamo adicionarPrestamo( float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {

		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {

	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlPrestamo.adicionarPrestamo( id, monto, saldoPendiente, interes, numeroCuotas,
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


	public Cuenta darCuentaPorId(long id) {
		return sqlCuenta.darCuentaPorId (pmf.getPersistenceManager(), id);
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

	
	/* ****************************************************************
	 * 			Métodos para manejar PUESTOATENCIONOFICINA
	 *****************************************************************/

	public PuestoAtencionOficina adicionarPuestoAtencionOficina( int telefono, String localizacion,
			long oficina) {
		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {
	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlPuestoAtencionOficina.adicionarPuestoAtencionOficina(pm, id, telefono, localizacion,
	        			 oficina);
	            tx.commit();
	            
	            log.trace ("Inserción de puesto atencion oficina: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
	            
	            return new PuestoAtencionOficina ( id, telefono, localizacion, oficina);
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
	 * 			Métodos para manejar PUESTODIGITAL
	 *****************************************************************/
	public PuestoDigital adicionarPuestoDigital( int telefono, String tipo, String url) {
		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {
	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlPuestoDigital.adicionarPuestoDigital(pm, id, telefono, tipo, url);
	            tx.commit();
	            
	            log.trace ("Inserción de puesto digital: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
	            
	            return new PuestoDigital ( id, telefono, tipo, url);
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

	
	/* ****************************************************************
	 * 			Métodos para manejar CAJEROAUTOMATICO
	 *****************************************************************/
	public CajeroAutomatico adicionarCajeroAutomatico( int telefono, String localizacion) {
		PersistenceManager pm = pmf.getPersistenceManager();
		 Transaction tx=pm.currentTransaction();
		   try
	        {
	            tx.begin();
	            long id = nextval ();
	            long tuplasInsertadas = sqlCajeroAutomatico.adicionarCajeroAutomatico(pm, id, telefono, localizacion);
	            tx.commit();
	            
	            log.trace ("Inserción de Cajero automatico: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
	            
	            return new CajeroAutomatico (id, telefono, localizacion);
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
		// TODO Auto-generated method stub
		return 0;
	}

	public long realizarPago(long idPrestamo, float montoPago) {
		// TODO Auto-generated method stub
		return 0;
	}

}
