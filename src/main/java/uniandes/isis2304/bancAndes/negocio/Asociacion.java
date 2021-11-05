package uniandes.isis2304.bancAndes.negocio;

public class Asociacion implements VOAsociacion{

	private long id;
	
	private float valor;
	
	private String frecuencia;
	
	private int cuentaCorporativo;

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

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public int getCuentaCorporativo() {
		return cuentaCorporativo;
	}

	public void setCuentaCorporativo(int cuentaCorporativo) {
		this.cuentaCorporativo = cuentaCorporativo;
	}

	public Asociacion() {
		this.id = 0;
		this.valor = 0;
		this.frecuencia = "";
		this.cuentaCorporativo = 0;
	}
	
	public Asociacion(long id, float valor, String frecuencia,  int cuentaCorporativo) {
	
		this.id = id;
		this.valor = valor;
		this.frecuencia = frecuencia;
		this.cuentaCorporativo = cuentaCorporativo;
	}

	@Override
	public String toString() {
		return "Asociacion [id=" + id + ", valor=" + valor + ", frecuencia=" + frecuencia + ", cuentaCorporativo="
				+ cuentaCorporativo + "]";
	}
	
}
