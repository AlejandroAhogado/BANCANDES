package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class Cuenta implements VOCuenta{

	private long id;
	private int numeroCuenta;
	//El estado de una cuenta puede ser ACTIVA, DESACTIVADA, CERRADA
	private String estado;
	//El tipo de una cuenta puede ser AHORROS, CORRIENTE, AFC, CDT
	private String tipo;
	private float saldo;
	private Date fechaCreacion;
	private Date fechaVencimiento;
	private float tasaRendimiento;
	//El id de la oficina donde se crea la cuenta
	private long oficina;
	private String corporativo; 
	
	//Constructor por defecto
	public Cuenta() {
		this.id = 0;
		this.numeroCuenta = 0;
		this.estado = "";
		this.tipo = "";
		this.saldo = 0;
		long hoy=System.currentTimeMillis();  
		this.fechaCreacion=new java.sql.Date(hoy); 
		this.fechaVencimiento=new java.sql.Date(hoy); 
		this.tasaRendimiento = 0;
		this.oficina = 0;
		this.corporativo = "FALSE";
	}
	
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}


	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}


	public String getCorporativo() {
		return corporativo;
	}


	public void setCorporativo(String corporativo) {
		this.corporativo = corporativo;
	}


	//Constructor usando campos
	public Cuenta(long id, int numeroCuenta, String estado, String tipo, float saldo, Date fechaCreacion,
			Date fechaVencimiento, float tasaRendimiento, long oficina, String corporativo) {
		
		super();
		System.out.println(id + numeroCuenta + estado + tipo + saldo + fechaCreacion +
				fechaVencimiento + tasaRendimiento + oficina);
		this.id = id;
		this.numeroCuenta = numeroCuenta;
		this.estado = estado;
		this.tipo = tipo;
		this.saldo = saldo;
		this.fechaCreacion = fechaCreacion;
		this.fechaVencimiento = fechaVencimiento;
		this.tasaRendimiento = tasaRendimiento;
		this.oficina = oficina;
		this.corporativo = corporativo;
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
				+ ", saldo=" + saldo + ", fechaCreacion=" + fechaCreacion + ", fechaVencimiento=" + fechaVencimiento
				+ ", tasaRendimiento=" + tasaRendimiento + ", oficina=" + oficina + ", corporativo=" + corporativo
				+ "]";
	}
	
}
