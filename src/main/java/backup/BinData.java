package backup;

import java.io.Serializable;
import java.util.List;

import entities.Exercise;
import entities.Serie;
import entities.User;
import entities.UserExerciseLine;
import entities.UserSerieLine;
import entities.UserWorkoutLine;
import entities.Workout;

public class BinData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	List<Exercise>         exercises         = null;
	List<Serie>            series            = null;
	List<User>             users             = null;
	List<UserExerciseLine> userExerciseLines = null;
	List<UserSerieLine>    userSerieLines    = null;
	List<UserWorkoutLine>  userWorkoutLines  = null;
	List<Workout>          workouts          = null;

	public BinData() {}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<UserExerciseLine> getUserExerciseLines() {
		return userExerciseLines;
	}

	public void setUserExerciseLines(List<UserExerciseLine> userExerciseLines) {
		this.userExerciseLines = userExerciseLines;
	}

	public List<UserSerieLine> getUserSerieLines() {
		return userSerieLines;
	}

	public void setUserSerieLines(List<UserSerieLine> userSerieLines) {
		this.userSerieLines = userSerieLines;
	}

	public List<UserWorkoutLine> getUserWorkoutLines() {
		return userWorkoutLines;
	}

	public void setUserWorkoutLines(List<UserWorkoutLine> userWorkoutLines) {
		this.userWorkoutLines = userWorkoutLines;
	}

	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}
	
	
}
