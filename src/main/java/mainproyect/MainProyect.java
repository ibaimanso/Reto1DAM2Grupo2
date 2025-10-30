package mainproyect;

import java.io.IOException;

import ui.Window;

public class MainProyect {

	public static void main(String[] args) {
		tryBackup();
		new Window().start();
	}
	
	private static void tryBackup() {
		int            errorCode      = 0;
		ProcessBuilder processBuilder = null;
		Process        process        = null;

		try {
			processBuilder = new ProcessBuilder("java", "bin/main/java/backup/BackupManager");
			process = processBuilder.start();
			
			errorCode = process.waitFor();
			
			if (errorCode != 0) {
				switch(errorCode) {
				case 1:
					System.out.println("No se ha podido establecer la clonexión a la base de datos.");
					break;
				default:
					System.out.printf("Ha ocurrdo un error de tipo: %d\n", errorCode);
					break;
				}
			}
		} catch (IOException ex) {
		} catch (InterruptedException e) {
		}
	}
}
