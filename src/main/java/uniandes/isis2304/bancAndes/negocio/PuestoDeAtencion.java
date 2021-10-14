package uniandes.isis2304.bancAndes.negocio;

public class PuestoDeAtencion implements VOPuestoDeAtencion {

	private long id;

	public PuestoDeAtencion(long id) {
		this.id = id;
	}
	
	public PuestoDeAtencion() {
		this.id = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PuestoDeAtencion [id=" + id + "]";
	}
	
}
