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

import entities.Serie;
import firebase.exceptions.DBException;

public class SeriesManager implements ManagerInterface<Serie> {
	
	private static SeriesManager instance = null;
	
	private String collectionName = null;
	
	public SeriesManager() {
		this.collectionName = "series";
	}
	
	public static SeriesManager getInstance() {
		if (null == instance) {
			instance = new SeriesManager();
		}
		return instance;
	}

	@Override
	public List<Serie> selectAll() throws DBException {
		List<Serie> ret = null;

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> users = qs.getDocuments();
			if (null == ret) ret = new ArrayList<Serie>();
			for (QueryDocumentSnapshot user: users) {
				ret.add(new Serie(
					user.getLong("id").intValue(),
					user.getLong("exerciseId").intValue(),
					user.getLong("expectedTime").intValue(),
					user.getLong("restTime").intValue(),
					user.getLong("repetitions").intValue(),
					user.getString("name"),
					user.getString("iconPath")
				));
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public Serie selectById(int id) throws DBException {
		Serie ret = null;
		
		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			DocumentReference dr = db.collection(this.collectionName).document(String.valueOf(id));
			DocumentSnapshot  ds = dr.get().get();
			if (null != ds) {
				ret = new Serie(
					ds.getLong("id").intValue(),
					ds.getLong("exerciseId").intValue(),
					ds.getLong("expectedTime").intValue(),
					ds.getLong("restTime").intValue(),
					ds.getLong("repetitions").intValue(),
					ds.getString("name"),
					ds.getString("iconPath")
				);
			}
		} catch (Exception ex) {
			throw new DBException();
		}

		return ret;
	}

	@Override
	public void insert(Serie t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		CollectionReference query = db.collection(this.collectionName);

		Map<String, Object> serieMap = new HashMap<>();
		serieMap.put("id",           t.getId());
		serieMap.put("exerciseId",   t.getExerciseId());
		serieMap.put("expectedTime", t.getExpectedTime());
		serieMap.put("restTime",     t.getRestTime());
		serieMap.put("repetitions",  t.getRepetitions());
		serieMap.put("name",         t.getName());
		serieMap.put("iconPath",     t.getIconPath());

		DocumentReference dr = query.document(String.valueOf(t.getId()));
		dr.set(serieMap);
	}

	@Override
	public void update(Serie t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));

		Map<String, Object> serieMap = new HashMap<>();
		serieMap.put("id",           t.getId());
		serieMap.put("exerciseId",   t.getExerciseId());
		serieMap.put("expectedTime", t.getExpectedTime());
		serieMap.put("restTime",     t.getRestTime());
		serieMap.put("repetitions",  t.getRepetitions());
		serieMap.put("name",         t.getName());
		serieMap.put("iconPath",     t.getIconPath());

		dr.update(serieMap);
	}

	@Override
	public void delete(Serie t) throws DBException {
		Firestore db = ManagerFactory.getInstance().getDB();
		DocumentReference dr = db.collection(collectionName).document(String.valueOf(t.getId()));
		dr.delete();
	}

}
