package uniandes.isis2304.bancAndes.persistencia;



import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.bancAndes.negocio.Prestamo;

public class SQLPrestamo {

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
	public SQLPrestamo (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarPrestamo(PersistenceManager pm, long id, float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {
		
		   Query q = pm.newQuery(SQL, "INSERT INTO " + pba.darTablaPrestamos () + "(id, monto, saldoPendiente, interes, numeroCuotas, diaPago, valorCuotaMinima, fechaPrestamo, cerrado) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(id, monto, saldoPendiente, interes, numeroCuotas,
       			 diaPago, valorCuotaMinima, fechaPrestamo, cerrado);
	        return (long) q.executeUnique();
		
	}

	public Prestamo darPrestamoPorId(PersistenceManager pm, long id) {
	
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pba.darTablaPrestamos () + " WHERE id = ?");
		q.setResultClass(Prestamo.class);
		q.setParameters(id);
		return (Prestamo) q.executeUnique();
		
	}

	public long cerrarPrestamo(PersistenceManager pm, long idPrestamo) {
	
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaPrestamos () + " SET cerrado = 'TRUE'  WHERE id = ?");
		q.setParameters(idPrestamo);
		return  (long) q.executeUnique();
		
	}

	public long realizarPago(PersistenceManager pm, long idPrestamo, float montoPago) {
		
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaPrestamos () + " SET saldoPendiente = saldoPendiente - ? WHERE id = ?");
		q.setParameters(montoPago, idPrestamo);
		return  (long) q.executeUnique();
	}

	//***********************************M�TODOS PARA EL RFC5*******************************************************
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
	 * @return una lista de objetos con los prestamos+cliente de todo bancandes
	 */
	public List<Object[]> consultarPrestamosGerenteGeneral(PersistenceManager pm, String criterio1,
			String signo1, String filtro1, String criterio2, String signo2, String filtro2, String ordenamiento,
			String tipoOrden) {
		String sql = "SELECT cp.cliente, pr.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaPrestamos() +" pr ";
		sql+= "ON cp.producto = pr.id WHERE " + criterio1 + " " + signo1 + " ? ";
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
	 * @return una lista de objetos con los prestamos+cliente de la oficina que realiza la consulta
	 */
	public List<Object[]> consultarPrestamosGerenteOficina(PersistenceManager pm, String idOficina,
			String criterio1, String signo1, String filtro1, String criterio2, String signo2, String filtro2,
			String ordenamiento, String tipoOrden) {
		String sql = "SELECT cp.cliente, pr.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaPrestamos() +" pr ";
		sql+= "ON cp.producto = pr.id WHERE oficina = ? and " + criterio1 + " " + signo1 + " ? ";
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
	 * @return una lista de objetos con los prestamos+cliente del cliente que realiza la consulta
	 */
	public List<Object[]> consultarPrestamosCliente(PersistenceManager pm, String loginCliente,
			String criterio1, String signo1, String filtro1, String criterio2, String signo2, String filtro2,
			String ordenamiento, String tipoOrden) {
		String sql = "SELECT cp.cliente, pr.* FROM ";
		sql+= pba.darTablaClientesProductos () + " cp ";
		sql+= "JOIN "+pba.darTablaPrestamos() +" pr ";
		sql+= "ON cp.producto = pr.id WHERE cliente = ? and " + criterio1 + " " + signo1 + " ? ";
		sql+= "and " + criterio2 + " " + signo2 + " ? order by "+ ordenamiento + " " + tipoOrden;
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(loginCliente, filtro1, filtro2);
		return q.executeList();
	}
	
	
}
