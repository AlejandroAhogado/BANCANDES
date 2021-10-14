package uniandes.isis2304.bancAndes.negocio;

public class Usuario implements VOUsuario {

	private String login;

	public Usuario(String login) {
		this.login = login;
	}
	
	public Usuario() {
		this.login = "";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "Usuario [login=" + login + "]";
	}
	
}
