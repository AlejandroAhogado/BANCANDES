package uniandes.isis2304.bancAndes.negocio;

public class Accion implements VOAccion{

	private long id;
	
	private float precio;
	
	private float dividendo;
	
	private String empresa;

	//Constructor por defecto
	public Accion() {
		this.id = 0;
		this.precio = 0;
		this.dividendo = 0;
		this.empresa = "";
	}
	
	//Constructor usando campos
	public Accion(long id, float precio, float dividendo, String empresa) {
		super();
		this.id = id;
		this.precio = precio;
		this.dividendo = dividendo;
		this.empresa = empresa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getDividendo() {
		return dividendo;
	}

	public void setDividendo(float dividendo) {
		this.dividendo = dividendo;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return "Accion [id=" + id + ", precio=" + precio + ", dividendo=" + dividendo + ", empresa=" + empresa + "]";
	}
	
}
