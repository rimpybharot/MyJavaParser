//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.nio.file.Files;
//
//import net.sourceforge.plantuml.FileFormat;
//import net.sourceforge.plantuml.FileFormatOption;
//import net.sourceforge.plantuml.SourceStringReader;
//
//public class PlantUMLDiargramGenerator {
//
//
//	public PlantUMLDiargramGenerator(File file) throws IOException {
//		// TODO Auto-generated constructor stub
//
//		String plantUmlSource = new String();
//
//		plantUmlSource += "@startuml\n";
//		FileReader fr = new FileReader(file);
//		@SuppressWarnings("resource")
//		BufferedReader br = new BufferedReader(fr);
//		plantUmlSource += br.readLine();
//		plantUmlSource += "\n@enduml";
//
//		SourceStringReader reader = new SourceStringReader(plantUmlSource);
//		
//		
//		System.out.println(plantUmlSource);
//
//		String fileName = (file.toString()).replace("txt","png");
//
//		FileOutputStream output = new FileOutputStream(new File(fileName));
//
//		reader.generateImage(output, new FileFormatOption(FileFormat.PNG));
//	}
//
//}
