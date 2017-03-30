import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ParseException;

public class PlantUMLNotationMaker {

	private List<String> interfaces;
	private List<String> classes;
	private List<String> interfaceImplentations;
	private List<String> extendsImplementation;
	private List<String> classNames;
	private List<String> dependencies;


	private String umlTextNotation;


	public PlantUMLNotationMaker(List<File> javafiles) throws ParseException, IOException{

		interfaces = new ArrayList<String>();
		interfaceImplentations = new ArrayList<String>();
		classes = new ArrayList<String>();
		extendsImplementation = new ArrayList<String>();
		classNames = new ArrayList<String>();
		dependencies = new ArrayList<String>();


		for (File file : javafiles){

			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.classOrInterfaceFinder(file);
			System.out.println("Parsed");

			classes.addAll(cdc.getClasses());
			interfaces.addAll(cdc.getInterfaces());
			interfaceImplentations.addAll(cdc.getImplementations());
			extendsImplementation.addAll(cdc.getExtensions());
			classNames.addAll(cdc.getClassNames());
		}

		for (File file : javafiles){
			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.setUses(file, classNames);
			dependencies.addAll(cdc.getDependency());
		}

		String plantUmlSource = new String();

		plantUmlSource += "@startuml\nskinparam classAttributeIconSize 0\n";
		for(String interfaceName : interfaces ){
			plantUmlSource += interfaceName;

		}

		for(String interfaceName : interfaceImplentations ){
			plantUmlSource += interfaceName;

		}
		for(String className : classes ){
			plantUmlSource += className;
		}
		for(String extension : extendsImplementation ){
			plantUmlSource += extension;

		}
		for(String dependency : dependencies ){
			plantUmlSource += dependency;

		}

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
