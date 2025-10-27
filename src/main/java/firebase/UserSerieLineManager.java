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

import entities.UserSerieLine;
import firebase.exceptions.DBException;

public class UserSerieLineManager implements ManagerInterface<UserSerieLine> {
	
	private static UserSerieLineManager instance = null;
	
	private String collectionName = null;
	
	public UserSerieLineManager() {
		this.collectionName = "userSerieLines";
	}
	
	public static UserSerieLineManager getInstance() {
		if (null == instance) {
			instance = new UserSerieLineManager();
		}
		return instance;
	}

	@Override
	public List<UserSerieLine> selectAll() throws DBException {
		List<UserSerieLine> ret = new ArrayList<UserSerieLine>();

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> userSerieLines = qs.getDocuments();
			for (QueryDocumentSnapshot userSerieLine: userSerieLines) {
				if (userSerieLine.getLong("userId").intValue() <= 0) continue;
				ret.add(new UserSerieLine(
					userSerieLine.getLong("userId").intValue(),
					userSerieLine.getLong("serieId").intValue()
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(UserSerieLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> userSerieLineMap = new HashMap<>();
		userSerieLineMap.put("userId",       t.getUserId());
		userSerieLineMap.put("userId",       t.getUserId());

		DocumentReference dr = query.document(String.format("%dx%d", t.getUserId(), t.getSerieId()));
		dr.set(userSerieLineMap);
	}

	@Override
	public void update(UserSerieLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getSerieId()));

		Map<String, Object> userSerieLineMap = new HashMap<>();
		userSerieLineMap.put("userId",       t.getUserId());
		userSerieLineMap.put("userId",       t.getUserId());

		dr.update(userSerieLineMap);
	}

	@Override
	public void delete(UserSerieLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getSerieId()));
		dr.delete();
	}

}
