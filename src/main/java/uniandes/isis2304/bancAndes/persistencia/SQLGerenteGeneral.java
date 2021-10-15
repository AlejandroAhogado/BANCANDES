package uniandes.isis2304.bancAndes.persistencia;

import javax.jdo.PersistenceManager;

public class SQLGerenteGeneral {

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
	public SQLGerenteGeneral (PersistenciaBancAndes pba)
	{
		this.pba = pba;
	}

	public long adicionarGerenteGeneral(PersistenceManager pm, String tipoDocumento, int numeroDocumento,
			String departamento, int codigopostal, String nacionalidad, String nombre, String direccion, String login,
			String contrasena, String correo, int telefono, String ciudad, String administrador, long oficina) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
