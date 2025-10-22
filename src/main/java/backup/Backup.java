package backup;

public class Backup {

	private static Backup instance = null;
	
	public Backup() {}
	
	public static Backup getInstance() {
		if (null == instance) {
			instance = new Backup();
		}
		
		return instance;
	}
	
}
