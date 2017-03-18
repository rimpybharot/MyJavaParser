import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;



public class PlantUMLDiargramGenerator {


	public PlantUMLDiargramGenerator(String plantumlTextNotation) throws IOException{
		// TODO Auto-generated constructor stub
		String fileName = "target\\UML.png";
		SourceStringReader reader = new SourceStringReader(plantumlTextNotation);
		System.out.println(plantumlTextNotation);
		FileOutputStream output = new FileOutputStream(new File(fileName));
		reader.generateImage(output, new FileFormatOption(FileFormat.PNG));
	}
}