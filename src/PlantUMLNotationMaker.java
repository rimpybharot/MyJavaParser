import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.github.javaparser.ParseException;

public class PlantUMLNotationMaker {

	private String interfaces;
	private String classes;
	private String interfaceImplentations;
	private String extendsImplementation;

	private String umlTextNotation;


	public PlantUMLNotationMaker(List<File> javafiles) throws ParseException{



		for (File file : javafiles){

			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.classOrInterfaceFinder(file);
			System.out.println("Parsed");
			if(cdc.getClasses()!=null){
				this.interfaces += cdc.getClasses();
				this.interfaces+="\n";
			}
			if(cdc.getInterfaces()!=null){
				this.interfaces += cdc.getInterfaces();
				this.interfaces+="\n";
			}
//			if(cdc.getImplements()!=null){
//				for(String implementation : cdc.getImplements()){
//					this.interfaceImplentations += implementation;
//				}
//			}
//			if(cdc.getExtends()!=null){
//				for(String extension : cdc.getExtends()){
//					this.extendsImplementation += extension;
//				}
//			}
		}

		//		VariableParser vp = new VariableParser();



		String plantUmlSource = new String();

		plantUmlSource += "@startuml\nskinparam classAttributeIconSize 0\n";
		plantUmlSource += interfaces;
		plantUmlSource += this.interfaceImplentations;

		//		plantUmlSource += classesOrInterfaces;

		//		plantUmlSource += " {\n";
		//		plantUmlSource += "\n"+vp.listVariables(file);
		//		plantUmlSource += "\n }";

		//		FileReader fr = new FileReader(file);

		//		@SuppressWarnings("resource")
		//		BufferedReader br = new BufferedReader(fr);
		//		plantUmlSource += br.readLine();
		plantUmlSource += "\n@enduml\n";


		//		plantUmlSource +=String.format("%n");
		//		plantUmlSource+="}";
		//		System.out.println(plantUmlSource);
		this.umlTextNotation = plantUmlSource;
	}

	public String getumlTextNoation(){



		return this.umlTextNotation;

	}

}
