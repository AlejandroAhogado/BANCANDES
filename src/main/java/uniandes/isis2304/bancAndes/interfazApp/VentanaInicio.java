package uniandes.isis2304.bancAndes.interfazApp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uniandes.isis2304.bancAndes.negocio.BancAndes;
import uniandes.isis2304.bancAndes.negocio.Cliente;
import uniandes.isis2304.bancAndes.negocio.VOCajero;
import uniandes.isis2304.bancAndes.negocio.VOCliente;
import uniandes.isis2304.bancAndes.negocio.VOGerenteDeOficina;
import uniandes.isis2304.bancAndes.negocio.VOGerenteGeneral;

public class VentanaInicio extends JFrame implements ActionListener{

	public static final String CLIENTE = "Cliente";
	public static final String GERENTEGENERAL = "Gerente General";
	public static final String CAJERO = "Cajero";
	public static final String GERENTEDEOFICINA = "Gerente de Oficina";
	
	 /**
     * AsociaciÃ³n a la clase principal del negocio.
     */
    private BancAndes bancAndes;
    
    private InterfazBancAndesApp interfazPrincipal;
    /**
     * Información del usuario
     */
    String[] info= new String[2];
	
	private JButton bCliente;
	private JButton bGerenteGeneral;
	private JButton bCajero;
	private JButton bGerenteDeOficina;
	
	public VentanaInicio( BancAndes bancAndes, InterfazBancAndesApp interfazPrincipal) {
		
		this.bancAndes = bancAndes;
		this.interfazPrincipal = interfazPrincipal;
		
		setTitle("Bienvenido a BancAndes");
		setSize(new Dimension(400, 300));
		setResizable(false);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
		
		JLabel titulo = new JLabel("Ingresar como: ");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(titulo);
		
		buttonPane.add(Box.createRigidArea(new Dimension(0,25)));
		
		bCliente = new JButton(CLIENTE);
		bCliente.setForeground(Color.WHITE);
		bCliente.setBackground(Color.BLUE);
		bCliente.setActionCommand(CLIENTE);
		bCliente.addActionListener(this);
		bCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(bCliente);
		
		buttonPane.add(Box.createRigidArea(new Dimension(0,25)));
		
		bGerenteGeneral = new JButton(GERENTEGENERAL);
		bGerenteGeneral.setForeground(Color.WHITE);
		bGerenteGeneral.setBackground(Color.BLUE);
		bGerenteGeneral.setActionCommand(GERENTEGENERAL);
		bGerenteGeneral.addActionListener(this);
		bGerenteGeneral.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(bGerenteGeneral);
		
		buttonPane.add(Box.createRigidArea(new Dimension(0,25)));
		
		bCajero = new JButton(CAJERO);
		bCajero.setForeground(Color.WHITE);
		bCajero.setBackground(Color.BLUE);
		bCajero.setActionCommand(CAJERO);
		bCajero.addActionListener(this);
		bCajero.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(bCajero);
		
		buttonPane.add(Box.createRigidArea(new Dimension(0,25)));
		
		bGerenteDeOficina = new JButton(GERENTEDEOFICINA);
		bGerenteDeOficina.setForeground(Color.WHITE);
		bGerenteDeOficina.setBackground(Color.BLUE);
		bGerenteDeOficina.setActionCommand(GERENTEDEOFICINA);
		bGerenteDeOficina.addActionListener(this);
		bGerenteDeOficina.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPane.add(bGerenteDeOficina);
		add(buttonPane);
		setVisible(true);
	}
	
	public void iniciarSesion() {
		JTextField login = new JTextField();
		JTextField contrasena = new JTextField();
		 
		 Object[] message = {
		     "Login: ", login,
		     "Constraseña: ", contrasena
		 };

		 int option = JOptionPane.showConfirmDialog(null, message, "Datos cliente", JOptionPane.OK_CANCEL_OPTION);
		 
		 if (option==JOptionPane.OK_OPTION) {
			 info[0] =login.getText();
			 info [1] = contrasena.getText();
		 }
	}
	public void mensajeError() {
		JOptionPane.showMessageDialog( this , "Login "+info[0]+" no válido",
				"Error de inicio de sesión" , JOptionPane.ERROR_MESSAGE );
	}
	public void mensajeErrorContrasena() {
		JOptionPane.showMessageDialog( this , "La contraseña no corresponde al nombre de usuario. Vuelva a intentarlo",
				"Error de inicio de sesión" , JOptionPane.ERROR_MESSAGE );
	}
	 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		
		if (comando.equals(CLIENTE)) {
			iniciarSesion();
			VOCliente c = bancAndes.darClientePorLogin(info[0]);
			
			if (c!=null) {
				if(c.getContrasena().trim().equals(info[1].trim())) {
				interfazPrincipal.setUsuario(CLIENTE);
				interfazPrincipal.setEsAdmin(false);
	            interfazPrincipal.setVisible( true );
				this.dispose();
				}
				else {
					mensajeErrorContrasena();
				}
			}
			else {
				mensajeError();
			}
			
		}
		else if (comando.equals(GERENTEGENERAL)) {
			iniciarSesion();
			VOGerenteGeneral gg = bancAndes.darGerenteGeneralPorLogin(info[0]);
			
			if (gg!=null) {
				if(gg.getContrasena().trim().equals(info[1].trim())) {
				interfazPrincipal.setUsuario(GERENTEGENERAL);
				boolean esAdmin = gg.getAdministrador().equals("TRUE");
				interfazPrincipal.setEsAdmin(esAdmin);
	            interfazPrincipal.setVisible( true );
				this.dispose();
				}
				else {
					mensajeErrorContrasena();
				}
			}
			else {
				mensajeError();
			}
		}
		else if (comando.equals(CAJERO)) {
			iniciarSesion();
			VOCajero cj = bancAndes.darCajeroPorLogin(info[0]);
			
			if (cj!=null) {
				if(cj.getContrasena().trim().equals(info[1].trim())) {
				interfazPrincipal.setUsuario(CAJERO);
				boolean esAdmin = cj.getAdministrador().equals("TRUE");
				interfazPrincipal.setEsAdmin(esAdmin);
	            interfazPrincipal.setVisible( true );
				this.dispose();
				}
				else {
					mensajeErrorContrasena();
				}
			}
			else {
				mensajeError();
			}
		}
		else if (comando.equals(GERENTEDEOFICINA)) {
			iniciarSesion();
			VOGerenteDeOficina go = bancAndes.darGerenteDeOficinaPorLogin(info[0]);
			
			if (go!=null) {
				if(go.getContrasena().trim().equals(info[1].trim())) {
			
				interfazPrincipal.setUsuario(GERENTEDEOFICINA);
				boolean esAdmin = go.getAdministrador().equals("TRUE");
				interfazPrincipal.setEsAdmin(esAdmin);
	            interfazPrincipal.setVisible( true );
				this.dispose();
				}
				else {
					mensajeErrorContrasena();
				}
			}
			else {
				mensajeError();
			}
		}
		
	}
		
}
