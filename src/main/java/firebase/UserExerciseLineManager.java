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

import entities.UserExerciseLine;
import firebase.exceptions.DBException;

public class UserExerciseLineManager implements ManagerInterface<UserExerciseLine> {
	
	private static UserExerciseLineManager instance = null;
	
	private String collectionName = null;
	
	public UserExerciseLineManager() {
		this.collectionName = "userExerciseLines";
	}
	
	public static UserExerciseLineManager getInstance() {
		if (null == instance) {
			instance = new UserExerciseLineManager();
		}
		return instance;
	}

	@Override
	public List<UserExerciseLine> selectAll() throws DBException {
		List<UserExerciseLine> ret = new ArrayList<UserExerciseLine>();

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> userExerciseLines = qs.getDocuments();
			for (QueryDocumentSnapshot userExerciseLine: userExerciseLines) {
				if (userExerciseLine.getLong("userId").intValue() <= 0) continue;
				ret.add(new UserExerciseLine(
					userExerciseLine.getLong("userId").intValue(),
					userExerciseLine.getLong("exerciseId").intValue()
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(UserExerciseLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> userExerciseLineMap = new HashMap<>();
		userExerciseLineMap.put("userId",     t.getUserId());
		userExerciseLineMap.put("exerciseId", t.getExerciseId());

		DocumentReference dr = query.document(String.format("%dx%d", t.getUserId(), t.getExerciseId()));
		dr.set(userExerciseLineMap);
	}

	@Override
	public void update(UserExerciseLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getExerciseId()));

		Map<String, Object> userExerciseLineMap = new HashMap<>();
		userExerciseLineMap.put("userId",     t.getUserId());
		userExerciseLineMap.put("exerciseId", t.getExerciseId());

		dr.update(userExerciseLineMap);
	}

	@Override
	public void delete(UserExerciseLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getExerciseId()));
		dr.delete();
	}
	
	
}
