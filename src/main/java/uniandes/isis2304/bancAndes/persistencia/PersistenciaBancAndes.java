package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

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
	

	public Usuario adicionarUsuario(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cliente adicionarCliente(String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cliente> darClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Empleado adicionarEmpleado(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public GerenteGeneral adicionarGerenteGeneral(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador, long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public GerenteDeOficina adicionarGerenteDeOficina(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cliente> darClientePorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public OperacionBancaria adicionarOperacionBancaria(long id, float valor, Date fecha, String cliente, long producto,
			String tipoOperacion, long puestoAtencion, String empleado) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuenta adicionarCuenta(long id, int numeroCuenta, String estado, String tipo, float saldo,
			Date fechaCreacion, Date dechaVencimiento, float tasaRendimiento, long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public Prestamo adicionarPrestamo(long id, float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cuenta> darCuentaPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Prestamo> darPrestamoPorId(long id) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public PuestoDeAtencion adicionarPuestoDeAtencion(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PuestoAtencionOficina adicionarPuestoAtencionOficina(long id, int telefono, String localizacion,
			long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public PuestoDigital adicionarPuestoDigital(long id, int telefono, String tipo, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public Oficina adicionarOficina(long id, String nombre, String direccion, int puestosPosibles,
			String gerenteLogin) {
		// TODO Auto-generated method stub
		return null;
	}

	public CajeroAutomatico adicionarCajeroAutomatico(long id, int telefono, String localizacion) {
		// TODO Auto-generated method stub
		return null;
	}

	public long cerrarPrestamo(long idPrestamo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long realizarPago(long idPrestamo, float montoPago) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String darSeqBancAndes() {
		// TODO Auto-generated method stub
		return null;
	}

}
