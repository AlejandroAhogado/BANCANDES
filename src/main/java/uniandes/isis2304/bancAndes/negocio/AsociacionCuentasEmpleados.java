package uniandes.isis2304.bancAndes.negocio;

public class AsociacionCuentasEmpleados implements VOAsociacionCuentasEmpleados {

	
	public long asociacion;
	
	public long cuentaEmpleado;

	public AsociacionCuentasEmpleados() {
		this.asociacion = 0;
		this.cuentaEmpleado = 0;
	}
	
	public AsociacionCuentasEmpleados(long asociacion, long cuentaEmpleado) {
		super();
		this.asociacion = asociacion;
		this.cuentaEmpleado = cuentaEmpleado;
	}

	public long getAsociacion() {
		return asociacion;
	}

	public void setAsociacion(long asociacion) {
		this.asociacion = asociacion;
	}

	public long getCuentaEmpleado() {
		return cuentaEmpleado;
	}

	public void setCuentaEmpleado(long cuentaEmpleado) {
		this.cuentaEmpleado = cuentaEmpleado;
	}

	@Override
	public String toString() {
		return "AsociacionCuentasEmpleados [asociacion=" + asociacion + ", cuentaEmpleado=" + cuentaEmpleado + "]";
	}
	
}

