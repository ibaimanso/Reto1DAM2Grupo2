package ui.panels;

import javax.swing.JPanel;

import ui.Window;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controllers.ControllerFactory;
import entities.Exercise;
import entities.Serie;
import entities.Workout;
import firebase.exceptions.DBException;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WorkoutPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private Window window = null;
    
    private JButton         btnPerfil             = null;
    private JButton         btnSeleccionarWorkout = null;
    private JButton         btnHistorial          = null;
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
        
        
        btnHistorial = new JButton("Historial");
        btnHistorial.setBounds(480, 33, 100, 23);
        btnHistorial.addActionListener(e -> {
            window.getHistoryPanel().loadHistory();
            window.showPanel(Window.HISTORY_PANEL);
        });
        add(btnHistorial);
        
        lblWorkouts = new JLabel("Listado de Workouts");
        lblWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
        lblWorkouts.setBounds(70, 70, 120, 14);
        add(lblWorkouts);
        
        lblExercises = new JLabel("Listado de ejercicios");
        lblExercises.setHorizontalAlignment(SwingConstants.CENTER);
        lblExercises.setBounds(340, 70, 120, 14);
        add(lblExercises);
        
        workoutList = new JList<>();
        workoutList.setBounds(30, 100, 200, 250);
        workoutList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    updateExercises();
                } catch (DBException ex) {
                    JOptionPane.showMessageDialog(
                        this, 
                        "No se ha podido acceder a los ejercicios",
                        "Error de ejercicios",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        workoutList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && workoutList.getSelectedValue() != null) {
                    openSelectedWorkout();
                }
            }
        });
        add(workoutList);
        
        
        exerciseList = new JList<>();
        exerciseList.setBounds(300, 100, 200, 250);
        add(exerciseList);
        
        btnSeleccionarWorkout = new JButton("Seleccionar");
        btnSeleccionarWorkout.setBounds(40, 381, 168, 23);
        btnSeleccionarWorkout.addActionListener(e -> openSelectedWorkout());
        add(btnSeleccionarWorkout);
        
    }
    
    private void openSelectedWorkout() {
        Workout selectedWorkout = workoutList.getSelectedValue();
        if (selectedWorkout == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un workout primero", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            List<Exercise> exercises = ControllerFactory.getInstance()
                .getExerciseController()
                .getExercisesByWorkout(selectedWorkout);
            Map<Integer, List<Serie>> seriesByExercise = new HashMap<>();
            for (Exercise ex : exercises) {
                List<Serie> series = ControllerFactory.getInstance()
                    .getSerieController()
                    .getSeriesByExercise(ex);
                seriesByExercise.put(ex.getId(), series);
            }
            window.getExercisePanel().setWorkoutData(selectedWorkout, exercises, seriesByExercise);
            window.showPanel(Window.EXERCISE_PANEL);
        } catch (DBException ex) {
            JOptionPane.showMessageDialog(this, "No se han podido cargar los datos del workout", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        workoutListModel.clear();
        exerciseListModel.clear();
        
        try {
            List<Workout> workouts = ControllerFactory.getInstance().getWorkoutController().getWorkoutsByLevel(this.window.getUserLogin());
            
            for (Workout workout : workouts) {
                workoutListModel.addElement(workout);
            }
            
            workoutList.setModel(workoutListModel);
            exerciseList.setModel(exerciseListModel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this, 
                "No se han podido cargar los workouts: " + ex.getMessage(),
                "Error de carga",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}