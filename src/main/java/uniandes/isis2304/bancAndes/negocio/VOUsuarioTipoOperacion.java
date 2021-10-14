package uniandes.isis2304.bancAndes.negocio;

public interface VOUsuarioTipoOperacion {


	public String getTipoOperacion(); 

	public void setTipoOperacion(String tipoOperacion);

	public String getUsuario(); 

	public void setUsuario(String usuario); 

	@Override
	public String toString(); 
	
}
