import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import java.util.*;
import com.github.javaparser.*;



public class PlantUMLDiagramGenerator {

	public static void main(String[] args) throws ParseException, IOException {
		FileHandling fh = new FileHandling(args[0]);
	    File f = new File(args[0].toString());
		
		List<File> javafiles = fh.getJavaFiles();

//		callers p = new callers(javafiles);
		PlantUMLNotationMaker p = new PlantUMLNotationMaker(javafiles);

		new PlantUMLDiagramGenerator(p.getumlTextNotation(), f.getName());


	}

	public PlantUMLDiagramGenerator(String plantumlTextNotation, String filename) throws IOException{
		new File("output").mkdir();

		String fileName = "output\\"+filename+".png";
		System.out.println("Output file located at " + fileName);
		SourceStringReader reader = new SourceStringReader(plantumlTextNotation);
		// System.out.println(plantumlTextNotation);
		FileOutputStream output = new FileOutputStream(new File(fileName));
		reader.generateImage(output, new FileFormatOption(FileFormat.PNG));
	}
}