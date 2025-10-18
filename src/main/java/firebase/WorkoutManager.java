package firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import entities.Workout;
import firebase.exceptions.DBException;

public class WorkoutManager implements ManagerInterface<Workout> {
	
	private static WorkoutManager instance = null;

	private String collectionName = null;
	
	public WorkoutManager() {
		this.collectionName = "workouts";
	}
	
	public static WorkoutManager getInstance() {
		if (null == instance) {
			instance = new WorkoutManager();
		}
		return instance;
	}

	@Override
	public List<Workout> selectAll() throws DBException {
		List<Workout> ret = null;

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> users = qs.getDocuments();
			if (null == ret) ret = new ArrayList<Workout>();
			for (QueryDocumentSnapshot user: users) {
				ret.add(new Workout(
					user.getLong("id").intValue(),
					user.getLong("level").intValue(),
					user.getString("name"),
					user.getString("descript"),
					user.getString("videoURL")
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public Workout selectById(int id) throws DBException {
		Workout ret = null;
		
		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			DocumentReference dr = db.collection(this.collectionName).document(String.valueOf(id));
			DocumentSnapshot  ds = dr.get().get();
			if (null != ds) {
				ret = new Workout(
					ds.getLong("id").intValue(),
					ds.getLong("level").intValue(),
					ds.getString("name"),
					ds.getString("descript"),
					ds.getString("videoURL")
				);
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(Workout t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> workoutMap = new HashMap<>();
		workoutMap.put("id",       t.getId());
		workoutMap.put("level",    t.getLevel());
		workoutMap.put("name",     t.getName());
		workoutMap.put("descript", t.getDescript());
		workoutMap.put("videoUrl", t.getVideoUrl());

		DocumentReference dr = query.document(String.valueOf(t.getId()));
		dr.set(workoutMap);
	}

	@Override
	public void update(Workout t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));

		Map<String, Object> workoutMap = new HashMap<>();
		workoutMap.put("id",       t.getId());
		workoutMap.put("level",    t.getLevel());
		workoutMap.put("name",     t.getName());
		workoutMap.put("descript", t.getDescript());
		workoutMap.put("videoUrl", t.getVideoUrl());

		dr.update(workoutMap);
	}

	@Override
	public void delete(Workout t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));
		dr.delete();
	}

}
