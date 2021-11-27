package uniandes.isis2304.bancAndes.negocio;

public class Producto implements VOProducto{

	private long id;
	
	//CDT, CUENTA, PRESTAMO, ACCION, DEPOSITO_INVERSION
	private String tipo;

	public Producto(long id, String tipo) {
		this.id = id;
		this.tipo = tipo;
	}
	
	public Producto() {
		this.id = 0;
		this.tipo ="";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", tipo=" + tipo + "]";
	}

	
	
	
	
}
