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

	public WorkoutPanel(Window window) {
		this.window = window;
		this.setVisible(true);
		this.setSize(600, 500);
		setLayout(null);
		
		JButton btnPerfil = new JButton("Perfil");
		btnPerfil.setBounds(480, 33, 89, 23);
		add(btnPerfil);
		
		JLabel lblWorkouts = new JLabel("Listado de Workouts");
		lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWorkouts.setBounds(226, 68, 120, 14);
		add(lblWorkouts);
		
		JList list = new JList();
		list.setBounds(118, 107, 349, 263);
		add(list);
		
		JButton btnSeleccionarWorkout = new JButton("Seleccionar");
		btnSeleccionarWorkout.setBounds(299, 381, 168, 23);
		btnSeleccionarWorkout.addActionListener(e ->
		window.showPanel(Window.EXERCISE_PANEL)
		);
		add(btnSeleccionarWorkout);
		
	}
}
