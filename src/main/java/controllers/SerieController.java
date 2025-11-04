package controllers;

import java.util.ArrayList;
import java.util.List;

import entities.Exercise;
import entities.Serie;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;

public class SerieController {
	
	private static SerieController instance = null;

	public SerieController() {}
	
	public static SerieController getInstance() {
		if (null == instance) {
			instance = new SerieController();
		}
		return instance;
	}

	public List<Serie> getSeriesByExercise(Exercise exercise) throws DBException {
		List<Serie> ret = new ArrayList<>();

		List<Serie> all = ManagerFactory.getInstance().getSerieManager().selectAll();
		for (int i = 0; i < all.size(); i++) {
			if (all.get(i).getExerciseId() == exercise.getId()) {
				ret.add(all.get(i));
			}
		}

		return ret;
	}
}