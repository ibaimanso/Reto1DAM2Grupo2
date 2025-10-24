package binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ObjectFileManager<T> {
	
	public ObjectFileManager() {}

	
	public void write(T object, String filePath)
	throws FileNotFoundException, IOException {
		File               file = null;
		FileOutputStream   fos  = null;
		ObjectOutputStream oos  = null;
		
		try {
			file = new File(filePath);
			fos  = new FileOutputStream(file);
			oos  = new ObjectOutputStream(fos);
			
			oos.writeObject(object);
		} finally {
			try {if (null != oos) {oos.close();}}
			catch (Exception ex) {}

			try {if (null != fos) {fos.close();}}
			catch (Exception ex) {}
		}
	}
	
	@SuppressWarnings("unchecked")
	public T read(String filePath)
	throws ClassNotFoundException, IOException {
		T                 object = null;
		File              file   = null;
		FileInputStream   fis    = null;
		ObjectInputStream ois    = null;
		
		try {
			file = new File(filePath);
			fis  = new FileInputStream(file);
			ois  = new ObjectInputStream(fis);
			
			object = (T) ois.readObject();
		} finally {
			try {if (null != ois) ois.close();}
			catch (Exception ex) {}

			try {if (null != fis) fis.close();}
			catch (Exception ex) {}
		}

		return object;
	}
}