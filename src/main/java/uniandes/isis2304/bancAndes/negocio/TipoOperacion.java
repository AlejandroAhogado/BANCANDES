package uniandes.isis2304.bancAndes.negocio;

public class TipoOperacion implements VOTipoOperacion {

	//Puede ser 'ABRIR','CERRAR','CONSIGNAR','TRANSFERIR','RETIRAR','SOLICITAR', 'APROBAR', 'RENOVAR', 'RECHAZAR','DESACTIVAR','ACTIVAR', 'PAGAR_CUOTA', 'LIQUIDAR_RENDIMIENTOS', 'PAGAR_CUOTA_EXTRAORDINARIA'
	private String tipo;

	public TipoOperacion(String tipo) {
		this.tipo = tipo;
	}
	
	public TipoOperacion() {
		this.tipo = "";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "TipoOperacion [tipo=" + tipo + "]";
	}
	
	
}
