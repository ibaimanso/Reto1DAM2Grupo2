package ui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ui.Window;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;
	
	private JLabel         nameLabel      = null;
	private JTextField     nameField      = null;
	private JLabel         pwLabel        = null;
	private JPasswordField pwField        = null;
	private JButton        registerButton = null;
	private JButton        loginButton    = null;


	public LoginPanel(Window window) {
		this.window = window;
		
		this.builder();
	}
	
	private void builder() {
		this.setLayout(new GridLayout(3, 2));

		this.nameLabel = new JLabel("Nombre:");
		this.add(this.nameLabel);

		this.nameField = new JTextField();
		this.add(this.nameField);

		this.pwLabel   = new JLabel("Contraseña:");
		this.add(this.pwLabel);

		this.pwField   = new JPasswordField();
		this.add(this.pwField);
		
		this.registerButton = new JButton("Registrarse");
		this.registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		this.add(this.registerButton);

		this.loginButton = new JButton("Login");
		this.loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		this.add(this.loginButton);
	}
	
	private void register() {
		window.showPanel(Window.REGISTER_PANEL);
	}
	
	private void login() {
			window.showPanel(Window.WORKOUT_PANEL);
	}
}
