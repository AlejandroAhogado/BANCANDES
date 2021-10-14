package uniandes.isis2304.bancAndes.negocio;

public class ClienteProducto implements VOClienteProducto{

	//el id del producto
	private long producto;
	
	private String login;

	//constructor por defecto
	public ClienteProducto() {
		this.producto = 0;
		this.login = "";
	}
	
	//Constructor usando campos
	public ClienteProducto(long producto, String login) {
		super();
		this.producto = producto;
		this.login = login;
	}

	public long getProducto() {
		return producto;
	}

	public void setProducto(long producto) {
		this.producto = producto;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "ClienteProducto [producto=" + producto + ", login=" + login + "]";
	}
	
	
	
	
}
