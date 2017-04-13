import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	private List<String> extensions;
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
		extensions = new ArrayList<String>();
		interfaces = new ArrayList<String>();
		classesOrInterfaces = new ArrayList<>();
		classifierNames = new ArrayList<>();


		/*Go to classes and Parse each to get complete class data*/
		for(File file : javafiles){
			ClassesDeclarationChecker cdc = new ClassesDeclarationChecker();
			cdc.classOrInterfaceFinder(file);
			this.classesMethods.putAll(cdc.getMethods());
			this.classesConstructors.putAll(cdc.getConstructors());
			this.classesImplements.putAll(cdc.getImplementations());
			this.classesFields.putAll(cdc.getFields());
			this.extensions.addAll(cdc.getExtensions());
			this.interfaces.addAll(cdc.getInterfaces());
			this.classesOrInterfaces.addAll(cdc.getClassesORInterfaces());
		}


		/*take classes and interfaces name*/
		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			this.classifierNames.add(ci.getNameAsString());

		}

		/*Get classes and their associations with other classes*/
		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			VariableParser vp = new VariableParser(ci, this.classifierNames);
			associationsList.putAll(vp.getAssociations());
			List<FieldDeclaration> fd = this.classesFields.get(ci);
			if(vp.getFieldsToBeRemoved()!=null && fd!=null){
				fd.removeAll(vp.getFieldsToBeRemoved());
			}
			this.classesFields.remove(Collections.singleton(null));  
		}


		/*Get dependencies between classes and interfaces*/
		for(ClassOrInterfaceDeclaration ci : this.classesOrInterfaces){
			DependencyGetter dg = new DependencyGetter(ci, this.interfaces);
			for(String dependency : dg.getUses()){
				plantUmlSource += dependency;
			}
		}

		/*Create associations between classes and interfaces*/

		Association association = new Association(associationsList);

		this.finalRel = association.getRelations();

		for(ArrayList<String> relation : this.finalRel){
			String associati = "";
			for(String s : relation){
				associati+=s;
			}
			plantUmlSource += associati+"\n";
		}

		/*Create extensions between classes and interfaces*/

		for(String extension : extensions ){
			plantUmlSource += extension;

		}

		/*Create realizations between classes and interfaces*/

		//Remove common methods between interfaces and classes
		Realization r = new Realization(this.classesMethods, this.classesImplements);
		this.classesMethods = r.getRefinedMethods();


		//Create implementation relations
		for (Map.Entry<ClassOrInterfaceDeclaration, List<String>> entry : this.classesImplements.entrySet()) {
			for(String inter : entry.getValue()){
				plantUmlSource += "\n"+ inter+ " <|.. " + entry.getKey().getNameAsString();

			}
		}

		/*Remove getters setters and turn private fields if get set to public*/

		for(Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry: this.classesMethods.entrySet()){
			GetterSetter gs = new GetterSetter();

			List<MethodDeclaration> gsmd = entry.getValue();
			List<FieldDeclaration> gsfd = this.classesFields.get(entry.getKey());
			if(!gsmd.isEmpty()){
				if(!(gsfd==null)){
					gs.getGetterSetter(entry.getValue(), this.classesFields.get(entry.getKey()));
				}
			}
		}


		/*Create attributes between classes and interfaces*/

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

		/*Create constructor of classes*/

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

		/*Create methods of classes*/

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


	public String getumlTextNotation(){
		return this.umlTextNotation;
	}
}
