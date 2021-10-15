package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

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
			float saldo, Date fechaCreacion, Date dechaVencimiento, float tasaRendimiento, long oficina) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Cuenta> darCuentas(PersistenceManager persistenceManager) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuenta darCuentaPorId(PersistenceManager persistenceManager, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long cerrarCuenta(PersistenceManager pm, long idCuenta) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long actualizarSaldoCuenta(PersistenceManager pm, long idCuenta, float cambioSaldo) {
		// TODO Auto-generated method stub
		return 0;
	}


	
}
