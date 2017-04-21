import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.ParseException;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
public class PlantUMLDiagramGenerator {

	public static void main(String[] args){
		FileHandling fh = new FileHandling(args[0]);
		File f = new File(args[0].toString());
		if(f.exists()){
		List<File> javafiles = fh.getJavaFiles();
		PlantUMLNotationMaker p;
		try {
			p = new PlantUMLNotationMaker(javafiles);
			if(f.getName()!=null || !f.getName().isEmpty()){
				try {
					new PlantUMLDiagramGenerator(p.getumlTextNotation(), f.getName());
				} catch (SecurityException | IllegalArgumentException e) {
					System.out.println("SecurityException file path");
					return;
				}
			}
			else{
				System.out.println("Path is not correct");
				return;
			}
		} catch (ParseException | IOException e) {
			System.out.println("Check file path");
			return;
		}
	}
		else{
			System.out.println("File does not exist");
		}

	}

	public PlantUMLDiagramGenerator(String plantumlTextNotation, String filename){
		new File("output").mkdir();
		String fileName = "output\\"+filename+".png";
		System.out.println("Output file located at " + fileName);
		SourceStringReader reader = new SourceStringReader(plantumlTextNotation);
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File(fileName));
			try {
				reader.generateImage(output, new FileFormatOption(FileFormat.PNG));
			} catch (IOException e) {
				System.out.println("something wrong with creating image, please checkm your settings");
			}
		} catch (FileNotFoundException e) {
			System.out.println("something wrong with creating image, please checkm your settings");
		}

	}
}