package backup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import binary.ObjectFileManager;
import entities.Exercise;
import entities.Serie;
import entities.User;
import entities.UserExerciseLine;
import entities.UserSerieLine;
import entities.UserWorkoutLine;
import entities.Workout;
import firebase.ManagerFactory;
import firebase.exceptions.DBException;
import xml.ManagerFactoryXML;
import xml.exceptions.XMLException;

public class BackupManager {

	private static BackupManager instance = null;
	
	private final String binPath = "C:/trastero/bin/bin_data.dat";
	
	public BackupManager() {}
	
	public static BackupManager getInstance() {
		if (null == instance) {
			instance = new BackupManager();
		}
		
		return instance;
	}
	
	private User getUserById(List<User> users, User user) {
		for (int index = 0; index < users.size(); index++) {
			if (user.getId() == users.get(index).getId()) {
				return users.get(index);
			}
		}
		return null;
	}
	
	public void tryBackup() {
		
		// TODO obtener las fechas de actualización y subir o bajar los datos en base a cual sea más reciente.
		
		List<User> localUsers  = null;
		List<User> onlineUsers = null;
		
		try {
			onlineUsers = ManagerFactory.getInstance().getUserManager().selectAll();
		} catch (DBException e) {
			System.exit(1);
		}
		
		try {
			localUsers = ManagerFactoryXML.getInstance().getUserManager().selectAll();
		} catch (XMLException e) {
			System.exit(2);
		}
		
		for (int index = 0; index < onlineUsers.size(); index++) {
			User localUser  = null;
			User onlineUser = null;
			
			onlineUser = onlineUsers.get(index);
			
			localUser = getUserById(localUsers, onlineUser);
			
			if (null == localUser) {
				this.makeLocalBackup(onlineUser);
			} else {
				LocalDateTime localDate  = LocalDateTime.parse(localUser.getLastMod(),  DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				LocalDateTime onlineDate = LocalDateTime.parse(onlineUser.getLastMod(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				
				if (localDate.isBefore(onlineDate)) {
					this.makeLocalBackup(localUser);
				} else if (onlineDate.isBefore(localDate)) {
					this.makeOnlineBackup(onlineUser);
				}
			}
		}

		List<Workout>  onlineWorkouts  = null;
		List<Exercise> onlineExercises = null;
		List<Serie>    onlineSeries    = null;

		List<Workout>  localWorkouts   = null;
		List<Exercise> localExercises  = null;
		List<Serie>    localSeries     = null;
		
		try {
			onlineWorkouts  = ManagerFactory.getInstance().getWorkoutManager().selectAll();
			onlineExercises = ManagerFactory.getInstance().getExerciseManager().selectAll();
			onlineSeries    = ManagerFactory.getInstance().getSerieManager().selectAll();
			
			if (
				null == onlineWorkouts  ||
				null == onlineExercises ||
				null == onlineSeries
			) {
				System.exit(4);
			}
			
			localWorkouts  = ManagerFactoryXML.getInstance().getWorkoutManager().selectAll();
			localExercises = ManagerFactoryXML.getInstance().getExerciseManager().selectAll();
			localSeries    = ManagerFactoryXML.getInstance().getSerieManager().selectAll();
			
			if (
				null == localWorkouts  ||
				null == localExercises ||
				null == localSeries
			) {
				System.exit(5);
			}
			
			for (Workout workout: localWorkouts)
				ManagerFactoryXML.getInstance().getWorkoutManager().delete(workout);
			for (Exercise exercise: localExercises)
				ManagerFactoryXML.getInstance().getExerciseManager().delete(exercise);
			for (Serie serie: localSeries)
				ManagerFactoryXML.getInstance().getSerieManager().delete(serie);
			
			for (Workout workout: onlineWorkouts)
				ManagerFactoryXML.getInstance().getWorkoutManager().insert(workout);
			for (Exercise exercise: onlineExercises)
				ManagerFactoryXML.getInstance().getExerciseManager().insert(exercise);
			for (Serie serie: onlineSeries)
				ManagerFactoryXML.getInstance().getSerieManager().insert(serie);
			
		} catch (Exception ex) {
			System.exit(3);
		}
		
		try {
			this.makeBinaryBackup();
		} catch (Exception ex) {
			System.exit(100);
		}
	}
	
	private List<UserWorkoutLine> getUserWorkoutLinesByUser(User user, List<UserWorkoutLine> uwls) {
		List<UserWorkoutLine> ret = new ArrayList<UserWorkoutLine>();

		for (UserWorkoutLine uwl: uwls) {
			if (uwl.getUserId() == user.getId()) {
				ret.add(uwl);
			}
		}
		
		return ret;
	}

	private List<UserExerciseLine> getUserExerciseLinesByUser(User user, List<UserExerciseLine> uels) {
		List<UserExerciseLine> ret = new ArrayList<UserExerciseLine>();

		for (UserExerciseLine uel: uels) {
			if (uel.getUserId() == user.getId()) {
				ret.add(uel);
			}
		}
		
		return ret;
	}

	private List<UserSerieLine> getUserSerieLinesByUser(User user, List<UserSerieLine> usls) {
		List<UserSerieLine> ret = new ArrayList<UserSerieLine>();

		for (UserSerieLine usl: usls) {
			if (usl.getUserId() == user.getId()) {
				ret.add(usl);
			}
		}
		
		return ret;
	}
	
	private UserData getUserData(
		User user,
		List<UserWorkoutLine> userWorkoutLines,
		List<UserExerciseLine> userExerciseLines,
		List<UserSerieLine> userSerieLines
	) {
		UserData userData = new UserData();
		
		userData.user = user;
		userData.userWorkoutLines  = getUserWorkoutLinesByUser(user, userWorkoutLines);
		userData.userExerciseLines = getUserExerciseLinesByUser(user, userExerciseLines);
		userData.userSerieLines    = getUserSerieLinesByUser(user, userSerieLines);
		
		return userData;
	}
	
	private void makeLocalBackup(User localUser) {
		User onlineUser = null;

		List<UserWorkoutLine>  onlineUserWorkoutLines  = null;
		List<UserExerciseLine> onlineUserExerciseLines = null;
		List<UserSerieLine>    onlineUserSerieLines    = null;

		List<UserWorkoutLine>  localUserWorkoutLines   = null;
		List<UserExerciseLine> localUserExerciseLines  = null;
		List<UserSerieLine>    localUserSerieLines     = null;
		
		try {
			onlineUserWorkoutLines  = ManagerFactory.getInstance().getUserWorkoutLineManager().selectAll();
			onlineUserExerciseLines = ManagerFactory.getInstance().getUserExerciseLineManager().selectAll();
			onlineUserSerieLines    = ManagerFactory.getInstance().getUserSerieLineManager().selectAll();
		} catch (Exception ex) {
			System.exit(6);
		}
		
		try {
			localUserWorkoutLines  = ManagerFactoryXML.getInstance().getUserWorkoutLineManager().selectAll();
			localUserExerciseLines = ManagerFactoryXML.getInstance().getUserExerciseLineManager().selectAll();
			localUserSerieLines    = ManagerFactoryXML.getInstance().getUserSerieLineManager().selectAll();
		} catch (Exception ex) {
			System.exit(7);
		}
		
		/*
		try {
			for (UserWorkoutLine uwl: localUserWorkoutLines)
				ManagerFactory.getInstance().getUserWorkoutLineManager().delete(uwl);
			for (UserExerciseLine uel: localUserExerciseLines)
				ManagerFactory.getInstance().getUserExerciseLineManager().delete(uel);
			for (UserSerieLine usl: localUserSerieLines)
				ManagerFactory.getInstance().getUserSerieLineManager().delete(usl);
		} catch (Exception ex) {
			System.exit(8);
		}
		
		try {
			for (UserWorkoutLine uwl: onlineUserWorkoutLines)
				ManagerFactory.getInstance().getUserWorkoutLineManager().insert(uwl);
			for (UserExerciseLine uel: onlineUserExerciseLines)
				ManagerFactory.getInstance().getUserExerciseLineManager().insert(uel);
			for (UserSerieLine usl: onlineUserSerieLines)
				ManagerFactory.getInstance().getUserSerieLineManager().insert(usl);
		} catch (Exception ex) {
			System.exit(9);
		}
		*/
	}

	private void makeOnlineBackup(User onlineUser) {
		User localUser = null;
	}
	
	private void makeBinaryBackup()
	throws XMLException, FileNotFoundException, IOException {
		BinData bd = new BinData();

		bd.setExercises(ManagerFactoryXML.getInstance().getExerciseManager().selectAll());
		bd.setSeries(ManagerFactoryXML.getInstance().getSerieManager().selectAll());
		bd.setUsers(ManagerFactoryXML.getInstance().getUserManager().selectAll());
		bd.setUserExerciseLines(ManagerFactoryXML.getInstance().getUserExerciseLineManager().selectAll());
		bd.setUserSerieLines(ManagerFactoryXML.getInstance().getUserSerieLineManager().selectAll());
		bd.setUserWorkoutLines(ManagerFactoryXML.getInstance().getUserWorkoutLineManager().selectAll());
		bd.setWorkouts(ManagerFactoryXML.getInstance().getWorkoutManager().selectAll());
		
		new ObjectFileManager<BinData>().write(bd, binPath);
	}
}

class UserData {
	public User user = null;
	public List<UserWorkoutLine>  userWorkoutLines  = null;
	public List<UserExerciseLine> userExerciseLines = null;
	public List<UserSerieLine>    userSerieLines    = null;
}
