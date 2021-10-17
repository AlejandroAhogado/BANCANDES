package uniandes.isis2304.bancAndes.negocio;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.persistencia.PersistenciaBancAndes;
import uniandes.isis2304.bancAndes.persistencia.PersistenciaParranderos;

public class BancAndes {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(BancAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaBancAndes pba;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public BancAndes ()
	{
		pba = PersistenciaBancAndes.getInstance ();
	}
	
	/**
	 * El constructor que recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public BancAndes (JsonObject tableConfig)
	{
		pba = PersistenciaBancAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pba.cerrarUnidadPersistencia ();
	}
	
	/**
	 * REQUERIMIENTOS FUNCIONALES:
	 * MODIFICACION
	 * Registrar usuario
	 * Registrar oficina
	 * Registrar punto de atencion
	 * Registrar cuenta
	 * Cerrar cuenta
	 * Registrar operación sobre cuenta
	 * Registrar prestamo
	 * registrar operacion sobre prestamo
	 * cerrar prestamo
	 * 
	 * CONSULTA
	 * consultar cuentas (todas)
	 * consultar clientes
	 * consultar operaciones
	 * consultar cliente por login
	 * 
	 */
	
	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un usuario
	 * Adiciona entradas al log de la aplicación
	 * @param login - El login del usuario
	 * @return El objeto Usuario adicionado. null si ocurre alguna Excepción
	 */
	public Usuario adicionarUsuario (String login)
	{
        log.info ("Adicionando Usuario: " + login);
        Usuario usuario = pba.adicionarUsuario (login);		
        log.info ("Adicionando Usuario: " + usuario);
        return usuario;
	}
	
	/**
	 * Elimina de manera persistente un usuario
	 * Elimina entradas al log de la aplicación
	 * @param login - El login del usuario
	 * @return El objeto Usuario eliminado. null si ocurre alguna Excepción
	 */
	public long eliminarUsuario (String login)
	{
	
        log.info ("Eliminando Usuario: " + login);
        long resp= pba.eliminarUsuario (login);		
        log.info ("Eliminando usuario por login: " + resp + " tuplas eliminadas");
        
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un cliente
	 * Adiciona entradas al log de la aplicación
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param departamento
	 * @param codigopostal
	 * @param nacionalidad
	 * @param nombre
	 * @param direccion
	 * @param login
	 * @param contrasena
	 * @param correo
	 * @param telefono
	 * @param ciudad
	 * @param tipo
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente (String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String tipo)
	{
        log.info ("Adicionando Cliente: " + login);
        Cliente cliente = pba.adicionarCliente (tipoDocumento, numeroDocumento, departamento, codigopostal,
    			nacionalidad, nombre, direccion, login, contrasena, correo,
    			telefono, ciudad, tipo);		
        log.info ("Adicionando Cliente: " + cliente);
        return cliente;
	}
	
	/**
	 * Encuentra todos los clientes en BancAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Clientes con todos los clientes que conoce la aplicación, llenos con su información básica
	 */
	public List<Cliente> darClientes ()
	{
		log.info ("Consultando Clientes");
        List<Cliente> clientes = pba.darClientes();	
        log.info ("Consultando Clientes: " + clientes.size() + " existentes");
        return clientes;
	}
	
	/**
	 * Encuentra todos los clientes en BancAndes y los devuelve como una lista de VOCliente
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCliente con todos los clientes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCliente> darVOClientes()
	{
		log.info ("Generando los VO de Clientes");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente c : pba.darClientes ())
        {
        	voClientes.add (c);
        }
        log.info ("Generando los VO de Clientes: " + voClientes.size() + " existentes");
        return voClientes;
	}
	
	/**
	 * Encuentra el cliente en BancAndes con el login solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param login - El login del cliente
	 * @return Un objeto Cliente con el login que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public Cliente darClientePorLogin (String login)
	{
		log.info ("Buscando Cliente por login: " + login);
		Cliente cliente= pba.darClientePorLogin (login);
		return cliente;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los EMPLEADO
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un empleado
	 * Adiciona entradas al log de la aplicación
	 * @param login
	 * @return El objeto Empleado adicionado. null si ocurre alguna Excepción
	 */
	public Empleado adicionarEmpleado (String login)
	{
        log.info ("Adicionando Empleado: " + login);
        Empleado empleado = pba.adicionarEmpleado (login);		
        log.info ("Adicionando Empleado: " + empleado);
        return empleado;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar al GERENTEGENERAL
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un gerenteGeneral
	 * Adiciona entradas al log de la aplicación
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param departamento
	 * @param codigopostal
	 * @param nacionalidad
	 * @param nombre
	 * @param direccion
	 * @param login
	 * @param contrasena
	 * @param correo
	 * @param telefono
	 * @param ciudad
	 * @param administrador
	 * @param oficina
	 * @return El objeto GerenteGeneral adicionado. null si ocurre alguna Excepción
	 */
	public GerenteGeneral adicionarGerenteGeneral (String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String administrador, long oficina)
	{
        log.info ("Adicionando Gerente General: " + login);
        GerenteGeneral gerenteGeneral = pba.adicionarGerenteGeneral (tipoDocumento, numeroDocumento, departamento, codigopostal,
    			nacionalidad, nombre, direccion, login, contrasena, correo,
    			telefono, ciudad, administrador, oficina);		
        log.info ("Adicionando Gerente General: " + gerenteGeneral);
        return gerenteGeneral;
	}
	

	/* ****************************************************************
	 * 			Métodos para manejar al CAJERO
	 *****************************************************************/
	public Cajero adicionarCajero (String tipoDocumento, int numeroDocumento, String departamento, int codigopostal, String nacionalidad,
			String nombre, String direccion, String login, String contrasena, String correo, int telefono, String ciudad,
			String administrador, long puestoAtencionoficina, long oficina)
	{
        log.info ("Adicionando Cajero: " + login);
        Cajero cajero = pba.adicionarCajero(tipoDocumento, numeroDocumento, departamento, codigopostal,
    			nacionalidad, nombre, direccion, login, contrasena, correo,
    			telefono, ciudad, administrador, puestoAtencionoficina, oficina);		
        log.info ("Adicionando Cajero: " + cajero);
        return cajero;
	}
	
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar al GERENTEDEOFICINA
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un gerenteDeOficina
	 * Adiciona entradas al log de la aplicación
	 * @param tipoDocumento
	 * @param numeroDocumento
	 * @param departamento
	 * @param codigopostal
	 * @param nacionalidad
	 * @param nombre
	 * @param direccion
	 * @param login
	 * @param contrasena
	 * @param correo
	 * @param telefono
	 * @param ciudad
	 * @param administrador
	 * @return El objeto GerenteDeOficina adicionado. null si ocurre alguna Excepción
	 */
	public GerenteDeOficina adicionarGerenteDeOficina (String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String administrador)
	{
        log.info ("Adicionando Gerente de oficina: " + login);
        GerenteDeOficina gerenteDeOficina = pba.adicionarGerenteDeOficina(tipoDocumento, numeroDocumento, departamento, codigopostal,
    			nacionalidad, nombre, direccion, login, contrasena, correo,
    			telefono, ciudad, administrador);		
        log.info ("Adicionando Gerente de Oficina: " + gerenteDeOficina);
        return gerenteDeOficina;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los OPERACIONES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una operacions
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param valor
	 * @param fecha
	 * @param cliente
	 * @param producto
	 * @param tipoOperacion
	 * @param puestoAtencion
	 * @param empleado
	 * @return
	 * @return El objeto Operacion adicionado. null si ocurre alguna Excepción
	 */
	public OperacionBancaria adicionarOperacionBancaria ( float valor, Date fecha, String cliente, long producto, String tipoOperacion,
			long puestoAtencion, String empleado)
	{
        log.info ("Adicionando Operacion Bancaria ");
        OperacionBancaria operacionBancaria = pba.adicionarOperacionBancaria ( valor, fecha, cliente, producto, tipoOperacion,
    			puestoAtencion, empleado);		
        log.info ("Adicionando Operacion Bancaria: " + operacionBancaria);
        return operacionBancaria;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los CUENTAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una cuenta
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param numeroCuenta
	 * @param estado
	 * @param tipo
	 * @param saldo
	 * @param fechaCreacion
	 * @param dechaVencimiento
	 * @param tasaRendimiento
	 * @param oficina
	 * @return El objeto Cuenta adicionado. null si ocurre alguna Excepción
	 */
	public Cuenta adicionarCuenta( int numeroCuenta, String estado, String tipo, float saldo, Date fechaCreacion,
			Date dechaVencimiento, float tasaRendimiento, long oficina)
	{
        log.info ("Adicionando Cuenta ");
        Cuenta cuenta = pba.adicionarCuenta (numeroCuenta, estado, tipo, saldo, fechaCreacion,
    			dechaVencimiento, tasaRendimiento, oficina);		
        log.info ("Adicionando Cuenta: " + cuenta);
        return cuenta;
	}
	/**
	 * Encuentra todoas las cuentas en BancAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Cuentas con todos los cuentas que conoce la aplicación, llenos con su información básica
	 */
	public List<Cuenta> darCuentas ()
	{
		log.info ("Consultando Cuentas");
        List<Cuenta> cuentas = pba.darCuentas();	
        log.info ("Consultando Cuentas: " + cuentas.size() + " existentes");
        return cuentas;
	}
	
	/**
	 * Encuentra todas las cuentas en BancAndes y los devuelve como una lista de VOCuenta
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCuenta con todas las cuentas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCuenta> darVOCuentas()
	{
		log.info ("Generando los VO de Cuentas");        
        List<VOCuenta> voCuentas = new LinkedList<VOCuenta> ();
        for (Cuenta c : pba.darCuentas ())
        {
        	voCuentas.add (c);
        }
        log.info ("Generando los VO de Clientes: " + voCuentas.size() + " existentes");
        return voCuentas;
	}
	/**
	 * Encuentra la cuenta en BancAndes con el id solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param id de la cuenta
	 * @return Un objeto Cuenta con el id que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public Cuenta darCuentaPorId (long id)
	{
		log.info ("Buscando Cuenta por id: " + id);
		Cuenta cuenta= pba.darCuentaPorId (id);
		return cuenta;
	}
	
	/**
	 * Cierra una cuenta (deja el saldo en cero y cambia su estado a CERRADA) dado su id
	 * Adiciona entradas al log de la aplicación
	 * @param idCuenta
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que una cuenta con ese identificador no existe
	 */
	public long cerrarCuenta (long idCuenta)
	{
        log.info ("Cerrando cuenta: " + idCuenta);
        long cambios = pba.cerrarCuenta (idCuenta);
        return cambios;
	}
	
	/**
	 * Actualiza el saldo de una cuenta dado su id
	 * Adiciona entradas al log de la aplicación
	 * @param idCuenta
	 * @param cambioSaldo
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que una cuenta activa con ese identificador no existe
	 */
	public long actualizarSaldoCuenta (long idCuenta, float cambioSaldo)
	{
        log.info ("Actualizando saldo de cuenta: " + idCuenta);
        long cambios = pba.actualizarSaldoCuenta (idCuenta, cambioSaldo);
        return cambios;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PRESTAMOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un prestamo
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param monto
	 * @param saldoPendiente
	 * @param interes
	 * @param numeroCuotas
	 * @param diaPago
	 * @param valorCuotaMinima
	 * @param fechaPrestamo
	 * @param cerrado
	 * @return El objeto Prestamo adicionado. null si ocurre alguna Excepción
	 */
	public Prestamo adicionarPrestamo ( float monto, float saldoPendiente, float interes, int numeroCuotas, int diaPago,
			float valorCuotaMinima, Date fechaPrestamo, String cerrado)
	{
        log.info ("Adicionando Prestamo ");
        Prestamo prestamo = pba.adicionarPrestamo (monto, saldoPendiente, interes, numeroCuotas, diaPago,
    			valorCuotaMinima, fechaPrestamo, cerrado);		
        log.info ("Adicionando Prestamo: " + prestamo);
        return prestamo;
	}
	/**
	 * Encuentra el prestamo en BancAndes con el id solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param id del prestamo
	 * @return Un objeto Prestamo con el id que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public Prestamo darPrestamoPorId (long id)
	{
		log.info ("Buscando Prestamo por id: " + id);
		Prestamo prestamo = pba.darPrestamoPorId (id);
		return prestamo;
	}
	//cerrar prestamo
	
	//registrar pago
	
	/**
	 * Cierra un prestamo (cambia su atributo de cerrado a true) dado su id
	 * Adiciona entradas al log de la aplicación
	 * @param idPrestamo
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que una cuenta con ese identificador no existe
	 */
	public long cerrarPrestamo (long idPrestamo)
	{
        log.info ("Prestamo cuenta: " + idPrestamo);
        long cambios = pba.cerrarPrestamo (idPrestamo);
        return cambios;
	}
	
	/**
	 * Actualiza el monto del prestamo dado su id
	 * Adiciona entradas al log de la aplicación
	 * @param idPrestamo
	 * @param pagoMonto
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que no se realiz� el pago
	 */
	public long realizarPago (long idPrestamo, float montoPago)
	{	
		float valorCuota = darPrestamoPorId(idPrestamo).getValorCuotaMinima();
        log.info ("Realizando Pago: " + idPrestamo);
        if(valorCuota>montoPago) {
        	return 0;}
        long cambios = pba.realizarPago (idPrestamo, montoPago);
        return cambios;
     }
	
	/* **********************
	 * 			Metodos para manejar los puestos de atencion
	 ***********************/
	/**
	 * Adiciona de manera persistente un puesto de atencion
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @return El objeto PuestoDeAtencion adicionado. null si ocurre alguna Excepción
	 */
	public PuestoDeAtencion adicionarPuestoDeAtencion ()
	{
       log.info ("Adicionando Puesto de atencion " );
       PuestoDeAtencion puestoDeAtencion = pba.adicionarPuestoDeAtencion ();		
       log.info ("Adicionando Puesto de atencion: " + puestoDeAtencion);
       return puestoDeAtencion;
	}
	

	/* **********************
	 * 			Metodos para manejar los puestos atencion oficina
	 ***********************/	
	/**
	 *  Adiciona de manera persistente un PuestoAtencionOficina
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param telefono
	 * @param localizacion
	 * @param oficina
	 * @return El objeto PuestoAtencionOficina adicionado. null si ocurre alguna Excepción
	 */
	public PuestoAtencionOficina adicionarPuestoAtencionOficina (int telefono, String localizacion, long oficina)
	{
       log.info ("Adicionando Puesto de atencion oficina " );
       PuestoAtencionOficina puestoAtencionOficina = pba.adicionarPuestoAtencionOficina ( telefono, localizacion, oficina);		
       log.info ("Adicionando Puesto de atencion oficina: " + puestoAtencionOficina);
       return puestoAtencionOficina;
	}
	
	/* **********************
	 * 			Metodos para manejar puesto digital
	 ***********************/
	/**
	 * Adiciona de manera persistente un puesto digital
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param telefono
	 * @param tipo
	 * @param url
	 * @return El objeto PuestoDigital adicionado. null si ocurre alguna Excepción
	 */
	public PuestoDigital adicionarPuestoDigital (int telefono, String tipo, String url)
	{
       log.info ("Adicionando Puesto digital ");
       PuestoDigital puestoDigital = pba.adicionarPuestoDigital ( telefono, tipo, url);		
       log.info ("Adicionando Puesto digital: " + puestoDigital);
       return puestoDigital;
	}
	
	/* **********************
	 * 			Metodos para oficina
	 ***********************/
	/**
	 * Adiciona de manera persistente un puesto de atencion
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param nombre
	 * @param direccion
	 * @param puestosPosibles
	 * @param gerenteLogin
	 * @return El objeto Oficina adicionado. null si ocurre alguna Excepción
	 */
	public Oficina adicionarOficina ( String nombre, String direccion, int puestosPosibles, String gerenteLogin)
	{
       log.info ("Adicionando Oficina ");
       Oficina oficina = pba.adicionarOficina( nombre, direccion, puestosPosibles, gerenteLogin);		
       log.info ("Adicionando Oficina: " + oficina);
       return oficina;
	}
	
	
	/* **********************
	 * 			Metodos para cajero automatico
	 ***********************/
	/**
	 * Adiciona de manera persistente un cajero automatico
	 * Adiciona entradas al log de la aplicación
	 * @param id
	 * @param telefono
	 * @param localizacion
	 * @return El objeto CajeroAutomatico adicionado. null si ocurre alguna Excepción
	 */
	public CajeroAutomatico adicionarCajeroAutomatico ( int telefono, String localizacion)
	{
       log.info ("Adicionando Cajero Automatico ");
       CajeroAutomatico cajeroAutomatico = pba.adicionarCajeroAutomatico(telefono, localizacion);		
       log.info ("Adicionando Cajero Automatico: " + cajeroAutomatico);
       return cajeroAutomatico;
	}
}
