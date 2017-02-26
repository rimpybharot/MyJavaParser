
import java.io.File;

public class FileHandling{
	
	
	public FileHandling(String path){

		File folder = new File(path);
		if (folder.isDirectory()){
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
		    if (file.isFile() && (file.getName()).endsWith(".java"))
		    {
		    	
		        System.out.println(file.getName());
		    }
	
		}
	}
	}
}
	
