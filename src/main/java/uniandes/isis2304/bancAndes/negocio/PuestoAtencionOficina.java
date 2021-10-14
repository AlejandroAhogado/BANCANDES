package uniandes.isis2304.bancAndes.negocio;

public class PuestoAtencionOficina implements VOPuestoAtencionOficina{

	private long id;
	
	private int telefono;
	
	private String localizacion;
	
	private long oficina;

	public PuestoAtencionOficina(long id, int telefono, String localizacion, long oficina) {
		this.id = id;
		this.telefono = telefono;
		this.localizacion = localizacion;
		this.oficina = oficina;
	}
	
	public PuestoAtencionOficina() {
		this.id = 0;
		this.telefono = 0;
		this.localizacion = "";
		this.oficina = 0;
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


	public long getOficina() {
		return oficina;
	}


	public void setOficina(long oficina) {
		this.oficina = oficina;
	}

	@Override
	public String toString() {
		return "PuestoAtencionOficina [id=" + id + ", telefono=" + telefono + ", localizacion=" + localizacion
				+ ", oficina=" + oficina + "]";
	}
	
}
