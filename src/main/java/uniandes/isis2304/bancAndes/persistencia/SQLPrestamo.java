package uniandes.isis2304.bancAndes.persistencia;



import java.sql.Date;

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
	
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaPrestamos () + " SET cerrado = FALSE  WHERE id = ?");
		q.setParameters(idPrestamo);
		return  (long) q.executeUnique();
		
	}

	public long realizarPago(PersistenceManager pm, long idPrestamo, float montoPago) {
		
		Query q = pm.newQuery(SQL, "UPDATE " + pba.darTablaPrestamos () + " SET saldoPendiente = saldoPendiente - ? WHERE id = ?");
		q.setParameters(montoPago, idPrestamo);
		return  (long) q.executeUnique();
	}
	
	
}
