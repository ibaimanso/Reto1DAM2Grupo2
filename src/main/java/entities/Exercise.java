package entities;

import java.util.Objects;

public class Exercise {
	
	private int    id        = 0;
	private int    workoutId = 0;
	private String name      = null;
	private String descript  = null;
	
	
	public Exercise(int id, int workoutId, String name, String descript) {		
		this.id        = id;
		this.workoutId = workoutId;
		this.name      = name;
		this.descript  = descript;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWorkoutId() {
		return workoutId;
	}

	public void setWorkoutId(int workoutId) {
		this.workoutId = workoutId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descript, id, name, workoutId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exercise other = (Exercise) obj;
		return Objects.equals(descript, other.descript) && id == other.id && Objects.equals(name, other.name)
				&& workoutId == other.workoutId;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", workoutId=" + workoutId + ", name=" + name + ", descript=" + descript + "]";
	}
}
