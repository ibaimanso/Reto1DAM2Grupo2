package ui.panels;

import javax.swing.JPanel;

import ui.Window;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import controllers.ControllerFactory;
import controllers.exceptions.NameIsAlreadyUsedException;
import controllers.exceptions.PKException;
import entities.User;
import firebase.ManagerFactory;
import firebase.ManagerInterface;
import firebase.exceptions.DBException;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ResourceBundle.Control;

import javax.swing.JButton;

public class RegisterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Window window = null;
	
	private JButton    btnContinuar          = null;
	private JButton    btnCancelar           = null;
	private JLabel     lblRegistro           = null;
	private JLabel     lblFName              = null;
	private JLabel     lblLName              = null;
	private JLabel     lblEmail              = null;
	private JLabel     lblPassword           = null;
	private JLabel     lblBirth              = null;
	private JTextField textFieldFName        = null;
	private JTextField textFieldLName        = null;
	private JTextField textFieldEmail        = null;
	private JTextField textFieldPassword     = null;
	private JTextField textFieldBirth        = null;
	
	public RegisterPanel(Window window) {
		this.window = window;
		this.setVisible(true);
		this.setSize(600, 500);
		setLayout(null);
		
		lblRegistro = new JLabel("REGISTRO");
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistro.setBounds(235, 83, 100, 47);
		add(lblRegistro);
		
		lblFName = new JLabel("Nombre:");
		lblFName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFName.setBounds(109, 135, 100, 33);
		add(lblFName);
		
		lblLName = new JLabel("Apellidos:");
		lblLName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLName.setBounds(109, 182, 100, 33);
		add(lblLName);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(109, 226, 100, 33);
		add(lblEmail);
		
		lblPassword = new JLabel("Contraseña:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(109, 275, 100, 33);
		add(lblPassword);
		
		lblBirth = new JLabel("Fecha de Nacimiento:");
		lblBirth.setHorizontalAlignment(SwingConstants.CENTER);
		lblBirth.setBounds(72, 319, 121, 33);
		add(lblBirth);
		
		textFieldFName = new JTextField();
		textFieldFName.setBounds(202, 141, 175, 20);
		add(textFieldFName);
		textFieldFName.setColumns(10);
		
		textFieldLName = new JTextField();
		textFieldLName.setColumns(10);
		textFieldLName.setBounds(202, 188, 175, 20);
		add(textFieldLName);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(202, 232, 175, 20);
		add(textFieldEmail);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setColumns(10);
		textFieldPassword.setBounds(202, 281, 175, 20);
		add(textFieldPassword);
		
		textFieldBirth = new JTextField();
		textFieldBirth.setColumns(10);
		textFieldBirth.setBounds(202, 323, 175, 20);
		add(textFieldBirth);
		
		btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(288, 354, 89, 23);
		add(btnContinuar);
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logUp();
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(202, 354, 89, 23);
		add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.showPanel(Window.LOGIN_PANEL);
			}
		});
		
	}
	
	private void logUp() {
		LocalDateTime ldt = LocalDateTime.now();
		User user = new User(
			0,
			textFieldFName.getText(),
			textFieldLName.getText(),
			textFieldPassword.getText(),
			textFieldEmail.getText(),
			textFieldBirth.getText(),
			ldt.toString()
		);
		try {
			ControllerFactory.getInstance().getUserController().signUp(user);
			window.showPanel(Window.WORKOUT_PANEL);
		} catch (NameIsAlreadyUsedException ex) {
	        JOptionPane.showMessageDialog(
	        	this,
	        	"Este usuario ya existe",
	        	"Nombre ya en uso",
	        	JOptionPane.INFORMATION_MESSAGE
	        );
		} catch (DBException ex) {
	        JOptionPane.showMessageDialog(
	        	this,
	        	"No hay conexión a la base de datos",
	        	"No hay acceso a internet.",
	        	JOptionPane.INFORMATION_MESSAGE
	        );
		} catch (Exception ex) {
	        JOptionPane.showMessageDialog(
	        	this,
	        	"Ha ocurrido un error inesperado",
	        	"Error inesperado",
	        	JOptionPane.INFORMATION_MESSAGE
	        );
		}
	}
	
}
