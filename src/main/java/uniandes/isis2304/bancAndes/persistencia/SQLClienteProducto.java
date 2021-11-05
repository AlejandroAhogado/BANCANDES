package uniandes.isis2304.bancAndes.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.ClienteProducto;
import uniandes.isis2304.bancAndes.negocio.Cuenta;
import uniandes.isis2304.bancAndes.negocio.UsuarioTipoOperacion;

public class SQLClienteProducto {


	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaBancAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaBancAndes pba;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLClienteProducto (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarClienteProducto(PersistenceManager pm, long producto, String cliente) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaClientesProductos () + "(producto, cliente) values (?, ?)");
        q.setParameters(producto, cliente);
        return (long) q.executeUnique();
	}

	public List<ClienteProducto> darClienteProductoPorCliente(PersistenceManager pm, String login) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientesProductos () + " WHERE cliente = ?");
		q.setParameters(login);
		q.setResultClass(ClienteProducto.class);
		return (List<ClienteProducto>) q.executeList();
	}

	public List<ClienteProducto> darClienteProductoPorProducto(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientesProductos () + " WHERE producto = ?");
		q.setParameters(id);
		q.setResultClass(ClienteProducto.class);
		return (List<ClienteProducto>) q.executeList();
	}

	public ClienteProducto darClienteProducto(PersistenceManager pm, float producto, String cliente) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaClientesProductos () + " WHERE producto = ? AND cliente = ?");
		q.setParameters(producto, cliente);
		q.setResultClass(ClienteProducto.class);
		return (ClienteProducto) q.executeUnique();
	}
	
	
	//************************************************PARA LOS REQUERIMIENTOS DE CONSULTA

	/**
	 * Consultar un cliente en el sistema por su login (como un gerente general)
	 * @param pm
	 * @param login
	 * @return
	 */
	public List<Object[]> consultarClienteGG (PersistenceManager pm, String login){
		String sql = "SELECT * FROM " +pba.darTablaCuentas ()+" JOIN (";
		sql+= "SELECT * FROM ( ";
		sql+= "SELECT cl.login, cl.tipo, cl.nombre, cl.correo, cl.telefono, cl.ciudad, cp.producto ";
		sql+= "from "+pba.darTablaClientes ()+" cl ";
		sql+= "INNER JOIN "+pba.darTablaClientesProductos ()+" cp ";
		sql+= "ON cl.login = cp.cliente ) PRIMERA";
		sql+= "JOIN( ";
		sql+= "select ob.id,ob.valor,ob.tipooperacion, pao.oficina, ob.cliente ";
		sql+= "from( "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+pba.darTablaPuestosAtencionOficina ()+" pao ";
		sql+= "ON ob.puestoAtencion = pao.id) SEGUNDA ";
		sql+= "ON PRIMERA.LOGIN = SEGUNDA.cliente)TERCERA ";
		sql+= "ON TERCERA.PRODUCTO = CUENTAS.ID ";
		sql+= "WHERE login = ?  ";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(login);
		return q.executeList();
	}
	/**
	 * Consultar un cliente en la oficina del gerente de oficina que consulta
	 * @param pm
	 * @param login
	 * @param idOficina
	 * @return
	 */
	public List<Object[]> consultarClienteGOf (PersistenceManager pm, String login, long idOficina){
		String sql = "SELECT * FROM " +pba.darTablaCuentas ()+" JOIN (";
		sql+= "SELECT * FROM ( ";
		sql+= "SELECT cl.login, cl.tipo, cl.nombre, cl.correo, cl.telefono, cl.ciudad, cp.producto ";
		sql+= "from "+pba.darTablaClientes ()+" cl ";
		sql+= "INNER JOIN "+pba.darTablaClientesProductos ()+" cp ";
		sql+= "ON cl.login = cp.cliente ) PRIMERA";
		sql+= "JOIN( ";
		sql+= "select ob.id,ob.valor,ob.tipooperacion, pao.oficina, ob.cliente ";
		sql+= "from( "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+pba.darTablaPuestosAtencionOficina ()+" pao ";
		sql+= "ON ob.puestoAtencion = pao.id) SEGUNDA ";
		sql+= "ON PRIMERA.LOGIN = SEGUNDA.cliente)TERCERA ";
		sql+= "ON TERCERA.PRODUCTO = CUENTAS.ID ";
		sql+= "WHERE login = ?  and oficina = ?";
		
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(login, idOficina);
		return q.executeList();
	}
}
