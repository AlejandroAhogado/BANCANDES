package uniandes.isis2304.bancAndes.negocio;

public class PuestoDigital implements VOPuestoDigital{

	private long id;
	
	private int telefono;
	
	//Puede ser de tipo APP, PAGINA_WEB
	private String tipo;
	
	private String url;

	public PuestoDigital(long id, int telefono, String tipo, String url) {
		this.id = id;
		this.telefono = telefono;
		this.tipo = tipo;
		this.url = url;
	}
	
	public PuestoDigital() {
		this.id = 0;
		this.telefono = 0;
		this.tipo = "";
		this.url = "";
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PuestoDigital [id=" + id + ", telefono=" + telefono + ", tipo=" + tipo + ", url=" + url + "]";
	}
	
	
}
