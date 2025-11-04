package entities;

import java.io.Serializable;
import java.util.Objects;

public class UserWorkoutLine implements Serializable {

	private static final long serialVersionUID = 1L;

	private int    userId    = 0;
	private int    workoutId = 0;
	private String doneDate  = null;
	private int    totalTime = 0; // Tiempo total en segundos
	
	
	public UserWorkoutLine(int userId, int workoutId, String doneDate) {
		this.userId    = userId;
		this.workoutId = workoutId;
		this.doneDate  = doneDate;
		this.totalTime = 0;
	}
	
	public UserWorkoutLine(int userId, int workoutId, String doneDate, int totalTime) {
		this.userId    = userId;
		this.workoutId = workoutId;
		this.doneDate  = doneDate;
		this.totalTime = totalTime;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(int workoutId) {
		this.workoutId = workoutId;
	}

	public String getDoneDate() {
		return doneDate;
	}

	public void setDoneDate(String doneDate) {
		this.doneDate = doneDate;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(doneDate, userId, workoutId, totalTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserWorkoutLine other = (UserWorkoutLine) obj;
		return Objects.equals(doneDate, other.doneDate) && userId == other.userId 
				&& workoutId == other.workoutId && totalTime == other.totalTime;
	}

	@Override
	public String toString() {
		return "UserWorkoutLine [userId=" + userId + ", workoutId=" + workoutId 
				+ ", doneDate=" + doneDate + ", totalTime=" + totalTime + "]";
	}
}