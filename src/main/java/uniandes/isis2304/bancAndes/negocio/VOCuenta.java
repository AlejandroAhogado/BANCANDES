package uniandes.isis2304.bancAndes.negocio;

import java.util.Date;

public interface VOCuenta {

	public long getId();


	public int getNumeroCuenta();


	public String getEstado();


	public String getTipo();


	public float getSaldo();

	public Date getFechaCreacion();


	public Date getDechaVencimiento();

	public float getTasaRendimiento();


	public long getOficina();

}
