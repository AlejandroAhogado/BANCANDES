package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Cuenta;

public class SQLCuenta {


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
	public SQLCuenta (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarCuenta(PersistenceManager pm, long id, int numeroCuenta, String estado, String tipo,
			float saldo, Date fechaCreacion, Date fechaVencimiento, float tasaRendimiento, long oficina) {
		Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaCuentas () + "(id, numeroCuenta, estado, tipo, saldo,fechaCreacion, fechaVencimiento, tasaRendimiento, oficina) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id, numeroCuenta, estado, tipo, saldo,
				fechaCreacion, fechaVencimiento, tasaRendimiento, oficina);
		return (long) q.executeUnique();
	}

	public List<Cuenta> darCuentas(PersistenceManager pm) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCuentas ());
		q.setResultClass(Cuenta.class);
		return (List<Cuenta>) q.executeList();
	}

	public Cuenta darCuentaPorId(PersistenceManager pm, long id) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCuentas () + " WHERE id = ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(id);
		return (Cuenta) q.executeUnique();
	}

	public long cerrarCuenta(PersistenceManager pm, long idCuenta) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET estado = CERRADA AND saldo = 0 WHERE id = ?");
		q.setParameters(idCuenta);
		return (long) q.executeUnique();
	}

	public long actualizarSaldoCuenta(PersistenceManager pm, long idCuenta, float cambioSaldo) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET saldo = saldo+cambioSaldo WHERE id = ?");
		q.setParameters(idCuenta);
		return (long) q.executeUnique();
	}

	public long cambiarActividadCuenta(PersistenceManager pm, long idCuenta, String estado) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET estado = ? WHERE id = ?");
		q.setParameters(estado, idCuenta);
		return (long) q.executeUnique();
	}

	public List<Cuenta> consultarCuentasCliente(PersistenceManager pm, String login, String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT cliente," + pba.darTablaCuentas()+".* FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE cliente = ? and ? = ? and ? = ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(login,criterio,filtro,criterio2, filtro2, ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}
	
	public List<Cuenta> consultarCuentasClienteAgrupamiento(PersistenceManager pm, String agrupamiento, String login, String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE cliente = ? and ? = ? and ? = ? group by ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(agrupamiento,login,criterio,filtro,criterio2, filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}
	
	public List<Cuenta> consultarCuentasGerenteOficina(PersistenceManager pm, String idOficina, String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT cliente," + pba.darTablaCuentas()+".* FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE oficina = ? and ? = ? and ? = ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(idOficina,criterio,filtro,criterio2, filtro2, ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}
	
	public List<Cuenta> consultarCuentasGerenteOficinaAgrupamiento(PersistenceManager pm, String agrupamiento, String idOficina, String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE oficina = ? and ? = ? and ? = ? group by ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(agrupamiento,idOficina,criterio,filtro,criterio2, filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}

	
	public List<Cuenta> consultarCuentasGerenteGeneral(PersistenceManager pm, String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT cliente," + pba.darTablaCuentas()+".* FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE ? = ? and ? = ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(criterio,filtro,criterio2, filtro2, ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}
	
	public List<Cuenta> consultarCuentasGerenteGeneralAgrupamiento(PersistenceManager pm, String agrupamiento,String criterio, String filtro, String criterio2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		Query q = pm.newQuery(SQL, "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE ? = ? and ? = ? group by ? order by ? ?");
		q.setResultClass(Cuenta.class);
		q.setParameters(agrupamiento,criterio,filtro,criterio2, filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return (List<Cuenta>) q.executeList();
	}


}
