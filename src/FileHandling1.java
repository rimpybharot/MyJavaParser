
import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class FileHandling1{

	public static String path ;

	public FileHandling1(String path){
		FileHandling1.path = path;
	}

	public List<File> getJavaFiles(){

		//Array of file which will hold all java files
		List<File> fileList = new ArrayList<>();

		//path is set as folder path
		File folder = new File(path);


		//check if path is a directory
		if (folder.isDirectory()){
			//list all contents of the directory
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				if (file.isFile() && (file.getName()).endsWith(".java"))
				{
					fileList.add(file);

				}
			}

		}
		return fileList;


	}

}
