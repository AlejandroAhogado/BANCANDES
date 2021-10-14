package uniandes.isis2304.bancAndes.negocio;

public class GerenteDeOficina implements VOGerenteDeOficina{

	//El tipo de documento de un GerenteGeneral puede ser PEP, CEDULA_CIUDADANIA, CEDULA_EXTRANJERIA
		private String tipoDocumento;
		
		private int numeroDocumento;
		
		private String departamento;
		
		private int codigopostal;
		
		private String nacionalidad;
		
		private String nombre;
		
		private String direccion;
		
		private String login;
		
		private String contrasena;
		
		private String correo;
		
		private int telefono;
		
		private String ciudad;
		
		//TRUE O FALSE para indicar si es administrador
		private String administrador;

		
		
		//Constructor por defecto
		public GerenteDeOficina() {
			this.tipoDocumento = "";
			this.numeroDocumento = 0;
			this.departamento ="";
			this.codigopostal = 0;
			this.nacionalidad = "";
			this.nombre = "";
			this.direccion = "";
			this.login = "";
			this.contrasena = "";
			this.correo="@";
			this.telefono = 0;
			this.ciudad = "";
			this.administrador = "FALSE";
			
		}
		//Constructor usando campos
		public GerenteDeOficina(String tipoDocumento, int numeroDocumento, String departamento, int codigopostal,
				String nacionalidad, String nombre, String direccion, String login, String contrasena, String correo,
				int telefono, String ciudad, String administrador) {
			super();
			this.tipoDocumento = tipoDocumento;
			this.numeroDocumento = numeroDocumento;
			this.departamento = departamento;
			this.codigopostal = codigopostal;
			this.nacionalidad = nacionalidad;
			this.nombre = nombre;
			this.direccion = direccion;
			this.login = login;
			this.contrasena = contrasena;
			this.correo = correo;
			this.telefono = telefono;
			this.ciudad = ciudad;
			this.administrador = administrador;
		}
		public String getTipoDocumento() {
			return tipoDocumento;
		}
		public void setTipoDocumento(String tipoDocumento) {
			this.tipoDocumento = tipoDocumento;
		}
		public int getNumeroDocumento() {
			return numeroDocumento;
		}
		public void setNumeroDocumento(int numeroDocumento) {
			this.numeroDocumento = numeroDocumento;
		}
		public String getDepartamento() {
			return departamento;
		}
		public void setDepartamento(String departamento) {
			this.departamento = departamento;
		}
		public int getCodigopostal() {
			return codigopostal;
		}
		public void setCodigopostal(int codigopostal) {
			this.codigopostal = codigopostal;
		}
		public String getNacionalidad() {
			return nacionalidad;
		}
		public void setNacionalidad(String nacionalidad) {
			this.nacionalidad = nacionalidad;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getDireccion() {
			return direccion;
		}
		public void setDireccion(String direccion) {
			this.direccion = direccion;
		}
		public String getLogin() {
			return login;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public String getContrasena() {
			return contrasena;
		}
		public void setContrasena(String contrasena) {
			this.contrasena = contrasena;
		}
		public String getCorreo() {
			return correo;
		}
		public void setCorreo(String correo) {
			this.correo = correo;
		}
		public int getTelefono() {
			return telefono;
		}
		public void setTelefono(int telefono) {
			this.telefono = telefono;
		}
		public String getCiudad() {
			return ciudad;
		}
		public void setCiudad(String ciudad) {
			this.ciudad = ciudad;
		}
		public String getAdministrador() {
			return administrador;
		}
		public void setAdministrador(String administrador) {
			this.administrador = administrador;
		}
		@Override
		public String toString() {
			return "GerenteDeOficina [tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento
					+ ", departamento=" + departamento + ", codigopostal=" + codigopostal + ", nacionalidad="
					+ nacionalidad + ", nombre=" + nombre + ", direccion=" + direccion + ", login=" + login
					+ ", contrasena=" + contrasena + ", correo=" + correo + ", telefono=" + telefono + ", ciudad="
					+ ciudad + ", administrador=" + administrador + "]";
		}
		
		
}
