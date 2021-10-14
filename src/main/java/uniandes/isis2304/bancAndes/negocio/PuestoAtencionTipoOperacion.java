package uniandes.isis2304.bancAndes.negocio;

public class PuestoAtencionTipoOperacion implements VOPuestoAtencionTipoOperacion{

	// Puede ser de tipo 'ABRIR','CERRAR','CONSIGNAR','TRANSFERIR','RETIRAR','SOLICITAR', 'APROBAR', 'RENOVAR', 'RECHAZAR','DESACTIVAR','ACTIVAR', 'PAGAR_CUOTA', 'LIQUIDAR_RENDIMIENTOS', 'PAGAR_CUOTA_EXTRAORDINARIA'
	private String tipoOperacion;
	
	private long puestoAtencion;

	public PuestoAtencionTipoOperacion(String tipoOperacion, long puestoAtencion) {
		this.tipoOperacion = tipoOperacion;
		this.puestoAtencion = puestoAtencion;
	}
	
	public PuestoAtencionTipoOperacion() {
		this.tipoOperacion = "";
		this.puestoAtencion = 0;
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public long getPuestoAtencion() {
		return puestoAtencion;
	}

	public void setPuestoAtencion(long puestoAtencion) {
		this.puestoAtencion = puestoAtencion;
	}

	@Override
	public String toString() {
		return "PuestoAtencionTipoOperacion [tipoOperacion=" + tipoOperacion + ", puestoAtencion=" + puestoAtencion
				+ "]";
	}
	
}
