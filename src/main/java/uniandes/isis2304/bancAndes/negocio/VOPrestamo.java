package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOPrestamo {

	public long getId(); 
	
	public float getMonto();

	public float getSaldoPendiente();
	
	public float getInteres(); 
	
	public int getNumeroCuotas(); 

	public int getDiaPago(); 
	
	public float getValorCuotaMinima();

	public Date getFechaPrestamo(); 

	public String getCerrado(); 

	public String toString();

	
}
