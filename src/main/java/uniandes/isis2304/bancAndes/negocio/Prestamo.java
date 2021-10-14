package uniandes.isis2304.bancAndes.negocio;

import java.util.Date;

public class Prestamo implements VOPrestamo{

	private long id;
	
	private  float monto;
	
	private float saldoPendiente;
	
	private float interes;
	
	private int numeroCuotas;
	
	private int diaPago;
	
	private float valorCuotaMinima;
	
	private Date fechaPrestamo;
	
	private String cerrado;

	public Prestamo(long id, float monto, float saldoPendiente, float interes, int numeroCuotas, int diaPago,
			float valorCuotaMinima, Date fechaPrestamo, String cerrado) {
		this.id = id;
		this.monto = monto;
		this.saldoPendiente = saldoPendiente;
		this.interes = interes;
		this.numeroCuotas = numeroCuotas;
		this.diaPago = diaPago;
		this.valorCuotaMinima = valorCuotaMinima;
		this.fechaPrestamo = fechaPrestamo;
		this.cerrado = cerrado;
	}
	
	public Prestamo()
	{
		this.id = 0;
		this.monto = 0;
		this.saldoPendiente = 0;
		this.interes = 0;
		this.numeroCuotas = 0;
		this.diaPago = 1;
		this.valorCuotaMinima = 0;
		this.fechaPrestamo = new Date();
		this.cerrado = "FALSE";
		
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public float getSaldoPendiente() {
		return saldoPendiente;
	}

	public void setSaldoPendiente(float saldoPendiente) {
		this.saldoPendiente = saldoPendiente;
	}

	public float getInteres() {
		return interes;
	}

	public void setInteres(float interes) {
		this.interes = interes;
	}

	public int getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(int numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public int getDiaPago() {
		return diaPago;
	}

	public void setDiaPago(int diaPago) {
		this.diaPago = diaPago;
	}

	public float getValorCuotaMinima() {
		return valorCuotaMinima;
	}

	public void setValorCuotaMinima(float valorCuotaMinima) {
		this.valorCuotaMinima = valorCuotaMinima;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getCerrado() {
		return cerrado;
	}

	public void setCerrado(String cerrado) {
		this.cerrado = cerrado;
	}
	
	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", monto=" + monto + ", saldoPendiente=" + saldoPendiente + ", interes=" + interes
				+ ", numeroCuotas=" + numeroCuotas + ", diaPago=" + diaPago + ", valorCuotaMinima=" + valorCuotaMinima
				+ ", fechaPrestamo=" + fechaPrestamo + ", cerrado=" + cerrado + "]";
	}

	
}
