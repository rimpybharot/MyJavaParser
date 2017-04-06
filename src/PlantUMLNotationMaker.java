import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class PlantUMLNotationMaker {

	private String umlTextNotation;
	private HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> classesMethods;
	private HashMap<ClassOrInterfaceDeclaration, List<ConstructorDeclaration>> classesConstructors;
	private HashMap<ClassOrInterfaceDeclaration, List<String>> classesImplements;
	private HashMap<ClassOrInterfaceDeclaration, List<FieldDeclaration>> classesFields;
	private List<String> extendsImplementation;
	private List<String> interfaces;
	private List<ClassOrInterfaceDeclaration> classesOrInterfaces;
	private List<String> classifierNames ;
	private HashMap<String, ArrayList<String>> associationsList = new HashMap<>();
	private List<ArrayList<String>> finalRel = new ArrayList<ArrayList<String>>();

	public PlantUMLNotationMaker(List<File> javafiles) throws ParseException, IOException{

		String plantUmlSource = new String();

		plantUmlSource += "@startuml\nskinparam classAttributeIconSize 0\n";
		classesMethods = new HashMap<>();
		classesConstructors = new HashMap<>();
		classesImplements = new HashMap<>();
		classesFields = new HashMap<>();
		extendsImplementation = new ArrayList<String>();
		interfaces = new ArrayList<String>();
		classesOrInterfaces = new ArrayList<>();
		classifierNames = new ArrayList<>();

		for(File file : javafiles){
			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.classOrInterfaceFinder(file);
			this.classesMethods.putAll(cdc.getMethods());
			this.classesConstructors.putAll(cdc.getConstructors());
			this.classesImplements.putAll(cdc.getImplementations());
			this.classesFields.putAll(cdc.getFields());
			this.extendsImplementation.addAll(cdc.getExtensions());
			this.interfaces.addAll(cdc.getInterfaces());
			this.classesOrInterfaces.addAll(cdc.getClassesORInterfaces());
		}

		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			DependencyGetter dg = new DependencyGetter(ci, this.interfaces);
			for(String dependency : dg.getUses()){
				plantUmlSource += dependency;
			}
		}
		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			this.classifierNames.add(ci.getNameAsString());

		}
		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			VariableParser vp = new VariableParser(ci, this.classifierNames);
			associationsList.putAll(vp.getAssociations());
			List<FieldDeclaration> fd = this.classesFields.get(ci);
			if(vp.getFieldsToBeRemoved()!=null && fd!=null){
				fd.removeAll(vp.getFieldsToBeRemoved());
			}
		}



		/*Code for Association*/
		
		Association association = new Association(associationsList, this.classifierNames);
		
		for(ArrayList<String> relation : association.finalRel){
			String associati = "";
			for(String s : relation){
				associati+=s;
			}
			plantUmlSource += associati+"\n";
		}

		for(String extension : extendsImplementation ){
			plantUmlSource += extension;

		}
		for (Map.Entry<ClassOrInterfaceDeclaration, List<String>> entry : this.classesImplements.entrySet()) {
			for(String inter : entry.getValue()){
				plantUmlSource += "\n"+ inter+ " <|.. " + entry.getKey().getNameAsString();

			}
		}
		for (Map.Entry<ClassOrInterfaceDeclaration, List<FieldDeclaration>> entry : this.classesFields.entrySet()) {
			if(!entry.getValue().isEmpty()){
				if(!entry.getKey().isInterface()){
					plantUmlSource+="\nclass "+entry.getKey().getNameAsString() + "{\n";}
				for(FieldDeclaration variable : entry.getValue()){
					FieldParser fp = new FieldParser();
					plantUmlSource += fp.variablestringCreator(variable)+"\n";
				}
				plantUmlSource+="}\n";
			}

		}
		for (Map.Entry<ClassOrInterfaceDeclaration, List<ConstructorDeclaration>> entry :
			this.classesConstructors.entrySet()) {
			if(!entry.getValue().isEmpty()){
				if(!entry.getKey().isInterface()){
					plantUmlSource+="\nclass "+entry.getKey().getNameAsString() + "{\n";}
				else{
					plantUmlSource+="\ninterface "+entry.getKey().getNameAsString() + "{\n";}

				for(ConstructorDeclaration method : entry.getValue()){
					ConstructorChecker c = new ConstructorChecker();
					plantUmlSource += c.constructorStringCreator(method)+"\n";
				}
				plantUmlSource+="}\n";
			}

		}
		for (Map.Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry : this.classesMethods.entrySet()) {
			if(!entry.getValue().isEmpty()){
				if(!entry.getKey().isInterface()){
					plantUmlSource+="\nclass "+entry.getKey().getNameAsString() + "{\n";}
				else{
					plantUmlSource+="\ninterface "+entry.getKey().getNameAsString() + "{\n";}

				for(MethodDeclaration method : entry.getValue()){
					MethodDeclarationChecker md = new MethodDeclarationChecker();
					plantUmlSource += md.methodStringCreator(method)+"\n";
				}
				plantUmlSource+="}\n";
			}

		}
		plantUmlSource += "\n@enduml\n";
		this.umlTextNotation = plantUmlSource;
	}

	public String getumlTextNoation(){
		return this.umlTextNotation;
	}
}
