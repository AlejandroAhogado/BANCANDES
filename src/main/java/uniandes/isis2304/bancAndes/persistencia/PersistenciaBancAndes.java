package uniandes.isis2304.bancAndes.persistencia;

import java.util.Date;
import java.util.List;

import uniandes.isis2304.bancAndes.negocio.CajeroAutomatico;
import uniandes.isis2304.bancAndes.negocio.Cliente;
import uniandes.isis2304.bancAndes.negocio.Cuenta;
import uniandes.isis2304.bancAndes.negocio.Empleado;
import uniandes.isis2304.bancAndes.negocio.GerenteDeOficina;
import uniandes.isis2304.bancAndes.negocio.GerenteGeneral;
import uniandes.isis2304.bancAndes.negocio.Oficina;
import uniandes.isis2304.bancAndes.negocio.OperacionBancaria;
import uniandes.isis2304.bancAndes.negocio.Prestamo;
import uniandes.isis2304.bancAndes.negocio.PuestoAtencionOficina;
import uniandes.isis2304.bancAndes.negocio.PuestoDeAtencion;
import uniandes.isis2304.bancAndes.negocio.PuestoDigital;
import uniandes.isis2304.bancAndes.negocio.Usuario;

public class PersistenciaBancAndes {

	public Usuario adicionarUsuario(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cliente adicionarCliente(String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
			String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
			int telefono, String ciudad, String tipo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cliente> darClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	public Empleado adicionarEmpleado(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public GerenteGeneral adicionarGerenteGeneral(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador, long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public GerenteDeOficina adicionarGerenteDeOficina(String tipoDocumento, int numeroDocumento, String departamento,
			int codigopostal, String nacionalidad, String nombre, String direccion, String login, String contrasena,
			String correo, int telefono, String ciudad, String administrador) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cliente> darClientePorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public OperacionBancaria adicionarOperacionBancaria(long id, float valor, Date fecha, String cliente, long producto,
			String tipoOperacion, long puestoAtencion, String empleado) {
		// TODO Auto-generated method stub
		return null;
	}

	public Cuenta adicionarCuenta(long id, int numeroCuenta, String estado, String tipo, float saldo,
			Date fechaCreacion, Date dechaVencimiento, float tasaRendimiento, long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public Prestamo adicionarPrestamo(long id, float monto, float saldoPendiente, float interes, int numeroCuotas,
			int diaPago, float valorCuotaMinima, Date fechaPrestamo, String cerrado) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cuenta> darCuentaPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Prestamo> darPrestamoPorId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long cerrarCuenta(long idCuenta) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long actualizarSaldoCuenta(long idCuenta, float cambioSaldo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Cuenta> darCuentas() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public PuestoDeAtencion adicionarPuestoDeAtencion(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PuestoAtencionOficina adicionarPuestoAtencionOficina(long id, int telefono, String localizacion,
			long oficina) {
		// TODO Auto-generated method stub
		return null;
	}

	public PuestoDigital adicionarPuestoDigital(long id, int telefono, String tipo, String url) {
		// TODO Auto-generated method stub
		return null;
	}

	public Oficina adicionarOficina(long id, String nombre, String direccion, int puestosPosibles,
			String gerenteLogin) {
		// TODO Auto-generated method stub
		return null;
	}

	public CajeroAutomatico adicionarCajeroAutomatico(long id, int telefono, String localizacion) {
		// TODO Auto-generated method stub
		return null;
	}

	public long cerrarPrestamo(long idPrestamo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long realizarPago(long idPrestamo, float montoPago) {
		// TODO Auto-generated method stub
		return 0;
	}

}
