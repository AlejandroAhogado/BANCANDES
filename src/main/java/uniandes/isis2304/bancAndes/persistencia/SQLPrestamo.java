package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;

import javax.jdo.PersistenceManager;

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

	public long adicionarPrestamo(long id, float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Prestamo darPrestamoPorId(PersistenceManager persistenceManager, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long cerrarPrestamo(PersistenceManager pm, long idPrestamo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long realizarPago(PersistenceManager pm, long idPrestamo, float montoPago) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
