package ui.panels;

import javax.swing.JPanel;

import ui.Window;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import entities.Exercise;
import entities.Serie;
import entities.Workout;

public class ExercisePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;

	private JLabel  lblWorkoutName     = null;
	private JLabel  lblExerciseName    = null;
	private JLabel  lblDescription     = null;
	private JLabel  lblTotalTimer      = null;
	private JLabel  lblSeriesCountdown = null;
	private JPanel  seriesListPanel    = null;
	private JButton btnStartPauseNext  = null;
	private JButton btnExit            = null;

	private Timer totalTimer       = null; 
	private Timer countdownTimer   = null; 
	private int   totalSeconds     = 0;
	private int   remainingSeconds = 0;
	
	private enum State { IDLE, RUNNING, PAUSED, COMPLETED }
	private State state = State.IDLE;
	
	private List<SeriesData> series = new ArrayList<>();
	private int currentSeriesIndex = -1; 
	private boolean inRest = false;
	private int completedSeries = 0;

	// Datos de workout y ejercicios
	private Workout workout = null;
	private List<Exercise> exercises = new ArrayList<>();
	private Map<Integer, List<SeriesData>> seriesByExerciseId = new HashMap<>();
	private int currentExerciseIndex = -1;

	// Informacion de prueba que se cambiara por la informacion recogida de firebase Firebase
	public static class SeriesData {
		public String name = null;
		public int reps = 0;
		public int durationSeconds = 0;
		public int restSeconds = 0;
		public ImageIcon photo;
		
		public SeriesData(String name, int reps, int durationSeconds, int restSeconds, ImageIcon photo) {
			this.name = name;
			this.reps = reps;
			this.durationSeconds = durationSeconds;
			this.restSeconds = restSeconds;
			this.photo = photo;
		}
	}

	
	public ExercisePanel(Window window) {
		this.window = window;
		//this.setVisible(true);
		this.setSize(600, 500);
		setLayout(null);


		lblWorkoutName = new JLabel("Workout: -");
		lblWorkoutName.setBounds(10, 10, 400, 20);
		add(lblWorkoutName);

		lblExerciseName = new JLabel("Exercise: -");
		lblExerciseName.setBounds(10, 35, 400, 24);
		lblExerciseName.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblExerciseName);

		lblDescription = new JLabel("Description: -");
		lblDescription.setBounds(10, 60, 560, 20);
		add(lblDescription);

		// Cronometros
		
		lblTotalTimer = new JLabel("Total: 00:00:00");
		lblTotalTimer.setBounds(420, 10, 160, 30);
		lblTotalTimer.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTotalTimer);

		lblSeriesCountdown = new JLabel("Series: 00:00");
		lblSeriesCountdown.setBounds(420, 40, 160, 30);
		lblSeriesCountdown.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblSeriesCountdown);

		seriesListPanel = new JPanel();
		seriesListPanel.setLayout(new GridLayout(0, 1, 5, 5));
		JScrollPane scroll = new JScrollPane(seriesListPanel);
		scroll.setBounds(10, 95, 560, 300);
		add(scroll);

		btnStartPauseNext = new JButton("Start");
		btnStartPauseNext.setBounds(150, 410, 140, 35);
		btnStartPauseNext.setBackground(Color.GREEN);
		btnStartPauseNext.setOpaque(true);
		add(btnStartPauseNext);

		btnExit = new JButton("Exit");
		btnExit.setBounds(320, 410, 140, 35);
		btnExit.setBackground(Color.LIGHT_GRAY);
		btnExit.setOpaque(true);
		add(btnExit);

		// Inicialización de cronómetros
		totalTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalSeconds++;
				lblTotalTimer.setText("Total: " + formatHMS(totalSeconds));
			}
		});

		countdownTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (remainingSeconds > 0) {
					remainingSeconds--;
					lblSeriesCountdown.setText((inRest?"Rest: ":"Work: ") + formatMS(remainingSeconds));
				} else {
					countdownTimer.stop();
					if (!inRest) {
						SeriesData s = series.get(currentSeriesIndex);
						if (s.restSeconds > 0) {
							inRest = true;
							remainingSeconds = s.restSeconds;
							countdownTimer.start();
						} else {
							completedSeries++;
							proceedToNextSeriesOrFinish();
						}
					} else {
						inRest = false;
						completedSeries++;
						proceedToNextSeriesOrFinish();
					}
				}
			}
		});

		btnStartPauseNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (ExercisePanel.this) {
					if (state == State.IDLE || state == State.PAUSED) {
						if (currentSeriesIndex == -1) {
							if (series.size() > 0) {
								startSeries(0);
							}
						}

						totalTimer.start();
						if (remainingSeconds > 0) countdownTimer.start();
						state = State.RUNNING;
						btnStartPauseNext.setText("Pause");
						btnStartPauseNext.setBackground(Color.ORANGE);
					} else if (state == State.RUNNING) {

						totalTimer.stop();
						countdownTimer.stop();
						state = State.PAUSED;
						btnStartPauseNext.setText("Start");
						btnStartPauseNext.setBackground(Color.GREEN);
					} else if (state == State.COMPLETED) {
						// Pasar al siguiente ejercicio si existe, si no salir con resumen
						if (currentExerciseIndex + 1 < exercises.size()) {
							showExercise(currentExerciseIndex + 1);
						} else {
							showSummaryAndExit();
						}
					}
				}
			}
		});

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopAllTimers();
				showSummaryAndExit();
			}
		});

		// Eliminamos datos de ejemplo; los datos reales llegan con setWorkoutData()
		// loadSampleData();
	}

	// Establecer datos del workout completo y sus ejercicios/series
	public void setWorkoutData(Workout workout, List<Exercise> exercises, Map<Integer, List<Serie>> seriesByExercise) {
		this.workout = workout;
		this.exercises = new ArrayList<>();
		if (exercises != null) this.exercises.addAll(exercises);
		this.seriesByExerciseId.clear();
		if (seriesByExercise != null) {
			for (Map.Entry<Integer, List<Serie>> entry : seriesByExercise.entrySet()) {
				List<SeriesData> converted = new ArrayList<>();
				for (Serie s : entry.getValue()) {
					ImageIcon icon = null;
					try {
						if (s.getIconPath() != null && !s.getIconPath().isEmpty()) {
							icon = new ImageIcon(s.getIconPath());
						}
					} catch (Exception ignore) {}
					converted.add(new SeriesData(
						s.getName(),
						s.getRepetitions(),
						s.getExpectedTime(),
						s.getRestTime(),
						icon
					));
				}
				this.seriesByExerciseId.put(entry.getKey(), converted);
			}
		}
		// Mostrar el primer ejercicio si existe
		if (this.exercises.size() > 0) {
			showExercise(0);
		} else {
			// Sin ejercicios
			setExerciseData(workout != null ? workout.getName() : "-", "-", "-", Collections.emptyList());
		}
	}

	private void showExercise(int index) {
		if (index < 0 || index >= exercises.size()) return;
		this.currentExerciseIndex = index;
		Exercise ex = exercises.get(index);
		List<SeriesData> data = seriesByExerciseId.getOrDefault(ex.getId(), Collections.emptyList());
		setExerciseData(workout != null ? workout.getName() : "-", ex.getName(), ex.getDescript(), data);
	}

	private void startSeries(int index) {
		if (index < 0 || index >= series.size()) return;
		currentSeriesIndex = index;
		inRest = false;
		SeriesData s = series.get(index);
		remainingSeconds = s.durationSeconds;
		highlightCurrentSeries();
		lblSeriesCountdown.setText("Work: " + formatMS(remainingSeconds));

		totalTimer.start();
		countdownTimer.start();
		state = State.RUNNING;
		btnStartPauseNext.setText("Pause");
		btnStartPauseNext.setBackground(Color.ORANGE);
	}

	private void proceedToNextSeriesOrFinish() {
		if (currentSeriesIndex + 1 < series.size()) {

			startSeries(currentSeriesIndex + 1);
		} else {

			state = State.COMPLETED;
			countdownTimer.stop();
			totalTimer.stop();
			btnStartPauseNext.setText("Next Exercise");
			btnStartPauseNext.setBackground(Color.BLUE);
			lblSeriesCountdown.setText("Completed");
		}
	}

	private void resetForNextExercise() {

		stopAllTimers();
		totalSeconds = 0;
		remainingSeconds = 0;
		currentSeriesIndex = -1;
		completedSeries = 0;
		state = State.IDLE;
		lblTotalTimer.setText("Total: 00:00:00");
		lblSeriesCountdown.setText("Series: 00:00");
		btnStartPauseNext.setText("Start");
		btnStartPauseNext.setBackground(Color.GREEN);
		highlightCurrentSeries();
	}

	private void stopAllTimers() {
		if (totalTimer != null) totalTimer.stop();
		if (countdownTimer != null) countdownTimer.stop();
	}

	private void showSummaryAndExit() {
		int total = totalSeconds;
		double percent = series.size() == 0 ? 0 : (100.0 * completedSeries / series.size());
		String msg = String.format("Exercises done: %d/%d\nTotal time: %s\nCompleted: %.0f%%\n\n%s",
			completedSeries, series.size(), formatHMS(total), percent, motivationalMessage(percent));
		JOptionPane.showMessageDialog(this, msg, "Workout summary", JOptionPane.INFORMATION_MESSAGE);
		
		// Registrar el workout completado si se finalizó al menos el 50%
		if (percent >= 50 && workout != null && window.getUserLogin() != null) {
			try {
				java.time.LocalDateTime now = java.time.LocalDateTime.now();
				String doneDate = now.toString();
				controllers.ControllerFactory.getInstance()
					.getUserWorkoutLineController()
					.registerWorkoutCompletion(window.getUserLogin(), workout, doneDate);
			} catch (Exception ex) {
				// No mostrar error al usuario, solo log
				System.err.println("Error al registrar workout completado: " + ex.getMessage());
			}
		}
		
		window.showPanel(Window.WORKOUT_PANEL);
	}

	private String motivationalMessage(double percent) {
		if (percent == 100) return "Perfecto, has completado el ejercicio!";
		if (percent >= 50) return "Buen trabajo, sigue así!";
		return "Vamos, vamos!";
	}

	
	// Para hacerlo mas visible, no es nada de logica
	
	private void highlightCurrentSeries() {
		for (int i = 0; i < seriesListPanel.getComponentCount(); i++) {
			java.awt.Component comp = seriesListPanel.getComponent(i);
			if (i == currentSeriesIndex) {
				comp.setBackground(new Color(220, 240, 255));
				comp.setForeground(Color.BLACK);
				if (comp instanceof javax.swing.JComponent) {
					((javax.swing.JComponent) comp).setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
				}
			} else {
				comp.setBackground(null);
				if (comp instanceof javax.swing.JComponent) {
					((javax.swing.JComponent) comp).setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				}
			}
		}
		seriesListPanel.revalidate();
		seriesListPanel.repaint();
	}

	// Metodo que se uso para pruebas
	
	public void setExerciseData(String workoutName, String exerciseName, String description, List<SeriesData> seriesData) {
		lblWorkoutName.setText("Workout: " + (workoutName == null ? "-" : workoutName));
		lblExerciseName.setText("Exercise: " + (exerciseName == null ? "-" : exerciseName));
		lblDescription.setText("Description: " + (description == null ? "-" : description));
		this.series = new ArrayList<>();
		if (seriesData != null) this.series.addAll(seriesData);
		refreshSeriesListUI();
		resetForNextExercise();
	}

	private void refreshSeriesListUI() {
		seriesListPanel.removeAll();
		for (SeriesData s : series) {
			JPanel p = new JPanel();
			p.setLayout(null);
			p.setOpaque(true);
			JLabel name = new JLabel(s.name + " (" + s.reps + " reps)");
			name.setBounds(5, 5, 300, 20);
			p.add(name);
			JLabel times = new JLabel("Work: " + formatMS(s.durationSeconds) + "  Rest: " + formatMS(s.restSeconds));
			times.setBounds(5, 26, 300, 18);
			p.add(times);
			JLabel img = new JLabel();
			img.setBounds(320, 5, 64, 64);
			if (s.photo != null) img.setIcon(s.photo);
			p.add(img);
			p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
			seriesListPanel.add(p);
		}
		seriesListPanel.revalidate();
		seriesListPanel.repaint();
	}

	
	//Informacion de prueba que se cambiara por la informacion recogida de firebase
	private void loadSampleData() {
		List<SeriesData> sample = new ArrayList<>();
		sample.add(new SeriesData("Series 1", 10, 10, 5, null));
		sample.add(new SeriesData("Series 2", 8, 8, 4, null));
		sample.add(new SeriesData("Series 3", 6, 12, 6, null));
		setExerciseData("Full Body", "Push Ups", "A set of push-ups to work chest and triceps.", sample);
	}

	private String formatHMS(int totalSec) {
		int h = totalSec / 3600;
		int m = (totalSec % 3600) / 60;
		int s = totalSec % 60;
		return String.format("%02d:%02d:%02d", h, m, s);
	}

	private String formatMS(int totalSec) {
		int m = totalSec / 60;
		int s = totalSec % 60;
		return String.format("%02d:%02d", m, s);
	}

}