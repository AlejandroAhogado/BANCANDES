package uniandes.isis2304.bancAndes.negocio;

public class Oficina implements VOOficina{

	private long id;
	
	private String nombre;
	
	private String direccion;
	
	private int puestosPosibles;
	
	private String gerenteLogin;
		
	public Oficina(long id, String nombre, String direccion, int puestosPosibles, String gerenteLogin)
	{
	this.id = id;
	this.nombre = nombre;
	this.direccion = direccion;
	this.puestosPosibles = puestosPosibles;
	this.gerenteLogin = gerenteLogin;
	}
	
	public Oficina()
	{
		this.id = 0;
		this.nombre = "";
		this.direccion = "";
		this.puestosPosibles = 0;
		this.gerenteLogin = "";

	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getPuestosPosibles() {
		return puestosPosibles;
	}

	public void setPuestosPosibles(int puestosPosibles) {
		this.puestosPosibles = puestosPosibles;
	}

	public String getGerenteLogin() {
		return gerenteLogin;
	}

	public void setGerenteLogin(String gerenteLogin) {
		this.gerenteLogin = gerenteLogin;
	}

	
	public String toString() {
		return "Oficina [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", puestosPosibles="
				+ puestosPosibles + ", gerenteLogin=" + gerenteLogin + "]";
	}


}
