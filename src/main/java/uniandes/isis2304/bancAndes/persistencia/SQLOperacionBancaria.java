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
			long producto, String tipoOperacion, long puestoAtencion, String empleado) {
		
		 Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaOperacionesBancarias () + "( id, valor, fecha, cliente, producto,tipoOperacion, puestoAtencion, empleado) values (?,?,?,?,?,?,?,?)");
	        q.setParameters(id, valor, fecha, cliente, producto,tipoOperacion, puestoAtencion, empleado);
	        return (long) q.executeUnique();
	}
	
	//************************************************SENTENCIAS PARA REQUERIMIENTOS DE CONSULTA
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
		sql+= "WHERE fecha BETWEEN To_Date(?, 'DD/MM/YY') AND To_Date(?, 'DD/MM/YY') AND pao.oficina=?";
		sql+= "Group By Tipooperacion, puestoAtencion ";
		sql+= "Order By numVeces ";
		sql+= "Fetch First 10 Rows Only";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(fechaInicio, fechaFin, idOficina);
		return q.executeList();
	}


}
