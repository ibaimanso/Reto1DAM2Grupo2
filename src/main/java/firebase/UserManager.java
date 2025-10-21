package firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import entities.User;
import firebase.exceptions.DBException;

public class UserManager implements ManagerInterface<User> {
	
	private static UserManager instance = null;
	
	private String collectionName = null;
	
	public UserManager() {
		this.collectionName = "users";
	}
	
	public static UserManager getInstance() {
		if (null == instance) {
			instance = new UserManager();
		}
		return instance;
	}

	@Override
	public List<User> selectAll() throws DBException {
		List<User> ret = null;

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> users = qs.getDocuments();
			if (null == ret) ret = new ArrayList<User>();
			for (QueryDocumentSnapshot user: users) {
				ret.add(new User(
					user.getLong("id").intValue(),
					user.getString("fname"),
					user.getString("lname"),
					user.getString("pw"),
					user.getString("email"),
					user.getString("birth"),
					user.getString("lastMod"),
					user.getLong("level").intValue()
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(User t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("id",    t.getId());
		userMap.put("fname", t.getFname());
		userMap.put("lname", t.getLname());
		userMap.put("pw",    t.getPw());
		userMap.put("email", t.getEmail());
		userMap.put("birth", t.getBirth());
		userMap.put("lastMod", t.getLastMod());
		userMap.put("level", t.getLevel());

		DocumentReference dr = query.document(String.valueOf(t.getId()));
		dr.set(userMap);
	}

	@Override
	public void update(User t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));

		Map<String, Object> userMap = new HashMap<>();
		userMap.put("id",    t.getId());
		userMap.put("fname", t.getFname());
		userMap.put("lname", t.getLname());
		userMap.put("pw",    t.getPw());
		userMap.put("email", t.getEmail());
		userMap.put("birth", t.getBirth());
		userMap.put("lastMod", t.getLastMod());
		userMap.put("level", t.getLevel());

		dr.update(userMap);
	}

	@Override
	public void delete(User t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));
		dr.delete();
	}

}
