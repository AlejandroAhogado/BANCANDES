package uniandes.isis2304.bancAndes.negocio;

import java.util.Date;

public class Cuenta implements VOCuenta{

	private long id;
	private int numeroCuenta;
	//El estado de una cuenta puede ser ACTIVA, DESACTIVADA, CERRADA
	private String estado;
	//El tipo de una cuenta puede ser AHORROS, CORRIENTE, AFC, CDT
	private String tipo;
	private float saldo;
	private Date fechaCreacion;
	private Date dechaVencimiento;
	private float tasaRendimiento;
	//El id de la oficina donde se crea la cuenta
	private long oficina;
	
	//Constructor por defecto
	public Cuenta() {
		this.id = 0;
		this.numeroCuenta = 0;
		this.estado = "";
		this.tipo = "";
		this.saldo = 0;
		this.fechaCreacion = new Date();
		this.dechaVencimiento = new Date();
		this.tasaRendimiento = 0;
		this.oficina = 0;
	}
	
	
	//Constructor usando campos
	public Cuenta(long id, int numeroCuenta, String estado, String tipo, float saldo, Date fechaCreacion,
			Date dechaVencimiento, float tasaRendimiento, long oficina) {
		super();
		this.id = id;
		this.numeroCuenta = numeroCuenta;
		this.estado = estado;
		this.tipo = tipo;
		this.saldo = saldo;
		this.fechaCreacion = fechaCreacion;
		this.dechaVencimiento = dechaVencimiento;
		this.tasaRendimiento = tasaRendimiento;
		this.oficina = oficina;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public int getNumeroCuenta() {
		return numeroCuenta;
	}


	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public float getSaldo() {
		return saldo;
	}


	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getDechaVencimiento() {
		return dechaVencimiento;
	}


	public void setDechaVencimiento(Date dechaVencimiento) {
		this.dechaVencimiento = dechaVencimiento;
	}


	public float getTasaRendimiento() {
		return tasaRendimiento;
	}


	public void setTasaRendimiento(float tasaRendimiento) {
		this.tasaRendimiento = tasaRendimiento;
	}


	public long getOficina() {
		return oficina;
	}


	public void setOficina(long oficina) {
		this.oficina = oficina;
	}


	@Override
	public String toString() {
		return "Cuenta [id=" + id + ", numeroCuenta=" + numeroCuenta + ", estado=" + estado + ", tipo=" + tipo
				+ ", saldo=" + saldo + ", fechaCreacion=" + fechaCreacion + ", dechaVencimiento=" + dechaVencimiento
				+ ", tasaRendimiento=" + tasaRendimiento + ", oficina=" + oficina + "]";
	}
	
	
}
