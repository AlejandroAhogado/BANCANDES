package uniandes.isis2304.bancAndes.negocio;

import java.sql.Date;

public class OperacionBancaria implements VOOperacionBancaria {

	private long id;
	
	private float valor;
	
	private Date fecha;
	
	private String cliente;
	
	private long productoOrigen;
	
	private long productoDestino;
	
	private String tipoOperacion;
	
	private long puestoAtencion;
	
	private String empleado;

	
	public OperacionBancaria(long id, float valor, Date fecha, String cliente, long productoOrigen,long productoDestino, String tipoOperacion,
			long puestoAtencion, String empleado) {
		
		this.id = id;
		this.valor = valor;
		this.fecha = fecha;
		this.cliente = cliente;
		this.productoOrigen = productoOrigen;
		this.productoDestino = productoDestino;
		this.tipoOperacion = tipoOperacion;
		this.puestoAtencion = puestoAtencion;
		this.empleado = empleado;
	}

	public OperacionBancaria()
	{
		this.id = 0;
		this.valor = 0;
		long hoy=System.currentTimeMillis();  
		this.fecha=new java.sql.Date(hoy); 
		this.cliente = "";
		this.productoOrigen = 0;
		this.productoDestino = 0;
		this.tipoOperacion = "";
		this.puestoAtencion = 0;
		this.empleado = "";
	}
	

	public long getProductoOrigen() {
		return productoOrigen;
	}

	public void setProductoOrigen(long productoOrigen) {
		this.productoOrigen = productoOrigen;
	}

	public long getProductoDestino() {
		return productoDestino;
	}

	public void setProductoDestino(long productoDestino) {
		this.productoDestino = productoDestino;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public float getValor() {
		return valor;
	}


	public void setValor(float valor) {
		this.valor = valor;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
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


	public String getEmpleado() {
		return empleado;
	}


	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	@Override
	public String toString() {
		return "OperacionBancaria [id=" + id + ", valor=" + valor + ", fecha=" + fecha + ", cliente=" + cliente
				+ ", productoOrigen=" + productoOrigen + ", productoDestino=" + productoDestino + ", tipoOperacion="
				+ tipoOperacion + ", puestoAtencion=" + puestoAtencion + ", empleado=" + empleado + "]";
	}


	
}
