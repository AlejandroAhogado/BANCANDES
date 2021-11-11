package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.AsociacionCuentasEmpleados;
import uniandes.isis2304.bancAndes.negocio.Cuenta;

public class SQLOperacionBancaria {

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
	public SQLOperacionBancaria (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarOperacionBancaria(PersistenceManager pm, long id, float valor, Date fecha, String cliente,
			long productoOrigen, long productoDestino, String tipoOperacion, long puestoAtencion, String empleado) {
		Query q;
		if(productoDestino!=0) {
			q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, productoOrigen, productoDestino,tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?,?)");
			q.setParameters(id, valor, fecha, cliente, productoOrigen,productoDestino,tipoOperacion, puestoAtencion, empleado);
		}
		else {
			q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, productoOrigen, tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?)");
			q.setParameters(id, valor, fecha, cliente, productoOrigen,tipoOperacion, puestoAtencion, empleado);
		}
		return (long) q.executeUnique();
	}

	//************************************************SENTENCIAS PARA REQUERIMIENTOS DE CONSULTA

	//----------------------------------RFC3--------------------------------------------------
	/**
	 * Consulta las operaciones m�s movidas en todas las oficinas
	 * @param pm
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<Object[]> consultar10OperacionesGG (PersistenceManager pm, String fechaInicio, String fechaFin){
		String sql = "SELECT Tipooperacion, puestoAtencion, Avg(Valor) valorPromedio, Count(*) numVeces FROM ";
		sql+= pba.darTablaOperacionesBancarias () + " ob ";
		sql+= "WHERE fecha BETWEEN To_Date(?, 'DD/MM/YY') AND To_Date(?, 'DD/MM/YY') ";
		sql+= "Group By Tipooperacion, puestoAtencion ";
		sql+= "Order By numVeces ";
		sql+= "Fetch First 10 Rows Only";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(fechaInicio, fechaFin);
		return q.executeList();
	}

	/**
	 * Consulta las operaciones m�s movidas en la oficina de 
	 * @param pm
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public List<Object[]> consultar10OperacionesGOf (PersistenceManager pm, String fechaInicio, String fechaFin, long idOficina){
		String sql = "SELECT Tipooperacion, puestoAtencion, Avg(Valor) valorPromedio, Count(*) numVeces FROM ";
		sql+= pba.darTablaOperacionesBancarias () + " ob ";
		sql+= "INNER JOIN "+ pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON ob.puestoAtencion = pao.id ";
		sql+= "WHERE fecha BETWEEN To_Date(?, 'DD/MM/YY') AND To_Date(?, 'DD/MM/YY') AND pao.oficina= ? ";
		sql+= "Group By Tipooperacion, puestoAtencion ";
		sql+= "Order By numVeces ";
		sql+= "Fetch First 10 Rows Only";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(fechaInicio, fechaFin, idOficina);
		return q.executeList();
	}

	//----------------------------------RFC4--------------------------------------------------
	/**
	 * Consulta el usuario (o usuarios) mas activos del sistema
	 *
	 * @param pm
	 * @param tipoUsuario
	 * @param valor
	 * @param idOficina
	 * @return
	 */
	public List<Object[]> obtenerUsuarioMasActivoValorGOf (PersistenceManager pm, String tipoUsuario, float valor, long idOficina){
		String sql = "SELECT * FROM ( ";
		sql+= "SELECT ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+ pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON ob.puestoAtencion = pao.id ";
		sql+= "WHERE valor> ? AND pao.oficina= ? ";
		sql+= "Group By ? ";
		sql+= ") where NUMOPERACIONES=( ";
		sql+= "select max(numoperaciones) ";
		sql+= "from( ";
		sql+= "Select ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+ pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "On ob.puestoAtencion = pao.id ";
		sql+= "where valor> ? And pao.oficina= ? ";
		sql+= "Group By ? ";
		sql+= ") )";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoUsuario, valor, idOficina, tipoUsuario, tipoUsuario, valor, idOficina, tipoUsuario);
		return q.executeList();
	}


	/**
	 * Consulta el usuario (o usuarios) mas activos del sistema como gerente de oficina usando el filtro de tipo de operacion
	 *
	 * @param pm
	 * @param tipoUsuario
	 * @param tipoOperacion
	 * @param idOficina
	 * @return
	 */
	public List<Object[]> obtenerUsuarioMasActivoTipoOpGOf (PersistenceManager pm, String tipoUsuario, String tipoOperacion, long idOficina){
		String sql = "SELECT * FROM ( ";
		sql+= "SELECT ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+ pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON ob.puestoAtencion = pao.id ";
		sql+= "WHERE tipoOperacion = ? AND pao.oficina= ? ";
		sql+= "Group By ? ";
		sql+= ") where NUMOPERACIONES=( ";
		sql+= "select max(numoperaciones) ";
		sql+= "from( ";
		sql+= "Select ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "INNER JOIN "+ pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "On ob.puestoAtencion = pao.id ";
		sql+= "where tipoOperacion = ? And pao.oficina= ? ";
		sql+= "Group By ? ";
		sql+= ") )";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoUsuario, tipoOperacion, idOficina, tipoUsuario, tipoUsuario, tipoOperacion, idOficina, tipoUsuario);
		return q.executeList();
	}

	/**
	 * Consulta el usuario (o usuarios) mas activos del sistema como un gerente general usando el filtro de valor
	 *
	 * @param pm
	 * @param tipoUsuario
	 * @param valor
	 * @param idOficina
	 * @return
	 */
	public List<Object[]> obtenerUsuarioMasActivoValorGG (PersistenceManager pm, String tipoUsuario, float valor){
		String sql = "SELECT * FROM ( ";
		sql+= "SELECT ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "WHERE valor> ? ";
		sql+= "Group By ? ";
		sql+= ") where NUMOPERACIONES=( ";
		sql+= "select max(numoperaciones) ";
		sql+= "from( ";
		sql+= "Select ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "where valor> ? ";
		sql+= "Group By ? ";
		sql+= ") )";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoUsuario, valor, tipoUsuario, tipoUsuario, valor, tipoUsuario);
		return q.executeList();
	}


	/**
	 * Consulta el usuario (o usuarios) mas activos del sistema como un gerente general usando el filtro de tipo de operacion
	 *
	 * @param pm
	 * @param tipoUsuario
	 * @param tipoOperacion
	 * @param idOficina
	 * @return
	 */
	public List<Object[]> obtenerUsuarioMasActivoTipoOpGG (PersistenceManager pm, String tipoUsuario, String tipoOperacion){
		String sql = "SELECT * FROM ( ";
		sql+= "SELECT ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "WHERE tipoOperacion = ? ";
		sql+= "Group By ? ";
		sql+= ") where NUMOPERACIONES=( ";
		sql+= "select max(numoperaciones) ";
		sql+= "from( ";
		sql+= "Select ?, count(*) NUMOPERACIONES ";
		sql+= "from "+pba.darTablaOperacionesBancarias ()+" ob ";
		sql+= "where tipoOperacion = ? ";
		sql+= "Group By ? ";
		sql+= ") )";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(tipoUsuario, tipoOperacion, tipoUsuario, tipoUsuario, tipoOperacion, tipoUsuario);
		return q.executeList();
	}

	//----------------------------------RFC6--------------------------------------------------
	/**
	 * @param pm
	 * @param criterio1
	 * @param signo1
	 * @param filtro1
	 * @param criterio2
	 * @param signo2
	 * @param filtro2
	 * @param ordenamiento
	 * @param tipoOrden
	 * @return una lista de objetos con las operaciones de bancAndes
	 */
	public List<Object[]> consultarOperacionesGerenteGeneral(PersistenceManager pm, String criterio1,
			String signo1, String filtro1, String criterio2, String signo2, String filtro2, String ordenamiento,
			String tipoOrden) {
		String sql = "SELECT opb.*, pao.oficina FROM ";
		sql+= pba.darTablaOperacionesBancarias () + " opb ";
		sql+= "JOIN "+pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON pao.id = opb.puestoAtencion WHERE " + criterio1 + " " + signo1 + " ? ";
		sql+= "and " + criterio2 + " " + signo2 + " ? order by "+ ordenamiento + " " + tipoOrden;
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(filtro1, filtro2);
		return q.executeList();
	}

	/**
	 * @param pm
	 * @param idOficina
	 * @param criterio1
	 * @param signo1
	 * @param filtro1
	 * @param criterio2
	 * @param signo2
	 * @param filtro2
	 * @param ordenamiento
	 * @param tipoOrden
	 * @return una lista de objetos con las operaciones de la oficina del gerente de oficina que consulta
	 */
	public List<Object[]> consultarOperacionesGerenteOficina(PersistenceManager pm, String idOficina,
			String criterio1, String signo1, String filtro1, String criterio2, String signo2, String filtro2,
			String ordenamiento, String tipoOrden) {
		String sql = "SELECT opb.*, pao.oficina FROM ";
		sql+= pba.darTablaOperacionesBancarias () + " opb ";
		sql+= "JOIN "+pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON pao.id = opb.puestoAtencion WHERE oficina = ? and " + criterio1 + " " + signo1 + " ? ";
		sql+= "and " + criterio2 + " " + signo2 + " ? order by "+ ordenamiento + " " + tipoOrden;
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idOficina, filtro1, filtro2);
		return q.executeList();
	}

	/**
	 * @param pm
	 * @param loginCliente
	 * @param criterio1
	 * @param signo1
	 * @param filtro1
	 * @param criterio2
	 * @param signo2
	 * @param filtro2
	 * @param ordenamiento
	 * @param tipoOrden
	 * @return una lista de objetos con las operaciones del cliente que realiza la consulta
	 */
	public List<Object[]> consultarOperacionesCliente(PersistenceManager pm, String loginCliente,
			String criterio1, String signo1, String filtro1, String criterio2, String signo2, String filtro2,
			String ordenamiento, String tipoOrden) {
		String sql = "SELECT opb.*, pao.oficina FROM ";
		sql+= pba.darTablaOperacionesBancarias () + " opb ";
		sql+= "JOIN "+pba.darTablaPuestosAtencionOficina() +" pao ";
		sql+= "ON pao.id = opb.puestoAtencion WHERE cliente = ? and " + criterio1 + " " + signo1 + " ? ";
		sql+= "and " + criterio2 + " " + signo2 + " ? order by "+ ordenamiento + " " + tipoOrden;
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(loginCliente, filtro1, filtro2);
		return q.executeList();
	}

	/**
	 * @param pm
	 * @param listaCuentas
	 * @param idCuenta
	 * @param valor
	 * @param cliente
	 * @param puestoAtencionoficina
	 * @param loginUsuarioSistema
	 * @return
	 */
	public int pagarNomina(PersistenceManager pm, List<AsociacionCuentasEmpleados> listaCuentas, long idCuenta,
			float valor, String cliente, long puestoAtencionoficina, String loginUsuarioSistema) {


		int i = 0;
		boolean continuar = true;
		while ( i<listaCuentas.size() && continuar) {

			Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaCuentas () + " WHERE id = ?");
			q.setResultClass(Cuenta.class);
			q.setParameters(idCuenta);
			Cuenta cuenta = (Cuenta) q.executeUnique();

			if (cuenta.getSaldo()>=valor) {

				Query q2;
				long hoy=System.currentTimeMillis();
				long idd = pba.nextval();
				if (loginUsuarioSistema!=null) {

					q2 = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, productoOrigen, productoDestino,tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?,?)");
					q2.setParameters(idd, valor, new java.sql.Date(hoy), cliente, idCuenta,listaCuentas.get(i).getCuentaEmpleado(),"TRANSFERIR", puestoAtencionoficina, loginUsuarioSistema);
					q2.executeUnique();
				}else {
					q2 = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, productoOrigen, productoDestino,tipoOperacion, puestoAtencion) values (?,?,?,?,?,?,?,?)");
					q2.setParameters(idd, valor, new java.sql.Date(hoy), cliente, idCuenta,listaCuentas.get(i).getCuentaEmpleado(),"TRANSFERIR", puestoAtencionoficina);
					q2.executeUnique();
				}
				Query q3 = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET saldo = saldo + ? WHERE id = ?");
				q3.setParameters(-valor, idCuenta);
				q3.executeUnique();

				Query q4 = pm.newQuery(SQL, "UPDATE " + pba.darTablaCuentas () + " SET saldo = saldo + ? WHERE id = ? ");
				q4.setParameters(valor, listaCuentas.get(i).getCuentaEmpleado());
				q4.executeUnique();

				Query q5 = pm.newQuery(SQL, "SAVEPOINT SAVEPOINT"+idd);
				q5.executeUnique();
				i++;
			}else {
				continuar = false;
			}
		}
		return i;
	}

}
