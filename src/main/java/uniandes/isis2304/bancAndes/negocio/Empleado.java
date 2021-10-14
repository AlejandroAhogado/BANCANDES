package uniandes.isis2304.bancAndes.negocio;

public class Empleado implements VOEmpleado{

	private String login;

	public Empleado() {
		this.login = "";
	}
	
	public Empleado(String login) {
		super();
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "Empleado [login=" + login + "]";
	}
	
	
}
