import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;

import com.github.javaparser.ParseException;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLDiargramGenerator {


	public PlantUMLDiargramGenerator(File file) throws IOException, ParseException {
		// TODO Auto-generated constructor stub

		ClassesDeclarationChecker cdc = new ClassesDeclarationChecker(file);
		VariableParser vp = new VariableParser();

		
		
		String plantUmlSource = new String();

		plantUmlSource += "@startuml\nskinparam classAttributeIconSize 0\n";
		plantUmlSource += cdc.getClassName().toString();
		plantUmlSource += "{\n";
		plantUmlSource += "\n"+vp.listVariables(file)+"\nlove";
		plantUmlSource += "\n}";
		
		FileReader fr = new FileReader(file);
		
//		@SuppressWarnings("resource")
//		BufferedReader br = new BufferedReader(fr);
//		plantUmlSource += br.readLine();
		plantUmlSource += "\n@enduml\n";
		
		
//		plantUmlSource +=String.format("%n");
//		plantUmlSource+="}";
//		System.out.println(plantUmlSource);

		
		String fileName = "target\\" + (file.getName()).replaceAll("java", "png");



		


		SourceStringReader reader = new SourceStringReader(plantUmlSource);
		
		
		System.out.println(plantUmlSource);


		FileOutputStream output = new FileOutputStream(new File(fileName));

		reader.generateImage(output, new FileFormatOption(FileFormat.PNG));
	}

}
