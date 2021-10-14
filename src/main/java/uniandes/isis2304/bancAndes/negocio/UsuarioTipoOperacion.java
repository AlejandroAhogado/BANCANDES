package uniandes.isis2304.bancAndes.negocio;

public class UsuarioTipoOperacion implements VOUsuarioTipoOperacion {

	//Puedo ser 'ABRIR','CERRAR','CONSIGNAR','TRANSFERIR','RETIRAR','SOLICITAR', 'APROBAR', 'RENOVAR', 'RECHAZAR','DESACTIVAR','ACTIVAR', 'PAGAR_CUOTA', 'LIQUIDAR_RENDIMIENTOS', 'PAGAR_CUOTA_EXTRAORDINARIA'
	private String tipoOperacion;
	
	private String usuario;

	public UsuarioTipoOperacion(String tipoOperacion, String usuario) {
		this.tipoOperacion = tipoOperacion;
		this.usuario = usuario;
	}
	
	public UsuarioTipoOperacion() {
		this.tipoOperacion = "";
		this.usuario = "";
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "UsuarioTipoOperacion [tipoOperacion=" + tipoOperacion + ", usuario=" + usuario + "]";
	}
	
}
