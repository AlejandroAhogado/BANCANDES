package uniandes.isis2304.bancAndes.negocio;

public interface VOPuestoDigital {

	public long getId(); 

	public int getTelefono();

	public String getTipo();

	public String getUrl(); 

	@Override
	public String toString();	
	
}
