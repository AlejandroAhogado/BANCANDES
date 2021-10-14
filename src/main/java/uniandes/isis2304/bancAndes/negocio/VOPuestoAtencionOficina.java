package uniandes.isis2304.bancAndes.negocio;

public interface VOPuestoAtencionOficina {

	
	public long getId();

	public int getTelefono(); 

	public String getLocalizacion();

	public long getOficina(); 
	
	@Override
	public String toString(); 
	
	
}
