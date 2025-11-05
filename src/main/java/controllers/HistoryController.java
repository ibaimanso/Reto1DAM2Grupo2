package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import entities.UserWorkoutLine;
import entities.Workout;
import entities.Exercise;
import entities.UserExerciseLine;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class HistoryController {
	
	private static HistoryController instance = null;
	
	public HistoryController() {}
	
	public static HistoryController getInstance() {
		if (null == instance) {
			instance = new HistoryController();
		}
		return instance;
	}
	
	
	public List<String> getFormattedHistory(User user) throws DBException {
		List<String> history = new ArrayList<>();
		
		// Obtener los workouts completados por el usuario
		
		List<UserWorkoutLine> userWorkouts = ControllerFactory.getInstance()
			.getUserWorkoutLineController()
			.getWorkoutsByUser(user);
		
		// Obtener todos los workouts para poder mostrar el nombre
		List<Workout> allWorkouts = ManagerFactory.getInstance().getWorkoutManager().selectAll();
		
		// Obtener todos los ejercicios
		List<Exercise> allExercises = ManagerFactory.getInstance().getExerciseManager().selectAll();
		
		// Obtener los ejercicios completados por el usuario
		List<UserExerciseLine> userExercises = ManagerFactory.getInstance()
			.getUserExerciseLineManager().selectAll();
		
		// Combinar la información
		for (UserWorkoutLine uwl : userWorkouts) {
			Workout workout = findWorkoutById(allWorkouts, uwl.getWorkoutId());
			if (workout != null) {
				int completionPercentage = calculateCompletionPercentage(
					workout.getId(), 
					user.getId(), 
					allExercises, 
					userExercises
				);
				
				String entry = String.format("Workout: %s | Fecha: %s | Completitud: %d%% | Tiempo: %s", 
					workout.getName(), 
					formatDate(uwl.getDoneDate()),
					completionPercentage,
					formatTime(uwl.getTotalTime()));
				history.add(entry);
			}
		}
		
		return history;
	}
	
	/**
	 * Calcula el porcentaje de ejercicios completados de un workout
	 */
	private int calculateCompletionPercentage(int workoutId, int userId, 
			List<Exercise> allExercises, List<UserExerciseLine> userExercises) {
		
		// Contar ejercicios totales del workout
		int totalExercises = 0;
		List<Integer> workoutExerciseIds = new ArrayList<>();
		for (Exercise ex : allExercises) {
			if (ex.getWorkoutId() == workoutId) {
				totalExercises++;
				workoutExerciseIds.add(ex.getId());
			}
		}
		
		if (totalExercises == 0) {
			return 100; // Si no hay ejercicios, consideramos completado al 100%
		}
		
		// Contar ejercicios completados por el usuario
		int completedExercises = 0;
		for (UserExerciseLine uel : userExercises) {
			if (uel.getUserId() == userId && workoutExerciseIds.contains(uel.getExerciseId())) {
				completedExercises++;
			}
		}
		
		// Calcular porcentaje
		return (completedExercises * 100) / totalExercises;
	}
	
	private Workout findWorkoutById(List<Workout> workouts, int id) {
		for (Workout w : workouts) {
			if (w.getId() == id) {
				return w;
			}
		}
		return null;
	}
	
	private String formatDate(String dateTime) {
		if (dateTime == null || dateTime.isEmpty()) {
			return "Fecha desconocida";
		}
		// Si el formato es ISO (2024-11-04T10:30:00), extraer fecha y hora
		if (dateTime.contains("T")) {
			String[] parts = dateTime.split("T");
			if (parts.length == 2) {
				String date = parts[0];
				String time = parts[1].substring(0, Math.min(8, parts[1].length()));
				return date + " " + time;
			}
		}
		return dateTime;
	}
	
	/**
	 * Formatea el tiempo en segundos a formato HH:MM:SS
	 */
	private String formatTime(int totalSeconds) {
		if (totalSeconds == 0) {
			return "00:00:00";
		}
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
}