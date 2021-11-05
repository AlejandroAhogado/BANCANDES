package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, productoOrigen, productoDestino,tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?,?)");
	        q.setParameters(id, valor, fecha, cliente, productoOrigen,productoDestino,tipoOperacion, puestoAtencion, empleado);
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

}
