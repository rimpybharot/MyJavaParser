import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseException;

public class PlantUMLNotationMaker {

	private List<String> interfaces;
	private String classes;
	private List<String> interfaceImplentations;
	private String extendsImplementation;

	private String umlTextNotation;


	public PlantUMLNotationMaker(List<File> javafiles) throws ParseException{

		interfaces = new ArrayList<String>();
		interfaceImplentations = new ArrayList<String>();
	
		

		for (File file : javafiles){

			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.classOrInterfaceFinder(file);
			System.out.println("Parsed");
//			if(cdc.getClasses()!=null){
//				this.classes = cdc.getClasses();
//			}
			if(cdc.getInterfaces()!=null){
				interfaces.addAll(cdc.getInterfaces());
			}
			
			interfaceImplentations.addAll(cdc.getImplementations());
			
//			if(cdc.getImplementations()!=null){
//				for(String implementation : cdc.getImplementations()){
//					interfaceImplentations.adimplementation;
//				}
////			}
//			if(cdc.getExtends()!=null){
//				for(String extension : cdc.getExtends()){
//					this.extendsImplementation += extension;
//				}
//			}
		}

		//		VariableParser vp = new VariableParser();



		String plantUmlSource = new String();

		plantUmlSource += "@startuml\nskinparam classAttributeIconSize 0\n";
		for(String interfaceName : interfaces ){
			plantUmlSource += interfaceName;
			
		}
		
		for(String interfaceName : interfaceImplentations ){
			plantUmlSource += interfaceName;
			
		}
//		plantUmlSource += interfaces;
//		plantUmlSource += this.interfaceImplentations;

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
