package entities;

import java.util.Objects;

public class User {

	private boolean trainer = false;
	private int     id      = 0;
	private int     level   = 0;
	private String  fname   = null;
	private String  lname   = null;
	private String  pw      = null;
	private String  email   = null;
	private String  birth   = null;
	private String  lastMod = null;

	
	public User(int id, String fname, String lname, String pw, String email, String birth, String lastMod) {
		this.trainer = false;
		this.id      = id;
		this.level   = 0;
		this.fname   = fname;
		this.lname   = lname;
		this.pw      = pw;
		this.email   = email;
		this.birth   = birth;
		this.lastMod = lastMod;
	}

	public User(int id, String fname, String lname, String pw, String email, String birth, String lastMod, int level, boolean trainer) {
		this.trainer = trainer;
		this.id      = id;
		this.level   = level;
		this.fname   = fname;
		this.lname   = lname;
		this.pw      = pw;
		this.email   = email;
		this.birth   = birth;
		this.lastMod = lastMod;
	}

	public boolean isTrainer() {
		return trainer;
	}

	public void setTrainer(boolean trainer) {
		this.trainer = trainer;
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

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getLastMod() {
		return lastMod;
	}

	public void setLastMod(String lastMod) {
		this.lastMod = lastMod;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birth, email, fname, id, lastMod, level, lname, pw, trainer);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(birth, other.birth) && Objects.equals(email, other.email)
				&& Objects.equals(fname, other.fname) && id == other.id && Objects.equals(lastMod, other.lastMod)
				&& level == other.level && Objects.equals(lname, other.lname) && Objects.equals(pw, other.pw)
				&& trainer == other.trainer;
	}

	@Override
	public String toString() {
		return "User [trainer=" + trainer + ", id=" + id + ", level=" + level + ", fname=" + fname + ", lname=" + lname
				+ ", pw=" + pw + ", email=" + email + ", birth=" + birth + ", lastMod=" + lastMod + "]";
	}

		
}