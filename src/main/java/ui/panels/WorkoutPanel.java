package ui.panels;

import javax.swing.JPanel;

import ui.Window;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controllers.ControllerFactory;
import entities.Exercise;
import entities.Workout;
import firebase.exceptions.DBException;

import javax.swing.JList;
import javax.swing.JOptionPane;

public class WorkoutPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;
	
	private JButton         btnPerfil             = null;
	private JButton         btnSeleccionarWorkout = null;
	private JLabel          lblWorkouts           = null;
	private JLabel          lblExercises          = null;
	private JList<Workout>  workoutList           = null;
	private JList<Exercise> exerciseList          = null;

	private DefaultListModel<Exercise> exerciseListModel = new DefaultListModel<>();
	private DefaultListModel<Workout> workoutListModel  = new DefaultListModel<>();
	
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
		lblWorkouts.setBounds(70, 70, 120, 14);
		add(lblWorkouts);
		
		lblExercises = new JLabel("Listado de ejercicios");
		lblExercises.setHorizontalAlignment(SwingConstants.CENTER);
		lblExercises.setBounds(340, 70, 120, 14);
		add(lblExercises);
		
		workoutList = new JList();
		workoutList.setBounds(30, 100, 200, 250);
		workoutList.addListSelectionListener(e -> {
			if (e.getValueIsAdjusting()) {
				try {
					updateExercises();
				} catch (DBException ex) {
					JOptionPane.showConfirmDialog(
						this, 
						"No se ha podido acceder a los ejercicios",
						"Error de ejercicios",
						JOptionPane.INFORMATION_MESSAGE
					);
				}			
			}
		});
		add(workoutList);
		
		
		exerciseList = new JList();
		exerciseList.setBounds(300, 100, 200, 250);
		add(exerciseList);
		
		btnSeleccionarWorkout = new JButton("Seleccionar");
		btnSeleccionarWorkout.setBounds(40, 381, 168, 23);
		btnSeleccionarWorkout.addActionListener(e ->
		window.showPanel(Window.EXERCISE_PANEL)
		);
		add(btnSeleccionarWorkout);
		
	}
	
	public void updateExercises() throws DBException {
		if (workoutList.getSelectedValue() != null) {
            exerciseListModel.clear();
            Workout selectedWorkout = workoutList.getSelectedValue();
            List<Exercise> exercises = ControllerFactory.getInstance().getExerciseController().getExercisesByWorkout(selectedWorkout);            		
            for (Exercise exercise : exercises) {
                exerciseListModel.addElement(exercise);
            }
            exerciseList.setModel(exerciseListModel);
        }
	}
	
	public void loadWorkouts() {
		
		try {
			List<Workout> workouts = ControllerFactory.getInstance().getWorkoutController().getWorkoutsByLevel(this.window.getUserLogin());
			
			for (Workout workout : workouts) {
				workoutListModel.addElement(workout);
			}
			
			workoutList.setModel(workoutListModel);
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}
}
