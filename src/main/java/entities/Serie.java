package entities;

import java.util.Arrays;
import java.util.Objects;

public class Serie {
	private int    id           = 0;
	private int    exerciseId   = 0;
	private int    expectedTime = 0;
	private int    restTime     = 0;
	private int    repetitions  = 0;
	private String name         = null;
	private byte[] icon         = null;
	
	
	public Serie(int id, int exerciseId, int expectedTime, int restTime, int repetitions, String name, byte[] icon) {
		this.id           = id;
		this.exerciseId   = exerciseId;
		this.expectedTime = expectedTime;
		this.restTime     = restTime;
		this.repetitions  = repetitions;
		this.name         = name;
		this.icon         = icon;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(int expectedTime) {
		this.expectedTime = expectedTime;
	}

	public int getRestTime() {
		return restTime;
	}

	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}

	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) {
		this.repetitions = repetitions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(icon);
		result = prime * result + Objects.hash(exerciseId, expectedTime, id, name, repetitions, restTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Serie other = (Serie) obj;
		return exerciseId == other.exerciseId && expectedTime == other.expectedTime && Arrays.equals(icon, other.icon)
				&& id == other.id && Objects.equals(name, other.name) && repetitions == other.repetitions
				&& restTime == other.restTime;
	}

	@Override
	public String toString() {
		return "Serie [id=" + id + ", exerciseId=" + exerciseId + ", expectedTime=" + expectedTime + ", restTime="
				+ restTime + ", repetitions=" + repetitions + ", name=" + name + ", icon=" + Arrays.toString(icon)
				+ "]";
	}
}
