package ui.panels;

import javax.swing.JPanel;

import ui.Window;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;

public class WorkoutPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;
	
	private JButton btnPerfil             = null;
	private JButton btnSeleccionarWorkout = null;
	private JLabel  lblWorkouts           = null;
	private JList   list                  = null;

	public WorkoutPanel(Window window) {
		this.window = window;
		this.setVisible(true);
		this.setSize(600, 500);
		setLayout(null);
		
		btnPerfil = new JButton("Perfil");
		btnPerfil.setBounds(480, 33, 89, 23);
		add(btnPerfil);
		
		lblWorkouts = new JLabel("Listado de Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setBounds(226, 68, 120, 14);
		add(lblWorkouts);
		
		list = new JList();
		list.setBounds(118, 107, 349, 263);
		add(list);
		
		btnSeleccionarWorkout = new JButton("Seleccionar");
		btnSeleccionarWorkout.setBounds(299, 381, 168, 23);
		btnSeleccionarWorkout.addActionListener(e ->
		window.showPanel(Window.EXERCISE_PANEL)
		);
		add(btnSeleccionarWorkout);
		
	}
}
