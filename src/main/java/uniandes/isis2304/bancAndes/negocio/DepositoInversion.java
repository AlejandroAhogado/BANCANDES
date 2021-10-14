package uniandes.isis2304.bancAndes.negocio;

import java.util.Date;

public class DepositoInversion implements VODepositoInversion{

	private long id;
	private float monto;
	private float tasaRendimiento;
	private String riesgo;
	private Date fechaInversion;
	
	//Constructor por defecto
	public DepositoInversion() {
		this.id = 0;
		this.monto = 0;
		this.tasaRendimiento = 0;
		this.riesgo = "";
		this.fechaInversion = new Date();
	}
	
	
	//Constructor usando campos
	public DepositoInversion(long id, float monto, float tasaRendimiento, String riesgo, Date fechaInversion) {
		super();
		this.id = id;
		this.monto = monto;
		this.tasaRendimiento = tasaRendimiento;
		this.riesgo = riesgo;
		this.fechaInversion = fechaInversion;
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


	public float getTasaRendimiento() {
		return tasaRendimiento;
	}


	public void setTasaRendimiento(float tasaRendimiento) {
		this.tasaRendimiento = tasaRendimiento;
	}


	public String getRiesgo() {
		return riesgo;
	}


	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}


	public Date getFechaInversion() {
		return fechaInversion;
	}


	public void setFechaInversion(Date fechaInversion) {
		this.fechaInversion = fechaInversion;
	}


	@Override
	public String toString() {
		return "DepositoInversion [id=" + id + ", monto=" + monto + ", tasaRendimiento=" + tasaRendimiento + ", riesgo="
				+ riesgo + ", fechaInversion=" + fechaInversion + "]";
	}
	
	
	
}
