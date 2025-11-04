package mainproyect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ui.Window;

public class MainProyect {

	public static void main(String[] args) {
		tryBackup();
		new Window().start();
		tryBackup();

	}
	
	private static void tryBackup() {
		int errorCode = 0;
		ProcessBuilder processBuilder = null;
		Process process = null;

		try {
			processBuilder = new ProcessBuilder(System.getProperty("java.home") + "/bin/java", "-cp", System.getProperty("java.class.path"), "backup.BackupManager");
			processBuilder.redirectErrorStream(true);
			process = processBuilder.start();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				reader.lines().forEach(System.out::println);
			}

			errorCode = process.waitFor();
			
			if (errorCode != 0) {
				switch(errorCode) {
					case 1:
						System.out.println("No se ha podido establecer la conexión a la base de datos.");
						break;
					default:
						System.out.printf("Ha ocurrido un error de tipo: %d%n", errorCode);
						break;
				}
			}
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
