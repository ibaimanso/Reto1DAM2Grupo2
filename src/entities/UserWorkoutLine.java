package entities;

import java.util.Objects;

public class UserWorkoutLine {
	private int    userId    = 0;
	private int    workoutId = 0;
	private String doneDate  = null;
	
	
	public UserWorkoutLine(int userId, int workoutId, String doneDate) {
		this.userId    = userId;
		this.workoutId = workoutId;
		this.doneDate  = doneDate;
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

	@Override
	public int hashCode() {
		return Objects.hash(doneDate, userId, workoutId);
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
		return Objects.equals(doneDate, other.doneDate) && userId == other.userId && workoutId == other.workoutId;
	}

	@Override
	public String toString() {
		return "UserWorkoutLine [userId=" + userId + ", workoutId=" + workoutId + ", doneDate=" + doneDate + "]";
	}
}
