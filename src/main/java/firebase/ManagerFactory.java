package firebase;

import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import firebase.exceptions.DBException;

public class ManagerFactory {
	
	private static ManagerFactory instance = null;
	
	private final String CREDENTIALS = "/fbConfig.json";

	private Firestore db = null;

	public ManagerFactory() throws DBException {
		try {
			InputStream serviceAccount = ManagerFactory.class.getResourceAsStream(CREDENTIALS);
			FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);
			this.db = FirestoreClient.getFirestore();
		} catch (Exception ex) {
			throw new DBException();
		}
	}
	
	public static ManagerFactory getInstance() throws DBException {
		if (null == instance) {
			instance = new ManagerFactory();
		}

		return instance;
	}
	
	public Firestore getDB() {
		return this.db;
	}

	public UserManager getUserManager() {
		return UserManager.getInstance();
	}
	
	public void finalize() {
		try {db.close();}
		catch (Exception ex) {}
	}
}
