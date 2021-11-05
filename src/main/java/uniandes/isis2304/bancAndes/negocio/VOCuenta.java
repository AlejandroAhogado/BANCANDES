package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOCuenta {

	public long getId();


	public int getNumeroCuenta();


	public String getEstado();


	public String getTipo();


	public float getSaldo();

	public Date getFechaCreacion();


	public Date getFechaVencimiento();

	public float getTasaRendimiento();


	public long getOficina();
	
	public String getCorporativo();

}
