
import java.io.File;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassesDeclarationChecker {

	private List<String> interfaces;
	private List<String> classes;
	private List<String> implemntations;
	private List<String> extensions;
	private List<String> variables;

	public ClassesDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.interfaces = new ArrayList<String>();
		this.classes = new ArrayList<String>();
		this.implemntations = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
		this.variables = new ArrayList<String>();
	}


	public void classOrInterfaceFinder(File file) throws ParseException, IOException {

		System.out.println("Reading file " + file.getName());

		try {
			new VoidVisitorAdapter<Object>() {


				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);


					if(n.isInterface()){
						System.out.println("getting interface " + n.getNameAsString());
						setInterfaces(n);
					}
					else{
						System.out.println("getting classes and implementions and extensions for " + n.getNameAsString());
						setImplements(n);
						setClasses(n);
						setExtends(n);
					}
				}
			}.visit(JavaParser.parse(file), null);
		} catch (IOException e) {
			new RuntimeException(e);

		}
	}


	public void setExtends(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
		//		extendsClass = n.getExtendedTypes();
		for(ClassOrInterfaceType citype : n.getExtendedTypes()){
			if(citype != null){
				this.extensions.add("\n"+citype.getNameAsString() + " <|-- " + n.getNameAsString());
			}
		}

	}

	public void setImplements(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
		for(ClassOrInterfaceType citype : n.getImplementedTypes()){
			if(citype != null){
				this.implemntations.add("\n"+ citype.getNameAsString()+ " <|.. " + n.getNameAsString());
			}
		}
	}

	public void setInterfaces(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
		this.interfaces.add("\ninterface " + n.getNameAsString());
	}

	//	public void setClasses(ClassOrInterfaceDeclaration n) {
	//		// TODO Auto-generated method stub
	//		this.classes.add("\n class " + n.getNameAsString());
	//	}

	public void setClasses(ClassOrInterfaceDeclaration n){
		String classDeclaration = "\nclass " + n.getNameAsString() + "{\n";

		VariableParser vp = new VariableParser();
		vp.listVariables(n);

		MethodDeclarationChecker md = new MethodDeclarationChecker();
		md.setMethodsDetails(n);
//		md.getMethodNames();
//		md.getParameters();
		
		

		for(String variable : vp.getVariables()){
			classDeclaration += variable + "\n";
		}

		for(String method : md.getMethodNames()){
			classDeclaration += method + "\n";
		}
		classDeclaration += "}";

		this.classes.add(classDeclaration);


	}

	public List<String> getExtensions() {
		// TODO Auto-generated method stub
		this.extensions.removeAll(Collections.singleton(null));  
		return this.extensions;
	}

	public List<String> getImplementations() {
		// TODO Auto-generated method stub
		this.implemntations.removeAll(Collections.singleton(null));  
		return this.implemntations;
	}

	public List<String> getInterfaces() {
		// TODO Auto-generated method stub
		return this.interfaces;
	}

	public List<String> getClasses() {
		// TODO Auto-generated method stub
		return this.classes;
	}
}
