package uniandes.isis2304.bancAndes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import javax.jdo.JDODataStoreException;
import javax.jdo.PersistenceManager;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.bancAndes.negocio.BancAndes;
import uniandes.isis2304.bancAndes.negocio.ClienteProducto;
import uniandes.isis2304.bancAndes.negocio.Cuenta;
import uniandes.isis2304.bancAndes.negocio.VOCajero;
import uniandes.isis2304.bancAndes.negocio.VOCajeroAutomatico;
import uniandes.isis2304.bancAndes.negocio.VOCliente;
import uniandes.isis2304.bancAndes.negocio.VOClienteProducto;
import uniandes.isis2304.bancAndes.negocio.VOCuenta;
import uniandes.isis2304.bancAndes.negocio.VOGerenteDeOficina;
import uniandes.isis2304.bancAndes.negocio.VOGerenteGeneral;
import uniandes.isis2304.bancAndes.negocio.VOOficina;
import uniandes.isis2304.bancAndes.negocio.VOOperacionBancaria;
import uniandes.isis2304.bancAndes.negocio.VOPrestamo;
import uniandes.isis2304.bancAndes.negocio.VOProducto;
import uniandes.isis2304.bancAndes.negocio.VOPuestoAtencionOficina;
import uniandes.isis2304.bancAndes.negocio.VOPuestoDeAtencion;
import uniandes.isis2304.bancAndes.negocio.VOPuestoDigital;
import uniandes.isis2304.bancAndes.negocio.VOUsuario;

import java.util.Date;
import java.util.List;

public class InterfazBancAndesApp extends JFrame implements ActionListener {

	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazBancAndesApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	public static final String CLIENTE = "Cliente";
	public static final String GERENTEGENERAL = "Gerente General";
	public static final String CAJERO = "Cajero";
	public static final String GERENTEDEOFICINA = "Gerente de Oficina";

	public static final String ACTIVA = "ACTIVA";
	public static final String CERRADA = "CERRADA";
	public static final String DESACTIVADA = "DESACTIVADA";


	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Indica si el login del usuario que ingreso al sistema
	 */
	private String loginUsuarioSistema;

	/**
	 * El tipo de usuario que ingres� al sistema
	 */
	private String tipoUsuario;
	/**
	 * Indica si el usuario es administrador
	 */
	private boolean esAdmin;
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private BancAndes bancAndes;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazBancAndesApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		bancAndes = new BancAndes (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "BancAndes App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "BancAndes APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/**
	 * Define el tipo de usuario que ingres� al sistema
	 * @param tipoUsuario
	 */
	public void setUsuario(String tipoUsuario) {
		this.tipoUsuario=tipoUsuario;
	}
	/**
	 * Define si el usuario que ingresa al sistema tiene permisos de administrador
	 * @param esAdmin
	 */
	public void setEsAdmin (boolean esAdmin) {
		this.esAdmin=esAdmin;
	}
	/**
	 * Define el login del usuario que ingres� al sistema
	 * @param loginUsuarioSistema
	 */
	public void setLoginUsuarioSistema(String loginUsuarioSistema) {
		this.loginUsuarioSistema=loginUsuarioSistema;
	}


	/* ****************************************************************
	 * 							REQF1 
	 *****************************************************************/
	/**
	 * Adiciona un usuario con la información dada por el usuario
	 * Se crea una nueva tupla de usuario en la base de datos, si un usuario con ese login no existía
	 */
	public void adicionarUsuario( )
	{
		if (tipoUsuario==CLIENTE) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{
				String login = JOptionPane.showInputDialog (this, "Login del usuario a registrar", "Agregar usuario", JOptionPane.QUESTION_MESSAGE);
				boolean entro = false;
				if (login != null)
				{
					VOUsuario u = bancAndes.adicionarUsuario (login);
					if (u==null)
					{
						throw new Exception ("No se pudo crear un usuario con login: " + login);
					}
					String[] opciones = {"Cliente", "Gerente General", "Gerente de oficina", "Cajero"};
					int tipoUsuario = JOptionPane.showOptionDialog(this, "Seleccione el tipo de usuario que quiere agregar", "Seleccion tipo usuario", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
					switch(tipoUsuario)
					{		

					case 0:
						if(this.tipoUsuario==GERENTEDEOFICINA) {

							JComboBox<String> cbOpcionesDocumento = new JComboBox<String>();
							cbOpcionesDocumento.addItem("NIT");
							cbOpcionesDocumento.addItem("PEP");
							cbOpcionesDocumento.addItem("CEDULA_CIUDADANIA");
							cbOpcionesDocumento.addItem("CEDULA_EXTRANJERIA");
							cbOpcionesDocumento.addItem("TARJETA_IDENTIDAD");


							JComboBox<String> cbOpcionesTipo = new JComboBox<String>();
							cbOpcionesTipo.addItem("PERSONA_NATURAL");
							cbOpcionesTipo.addItem("PERSONA_JURIDICA");

							JTextField numeroDocumento = new JTextField();
							JTextField departamento = new JTextField();
							JTextField codigopostal = new JTextField();
							JTextField nacionalidad = new JTextField();
							JTextField nombre = new JTextField();
							JTextField direccion = new JTextField();
							JTextField contrasena = new JTextField();
							JTextField correo = new JTextField();
							JTextField telefono = new JTextField();
							JTextField ciudad = new JTextField();

							Object[] message = {
									"TipoDocumento: ", cbOpcionesDocumento,
									"NumeroDocumento:", numeroDocumento,
									"Departamento:", departamento,
									"Codigopostal:", codigopostal,
									"Nacionalidad:", nacionalidad,
									"Nombre:", nombre,
									"Direccion:", direccion,
									"Contrasena:", contrasena,
									"Correo:", correo,
									"Telefono:", telefono,
									"Ciudad:", ciudad,
									"Tipo:", cbOpcionesTipo
							};

							int option = JOptionPane.showConfirmDialog(null, message, "Datos cliente", JOptionPane.OK_CANCEL_OPTION);
							VOCliente c=null;
							if (option == JOptionPane.OK_OPTION) {

								try {
									c =  bancAndes.adicionarCliente(
											(String)cbOpcionesDocumento.getSelectedItem(), 
											Integer.parseInt(numeroDocumento.getText()),
											departamento.getText(),
											Integer.parseInt(codigopostal.getText()), 
											nacionalidad.getText(),
											nombre.getText(), 
											direccion.getText(), 
											login, 
											contrasena.getText(),
											correo.getText(), 
											Integer.parseInt(telefono.getText()), 
											ciudad.getText(), 
											(String)cbOpcionesTipo.getSelectedItem()
											);
								} catch (Exception e) {
									bancAndes.eliminarUsuario(login);
									throw new Exception ("No se pudo crear un cliente con login: " + login);
								}

								bancAndes.adicionarUsuarioTipoOperacion("CONSIGNAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("LIQUIDAR_RENDIMIENTOS", login);
								bancAndes.adicionarUsuarioTipoOperacion("PAGAR_CUOTA", login);
								bancAndes.adicionarUsuarioTipoOperacion("PAGAR_CUOTA_EXTRAORDINARIA", login);
								bancAndes.adicionarUsuarioTipoOperacion("RETIRAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("SOLICIRTAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("TRASFERIR", login);
							} 	

							if (option == JOptionPane.CANCEL_OPTION)
							{			        	    
								bancAndes.eliminarUsuario(login);			        	    			
							}
						}
						else {entro = true;
							mensajeErrorPermisos();
						bancAndes.eliminarUsuario(login);
						panelDatos.actualizarInterfaz("Se elimino el usuario " + login);}

					case 1:

						if(this.esAdmin) {
							JComboBox<String> cbOpcionesDocumentoGG = new JComboBox<String>();

							cbOpcionesDocumentoGG.addItem("PEP");
							cbOpcionesDocumentoGG.addItem("CEDULA_CIUDADANIA");
							cbOpcionesDocumentoGG.addItem("CEDULA_EXTRANJERIA");


							JComboBox<String> cbOpcionesAdminGG = new JComboBox<String>();
							cbOpcionesAdminGG.addItem("TRUE");
							cbOpcionesAdminGG.addItem("FALSE");

							JTextField numeroDocumentoGG = new JTextField();
							JTextField departamentoGG = new JTextField();
							JTextField codigopostalGG = new JTextField();
							JTextField nacionalidadGG = new JTextField();
							JTextField nombreGG = new JTextField();
							JTextField direccionGG = new JTextField();
							JTextField contrasenaGG = new JTextField();
							JTextField correoGG = new JTextField();
							JTextField telefonoGG = new JTextField();
							JTextField ciudadGG = new JTextField();
							JTextField oficinaGG = new JTextField();

							Object[] messageGG = {
									"TipoDocumento: ", cbOpcionesDocumentoGG,
									"NumeroDocumento:", numeroDocumentoGG,
									"Departamento:", departamentoGG,
									"Codigopostal:", codigopostalGG,
									"Nacionalidad:", nacionalidadGG,
									"Nombre:", nombreGG,
									"Direccion:", direccionGG,
									"Contrasena:", contrasenaGG,
									"Correo:", correoGG,
									"Telefono:", telefonoGG,
									"Ciudad:", ciudadGG,
									"Administrador:", cbOpcionesAdminGG,
									"Oficina:", oficinaGG
							};

							int optionGG = JOptionPane.showConfirmDialog(null, messageGG, "Datos gerente general", JOptionPane.OK_CANCEL_OPTION);
							VOGerenteGeneral gg=null;
							if (optionGG == JOptionPane.OK_OPTION) {

								try {
									gg =  bancAndes.adicionarGerenteGeneral(
											(String)cbOpcionesDocumentoGG.getSelectedItem(), 
											Integer.parseInt(numeroDocumentoGG.getText()),
											departamentoGG.getText(),
											Integer.parseInt(codigopostalGG.getText()), 
											nacionalidadGG.getText(),
											nombreGG.getText(), 
											direccionGG.getText(), 
											login, 
											contrasenaGG.getText(),
											correoGG.getText(), 
											Integer.parseInt(telefonoGG.getText()), 
											ciudadGG.getText(), 
											(String)cbOpcionesAdminGG.getSelectedItem(),
											(long)Integer.parseInt(oficinaGG.getText())
											);
								} catch (Exception e) {
									bancAndes.eliminarUsuario(login);
									throw new Exception ("No se pudo crear un gerente general con login: " + login);
								}
								bancAndes.adicionarUsuarioTipoOperacion("ACTIVAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("APROBAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("CERRAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("DESACTIVAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("RECHAZAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("RENOVAR", login);
							} 	

							if (optionGG == JOptionPane.CANCEL_OPTION)
							{
								bancAndes.eliminarUsuario(login);				                		
							}
						}
						else {
							entro = true;
							mensajeErrorPermisos();
						bancAndes.eliminarUsuario(login);
						panelDatos.actualizarInterfaz("Se elimino el usuario " + login);}

					case 2:
						if(this.esAdmin) {
							JComboBox<String> cbOpcionesDocumentoGO = new JComboBox<String>();

							cbOpcionesDocumentoGO.addItem("PEP");
							cbOpcionesDocumentoGO.addItem("CEDULA_CIUDADANIA");
							cbOpcionesDocumentoGO.addItem("CEDULA_EXTRANJERIA");


							JComboBox<String> cbOpcionesAdminGO = new JComboBox<String>();
							cbOpcionesAdminGO.addItem("TRUE");
							cbOpcionesAdminGO.addItem("FALSE");

							JTextField numeroDocumentoGO = new JTextField();
							JTextField departamentoGO = new JTextField();
							JTextField codigopostalGO = new JTextField();
							JTextField nacionalidadGO = new JTextField();
							JTextField nombreGO = new JTextField();
							JTextField direccionGO = new JTextField();
							JTextField contrasenaGO = new JTextField();
							JTextField correoGO = new JTextField();
							JTextField telefonoGO = new JTextField();
							JTextField ciudadGO = new JTextField();


							Object[] messageGO = {
									"TipoDocumento: ", cbOpcionesDocumentoGO,
									"NumeroDocumento:", numeroDocumentoGO,
									"Departamento:", departamentoGO,
									"Codigopostal:", codigopostalGO,
									"Nacionalidad:", nacionalidadGO,
									"Nombre:", nombreGO,
									"Direccion:", direccionGO,
									"Contrasena:", contrasenaGO,
									"Correo:", correoGO,
									"Telefono:", telefonoGO,
									"Ciudad:", ciudadGO,
									"Administrador:", cbOpcionesAdminGO

							};

							int optionGO = JOptionPane.showConfirmDialog(null, messageGO, "Datos gerente oficina", JOptionPane.OK_CANCEL_OPTION);
							VOGerenteDeOficina go=null;
							if (optionGO == JOptionPane.OK_OPTION) {


								try {
									go =  bancAndes.adicionarGerenteDeOficina(
											(String)cbOpcionesDocumentoGO.getSelectedItem(), 
											Integer.parseInt(numeroDocumentoGO.getText()),
											departamentoGO.getText(),
											Integer.parseInt(codigopostalGO.getText()), 
											nacionalidadGO.getText(),
											nombreGO.getText(), 
											direccionGO.getText(), 
											login, 
											contrasenaGO.getText(),
											correoGO.getText(), 
											Integer.parseInt(telefonoGO.getText()), 
											ciudadGO.getText(), 
											(String)cbOpcionesAdminGO.getSelectedItem()			        	    				
											);
								} catch (Exception e) {
									bancAndes.eliminarUsuario(login);
									throw new Exception ("No se pudo crear un gerente de oficina con login: " + login);
								}       

								bancAndes.adicionarUsuarioTipoOperacion("ACTIVAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("APROBAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("CERRAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("DESACTIVAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("RECHAZAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("RENOVAR", login);

							} 	

							if (optionGO == JOptionPane.CANCEL_OPTION)
							{
								bancAndes.eliminarUsuario(login);			            
							}
						}
						else {
							entro = true;
							mensajeErrorPermisos();
						bancAndes.eliminarUsuario(login);
						panelDatos.actualizarInterfaz("Se elimino el usuario " + login);}
					case 3:
						if(this.esAdmin) {
							JComboBox<String> cbOpcionesDocumentoC = new JComboBox<String>();

							cbOpcionesDocumentoC.addItem("PEP");
							cbOpcionesDocumentoC.addItem("CEDULA_CIUDADANIA");
							cbOpcionesDocumentoC.addItem("CEDULA_EXTRANJERIA");


							JComboBox<String> cbOpcionesAdminC = new JComboBox<String>();
							cbOpcionesAdminC.addItem("TRUE");
							cbOpcionesAdminC.addItem("FALSE");

							JTextField numeroDocumentoC = new JTextField();
							JTextField departamentoC = new JTextField();
							JTextField codigopostalC = new JTextField();
							JTextField nacionalidadC = new JTextField();
							JTextField nombreC = new JTextField();
							JTextField direccionC = new JTextField();
							JTextField contrasenaC = new JTextField();
							JTextField correoC = new JTextField();
							JTextField telefonoC = new JTextField();
							JTextField ciudadC = new JTextField();
							JTextField puestoAtencionOficinaC = new JTextField();
							JTextField oficinaC = new JTextField();

							Object[] messageC = {
									"TipoDocumento: ", cbOpcionesDocumentoC,
									"NumeroDocumento:", numeroDocumentoC,
									"Departamento:", departamentoC,
									"Codigopostal:", codigopostalC,
									"Nacionalidad:", nacionalidadC,
									"Nombre:", nombreC,
									"Direccion:", direccionC,
									"Contrasena:", contrasenaC,
									"Correo:", correoC,
									"Telefono:", telefonoC,
									"Ciudad:", ciudadC,
									"Administrador:", cbOpcionesAdminC,
									"PuestoAtencionOficina:", puestoAtencionOficinaC,
									"Oficina:", oficinaC
							};

							int optionC = JOptionPane.showConfirmDialog(null, messageC, "Datos cajero", JOptionPane.OK_CANCEL_OPTION);
							VOCajero cc=null;
							if (optionC == JOptionPane.OK_OPTION) {

								try { 
									cc =  bancAndes.adicionarCajero(
											(String)cbOpcionesDocumentoC.getSelectedItem(), 
											Integer.parseInt(numeroDocumentoC.getText()),
											departamentoC.getText(),
											Integer.parseInt(codigopostalC.getText()), 
											nacionalidadC.getText(),
											nombreC.getText(), 
											direccionC.getText(), 
											login, 
											contrasenaC.getText(),
											correoC.getText(), 
											Integer.parseInt(telefonoC.getText()), 
											ciudadC.getText(), 
											(String)cbOpcionesAdminC.getSelectedItem(),
											(long)Integer.parseInt(puestoAtencionOficinaC.getText()),
											(long)Integer.parseInt(oficinaC.getText())
											);
								} catch (Exception e) {
									bancAndes.eliminarUsuario(login);
									throw new Exception ("No se pudo crear un cajero con login: " + login);
								}     

								bancAndes.adicionarUsuarioTipoOperacion("CONSIGNAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("LIQUIDAR_RENDIMIENTOS", login);
								bancAndes.adicionarUsuarioTipoOperacion("PAGAR_CUOTA", login);
								bancAndes.adicionarUsuarioTipoOperacion("PAGAR_CUOTA_EXTRAORDINARIA", login);
								bancAndes.adicionarUsuarioTipoOperacion("RETIRAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("SOLICITAR", login);
								bancAndes.adicionarUsuarioTipoOperacion("TRANSFERIR", login);
							} 	

							if (optionC == JOptionPane.CANCEL_OPTION)
							{
								bancAndes.eliminarUsuario(login);
							}
						}
						else {
							entro = true;
							mensajeErrorPermisos();
						bancAndes.eliminarUsuario(login);
						panelDatos.actualizarInterfaz("Se elimino el usuario " + login);}

					}
					if (entro==false) {
						String resultado = "En agregar Usuario\n\n";
						resultado += "Usuario agregado exitosamente: " + u;
						resultado += "\n Operacion terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
				
				}
				else
				{
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	/* ****************************************************************
	 *                 REQF2 
	 *****************************************************************/
	/**
	 * Adiciona una oficina con la informacion dada por el usuario
	 * Se crea una nueva tupla de oficina en la base de datos, si un usuario con ese login no existe
	 */
	public void registrarOficina()
	{
		if (tipoUsuario==CLIENTE) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{

				if(this.esAdmin) {

					JTextField nombreOF = new JTextField();
					JTextField direccionOF = new JTextField();
					JTextField puestosPosiblesOF = new JTextField();
					JTextField gerenteLoginOF = new JTextField();

					Object[] message = {
							"Nombre de la oficina: ", nombreOF,
							"Direccion:", direccionOF,
							"Puestos posibles:", puestosPosiblesOF,
							"Gerente (login):", gerenteLoginOF

					};

					int optionOF = JOptionPane.showConfirmDialog(null, message, "Datos oficina", JOptionPane.OK_CANCEL_OPTION);
					VOOficina of=null;
					if (optionOF == JOptionPane.OK_OPTION) {

						try { 
							of = bancAndes.adicionarOficina(nombreOF.getText(),
									direccionOF.getText(),
									Integer.parseInt(puestosPosiblesOF.getText()), 
									gerenteLoginOF.getText());

							String resultado = "En agregar Oficina\n\n";
							resultado += "Oficina agregada exitosamente ";
							resultado += "\n Operacion terminada";
							panelDatos.actualizarInterfaz(resultado);

						} catch (Exception e) {
							throw new Exception ("No se pudo crear una oficina con nombre: " + nombreOF.getText());
						}     
					}            

					if (optionOF == JOptionPane.CANCEL_OPTION)
					{
						JOptionPane.showMessageDialog( this , "Oficina con el nombre: "+nombreOF.getText()+" no fue creada",
								"Operacion cancelada" , JOptionPane.ERROR_MESSAGE );          
						panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
					}
				}
				else {mensajeErrorPermisos();}                                                                  



			} 
			catch (Exception e) 
			{
				//                                      e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}
	/* ****************************************************************
	 *        						REQF3
	 *****************************************************************/
	/**
	 * Adiciona un puesto de atencion con la información dada por el usuario
	 * Se crea una nueva tupla de puesto de atencion en la base de datos, si un usuario con ese login no existìa
	 */
	public void registrarPuntoDeAtencion( )
	{
		if (tipoUsuario==CLIENTE) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{            
				VOPuestoDeAtencion upao = bancAndes.adicionarPuestoDeAtencion();
				long id = upao.getId();

				if (this.esAdmin)
				{
					String[] opciones = {"Puesto de Atencion Oficina", "Puesto Digital", "Cajero Automatico"};
					int tipoPuesto = JOptionPane.showOptionDialog(this, "Seleccione el tipo de puesto de atencion que quiere agregar", "Seleccion tipo de puesto de atencion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
					int ya = 0;
					switch(tipoPuesto)
					{                          

					case 0:
						if(ya==0) {
							ya++;
							JTextField telefonoPAO = new JTextField();
							JTextField localizacionPAO = new JTextField();
							JTextField oficinaPAO = new JTextField();

							Object[] message = {
									"Telefono: ", telefonoPAO,
									"Localizacion:", localizacionPAO,
									"Oficina:", oficinaPAO
							};

							int option = JOptionPane.showConfirmDialog(null, message, "Datos puesto de atencion oficina", JOptionPane.OK_CANCEL_OPTION);
							VOPuestoAtencionOficina pao=null;
							if (option == JOptionPane.OK_OPTION) {

								try {
									pao =  bancAndes.adicionarPuestoAtencionOficina(
											id,
											Integer.parseInt(telefonoPAO.getText()),
											localizacionPAO.getText(),
											(long)Integer.parseInt(oficinaPAO.getText())                                                                                                                                        
											);

									int i=1;
									try {//asociar al puesto a uno o mas cajeros
										boolean asociando = true;


										while (asociando) {

											String login = JOptionPane.showInputDialog (this, "Login del cajero "+i+" a asociar", "Asociar  cajero", JOptionPane.QUESTION_MESSAGE);
											if (login != null) {

												bancAndes.asociarPuestoDeAtencionOficinaCajero(id, login);
												
												String asociacion = "En asociar Cajero al Puesto de atencion\n\n";
												asociacion += "Cajero "+ i+ " asociado exitosamente: " + login;
												asociacion += "\n Operacion terminada";

												panelDatos.actualizarInterfaz(asociacion);
												i++;

												String[] ynopt = {"S�", "No"};
												int x= JOptionPane.showOptionDialog(this, "�Desea asociar otro cajero?", "Nueva asociacion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ynopt, null);
												if (x==1) {
													asociando=false;
												}
											}
											else if (i>1) {
												panelDatos.actualizarInterfaz("Se asociaron "+i+" cajeros a la cuenta");
												asociando=false;
											}
											else {	
												bancAndes.eliminarPuestoDeAtencion(id);
												panelDatos.actualizarInterfaz("No se pudo asociar el cajero con login "+login+ "con el puesto con id: " + id);
												asociando=false;
											}
										}

									}catch (Exception e) {
										if (i==1) {
											bancAndes.eliminarProducto(id);
										}
										throw new Exception ("No se pudo asociar el cajero");

									}
									
									//hasta aqui va el ciclo de asociacion
									
								} catch (Exception e) {
									bancAndes.eliminarPuestoDeAtencion(id);
									throw new Exception ("No se pudo crear un puesto de atencion con id: " + id);
								}
							}            

							if (option == JOptionPane.CANCEL_OPTION)
							{            
								//falta eliminar el puesto de atencion
								bancAndes.eliminarPuestoDeAtencion(id);                                                                                  
							}
						}

					case 1:
						if(ya==0) {
							ya++;
							JTextField telefonoPD = new JTextField();
							JTextField urlPD = new JTextField();

							JComboBox<String> cbOpcionesTipoPD = new JComboBox<String>();
							cbOpcionesTipoPD.addItem("APP");
							cbOpcionesTipoPD.addItem("PAGINA_WEB");

							Object[] messagePD = {
									"Telefono: ", telefonoPD,
									"Tipo:", cbOpcionesTipoPD,
									"Url: (En caso de ser pagina web)", urlPD
							};

							int optionPD = JOptionPane.showConfirmDialog(null, messagePD, "Datos puesto digital", JOptionPane.OK_CANCEL_OPTION);
							VOPuestoDigital pd=null;
							if (optionPD == JOptionPane.OK_OPTION) {

								try {
									pd =  bancAndes.adicionarPuestoDigital(
											id,
											Integer.parseInt(telefonoPD.getText()),
											(String)cbOpcionesTipoPD.getSelectedItem(),
											urlPD.getText()                                                                                                                                            
											);
								} catch (Exception e) {
									//falta eliminar el id que se le habia dado
									bancAndes.eliminarPuestoDeAtencion(id);
									throw new Exception ("No se pudo crear un puesto digital con id: " + id);
								}
							}            

							if (optionPD == JOptionPane.CANCEL_OPTION)
							{            
								//falta eliminar el puesto de atencion
								bancAndes.eliminarPuestoDeAtencion(id);                                                                                  
							}
						}

					case 2:
						if(ya==0) {
							ya++;
							JTextField telefonoCA = new JTextField();
							JTextField localizacionCA = new JTextField();

							Object[] messageCA = {
									"Telefono: ", telefonoCA,
									"Localizacion:", localizacionCA
							};

							int optionCA = JOptionPane.showConfirmDialog(null, messageCA, "Datos cajero automatico", JOptionPane.OK_CANCEL_OPTION);
							VOCajeroAutomatico ca=null;
							if (optionCA == JOptionPane.OK_OPTION) {

								try {
									ca =  bancAndes.adicionarCajeroAutomatico(
											id,
											Integer.parseInt(telefonoCA.getText()),
											localizacionCA.getText()                                                                                                                                    
											);
								} catch (Exception e) {
									//falta eliminar el id que se le habia dado
									bancAndes.eliminarPuestoDeAtencion(id);
									throw new Exception ("No se pudo crear un cajeor automatico con id: " + id);
								}
							}            

							if (optionCA == JOptionPane.CANCEL_OPTION)
							{            
								//falta eliminar el puesto de atencion
								bancAndes.eliminarPuestoDeAtencion(id);                                                                                  
							}                                                     

						}
					}
					String resultado = "En agregar Puesto de atencion\n\n";
					resultado += "Puesto de atencion agregado exitosamente: " + upao;
					resultado += "\n Operacion terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
				else
				{
					//error permisos por admin
					mensajeErrorPermisos();
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}
			} 
			catch (Exception e) 
			{
				//                                      e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}


	/* ****************************************************************
	 * 							REQF4
	 *****************************************************************/
	/**
	 * Adiciona una cuenta con la información dada por un gerente de oficina
	 * Se crea una nueva tupla de cuenta en la base de datos, si los datos ingresados son correctos
	 */
	public void registrarCuenta( )
	{
		if (tipoUsuario!=GERENTEDEOFICINA) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{
				VOProducto p = bancAndes.adicionarProducto();
				long id = p.getId();
				System.out.println(id);


				JComboBox<String> cbTipo = new JComboBox<String>();
				cbTipo.addItem("AHORROS");
				cbTipo.addItem("CORRIENTE");
				cbTipo.addItem("AFC");
				cbTipo.addItem("CDT");

				JTextField numCuenta = new JTextField();
				JTextField saldo = new JTextField();
				JTextField fechaVencimiento = new JTextField();
				JTextField tasaRendimiento = new JTextField();
				JTextField oficina = new JTextField();
				
				JComboBox<String> cbCorporativo= new JComboBox<String>();
				cbCorporativo.addItem("FALSE");
				cbCorporativo.addItem("TRUE");

				Object[] message = {
						"Numero cuenta: ", numCuenta,
						"Estado: ", ACTIVA,
						"Tipo de cuenta: ", cbTipo,
						"Saldo:", saldo,
						"Fecha de vencimiento (dd-mm-aaaa) :", fechaVencimiento,
						"Tasa de rendimiento:", tasaRendimiento,
						"Oficina:", oficina,
						"Corporativo: ", cbCorporativo
				};

				int option = JOptionPane.showConfirmDialog(null, message, "Informacion cuenta", JOptionPane.OK_CANCEL_OPTION);
				VOCuenta ct=null;
				if (option == JOptionPane.OK_OPTION) {
					try {
						long hoy=System.currentTimeMillis();  
						SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
						java.util.Date fv = format.parse(fechaVencimiento.getText());
						
						java.sql.Date fvp =fechaVencimiento.getText().isEmpty()? null: new java.sql.Date(fv.getTime());
						
						ct =  bancAndes.adicionarCuenta(
								id, 
								Integer.parseInt(numCuenta.getText()),
								ACTIVA, 
								(String)cbTipo.getSelectedItem(), 
								(float)Integer.parseInt(saldo.getText()),
								new java.sql.Date(hoy),
								 new java.sql.Date(fvp.getTime()),
								Integer.parseInt(tasaRendimiento.getText()),
								(long)Integer.parseInt(oficina.getText()),
								(String)cbCorporativo.getSelectedItem()

								);
						if (ct==null) {
							throw new Exception ("La cuenta no se pudo agregar");
						}
						String resultado = "En agregar Cuenta\n\n";
						resultado += "Cuenta agregada exitosamente: " + ct;
						resultado += "\n Operacion terminada";
						panelDatos.actualizarInterfaz(resultado);
						int i=1;
						try {//asociar la cuenta a uno o mas clientes
							boolean asociando = true;


							while (asociando) {

								String login = JOptionPane.showInputDialog (this, "Login del cliente "+i+" a asociar", "Asociar la cuenta a un cliente", JOptionPane.QUESTION_MESSAGE);
								if (login != null) {

									VOClienteProducto cp=bancAndes.adicionarClienteProducto(id, login);
									if (cp==null) {
										throw new Exception ("El cliente no se pudo asociar al producto");
									}
									String asociacion = "En asociar Cliente a Producto\n\n";
									asociacion += "Cliente "+ i+ " asociado exitosamente: " + cp;
									asociacion += "\n Operacion terminada";

									panelDatos.actualizarInterfaz(asociacion);
									i++;

									String[] ynopt = {"S�", "No"};
									int x= JOptionPane.showOptionDialog(this, "�Desea asociar otro cliente?", "Nueva asociacion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ynopt, null);
									if (x==1) {
										asociando=false;
									}
								}
								else if (i>1) {
									panelDatos.actualizarInterfaz("Se asociaron "+i+" clientes a la cuenta");
									asociando=false;
								}
								else {	
									bancAndes.eliminarProducto(id);	
									panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
									asociando=false;
								}
							}

						}catch (Exception e) {
							if (i==1) {
								bancAndes.eliminarProducto(id);
							}
							throw new Exception ("No se pudo asociar el cliente");

						}


					} catch (Exception e) {
						bancAndes.eliminarProducto(id);
						throw new Exception ("No se pudo crear la cuenta con id: " + id);
					}
				} 	

				else {			        	    
					bancAndes.eliminarProducto(id);		
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}


			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}


	/* ****************************************************************
	 *                             REQF5
	 *****************************************************************/
	/**
	 * Cierra la cuenta de un cliente
	 * Se crea una nueva tupla de puesto de atencion en la base de datos, si un usuario con ese login no existìa
	 */
	public void cerrarCuenta( )
	{
		if (tipoUsuario==CLIENTE||tipoUsuario==GERENTEGENERAL||tipoUsuario==CAJERO) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{  
				JTextField idCu = new JTextField();
				Object[] messageCU = {
						"id cuenta: ", idCu
				};

				int optionCU = JOptionPane.showConfirmDialog(null, messageCU, "Datos de la cuenta a cerrar", JOptionPane.OK_CANCEL_OPTION);

				if (optionCU == JOptionPane.OK_OPTION) {

					try {
						long idCuenta = (long)Integer.parseInt(idCu.getText());
						VOCuenta cuenta = bancAndes.darCuentaPorId(idCuenta);
						long idOficina =  cuenta.getOficina();
						
						if (bancAndes.darOficinaPorId(idOficina).getGerenteLogin().equals(loginUsuarioSistema)) {
							bancAndes.cerrarCuenta(idCuenta);
							
							String resultado = "En cerrar cuenta\n\n";
							resultado += "Cuenta cerrada: "+ idCuenta;
							resultado += "\n Operacion terminada";
							panelDatos.actualizarInterfaz(resultado);
							
							
						}else {
							panelDatos.actualizarInterfaz("La cuenta debe ser cerrada por el gerente de oficina de la oficina donde se abrio");
						}

					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception ("No se pudo cerrar la cuenta con id: " +idCu.getText());
					}
				}            

				else if (optionCU == JOptionPane.CANCEL_OPTION)
				{            
					panelDatos.actualizarInterfaz("No se cerro ninguna cuenta. Operacion cancelada por el usuario");                                                                                
				}

				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}


	/* ****************************************************************
	 * 							REQF6
	 *****************************************************************/
	
	
// Pendiente arreglar cuenta origen cuenta destino!
	
	
	
	/**
	 * Registra una operacion sobre una cuenta. Las operaciones validas son:
	 */
	public void registrarOperacionCuenta() {
		if (tipoUsuario==GERENTEGENERAL || 	tipoUsuario==GERENTEDEOFICINA) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{            
				String[] opciones = {"Consignar", "Retirar"};
				int tipoOperacion = JOptionPane.showOptionDialog(this, "Seleccione el tipo de operacion que desea realizar", "Seleccion tipo de operacion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);
				int ya = 0;

				String idCuenta = JOptionPane.showInputDialog (this, "Id de la cuenta", "Indicar cuenta", JOptionPane.QUESTION_MESSAGE);
				VOCuenta cuenta = bancAndes.darCuentaPorId((long) Integer.parseInt(idCuenta));
					

				if (cuenta!=null && cuenta.getEstado().equals(ACTIVA)) {
					if (!cuenta.getTipo().equals("CDT")) {
						switch(tipoOperacion)
						{                          

						case 0:
							if(ya==0) {
								ya++;

								if(tipoUsuario==CAJERO) {//estamos en puesto atencion oficina
									VOCajero cajero = bancAndes.darCajeroPorLogin(loginUsuarioSistema);

									JTextField valor = new JTextField();
									JTextField cliente = new JTextField();

									Object[] message = {
											"Valor: ", valor,
											"Login cliente:", cliente
									};

									int option = JOptionPane.showConfirmDialog(null, message, "Registro de la operacion", JOptionPane.OK_CANCEL_OPTION);
									VOOperacionBancaria ob=null;
									if (option == JOptionPane.OK_OPTION) {

										try {
											long hoy=System.currentTimeMillis();
											ob =  bancAndes.adicionarOperacionBancaria(
													(float) Integer.parseInt(valor.getText()), 
													new java.sql.Date(hoy),
													cliente.getText(), 
													(long) Integer.parseInt(idCuenta), 
													"CONSIGNAR", 
													cajero.getPuestoAtencionoficina(), 
													loginUsuarioSistema);

											bancAndes.actualizarSaldoCuenta((long) Integer.parseInt(idCuenta), (float) Integer.parseInt(valor.getText()));
										} catch (Exception e) {
											throw new Exception ("No se pudo registrar la operacion sobre la cuenta: " + idCuenta);
										}
									}            

									if (option == JOptionPane.CANCEL_OPTION)
									{          
										panelDatos.actualizarInterfaz("Operacion cancelada");                                                                                 
									}

								}
								else {//es un cliente

									JComboBox<String> cbPuesto = new JComboBox<String>();
									cbPuesto.addItem("DIGITAL");
									cbPuesto.addItem("CAJERO AUTOMATICO");

									JTextField valor = new JTextField();
									JTextField puestoAtencion = new JTextField();

									Object[] message = {
											"Valor: ", valor,
											"Tipo de puesto de atencion: ", cbPuesto,
											"Id puesto de atencion:", puestoAtencion
									};

									int option = JOptionPane.showConfirmDialog(null, message, "Registro de la operacion", JOptionPane.OK_CANCEL_OPTION);
									VOOperacionBancaria ob=null;
									if(cbPuesto.getSelectedItem().equals("DIGITAL")) {
										VOPuestoDigital pd = bancAndes.darPuestoDigitalPorId((long) Integer.parseInt(puestoAtencion.getText()));
										if (pd==null) {
											throw new Exception ("No existe el puesto digital "+puestoAtencion.getText());
										}
									}
									else {
										VOCajeroAutomatico ca = bancAndes.darCajeroAutomaticoPorId((long) Integer.parseInt(puestoAtencion.getText()));
										if (ca==null) {
											throw new Exception ("No existe el cajero automatico "+puestoAtencion.getText());
										}
									}
									if (option == JOptionPane.OK_OPTION) {

										try {
											long hoy=System.currentTimeMillis();
											ob =  bancAndes.adicionarOperacionBancaria(
													(float) Integer.parseInt(valor.getText()), 
													new java.sql.Date(hoy),
													this.loginUsuarioSistema, 
													(long) Integer.parseInt(idCuenta), 
													"CONSIGNAR", 
													(long) Integer.parseInt(puestoAtencion.getText()), 
													null);

											bancAndes.actualizarSaldoCuenta((long) Integer.parseInt(idCuenta), (float) Integer.parseInt(valor.getText()));

										} catch (Exception e) {
											throw new Exception ("No se pudo registrar la operacion sobre la cuenta: " + idCuenta);
										}
									}            

									if (option == JOptionPane.CANCEL_OPTION)
									{          
										panelDatos.actualizarInterfaz("Operacion cancelada");                                                                                 
									}
								}
							}

						case 1:
							if(ya==0) {
								ya++;

								if(tipoUsuario==CAJERO) {//estamos en puesto atencion oficina
									VOCajero cajero = bancAndes.darCajeroPorLogin(loginUsuarioSistema);

									JTextField valor = new JTextField();
									JTextField cliente = new JTextField();

									Object[] message = {
											"Valor: ", valor,
											"Login cliente:", cliente
									};

									int option = JOptionPane.showConfirmDialog(null, message, "Registro de la operacion", JOptionPane.OK_CANCEL_OPTION);
									VOOperacionBancaria ob=null;
									if (option == JOptionPane.OK_OPTION) {

										if ((!cuenta.getTipo().equals("CORRIENTE") &&  cuenta.getSaldo()>=(float) Integer.parseInt(valor.getText())) ||(cuenta.getTipo().equals("CORRIENTE")) ) 
										{
											try {
												long hoy=System.currentTimeMillis();
												ob =  bancAndes.adicionarOperacionBancaria(
														(float) Integer.parseInt(valor.getText()), 
														new java.sql.Date(hoy),
														cliente.getText(), 
														(long) Integer.parseInt(idCuenta), 
														"RETIRAR", 
														cajero.getPuestoAtencionoficina(), 
														loginUsuarioSistema);

												bancAndes.actualizarSaldoCuenta((long) Integer.parseInt(idCuenta), - (float) Integer.parseInt(valor.getText()));

											} catch (Exception e) {
												throw new Exception ("No se pudo registrar la operacion sobre la cuenta: " + idCuenta);
											}
										}else {panelDatos.actualizarInterfaz("No se pudo realizar el retiro de la cuenta "+ idCuenta+ " porque se intento realizar un sobregiro.");}

									}            

									if (option == JOptionPane.CANCEL_OPTION)
									{          
										panelDatos.actualizarInterfaz("Operacion cancelada");                                                                                 
									}

								}
								else {//es un cliente

									JComboBox<String> cbPuesto = new JComboBox<String>();
									cbPuesto.addItem("DIGITAL");
									cbPuesto.addItem("CAJERO AUTOMATICO");

									JTextField valor = new JTextField();
									JTextField puestoAtencion = new JTextField();

									Object[] message = {
											"Valor: ", valor,
											"Tipo de puesto de atencion: ", cbPuesto,
											"Id puesto de atencion:", puestoAtencion
									};

									int option = JOptionPane.showConfirmDialog(null, message, "Registro de la operacion", JOptionPane.OK_CANCEL_OPTION);
									VOOperacionBancaria ob=null;
									if(cbPuesto.getSelectedItem().equals("DIGITAL")) {
										VOPuestoDigital pd = bancAndes.darPuestoDigitalPorId((long) Integer.parseInt(puestoAtencion.getText()));
										if (pd==null) {
											throw new Exception ("No existe el puesto digital "+puestoAtencion.getText());
										}
									}
									else {
										VOCajeroAutomatico ca = bancAndes.darCajeroAutomaticoPorId((long) Integer.parseInt(puestoAtencion.getText()));
										if (ca==null) {
											throw new Exception ("No existe el cajero automatico "+puestoAtencion.getText());
										}
									}
									if (option == JOptionPane.OK_OPTION) {
										if ((!cuenta.getTipo().equals("CORRIENTE") &&  cuenta.getSaldo()>=(float) Integer.parseInt(valor.getText())) ||(cuenta.getTipo().equals("CORRIENTE")) ) 
										{
											try {
												long hoy=System.currentTimeMillis();
												ob =  bancAndes.adicionarOperacionBancaria(
														(float) Integer.parseInt(valor.getText()), 
														new java.sql.Date(hoy),
														this.loginUsuarioSistema, 
														(long) Integer.parseInt(idCuenta), 
														"RETIRAR", 
														(long) Integer.parseInt(puestoAtencion.getText()), 
														null);


												bancAndes.actualizarSaldoCuenta((long) Integer.parseInt(idCuenta), - (float) Integer.parseInt(valor.getText()));

											} catch (Exception e) {
												throw new Exception ("No se pudo registrar la operacion sobre la cuenta: " + idCuenta);
											}
										}else {panelDatos.actualizarInterfaz("No se pudo realizar el retiro de la cuenta "+ idCuenta+ " porque se intento realizar un sobregiro.");}

									}            

									if (option == JOptionPane.CANCEL_OPTION)
									{          
										panelDatos.actualizarInterfaz("Operacion cancelada");                                                                                 
									}
								}
							}


						}

						String resultado = "En registrar operacion sobre cuenta\n\n";
						resultado += "Operacion registrada exitosamente sobre cuenta: " + idCuenta;
						resultado += "\n Operacion terminada";
						panelDatos.actualizarInterfaz(resultado);
					}
					else {
						panelDatos.actualizarInterfaz("No se puede registrar operaci�n sobre el CDT "+idCuenta);
					}
				}
				else {
					panelDatos.actualizarInterfaz("La cuenta "+idCuenta+" no est� activa");
				}



			} 
			catch (Exception e) 
			{
				//                                      e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}
	/* ****************************************************************
	 * 							REQF7
	 *****************************************************************/
	/**
	 * Adiciona un prestamo con la información dada por un gerente de oficina
	 * Se crea una nueva tupla de prestamo en la base de datos, si los datos ingresados son correctos
	 */
	public void registrarPrestamo( )
	{
		if (tipoUsuario!=GERENTEDEOFICINA) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{
				VOProducto p = bancAndes.adicionarProducto();
				long id = p.getId();
				System.out.println(id);

				JTextField monto = new JTextField();
				JTextField tasaInteres = new JTextField();
				JTextField numeroCuotas = new JTextField();
				JTextField diaPago = new JTextField();
				JTextField valorCuotaMinima = new JTextField();

				Object[] message = {
						"Monto: ", monto,
						"Tasa de interes:", tasaInteres,
						"Numero de cuotas:", numeroCuotas,
						"Dia de pago:", diaPago,
						"Valor cuota minima:", valorCuotaMinima
				};

				int option = JOptionPane.showConfirmDialog(null, message, "Informacion prestamo", JOptionPane.OK_CANCEL_OPTION);
				VOPrestamo pt=null;
				if (option == JOptionPane.OK_OPTION) {
					try {
						long hoy=System.currentTimeMillis();  

						pt =  bancAndes.adicionarPrestamo(
								id, 
								(float)Integer.parseInt(monto.getText()),
								(float)Integer.parseInt(monto.getText()),
								(float)Integer.parseInt(tasaInteres.getText()),
								Integer.parseInt(numeroCuotas.getText()),
								Integer.parseInt(diaPago.getText()),
								(float)Integer.parseInt(valorCuotaMinima.getText()),
								new java.sql.Date(hoy),
								"FALSE"
								);

						String resultado = "En agregar Prestamo\n\n";
						resultado += "Prestamo agregado exitosamente: " + pt;
						resultado += "\n Operacion terminada";
						panelDatos.actualizarInterfaz(resultado);

						int i=1;
						try {//asociar la cuenta a uno o mas clientes
							boolean asociando = true;


							while (asociando) {

								String login = JOptionPane.showInputDialog (this, "Login del cliente "+i+" a asociar", "Asociar el prestamo a un cliente", JOptionPane.QUESTION_MESSAGE);
								if (login != null) {

									VOClienteProducto cp=bancAndes.adicionarClienteProducto(id, login);
									if (cp==null) {
										throw new Exception ("El cliente no se pudo asociar al producto");
									}
									String asociacion = "En asociar Cliente a Producto\n\n";
									asociacion += "Cliente "+ i+ " asociado exitosamente: " + cp;
									asociacion += "\n Operacion terminada";

									panelDatos.actualizarInterfaz(asociacion);
									i++;

									String[] ynopt = {"S�", "No"};
									int x= JOptionPane.showOptionDialog(this, "�Desea asociar otro cliente?", "Nueva asociacion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ynopt, null);
									if (x==1) {
										asociando=false;
									}
								}
								else if (i>1) {
									panelDatos.actualizarInterfaz("Se asociaron "+i+" clientes al prestamo");
									asociando=false;
								}
								else {	
									bancAndes.eliminarProducto(id);	
									panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
									asociando=false;
								}
							}

						}catch (Exception e) {
							if (i==1) {
								bancAndes.eliminarProducto(id);
							}
							throw new Exception ("No se pudo asociar el cliente");

						}
						
						//hasta aqui va el ciclo de asociacion de clientes
						
					} catch (Exception e) {
						bancAndes.eliminarProducto(id);
						throw new Exception ("No se pudo crear el prestamo con id: " + id);
					}
				} 	

				else {			        	    
					bancAndes.eliminarProducto(id);		
					panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
				}


			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	/* ****************************************************************
	 * 							REQF8
	 *****************************************************************/
	/**
	 * Registra una operacion sobre un prestamo. Las operaciones validas son:PAGAR_CUOTA y PAGAR_CUOTA_EXTRAORDINARIA
	 */
	public void registrarOperacionPrestamo() {
		if (tipoUsuario==GERENTEGENERAL || 	tipoUsuario==GERENTEDEOFICINA || tipoUsuario==CLIENTE) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{            
				String idPrestamo = JOptionPane.showInputDialog (this, "Id del prestamo", "Indicar prestamo", JOptionPane.QUESTION_MESSAGE);
				VOPrestamo prestamo = bancAndes.darPrestamoPorId((long) Integer.parseInt(idPrestamo));

				if (prestamo!=null && prestamo.getCerrado().equals("FALSE")) {
					//estamos en puesto atencion oficina
					VOCajero cajero = bancAndes.darCajeroPorLogin(loginUsuarioSistema);

					JTextField valor = new JTextField();
					JTextField cliente = new JTextField();

					Object[] message = {
							"Valor: ", valor,
							"Login cliente: ", cliente
					};

					int option = JOptionPane.showConfirmDialog(null, message, "Registro de la operacion", JOptionPane.OK_CANCEL_OPTION);
					VOOperacionBancaria ob=null;
					if (option == JOptionPane.OK_OPTION) {
						ClienteProducto cp = bancAndes.darClienteProducto((long) Integer.parseInt(idPrestamo), cliente.getText());
						if (cp!=null) {
							if ((float) Integer.parseInt(valor.getText())>=prestamo.getValorCuotaMinima()) {
								String tipoOperacion = (float) Integer.parseInt(valor.getText())>prestamo.getValorCuotaMinima()? "PAGAR_CUOTA_EXTRAORDINARIA":"PAGAR_CUOTA";
								try {
									long hoy=System.currentTimeMillis();
									ob =  bancAndes.adicionarOperacionBancaria(
											(float) Integer.parseInt(valor.getText()), 
											new java.sql.Date(hoy),
											cliente.getText(), 
											(long) Integer.parseInt(idPrestamo), 
											tipoOperacion, 
											cajero.getPuestoAtencionoficina(), 
											loginUsuarioSistema);

									bancAndes.realizarPago((long) Integer.parseInt(idPrestamo), (float) Integer.parseInt(valor.getText()));
								} catch (Exception e) {
									throw new Exception ("No se pudo registrar la operacion sobre el prestamo: " + idPrestamo);
								}
							}
							else {
								throw new Exception ("No se pudo registrar el pago sobre el prestamo: " + idPrestamo+" porque no se alcanzo la cuota minima");
							}
						}
						else {
							throw new Exception ("No se puede realizar el pago porque el cliente "+cliente.getText()+" no esta asociado al prestamo "+idPrestamo);
						}
					}            

					if (option == JOptionPane.CANCEL_OPTION)
					{          
						panelDatos.actualizarInterfaz("Operacion cancelada");                                                                                 
					}




				}
				else {
					panelDatos.actualizarInterfaz("El prestamo "+idPrestamo+" no esta abierto");
				}



			} 
			catch (Exception e) 
			{
				//                                      e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}
	/* ****************************************************************
	 *                             REQF9
	 *****************************************************************/
	/**
	 * Cierra el prestamo de un cliente
	 */
	public void cerrarPrestamo( )
	{
		if (tipoUsuario==CLIENTE||tipoUsuario==GERENTEGENERAL||tipoUsuario==CAJERO) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{  
				JTextField idPr= new JTextField();
				Object[] messagePR = {
						"id prestamo: ", idPr
				};

				int optionPR = JOptionPane.showConfirmDialog(null, messagePR, "Datos del prestamo a cerrar", JOptionPane.OK_CANCEL_OPTION);

				if (optionPR == JOptionPane.OK_OPTION) {

					try {
						long idPrestamo = (long)Integer.parseInt(idPr.getText());
						VOPrestamo prestamo = bancAndes.darPrestamoPorId(idPrestamo);

						if (prestamo.getSaldoPendiente()==0) {
							bancAndes.cerrarPrestamo(idPrestamo);
							
							String resultado = "En cerrar prestamo\n\n";
							resultado += "Prestamo cerrado: "+idPrestamo;
							resultado += "\n Operacion terminada";
							panelDatos.actualizarInterfaz(resultado);
							
						}
						else {
							panelDatos.actualizarInterfaz("El prestamo debe tener un saldo pendiente de $0");
						}

					} catch (Exception e) {
						throw new Exception ("No se pudo cerrar el prestamo con id" );
					}
					
				}            

				if (optionPR == JOptionPane.CANCEL_OPTION)
				{            
					panelDatos.actualizarInterfaz("No se cerro ningun prestamo");                                                                                
				}

				
			} 
			catch (Exception e) 
			{
				// e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}




	/* ****************************************************************
	 *                             REFC1
	 *****************************************************************/
	/**
	 * Consultar las cuentas en bancAndes
	 */
	public void consultarCuentas( )
	{

		if (tipoUsuario==CAJERO) {
			mensajeErrorPermisos();
		}
		else {
			try {
				if(this.tipoUsuario==GERENTEDEOFICINA) {

					JComboBox<String> cbOpcionesCriterio = new JComboBox<String>();
					cbOpcionesCriterio.addItem("NINGUNO");
					cbOpcionesCriterio.addItem("TIPO");
					cbOpcionesCriterio.addItem("SALDO MAYOR A");
					cbOpcionesCriterio.addItem("FECHACREACION");
					cbOpcionesCriterio.addItem("CLIENTE");

					JComboBox<String> cbOpcionesCriterio2 = new JComboBox<String>();
					cbOpcionesCriterio2.addItem("NINGUNO");
					cbOpcionesCriterio2.addItem("TIPO");
					cbOpcionesCriterio2.addItem("SALDO MENOR A");
					cbOpcionesCriterio2.addItem("FECHACREACION");
					cbOpcionesCriterio2.addItem("CLIENTE");

					JComboBox<String> cbOpcionesAgrupamiento = new JComboBox<String>();
					cbOpcionesAgrupamiento.addItem("NINGUNO");
					cbOpcionesAgrupamiento.addItem("TIPO");
					cbOpcionesAgrupamiento.addItem("FECHACREACION");
					cbOpcionesAgrupamiento.addItem("CLIENTE");

					JComboBox<String> cbOpcionesOrdenamiento = new JComboBox<String>();
					cbOpcionesOrdenamiento.addItem("TIPO");
					cbOpcionesOrdenamiento.addItem("SALDO");
					cbOpcionesOrdenamiento.addItem("FECHACREACION");
					cbOpcionesOrdenamiento.addItem("TASARENDIMIENTO");
					cbOpcionesOrdenamiento.addItem("ID");
					cbOpcionesOrdenamiento.addItem("NUMEROCUENTA");
					cbOpcionesOrdenamiento.addItem("ESTADO");
					cbOpcionesOrdenamiento.addItem("CLIENTE");


					JComboBox<String> cbOpcionesTipoOrdenamiento = new JComboBox<String>();
					cbOpcionesTipoOrdenamiento.addItem("ASC");
					cbOpcionesTipoOrdenamiento.addItem("DESC");

					JTextField filtro1 = new JTextField();
					JTextField filtro2 = new JTextField();

					Object[] message = {
							"Agrupamiento: ", cbOpcionesAgrupamiento,
							"Primer Criterio:", cbOpcionesCriterio,
							"Primer Filtro:", filtro1,
							"Segundo Criterio:", cbOpcionesCriterio2,
							"Segundo Filtro:", filtro2,
							"Ordenamiento:", cbOpcionesOrdenamiento,
							"Tipo Ordenamiento:", cbOpcionesTipoOrdenamiento

					};

					int option = JOptionPane.showConfirmDialog(null, message, "Consulta de cuentas", JOptionPane.OK_CANCEL_OPTION);

					if (option == JOptionPane.OK_OPTION) {
						List<Object []> lvo = null;

						VOOficina voof = bancAndes.darOficinaPorGerenteDeOficina(loginUsuarioSistema);

						String signo1 = cbOpcionesCriterio.getSelectedItem().equals("SALDO MAYOR A") ? ">" : "=";
						String signo2 = cbOpcionesCriterio2.getSelectedItem().equals("SALDO MENOR A") ? "<" : "=";

						String criterio1p = cbOpcionesCriterio.getSelectedItem().equals("NINGUNO") ? "oficina" : (String) cbOpcionesCriterio.getSelectedItem();
						String criterio2p = cbOpcionesCriterio2.getSelectedItem().equals("NINGUNO") ? "oficina" : (String) cbOpcionesCriterio2.getSelectedItem();

						String filtro1p = cbOpcionesCriterio.getSelectedItem().equals("NINGUNO") ?  String.valueOf(voof.getId()) : (String) filtro1.getText();
						String filtro2p = cbOpcionesCriterio2.getSelectedItem().equals("NINGUNO") ? String.valueOf(voof.getId()) : (String) filtro2.getText();



						if (cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasGerenteOficina(
										String.valueOf(voof.getId()), 
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String) cbOpcionesOrdenamiento.getSelectedItem(),
										(String) cbOpcionesTipoOrdenamiento.getSelectedItem()
										);
							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}
						}

						if (!cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasGerenteOficinaAgrupamiento(
										(String)cbOpcionesAgrupamiento.getSelectedItem(),
										String.valueOf(voof.getId()),
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String)cbOpcionesOrdenamiento.getSelectedItem(),
										(String)cbOpcionesTipoOrdenamiento.getSelectedItem()
										);

							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}


						}

					} 	

					if (option == JOptionPane.CANCEL_OPTION)
					{			        	    
						panelDatos.actualizarInterfaz("Consulta cancelada");			        	    			
					}

				}

		

				else if(this.tipoUsuario==CLIENTE) {

					JComboBox<String> cbOpcionesCriterio = new JComboBox<String>();
					cbOpcionesCriterio.addItem("NINGUNO");
					cbOpcionesCriterio.addItem("TIPO");
					cbOpcionesCriterio.addItem("SALDO MAYOR A");
					cbOpcionesCriterio.addItem("FECHACREACION");

					JComboBox<String> cbOpcionesCriterio2 = new JComboBox<String>();
					cbOpcionesCriterio2.addItem("NINGUNO");
					cbOpcionesCriterio2.addItem("TIPO");
					cbOpcionesCriterio2.addItem("SALDO MENOR A");
					cbOpcionesCriterio2.addItem("FECHACREACION");
					
					JComboBox<String> cbOpcionesAgrupamiento = new JComboBox<String>();
					cbOpcionesAgrupamiento.addItem("NINGUNO");
					cbOpcionesAgrupamiento.addItem("TIPO");
					cbOpcionesAgrupamiento.addItem("FECHACREACION");

					JComboBox<String> cbOpcionesOrdenamiento = new JComboBox<String>();
					cbOpcionesOrdenamiento.addItem("TIPO");
					cbOpcionesOrdenamiento.addItem("SALDO");
					cbOpcionesOrdenamiento.addItem("FECHACREACION");
					cbOpcionesOrdenamiento.addItem("TASARENDIMIENTO");
					cbOpcionesOrdenamiento.addItem("ID");
					cbOpcionesOrdenamiento.addItem("NUMEROCUENTA");
					cbOpcionesOrdenamiento.addItem("ESTADO");


					JComboBox<String> cbOpcionesTipoOrdenamiento = new JComboBox<String>();
					cbOpcionesTipoOrdenamiento.addItem("ASC");
					cbOpcionesTipoOrdenamiento.addItem("DESC");

					JTextField filtro1 = new JTextField();
					JTextField filtro2 = new JTextField();

					Object[] message = {
							"Agrupamiento: ", cbOpcionesAgrupamiento,
							"Primer Criterio:", cbOpcionesCriterio,
							"Primer Filtro:", filtro1,
							"Segundo Criterio:", cbOpcionesCriterio2,
							"Segundo Filtro:", filtro2,
							"Ordenamiento:", cbOpcionesOrdenamiento,
							"Tipo Ordenamiento:", cbOpcionesTipoOrdenamiento

					};

					int option = JOptionPane.showConfirmDialog(null, message, "Consulta de cuentas", JOptionPane.OK_CANCEL_OPTION);

					if (option == JOptionPane.OK_OPTION) {
						List<Object []> lvo = null;


						String signo1 = cbOpcionesCriterio.getSelectedItem().equals("SALDO MAYOR A") ? ">" : "=";
						String signo2 = cbOpcionesCriterio2.getSelectedItem().equals("SALDO MENOR A") ? "<" : "=";

						String criterio1p = cbOpcionesCriterio.getSelectedItem().equals("NINGUNO") ? "cliente" : (String) cbOpcionesCriterio.getSelectedItem();
						String criterio2p = cbOpcionesCriterio2.getSelectedItem().equals("NINGUNO") ? "cliente" : (String) cbOpcionesCriterio2.getSelectedItem();

						String filtro1p = cbOpcionesCriterio.getSelectedItem().equals("NINGUNO") ?  this.loginUsuarioSistema : (String) filtro1.getText();
						String filtro2p = cbOpcionesCriterio2.getSelectedItem().equals("NINGUNO") ? this.loginUsuarioSistema  : (String) filtro2.getText();



						if (cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasCliente(
										this.loginUsuarioSistema , 
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String) cbOpcionesOrdenamiento.getSelectedItem(),
										(String) cbOpcionesTipoOrdenamiento.getSelectedItem()
										);
							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}
						}

						if (!cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasClienteAgrupamiento(
										(String)cbOpcionesAgrupamiento.getSelectedItem(),
										this.loginUsuarioSistema ,
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String)cbOpcionesOrdenamiento.getSelectedItem(),
										(String)cbOpcionesTipoOrdenamiento.getSelectedItem()
										);

							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}


						}

					} 	

					if (option == JOptionPane.CANCEL_OPTION)
					{			        	    
						panelDatos.actualizarInterfaz("Consulta cancelada");			        	    			
					}
				}







				else if(this.tipoUsuario==GERENTEGENERAL) {


					JComboBox<String> cbOpcionesCriterio = new JComboBox<String>();
					cbOpcionesCriterio.addItem("NINGUNO");
					cbOpcionesCriterio.addItem("TIPO");
					cbOpcionesCriterio.addItem("SALDO MAYOR A");
					cbOpcionesCriterio.addItem("FECHACREACION");
					cbOpcionesCriterio.addItem("OFICINA");
					cbOpcionesCriterio.addItem("CLIENTE");

					JComboBox<String> cbOpcionesCriterio2 = new JComboBox<String>();
					cbOpcionesCriterio2.addItem("NINGUNO");
					cbOpcionesCriterio2.addItem("TIPO");
					cbOpcionesCriterio2.addItem("SALDO MENOR A");
					cbOpcionesCriterio2.addItem("FECHACREACION");
					cbOpcionesCriterio.addItem("OFICINA");
					cbOpcionesCriterio.addItem("CLIENTE");

					JComboBox<String> cbOpcionesAgrupamiento = new JComboBox<String>();
					cbOpcionesAgrupamiento.addItem("NINGUNO");
					cbOpcionesAgrupamiento.addItem("TIPO");
					cbOpcionesAgrupamiento.addItem("FECHACREACION");
					cbOpcionesCriterio.addItem("OFICINA");
					cbOpcionesAgrupamiento.addItem("CLIENTE");

					JComboBox<String> cbOpcionesOrdenamiento = new JComboBox<String>();
					cbOpcionesOrdenamiento.addItem("TIPO");
					cbOpcionesOrdenamiento.addItem("SALDO");
					cbOpcionesOrdenamiento.addItem("FECHACREACION");
					cbOpcionesOrdenamiento.addItem("TASARENDIMIENTO");
					cbOpcionesOrdenamiento.addItem("ID");
					cbOpcionesOrdenamiento.addItem("NUMEROCUENTA");
					cbOpcionesOrdenamiento.addItem("ESTADO");
					cbOpcionesOrdenamiento.addItem("CLIENTE");


					JComboBox<String> cbOpcionesTipoOrdenamiento = new JComboBox<String>();
					cbOpcionesTipoOrdenamiento.addItem("ASC");
					cbOpcionesTipoOrdenamiento.addItem("DESC");

					JTextField filtro1 = new JTextField();
					JTextField filtro2 = new JTextField();

					Object[] message = {
							"Agrupamiento: ", cbOpcionesAgrupamiento,
							"Primer Criterio:", cbOpcionesCriterio,
							"Primer Filtro:", filtro1,
							"Segundo Criterio:", cbOpcionesCriterio2,
							"Segundo Filtro:", filtro2,
							"Ordenamiento:", cbOpcionesOrdenamiento,
							"Tipo Ordenamiento:", cbOpcionesTipoOrdenamiento

					};

					int option = JOptionPane.showConfirmDialog(null, message, "Consulta de cuentas", JOptionPane.OK_CANCEL_OPTION);

					if (option == JOptionPane.OK_OPTION) {
						List<Object []> lvo = null;

						String signo1 = cbOpcionesCriterio.getSelectedItem().equals("SALDO MAYOR A") ? ">" : "=";
						String signo2 = cbOpcionesCriterio2.getSelectedItem().equals("SALDO MENOR A") ? "<" : "=";

						String criterio1p;
						String criterio2p; 
						String filtro1p;
						String filtro2p;
						
						//si no hay primer criterio de filtro
						if (cbOpcionesCriterio.getSelectedItem().equals("NINGUNO")) {
							criterio1p = "id";
							signo1= ">";
							filtro1p = "0";
						}
						else {
							criterio1p= (String) cbOpcionesCriterio.getSelectedItem();
							filtro1p=(String) filtro1.getText();
						}

						//si no hay segundo criterio de filtro
						if (cbOpcionesCriterio2.getSelectedItem().equals("NINGUNO")) {
							criterio2p = "id";
							signo2= ">";
							filtro2p = "0";
						}
						else {
							criterio2p= (String) cbOpcionesCriterio2.getSelectedItem();
							filtro2p=(String) filtro2.getText();
						}

						if (cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasGerenteGeneral(
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String) cbOpcionesOrdenamiento.getSelectedItem(),
										(String) cbOpcionesTipoOrdenamiento.getSelectedItem()
										);
							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}
						}

						if (!cbOpcionesAgrupamiento.getSelectedItem().equals("NINGUNO")) {

							try {
								lvo = bancAndes.consultarCuentasGerenteGeneralAgrupamiento(
										(String)cbOpcionesAgrupamiento.getSelectedItem(),
										criterio1p,
										signo1, 
										filtro1p,
										criterio2p,
										signo2,
										filtro2p,
										(String)cbOpcionesOrdenamiento.getSelectedItem(),
										(String)cbOpcionesTipoOrdenamiento.getSelectedItem()
										);

							} catch (Exception e) {
								throw new Exception ("Error en la consulta");
							}


						}

					} 	

					if (option == JOptionPane.CANCEL_OPTION)
					{			        	    
						panelDatos.actualizarInterfaz("Consulta cancelada");			        	    			
					}
				}

			}
			catch (Exception e) 
			{
				//                                      e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}}

	}

	/* ****************************************************************
	 *                             REFC2
	 *****************************************************************/
	/**
	 * Consultar un cliente
	 */
	public void consultarCliente() {
		
	}
	
	/* ****************************************************************
	 *                             REFC3
	 *****************************************************************/
	/**
	 * Consultar las 10 operaciones de mayor movimiento en el sistema en un rango de fechas
	 */
	public void consultar10Operaciones() {
		if (tipoUsuario==CLIENTE||tipoUsuario==CAJERO) {
			mensajeErrorPermisos();
		}
		else {
			try 
			{  
				if (tipoUsuario==GERENTEGENERAL) {
					
					JTextField fechaInicio = new JTextField();
					JTextField fechaFin = new JTextField();

					Object[] message = {
							"Fecha de inicio (DD/MM/YY): ", fechaInicio,
							"Fecha de fin (DD/MM/YY):", fechaFin
					};
					int option = JOptionPane.showConfirmDialog(null, message, "Ingreso de rango de fechas", JOptionPane.OK_CANCEL_OPTION);

					List<Object []>top10= null;
					
					if (option == JOptionPane.OK_OPTION) {
						top10= bancAndes.consultar10OperacionesGG(fechaInicio.getText(), fechaFin.getText());
						
						String resultado = "Resultado de la consulta: ";
						int i=0;
						for (Object [] opb : top10) {
							i++;
							resultado += "\n Item "+i+": ";
							resultado+= "Tipo operacion: "+ opb[0];
							resultado+= ", Puesto atencion: "+ opb[1];
							resultado+= ", valor promedio: "+ opb[2];
							resultado+= ", Cantidad veces realizada: "+ opb[3];
						}

						resultado += "\n Consulta finalizada";
						panelDatos.actualizarInterfaz(resultado);
						
						
					}
					else {
						panelDatos.actualizarInterfaz("Consulta cancelada");
					}
				}
				else {//caso gerente de oficina
					JTextField fechaInicio = new JTextField();
					JTextField fechaFin = new JTextField();

					Object[] message = {
							"Fecha de inicio (DD/MM/YY): ", fechaInicio,
							"Fecha de fin (DD/MM/YY):", fechaFin
					};
					int option = JOptionPane.showConfirmDialog(null, message, "Ingreso de rango de fechas", JOptionPane.OK_CANCEL_OPTION);

					List<Object []>top10= null;
					
					if (option == JOptionPane.OK_OPTION) {
						VOOficina voof = bancAndes.darOficinaPorGerenteDeOficina(loginUsuarioSistema);
						
						top10= bancAndes.consultar10OperacionesGOf(fechaInicio.getText(), fechaFin.getText(), voof.getId());
						
						String resultado = "Resultado de la consulta: ";
						int i=0;
						for (Object [] opb : top10) {
							i++;
							resultado += "\n Item "+i+": ";
							resultado+= "Tipo operacion: "+ opb[0];
							resultado+= ", Puesto atencion: "+ opb[1];
							resultado+= ", valor promedio: "+ opb[2];
							resultado+= ", Cantidad veces realizada: "+ opb[3];
						}

						resultado += "\n Consulta finalizada";
						panelDatos.actualizarInterfaz(resultado);
						
						
					}
					else {
						panelDatos.actualizarInterfaz("Consulta cancelada");
					}
				}
				
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}
	/* ****************************************************************
	 *                             REFC4
	 *****************************************************************/
	/**
	 * Obtener los datos del usuario m�s activo
	 */
	public void obtenerUsuarioMasActivo() {
		if (tipoUsuario==CLIENTE||tipoUsuario==CAJERO) {
			mensajeErrorPermisos();
		}
		else {
			String[] opciones = {"Tipo de operacion", "Limite inferior del valor de la operacion"};
			int tipoFiltro = JOptionPane.showOptionDialog(this, "Seleccione el tipo de filtro que desea para buscar el usuario mas activo", "Seleccion Filtro", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, null);


			try 
			{  
				boolean entra=true;

				switch(tipoFiltro)
				{                          


				case 0://tipoFiltro: por tipo de operacion
					if(entra) {
						entra=false;
						JComboBox<String> cbTipoOperacion = new JComboBox<String>();
						cbTipoOperacion.addItem("ABRIR");
						cbTipoOperacion.addItem("CERRAR");
						cbTipoOperacion.addItem("CONSIGNAR");
						cbTipoOperacion.addItem("TRANSFERIR");
						cbTipoOperacion.addItem("RETIRAR");
						cbTipoOperacion.addItem("SOLICITAR");
						cbTipoOperacion.addItem("APROBAR");
						cbTipoOperacion.addItem("RENOVAR");
						cbTipoOperacion.addItem("RECHAZAR");
						cbTipoOperacion.addItem("DESACTIVAR");
						cbTipoOperacion.addItem("PAGAR_CUOTA");
						cbTipoOperacion.addItem("LIQUIDAR_RENDIMIENTOS");
						cbTipoOperacion.addItem("PAGAR_CUOTA_EXTRAORDINARIA");


						Object[] message = {
								"Tipo de operacion: ", cbTipoOperacion
						};
						int option = JOptionPane.showConfirmDialog(null, message, "Seleccion del tipo de operacion", JOptionPane.OK_CANCEL_OPTION);

						List<Object []>clientes= null;
						List<Object []>empleados= null;

						if (option == JOptionPane.OK_OPTION) {
							String resultado = "Resultado de la consulta: ";

							if(tipoUsuario==GERENTEGENERAL) {
								clientes= bancAndes. obtenerUsuarioMasActivoTipoOpGG("cliente", (String) cbTipoOperacion.getSelectedItem());
								empleados= bancAndes. obtenerUsuarioMasActivoTipoOpGG("empleado", (String) cbTipoOperacion.getSelectedItem());
							}
							else {//caso gerente de oficina
								VOOficina voof = bancAndes.darOficinaPorGerenteDeOficina(loginUsuarioSistema);

								clientes= bancAndes. obtenerUsuarioMasActivoTipoOpGOf("cliente", (String) cbTipoOperacion.getSelectedItem(), voof.getId());
								empleados= bancAndes. obtenerUsuarioMasActivoTipoOpGOf("empleado", (String) cbTipoOperacion.getSelectedItem(), voof.getId());
								
							}


							if(((Number) clientes.get(0)[1]).intValue()>(((Number) empleados.get(0)[1]).intValue())) {
								//Los clietes son mas activos
								
								int i=0;
								for (Object [] ca : clientes) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ca[0];
									resultado+= ", Numero de operaciones: "+ ca[1];
								}
							}
							else if(((Number) clientes.get(0)[1]).intValue()<(((Number) empleados.get(0)[1]).intValue())){
								//Los elpleados son mas activos
								int i=0;
								for (Object [] ea : empleados) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ea[0];
									resultado+= ", Numero de operaciones: "+ ea[1];
								}
							}
							else {
								//ambos son iguales de activos
								int i=0;
								for (Object [] ca : clientes) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ca[0];
									resultado+= ", Numero de operaciones: "+ ca[1];
								}
								for (Object [] ea : empleados) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ea[0];
									resultado+= ", Numero de operaciones: "+ ea[1];
								}
							}

							resultado += "\n Consulta finalizada";
							panelDatos.actualizarInterfaz(resultado);

						}

						else { //JOptionPane->cancel option
							panelDatos.actualizarInterfaz("Consulta cancelada");
						}


					}
				case 1://filtro por valores
					if(entra) {
						entra=false;
						JTextField valor = new JTextField();


						Object[] messageV = {
								"Operaciones con valores mayores a: ", valor
						};
						int optionV = JOptionPane.showConfirmDialog(null, messageV, "Ingresar el valor", JOptionPane.OK_CANCEL_OPTION);

						List<Object []>clientesV= null;
						List<Object []>empleadosV= null;

						if (optionV == JOptionPane.OK_OPTION) {
							String resultado = "Resultado de la consulta: ";

							if(tipoUsuario==GERENTEGENERAL) {
								clientesV= bancAndes. obtenerUsuarioMasActivoValorGG("cliente", (long) Integer.parseInt(valor.getText()));
								empleadosV= bancAndes. obtenerUsuarioMasActivoValorGG("empleado", (long) Integer.parseInt(valor.getText()));
							}
							else {//caso gerente de oficina
								VOOficina voof = bancAndes.darOficinaPorGerenteDeOficina(loginUsuarioSistema);

								clientesV= bancAndes. obtenerUsuarioMasActivoValorGOf("cliente", (long) Integer.parseInt(valor.getText()), voof.getId());
								empleadosV= bancAndes. obtenerUsuarioMasActivoValorGOf("empleado", (long) Integer.parseInt(valor.getText()), voof.getId());
							}

							if(((Number) clientesV.get(0)[1]).intValue()>(((Number) empleadosV.get(0)[1]).intValue())) {
								//Los clietes son mas activos
								int i=0;
								for (Object [] ca : clientesV) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ca[0];
									resultado+= ", Numero de operaciones: "+ ca[1];
								}
							}
							else if(((Number) clientesV.get(0)[1]).intValue()<(((Number) empleadosV.get(0)[1]).intValue())) {
								//Los elpleados son mas activos
								int i=0;
								for (Object [] ea : empleadosV) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ea[0];
									resultado+= ", Numero de operaciones: "+ ea[1];
								}
							}
							else {
								//ambos son iguales de activos
								int i=0;
								for (Object [] ca : clientesV) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ca[0];
									resultado+= ", Numero de operaciones: "+ ca[1];
								}
								for (Object [] ea : empleadosV) {
									i++;
									resultado += "\n Item "+i+": ";
									resultado+= "Login usuario: "+ ea[0];
									resultado+= ", Numero de operaciones: "+ ea[1];
								}
							}

							resultado += "\n Consulta finalizada";
							panelDatos.actualizarInterfaz(resultado);

						}

						else { //JOptionPane->cancel option
							panelDatos.actualizarInterfaz("Consulta cancelada");
						}


					}

				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}
	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecucion\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y bancAndes.log para más detalles";
		return resultado;
	}

	/**
	 * Mensaje de error desplegado cuando el usuario no tiene permisos para hacer la operaci�n
	 */
	public void mensajeErrorPermisos() {
		JOptionPane.showMessageDialog( this , "Esta cuenta no tiene permisos para realizar esta operaci�n",
				"Error en la operaci�n" , JOptionPane.ERROR_MESSAGE );
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void acercaDe() {
		panelDatos.actualizarInterfaz("Trabajo realizado por: /n Laura Quiroga - 201922965 y Alejandro Ahogado Prieto - 201920701");
	}



	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazBancAndesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazBancAndesApp interfaz = new InterfazBancAndesApp( );
			new VentanaInicio(interfaz.bancAndes, interfaz);
			
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}


}
