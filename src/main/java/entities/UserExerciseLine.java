package entities;

import java.io.Serializable;
import java.util.Objects;

public class UserExerciseLine implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId     = 0;
	private int exerciseId = 0;
	
	
	public UserExerciseLine(int userId, int exerciseId) {
		this.userId     = userId;
		this.exerciseId = exerciseId;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exerciseId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserExerciseLine other = (UserExerciseLine) obj;
		return exerciseId == other.exerciseId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "UserExerciseLine [userId=" + userId + ", exerciseId=" + exerciseId + "]";
	}
}
