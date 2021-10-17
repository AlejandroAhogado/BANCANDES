/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(BogotÃ¡	- Colombia)
 * Departamento	de	IngenierÃ­a	de	Sistemas	y	ComputaciÃ³n
 * Licenciado	bajo	el	esquema	Academic Free License versiÃ³n 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author GermÃ¡n Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia JimÃ©nez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.bancAndes.interfazApp;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Clase de interfaz para mostrar los resultados de la ejecuciÃ³n de las 
 * operaciones realizadas por el usuario
 * @author GermÃ¡n Bravo
 */
@SuppressWarnings("serial")
public class PanelDatos extends JPanel
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------


    // -----------------------------------------------------------------
    // Atributos de interfaz
    // -----------------------------------------------------------------
	/**
	 * Ã�rea de texto con barras de deslizamiento
	 */
	private JTextArea textArea;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el panel
     * 
     */
    public PanelDatos ()
    {
        setBorder (new TitledBorder ("Panel de informacion"));
        setLayout( new BorderLayout( ) );
        
        textArea = new JTextArea("Aqui­ sale el resultado de las operaciones solicitadas");
        textArea.setEditable(false);
        add (new JScrollPane(textArea), BorderLayout.CENTER);
    }

    // -----------------------------------------------------------------
    // MÃ©todos
    // -----------------------------------------------------------------

    /**
     * Actualiza el panel con la informaciÃ³n recibida por parÃ¡metro.
     * @param texto El texto con el que actualiza el Ã¡rea
     */
    public void actualizarInterfaz (String texto)
    {
    	textArea.setText(texto);
    }

}
