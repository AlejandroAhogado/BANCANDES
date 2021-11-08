package uniandes.isis2304.bancAndes.persistencia;

import java.sql.Date;
import java.sql.Types;
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
			float saldo, Date fechaCreacion, Date fechaVencimiento, float tasaRendimiento, long oficina, String corporativo) {
		Query q;
		if (fechaVencimiento!=null) {
			q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaCuentas () + "(id, numeroCuenta, estado, tipo, saldo,fechaCreacion, fechaVencimiento, tasaRendimiento, oficina, corporativo) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			q.setParameters(id, numeroCuenta, estado, tipo, saldo,
					fechaCreacion, fechaVencimiento, tasaRendimiento, oficina, corporativo);
		}
		else {
			q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaCuentas () + "(id, numeroCuenta, estado, tipo, saldo,fechaCreacion, tasaRendimiento, oficina, corporativo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			q.setParameters(id, numeroCuenta, estado, tipo, saldo,
					fechaCreacion, tasaRendimiento, oficina, corporativo);
		}
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
	
	public Cuenta darCuentaPorNumero(PersistenceManager pm, int numero) {
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCuentas () + " WHERE numeroCuenta = ?");
		q.setParameters(numero);
		q.setResultClass(Cuenta.class);
		return (Cuenta) q.executeUnique();
	}

	public long cerrarCuenta(PersistenceManager pm, long idCuenta) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET estado = CERRADA, saldo = 0 WHERE id = ?");
		q.setParameters(idCuenta);
		//q.
		return (long) q.execute();
	}

	public long actualizarSaldoCuenta(PersistenceManager pm, long idCuenta, float cambioSaldo) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET saldo = saldo + ? WHERE id = ?");
		q.setParameters(cambioSaldo, idCuenta);
		return (long) q.executeUnique();
	}

	public long cambiarActividadCuenta(PersistenceManager pm, long idCuenta, String estado) {
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET estado = ? WHERE id = ?");
		q.setParameters(estado, idCuenta);
		return (long) q.executeUnique();
	}

	//**************************************SENTENCIAS PARA LOS REQUERIMIENTOS DE CONSULTA**************************************************************
	
	
	public List<Object []> consultarCuentasCliente(PersistenceManager pm, String login, String criterio,String signo1 ,String filtro, String criterio2,String signo2 ,String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql = "SELECT cp.cliente, ct.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaCuentas() +" ct ";
		sql+= "ON cp.producto = ct.id WHERE cliente = ? and " + criterio + " " + signo1 + " ? ";
		sql+= "and " + criterio2 + " " + signo2 + " ? order by "+ ordenamiento + "  asc";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(login, filtro, filtro2);
		return q.executeList();
	}
	
	public List<Object []> consultarCuentasClienteAgrupamiento(PersistenceManager pm, String agrupamiento, String login, String criterio,String signo1 ,String filtro, String criterio2, String signo2,String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql = "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE cliente = ? and ? ? ? and ? ? ? group by ? order by ? ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(agrupamiento,login,criterio,signo1,filtro,criterio2,signo2 ,filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return q.executeList();
	}
	
	public List<Object []> consultarCuentasGerenteOficina(PersistenceManager pm, String idOficina, String criterio,String signo1, String filtro, String criterio2,String signo2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql = "SELECT cp.cliente, ct.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaCuentas() +" ct ";
		sql+= "ON cp.producto = ct.id WHERE oficina = ? and ? ? ? and ? ? ? order by ? ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idOficina,criterio,signo1,filtro,criterio2,signo2, filtro2, ordenamiento,tipoOrdenamiento);
		return q.executeList();
	}
	
	public List<Object []> consultarCuentasGerenteOficinaAgrupamiento(PersistenceManager pm, String agrupamiento, String idOficina, String criterio, String signo1, String filtro, String criterio2, String signo2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql =  "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE oficina = ? and ? ? ? and ? ? ? group by ? order by ? ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(agrupamiento,idOficina,criterio,signo1,filtro,criterio2, signo2,filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return q.executeList();
	}

	
	public List<Object []> consultarCuentasGerenteGeneral(PersistenceManager pm, String criterio, String signo1, String filtro, String criterio2, String signo2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql = "SELECT cp.cliente, ct.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaCuentas() +" ct ";
		sql+= "ON cp.producto = ct.id WHERE ? ? ? and ? ? ? order by ? ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(criterio,signo1, filtro,criterio2,signo2, filtro2, ordenamiento,tipoOrdenamiento);
		return q.executeList();
	}
	
	public List<Object []> consultarCuentasGerenteGeneralAgrupamiento(PersistenceManager pm, String agrupamiento,String criterio,  String signo1, String filtro, String criterio2, String signo2, String filtro2, String ordenamiento, String tipoOrdenamiento )
	{
		String sql = "SELECT ?, count(*) FROM " + pba.darTablaClientesProductos () +
				" JOIN "+pba.darTablaCuentas() + "ON producto = id WHERE ? ? ? and ? ? ? group by ? order by ? ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(agrupamiento,criterio,signo1, filtro,criterio2,signo2, filtro2, agrupamiento,ordenamiento,tipoOrdenamiento);
		return q.executeList();
	}

	


}
