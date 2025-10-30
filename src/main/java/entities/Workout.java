package entities;

import java.io.Serializable;
import java.util.Objects;

public class Workout implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int    id       = 0;
	private int    level    = 0;
	private String name     = null;
	private String descript = null;
	private String videoUrl = null;
	
	
	public Workout(int id, String name, String descript, String videoUrl, int level) {
		this.id       = id;
		this.level    = level;
		this.name     = name;
		this.descript = descript;
		this.videoUrl = videoUrl;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descript, id, level, name, videoUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		return Objects.equals(descript, other.descript) && id == other.id && level == other.level
				&& Objects.equals(name, other.name) && Objects.equals(videoUrl, other.videoUrl);
	}

	@Override
	public String toString() {
		return "Descripción: " + this.descript + " Nivel: " + this.level;
	}
}
