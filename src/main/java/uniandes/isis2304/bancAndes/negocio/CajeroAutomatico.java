package uniandes.isis2304.bancAndes.negocio;

public class CajeroAutomatico implements VOCajeroAutomatico{

	private long id;
	
	private int telefono;
	
	private String localizacion;

	//Constructor por defecto
	public CajeroAutomatico() {
		this.id = 0;
		this.telefono =0;
		this.localizacion = "";
	}
	
	//Constructor usando campos
	public CajeroAutomatico(long id, int telefono, String localizacion) {
		super();
		this.id = id;
		this.telefono = telefono;
		this.localizacion = localizacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	@Override
	public String toString() {
		return "CajeroAutomatico [id=" + id + ", telefono=" + telefono + ", localizacion=" + localizacion + "]";
	}
	
	
	
}
