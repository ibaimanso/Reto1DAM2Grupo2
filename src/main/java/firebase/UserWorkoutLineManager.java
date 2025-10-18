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

import entities.UserWorkoutLine;
import firebase.exceptions.DBException;

public class UserWorkoutLineManager implements ManagerInterface<UserWorkoutLine> {
	
	private static UserWorkoutLineManager instance = null;
	
	private String collectionName = null;
	
	public UserWorkoutLineManager() {
		this.collectionName = "userWorkoutLine";
	}
	
	public static UserWorkoutLineManager getInstance() {
		if (null == instance) {
			instance = new UserWorkoutLineManager();
		}
		return instance;
	}

	@Override
	public List<UserWorkoutLine> selectAll() throws DBException {
		List<UserWorkoutLine> ret = null;

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> userWorkoutLines = qs.getDocuments();
			if (null == ret) ret = new ArrayList<UserWorkoutLine>();
			for (QueryDocumentSnapshot userWorkoutLine: userWorkoutLines) {
				ret.add(new UserWorkoutLine(
					userWorkoutLine.getLong("userId").intValue(),
					userWorkoutLine.getLong("workoutId").intValue(),
					userWorkoutLine.getString("doneDate")
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(UserWorkoutLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> workoutMap = new HashMap<>();
		workoutMap.put("userId",       t.getUserId());
		workoutMap.put("workoutId",    t.getWorkoutId());
		workoutMap.put("doneDate",     t.getDoneDate());

		DocumentReference dr = query.document(String.format("%dx%d", t.getUserId(), t.getWorkoutId()));
		dr.set(workoutMap);
	}

	@Override
	public void update(UserWorkoutLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getWorkoutId()));

		Map<String, Object> userWorkoutLineMap = new HashMap<>();
		userWorkoutLineMap.put("userId",    t.getUserId());
		userWorkoutLineMap.put("workoutId", t.getWorkoutId());
		userWorkoutLineMap.put("doneDate",  t.getDoneDate());

		dr.update(userWorkoutLineMap);
	}

	@Override
	public void delete(UserWorkoutLine t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.format("%dx%d", t.getUserId(), t.getWorkoutId()));
		dr.delete();
	}

}
