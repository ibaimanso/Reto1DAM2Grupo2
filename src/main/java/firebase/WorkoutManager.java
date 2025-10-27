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
		List<Workout> ret = new ArrayList<Workout>();

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> workouts = qs.getDocuments();
			for (QueryDocumentSnapshot workout: workouts) {
				if (workout.getLong("id").intValue() <= 0) continue;
				ret.add(new Workout(
					workout.getLong("id").intValue(),
					workout.getString("name"),
					workout.getString("descript"),
					workout.getString("videoURL"),
					workout.getLong("level").intValue()
				));
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
		workoutMap.put("name",     t.getName());
		workoutMap.put("descript", t.getDescript());
		workoutMap.put("videoUrl", t.getVideoUrl());
		workoutMap.put("level",    t.getLevel());

		DocumentReference dr = query.document(String.valueOf(t.getId()));
		dr.set(workoutMap);
	}

	@Override
	public void update(Workout t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));

		Map<String, Object> workoutMap = new HashMap<>();
		workoutMap.put("id",       t.getId());
		workoutMap.put("name",     t.getName());
		workoutMap.put("descript", t.getDescript());
		workoutMap.put("videoUrl", t.getVideoUrl());
		workoutMap.put("level",    t.getLevel());

		dr.update(workoutMap);
	}

	@Override
	public void delete(Workout t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));
		dr.delete();
	}

}
