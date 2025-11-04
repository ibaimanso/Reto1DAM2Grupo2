package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import entities.UserWorkoutLine;
import entities.Workout;
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
	
	/**
	 * Obtiene el historial de workouts completados por el usuario
	 * Retorna una lista de strings formateados con la información
	 */
	public List<String> getFormattedHistory(User user) throws DBException {
		List<String> history = new ArrayList<>();
		
		// Obtener los workouts completados por el usuario
		List<UserWorkoutLine> userWorkouts = ControllerFactory.getInstance()
			.getUserWorkoutLineController()
			.getWorkoutsByUser(user);
		
		// Obtener todos los workouts para poder mostrar el nombre
		List<Workout> allWorkouts = ManagerFactory.getInstance().getWorkoutManager().selectAll();
		
		// Combinar la información
		for (UserWorkoutLine uwl : userWorkouts) {
			Workout workout = findWorkoutById(allWorkouts, uwl.getWorkoutId());
			if (workout != null) {
				String entry = String.format("Workout: %s | Fecha: %s", 
					workout.getName(), 
					formatDate(uwl.getDoneDate()));
				history.add(entry);
			}
		}
		
		return history;
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
}
