package uniandes.isis2304.bancAndes.negocio;

public class Producto implements VOProducto{

	private long id;

	public Producto(long id) {
		this.id = id;
	}
	
	public Producto() {
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
		return "Producto [id=" + id + "]";
	}
	
	
}
