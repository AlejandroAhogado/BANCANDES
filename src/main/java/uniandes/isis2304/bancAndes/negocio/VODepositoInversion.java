package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VODepositoInversion {

	public long getId();


	public float getMonto();


	public float getTasaRendimiento();

	public String getRiesgo();


	public Date getFechaInversion();

}
