package entities;

import java.util.Objects;

public class UserSerieLine {
	private int userId  = 0;
	private int serieId = 0;
	
	
	public UserSerieLine(int userId, int serieId) {
		this.userId  = userId;
		this.serieId = serieId;
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSerieId() {
		return serieId;
	}

	public void setSerieId(int serieId) {
		this.serieId = serieId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(serieId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSerieLine other = (UserSerieLine) obj;
		return serieId == other.serieId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "UserSerieLine [userId=" + userId + ", serieId=" + serieId + "]";
	}
}
