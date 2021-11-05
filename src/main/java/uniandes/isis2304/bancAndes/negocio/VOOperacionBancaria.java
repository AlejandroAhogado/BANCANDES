package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public interface VOOperacionBancaria {

	public long getId();
	
	public float getValor();

	public Date getFecha();

	public String getCliente(); 
	
	public long getProductoOrigen(); 
	
	public long getProductoDestino(); 

	public String getTipoOperacion();
	
	public long getPuestoAtencion();

	public String getEmpleado();

	@Override
	public String toString(); 
	
	
}
