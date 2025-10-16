package ui.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ui.Window;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;
	
	private JLabel    nameLabel = null;
	private JTextArea nameField = null;
	private JLabel    pwLabel   = null;
	private JTextArea pwField   = null;


	public LoginPanel(Window window) {
		this.window = window;
		
		this.builder();
	}
	
	private void builder() {
		this.nameLabel = new JLabel("Nombre:");
		this.nameField = new JTextArea();
		this.pwLabel   = new JLabel("Contraseña:");
		this.pwField   = new JTextArea();

		this.add(nameField);
		this.add(pwField);
	}
}
