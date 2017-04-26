
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassesDeclarationChecker {

	
	private HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> classesMethods;
	private HashMap<ClassOrInterfaceDeclaration, List<ConstructorDeclaration>> classesConstructors;

	private HashMap<ClassOrInterfaceDeclaration, List<FieldDeclaration>> classesVariables;
	private HashMap<ClassOrInterfaceDeclaration, List<String>> classesImplements;

	private List<ClassOrInterfaceDeclaration> classesOrInterfaces;
	private List<String> interfaces;
	private List<String> classifiers;
	private List<String> extensions;
	private List<String> classNames;
	private List<String> associations;

	public ClassesDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.classesMethods = new HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>>();
		this.classesVariables = new HashMap<ClassOrInterfaceDeclaration, List<FieldDeclaration>>();
		this.classesImplements = new HashMap<ClassOrInterfaceDeclaration, List<String>>();
		this.classesConstructors = new HashMap<ClassOrInterfaceDeclaration, List<ConstructorDeclaration>>();
		this.classesOrInterfaces = new ArrayList<>();

		this.interfaces = new ArrayList<String>();
		this.classifiers = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
		this.classNames = new ArrayList<String>();
		this.associations = new ArrayList<String>();

	}


	public void classOrInterfaceFinder(File file) throws ParseException, IOException {
		try {
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);
					setClassesOrInterfaces(n);
					if(n.isInterface()){
						setInterfaces(n);
					}
					else{
						setImplements(n);
						setExtends(n);
						setFields(n);
					}
					setClassifiers(n);
					setOperations(n);
				}
			}.visit(JavaParser.parse(file), null);
		} catch (IOException e) {
			new RuntimeException(e);

		}
	}

	public void setClassesOrInterfaces(ClassOrInterfaceDeclaration n){
		this.classesOrInterfaces.add(n);
	}
	
	public List<ClassOrInterfaceDeclaration> getClassesORInterfaces(){
		return this.classesOrInterfaces;
	}
	public void setOperations(ClassOrInterfaceDeclaration n) {
		MethodDeclarationChecker md = new MethodDeclarationChecker(n);
		this.classesMethods.put(n, md.getClassesMethods());
		ConstructorChecker cd = new ConstructorChecker(n);
		this.classesConstructors.put(n, cd.getClassesConstructor());
		
	}

	public void setFields(ClassOrInterfaceDeclaration n) {
		FieldParser fp = new FieldParser(n);
		this.classesVariables.put(n, fp.getFields());
	}


	public HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> getMethods() {
		this.classesMethods.remove(Collections.singleton(null));  
		return this.classesMethods;
	}
	public HashMap<ClassOrInterfaceDeclaration, List<ConstructorDeclaration>> getConstructors() {
		this.classesConstructors.remove(Collections.singleton(null));  
		return this.classesConstructors;
	}

	public HashMap<ClassOrInterfaceDeclaration, List<FieldDeclaration>> getFields() {
		this.classesVariables.remove(Collections.singleton(null));  
		return this.classesVariables;
	}


	public void setExtends(ClassOrInterfaceDeclaration n) {
		for(ClassOrInterfaceType citype : n.getExtendedTypes()){
			if(citype != null){
				this.extensions.add("\n"+citype.getNameAsString() + " <|-- " + n.getNameAsString());
			}
		}

	}

	public void setImplements(ClassOrInterfaceDeclaration n) {

		this.classesImplements.put(n, new ArrayList<String>());
		
		for(ClassOrInterfaceType citype : n.getImplementedTypes()){
			if(citype != null){
				this.classesImplements.get(n).add(citype.getNameAsString());
			}
		}


	}

	public void setInterfaces(ClassOrInterfaceDeclaration n) {
		this.interfaces.add(n.getNameAsString());
	}


	public void setClassifiers(ClassOrInterfaceDeclaration n){

		String classDeclaration;

		if(n.isInterface()){
			classDeclaration = "\ninterface " + n.getNameAsString() + "{\n";

		}
		else{
			classDeclaration = "\nclass " + n.getNameAsString() + "{\n";
		}
		classDeclaration += "}";

		this.classifiers.add(classDeclaration);


	}

	public List<String> getExtensions() {
		this.extensions.removeAll(Collections.singleton(null));  
		return this.extensions;
	}

	public HashMap<ClassOrInterfaceDeclaration, List<String>> getImplementations() {
		this.classesImplements.remove(Collections.singleton(null));  
		return this.classesImplements;
	}

	public List<String> getInterfaces() {
		this.interfaces.remove(Collections.singleton(null));  
		return this.interfaces;
	}

	public List<String> getClassifiers() {
		this.classifiers.remove(Collections.singleton(null));  
		return this.classifiers;
	}
}
