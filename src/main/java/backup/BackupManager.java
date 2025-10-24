package backup;

import java.io.FileNotFoundException;
import java.io.IOException;

import binary.ObjectFileManager;
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
	
	public void tryBackup() {
		
		// TODO obtener las fechas de actualización y subir o bajar los datos en base a cual sea más reciente.
		
		try {
			this.makeBinaryBackup();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
