package ui.panels;

import javax.swing.JPanel;

import ui.Window;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class RegisterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Window window = null;
	private JTextField textFieldNombre;
	private JTextField textApellidos;
	private JTextField textEmail;
	private JTextField textContrasena;
	private JTextField textFechaDeNacimiento;

	public RegisterPanel(Window window) {
		this.window = window;
		this.setVisible(true);
		this.setSize(600, 500);
		setLayout(null);
		
		JLabel lblRegistro = new JLabel("REGISTRO");
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setBounds(235, 83, 100, 47);
		add(lblRegistro);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(109, 135, 100, 33);
		add(lblNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setBounds(109, 182, 100, 33);
		add(lblApellidos);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(109, 226, 100, 33);
		add(lblEmail);
		
		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setHorizontalAlignment(SwingConstants.CENTER);
		lblContrasena.setBounds(109, 275, 100, 33);
		add(lblContrasena);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setHorizontalAlignment(SwingConstants.CENTER);
		lblFechaDeNacimiento.setBounds(72, 319, 121, 33);
		add(lblFechaDeNacimiento);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(202, 141, 175, 20);
		add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textApellidos = new JTextField();
		textApellidos.setColumns(10);
		textApellidos.setBounds(202, 188, 175, 20);
		add(textApellidos);
		
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(202, 232, 175, 20);
		add(textEmail);
		
		textContrasena = new JTextField();
		textContrasena.setColumns(10);
		textContrasena.setBounds(202, 281, 175, 20);
		add(textContrasena);
		
		textFechaDeNacimiento = new JTextField();
		textFechaDeNacimiento.setColumns(10);
		textFechaDeNacimiento.setBounds(202, 323, 175, 20);
		add(textFechaDeNacimiento);
		
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(288, 354, 89, 23);
		add(btnContinuar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(202, 354, 89, 23);
		add(btnCancelar);
	}
}
