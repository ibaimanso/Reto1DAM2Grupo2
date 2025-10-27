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

import entities.Exercise;
import firebase.exceptions.DBException;

public class ExerciseManager implements ManagerInterface<Exercise> {
	
	private static ExerciseManager instance = null;
	
	private String collectionName = null;

	public ExerciseManager() {
		this.collectionName = "exercises";
	}

	public static ExerciseManager getInstance() {
		if (null == instance) {
			instance = new ExerciseManager();
		}
		return instance;
	}

	@Override
	public List<Exercise> selectAll() throws DBException {
		List<Exercise> ret = null;

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> exercises = qs.getDocuments();
			if (null == ret) ret = new ArrayList<Exercise>();
			for (QueryDocumentSnapshot exercise: exercises) {
				if (exercise.getLong("id").intValue() <= 0) continue;
				ret.add(new Exercise(
					exercise.getLong("id").intValue(),
					exercise.getLong("workoutId").intValue(),
					exercise.getString("name"),
					exercise.getString("descript")
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(Exercise t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> exerciseMap = new HashMap<>();
		exerciseMap.put("id",        t.getId());
		exerciseMap.put("forkoutId", t.getWorkoutId());
		exerciseMap.put("name",      t.getName());
		exerciseMap.put("descript",  t.getDescript());

		DocumentReference dr = query.document(String.valueOf(t.getId()));
		dr.set(exerciseMap);
	}

	@Override
	public void update(Exercise t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));

		Map<String, Object> exerciseMap = new HashMap<>();
		exerciseMap.put("id",        t.getId());
		exerciseMap.put("forkoutId", t.getWorkoutId());
		exerciseMap.put("name",      t.getName());
		exerciseMap.put("descript",  t.getDescript());

		dr.update(exerciseMap);
	}

	@Override
	public void delete(Exercise t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));
		dr.delete();
	}

}
