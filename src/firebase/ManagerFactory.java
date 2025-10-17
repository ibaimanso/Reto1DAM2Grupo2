package firebase;

public class ManagerFactory {
	
	private static ManagerFactory instance = null;
	
	public static ManagerFactory getInstance() {
		if (null == instance) {
			instance = new ManagerFactory();
		}
		return instance;
	}

	public UserManager getUserManager() {
		return null;
	}
}
