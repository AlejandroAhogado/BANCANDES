package uniandes.isis2304.bancAndes.negocio;


import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.bancAndes.persistencia.PersistenciaBancAndes;

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
	

	 /**
     * Encuentra el gerenteGeneral en BancAndes con el login solicitado
     * Adiciona entradas al log de la aplicación
     * @param login - El login del gerenteGeneral
     * @return Un objeto gerenteGeneral con el login que conoce la aplicación, 
      * lleno con su información básica
     */
     public GerenteGeneral darGerenteGeneralPorLogin (String login)
     {
                  log.info ("Buscando Gerente General por login: " + login);
                  GerenteGeneral gerenteGeneral= pba.darGerenteGeneralPorLogin (login);
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
	
	
	/**
     * Encuentra el cajero en BancAndes con el login solicitado
     * Adiciona entradas al log de la aplicación
     * @param login - El login del cajero
     * @return Un objeto cajero con el login que conoce la aplicación, 
      * lleno con su información básica
     */
     public Cajero darCajeroPorLogin (String login)
     {
                  log.info ("Buscando Cajero por login: " + login);
                   Cajero cajero= pba.darCajeroPorLogin (login);
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
	
	/**
     * Encuentra el gerente de oficina en BancAndes con el login solicitado
     * Adiciona entradas al log de la aplicación
     * @param login - El login del gerente de oficina
     * @return Un objeto gerente de oficina con el login que conoce la aplicación, 
      * lleno con su información básica
     */
     public GerenteDeOficina darGerenteDeOficinaPorLogin (String login)
     {
                  log.info ("Buscando Gerente de Oficina por login: " + login);
                  GerenteDeOficina gerenteDeOficina= pba.darGerenteDeOficinaPorLogin (login);
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
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un producto
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Usuario adicionado. null si ocurre alguna Excepción
	 */
	public Producto adicionarProducto ()
	{
        log.info ("Adicionando Producto");
        Producto producto = pba.adicionarProducto ();		
        log.info ("Adicionando Producto: " + producto);
        return producto;
	}
	
	/**
	 * Elimina de manera persistente un producto
	 * Elimina entradas al log de la aplicación
	 * @param id - El id del producto
	 * @return El objeto Usuario eliminado. null si ocurre alguna Excepción
	 */
	public long eliminarProducto (long id)
	{
	
        log.info ("Eliminando Producto: " + id);
        long resp= pba.eliminarProducto (id);		
        log.info ("Eliminando producto por id: " + id + " tuplas eliminadas");
        
        return resp;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTESPRODUCTOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un ClienteProducto
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto ClienteProducto adicionado. null si ocurre alguna Excepción
	 */
	public ClienteProducto adicionarClienteProducto (long id, String login)
	{
        log.info ("Adicionando ClienteProducto");
        ClienteProducto clienteProducto = pba.adicionarClienteProducto(id, login);		
        log.info ("Adicionando ClienteProducto: " + clienteProducto);
        return clienteProducto;
	}
	
	/**
     * Encuentra la lista de ClienteProducto en BancAndes con el login del cliente dado
     * Adiciona entradas al log de la aplicación
     * @param login - El login del cliente
     * @return Una lista de ClienteProducot con el login que conoce la aplicación, 
      * lleno con su información básica
     */
     public List<ClienteProducto> darClienteProductoPorCliente (String login)
     {
                  log.info ("Buscando ClienteProducto por login del cliente: " + login);
                  List<ClienteProducto> cp= pba.darClienteProductoPorCliente (login);
                  return cp;
     }
     
     /**
      * Encuentra la lista de ClienteProducto en BancAndes con el id del producto dado
      * Adiciona entradas al log de la aplicación
      * @param id - El id del producto
      * @return Una lista de ClienteProducto con el login que conoce la aplicación, 
       * lleno con su información básica
      */
      public List<ClienteProducto> darClienteProductoPorProducto (long id)
      {
                   log.info ("Buscando ClienteProducto por id del producto: " + id);
                   List<ClienteProducto> cp= pba.darClienteProductoPorProducto (id);
                   return cp;
      }
      /**
       * Encuentra la lista de ClienteProducto en BancAndes con el producto y cliente dado
       * Adiciona entradas al log de la aplicación
       * @return Un ClienteProducto con el producto y login que conoce la aplicación, 
        * lleno con su información básica
       */
       public ClienteProducto darClienteProducto (float producto, String cliente)
       {
                    log.info ("Buscando ClienteProducto por producto: " + producto+" y cliente: "+cliente);
                    ClienteProducto cp= pba.darClienteProducto (producto, cliente);
                    return cp;
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
	public Cuenta adicionarCuenta( long id, int numeroCuenta, String estado, String tipo, float saldo, Date fechaCreacion,
			Date dechaVencimiento, float tasaRendimiento, long oficina)
	{
        log.info ("Adicionando Cuenta: " +id);
        Cuenta cuenta = pba.adicionarCuenta (id, numeroCuenta, estado, tipo, saldo, fechaCreacion,
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
	 * Cambia el estado de una cuenta dado su id y el estado al que se quiere actualizar
	 * Adiciona entradas al log de la aplicación
	 * @param idCuenta
	 * @param estado al que se quiere cambiar la cuenta (ACTIVA o DESACTIVADA)
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que una cuenta con ese identificador no existe
	 */
	public long cambiarActividadCuenta (long idCuenta, String estado)
	{
        log.info ("Cambiando estado cuenta: " + idCuenta +" a "+ estado);
        long cambios = pba.cambiarActividadCuenta (idCuenta, estado);
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
	public Prestamo adicionarPrestamo ( long id, float monto, float saldoPendiente, float interes, int numeroCuotas, int diaPago,
			float valorCuotaMinima, Date fechaPrestamo, String cerrado)
	{
        log.info ("Adicionando Prestamo: " +id);
        Prestamo prestamo = pba.adicionarPrestamo (id, monto, saldoPendiente, interes, numeroCuotas, diaPago,
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
	
	/**
	 * Cierra un prestamo (cambia su atributo de cerrado a true) dado su id
	 * Adiciona entradas al log de la aplicación
	 * @param idPrestamo
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que una cuenta con ese identificador no existe
	 */
	public long cerrarPrestamo (long idPrestamo)
	{
        log.info ("Cerrando prestamo: " + idPrestamo);
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
	/**
	 * Elimina de manera persistente un puesto de atencion
	 * Elimina entradas al log de la aplicación
	 * @param id - El id del puesto de atencion
	 * @return El objeto puesto de atencuon eliminado. null si ocurre alguna Excepción
	 */
	public long eliminarPuestoDeAtencion(long id)
	{

		log.info ("Eliminando Puesto de Atencion: " + id);
		long resp= pba.eliminarPuestoDeAtencion (id);                            
		log.info ("Eliminando puesto de atencion con id: " + id + " tuplas eliminadas");

		return resp;
	}

	public long asociarPuestoDeAtencionOficinaCajero (long idPuesto, String login)
	{
		log.info ("Asociando puesto de atencion al cajero: " + login);
		long cambios = pba.asociarPuestoDeAtencionOficinaCajero(idPuesto, login) ;
		return cambios;
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
	public PuestoAtencionOficina adicionarPuestoAtencionOficina (long id, int telefono, String localizacion, long oficina)
	{
		log.info ("Adicionando Puesto de atencion oficina " );
		PuestoAtencionOficina puestoAtencionOficina = pba.adicionarPuestoAtencionOficina ( id, telefono, localizacion, oficina);                         
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
	public PuestoDigital adicionarPuestoDigital (long id, int telefono, String tipo, String url)
	{
		log.info ("Adicionando Puesto digital ");
		PuestoDigital puestoDigital = pba.adicionarPuestoDigital ( id, telefono, tipo, url);                     
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
	
	/**
	 * Encuentra la oficina en BancAndes con el id solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param id de la cuenta
	 * @return Un objeto Oficina con el id que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public Oficina darOficinaPorId (long id)
	{
		log.info ("Buscando Oficina por id: " + id);
		Oficina oficina= pba.darOficinaPorId (id);
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
	public CajeroAutomatico adicionarCajeroAutomatico (long id, int telefono, String localizacion)
	{
		log.info ("Adicionando Cajero Automatico ");
		CajeroAutomatico cajeroAutomatico = pba.adicionarCajeroAutomatico(id, telefono, localizacion);                 
		log.info ("Adicionando Cajero Automatico: " + cajeroAutomatico);
		return cajeroAutomatico;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOTIPOOPERACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un UsuarioTipoOperacion
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto UsuarioTipoOperacion adicionado. null si ocurre alguna Excepción
	 */
	public UsuarioTipoOperacion adicionarUsuarioTipoOperacion (String tipoOperacion, String usuario)
	{
        log.info ("Adicionando UsuarioTipoOperacion");
        UsuarioTipoOperacion usuarioTipoOperacion = pba.adicionarUsuarioTipoOperacion(tipoOperacion, usuario);		
        log.info ("Adicionando UsuarioTipoOperacion: " + usuarioTipoOperacion);
        return usuarioTipoOperacion;
	}
	
	/**
     * Encuentra la lista de UsuarioTipoOperacion en BancAndes con el login del usuario dado
     * Adiciona entradas al log de la aplicación
     * @param login - El login del usuario
     * @return Una lista de UsuarioTipoOperacion con el login que conoce la aplicación, 
      * lleno con su información básica
     */
     public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorUsuario (String usuario)
     {
                  log.info ("Buscando UsuarioTipoOperacion por login del usuario: " + usuario);
                  List<UsuarioTipoOperacion> uto= pba.darUsuarioTipoOperacionPorUsuario(usuario);
                  return uto;
     }
     
     /**
      * Encuentra la lista de UsuarioTipoOperacion en BancAndes con el tipo de operacion dado
      * Adiciona entradas al log de la aplicación
      * @param id - El tipo de operacion
      * @return Una lista de UsuarioTipoOperacion con el tipo que conoce la aplicación, 
       * lleno con su información básica
      */
      public List<UsuarioTipoOperacion> darUsuarioTipoOperacionPorTipo (String tipoOperacion)
      {
                   log.info ("Buscando UsuarioTipoOperacion por tipo de operacion: " + tipoOperacion);
                   List<UsuarioTipoOperacion> uto= pba.darUsuarioTipoOperacionPorTipo (tipoOperacion);
                   return uto;
      }
      
      /**
       * Encuentra la lista de UsuarioTipoOperacion en BancAndes con el tipo de operacion y usuario dado
       * Adiciona entradas al log de la aplicación
       * @param id - El tipo de operacion
       * @return Un UsuarioTipoOperacion con el tipo y login que conoce la aplicación, 
        * lleno con su información básica
       */
       public UsuarioTipoOperacion darUsuarioTipoOperacion (String usuario, String tipoOperacion)
       {
                    log.info ("Buscando UsuarioTipoOperacion por tipo de operacion: " + tipoOperacion + " y usuario: "+ usuario);
                    UsuarioTipoOperacion uto= pba.darUsuarioTipoOperacion (usuario, tipoOperacion);
                    return uto;
       }

       /* ****************************************************************
   	 * 			Métodos para manejar los PUESTOATENCIONTIPOOPERACION
   	 *****************************************************************/
   	/**
   	 * Adiciona de manera persistente un PuestoAtencionTipoOperacion
   	 * Adiciona entradas al log de la aplicación
   	 * @return El objeto PuestoAtencionTipoOperacion adicionado. null si ocurre alguna Excepción
   	 */
   	public PuestoAtencionTipoOperacion adicionarPuestoAtencionTipoOperacion (String tipoOperacion, long puesto)
   	{
           log.info ("Adicionando PuestoAtencionTipoOperacion");
           PuestoAtencionTipoOperacion puestoAtencionTipoOperacion = pba.adicionarPuestoAtencionTipoOperacion(tipoOperacion, puesto);		
           log.info ("Adicionando PuestoAtencionTipoOperacion: " + puestoAtencionTipoOperacion);
           return puestoAtencionTipoOperacion;
   	}
   	
   	/**
        * Encuentra la lista de PuestoAtencionTipoOperacion en BancAndes con el id del puesto dado
        * Adiciona entradas al log de la aplicación
        * @return Una lista de PuestoAtencionTipoOperacion con el login que conoce la aplicación, 
         * lleno con su información básica
        */
        public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorPuesto (long puesto)
        {
                     log.info ("Buscando PuestoAtencionTipoOperacion por id del puesto: " + puesto);
                     List<PuestoAtencionTipoOperacion> puestoAtencionTipoOperacion= pba.darPuestoAtencionTipoOperacionPorPuesto(puesto);
                     return puestoAtencionTipoOperacion;
        }
        
        /**
         * Encuentra la lista de PuestoAtencionTipoOperacion en BancAndes con el tipo de operacion dado
         * Adiciona entradas al log de la aplicación
         * @param id - El tipo de operacion
         * @return Una lista de PuestoAtencionTipoOperacion con el tipo que conoce la aplicación, 
          * lleno con su información básica
         */
         public List<PuestoAtencionTipoOperacion> darPuestoAtencionTipoOperacionPorTipo (String tipoOperacion)
         {
                      log.info ("Buscando PuestoAtencionTipoOperacion por tipo de operacion: " + tipoOperacion);
                      List<PuestoAtencionTipoOperacion> puestoAtencionTipoOperacion= pba.darPuestoAtencionTipoOperacionPorTipo (tipoOperacion);
                      return puestoAtencionTipoOperacion;
         }
         
         /**
          * Encuentra la lista de PuestoAtencionTipoOperacion en BancAndes con el tipo de operacion y puesto dado
          * Adiciona entradas al log de la aplicación
          * @return Un PuestoAtencionTipoOperacion con el tipo y id que conoce la aplicación, 
           * lleno con su información básica
          */
          public PuestoAtencionTipoOperacion darPuestoAtencionTipoOperacion (long puesto, String tipoOperacion)
          {
                       log.info ("Buscando PuestoAtencionTipoOperacion por tipo de operacion: " + tipoOperacion);
                       PuestoAtencionTipoOperacion puestoAtencionTipoOperacion= pba.darPuestoAtencionTipoOperacion (puesto, tipoOperacion);
                       return puestoAtencionTipoOperacion;
          }

		/**
		 * @param parseInt
		 * @return
		 */
		public PuestoDigital darPuestoDigitalPorId(long id) {
			log.info ("Buscando Puesto digital por id: " + id);
			PuestoDigital puestoDigital= pba.darPuestoDigitalPorId (id);
			return puestoDigital;
		}

		/**
		 * @param parseInt
		 * @return
		 */
		public CajeroAutomatico darCajeroAutomaticoPorId(long id) {
			log.info ("Buscando Cajero automatico por id: " + id);
			CajeroAutomatico cajeroAutomatico= pba.darCajeroAutomaticoPorId (id);
			return cajeroAutomatico;
			}

		

}
