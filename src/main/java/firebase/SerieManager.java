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

import entities.Serie;
import firebase.exceptions.DBException;

public class SerieManager implements ManagerInterface<Serie> {
	
	private static SerieManager instance = null;
	
	private String collectionName = null;
	
	public SerieManager() {
		this.collectionName = "series";
	}
	
	public static SerieManager getInstance() {
		if (null == instance) {
			instance = new SerieManager();
		}
		return instance;
	}

	@Override
	public List<Serie> selectAll() throws DBException {
		List<Serie> ret = new ArrayList<Serie>();

		try {
			Firestore db = ManagerFactory.getInstance().getDB();
			ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
			QuerySnapshot qs = query.get();
			List<QueryDocumentSnapshot> series = qs.getDocuments();
			for (QueryDocumentSnapshot serie: series) {
				if (serie.getLong("id").intValue() <= 0) continue;
				ret.add(new Serie(
					serie.getLong("id").intValue(),
					serie.getLong("exerciseId").intValue(),
					serie.getLong("expectedTime").intValue(),
					serie.getLong("restTime").intValue(),
					serie.getLong("repetitions").intValue(),
					serie.getString("name"),
					serie.getString("iconPath")
				));
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
