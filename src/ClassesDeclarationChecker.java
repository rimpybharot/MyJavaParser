
import java.io.File;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.Collection;
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
	private List<String> classifiers;
	private List<String> implemntations;
	private List<String> extensions;
	private List<String> uses;
	private List<String> classNames;
	private List<String> associations;

	public ClassesDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.interfaces = new ArrayList<String>();
		this.classifiers = new ArrayList<String>();
		this.implemntations = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
		this.uses = new ArrayList<String>();
		this.classNames = new ArrayList<String>();
		this.associations = new ArrayList<String>();

	}


	public void classOrInterfaceFinder(File file) throws ParseException, IOException {

//		System.out.println("Reading file " + file.getName());
		try {
			new VoidVisitorAdapter<Object>() {


				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);
					setClassNames(n);
					if(n.isInterface()){
//						System.out.println("getting interface " + n.getNameAsString());
						setInterfaces(n);
						setCommonMethods(n);
					}
					else{
						//						System.out.println("getting classes and implementions and extensions for " + n.getNameAsString());
						setImplements(n);
						setExtends(n);
						//						setUses(n);
//						setAssociation(n);
					}
					setClassifiers(n);
				}
			}.visit(JavaParser.parse(file), null);
		} catch (IOException e) {
			new RuntimeException(e);

		}
	}


	protected void setCommonMethods(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub

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
		for(ClassOrInterfaceType citype : n.getImplementedTypes()){
			if(citype != null){
				this.implemntations.add("\n"+ citype.getNameAsString()+ " <|.. " + n.getNameAsString());
			}
		}
	}

	public void setInterfaces(ClassOrInterfaceDeclaration n) {
		this.interfaces.add("\ninterface " + n.getNameAsString());
	}


	public void setClassNames(ClassOrInterfaceDeclaration n){
		this.classNames.add(n.getNameAsString());
	}


	public List<String> getClassNames(){
		this.classNames.removeAll(Collections.singleton(null));  
		return this.classNames;
	}

	public void setClassifiers(ClassOrInterfaceDeclaration n){

		String classDeclaration;

		if(n.isInterface()){
			classDeclaration = "\ninterface " + n.getNameAsString() + "{\n";

		}
		else{
			classDeclaration = "\nclass " + n.getNameAsString() + "{\n";
		}

		VariableParser vp = new VariableParser();
		vp.listVariables(n);

		MethodDeclarationChecker md = new MethodDeclarationChecker();
		md.setMethodsDetails(n);


		for(String variable : vp.getVariables()){
			classDeclaration += variable + "\n";
		}

		for(String method : md.getMethodNames()){
			classDeclaration += method + "\n";
		}
		classDeclaration += "}";

		this.classifiers.add(classDeclaration);


	}
	public void setAssociation(Collection<? extends String> association) {
		// TODO Auto-generated method stub
			this.associations.addAll(association);
	}
	public void setUses(File file, List<String> classNames) {
		// TODO Auto-generated method stub
		try {
			new VoidVisitorAdapter<Object>() {


				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);
					MethodDeclarationChecker md = new MethodDeclarationChecker();
					//					md.setMethodsDetails(n);
					md.setParameters(n, classNames);
					setDependency(md.getUses());
				}
			}.visit(JavaParser.parse(file), null);
		} catch (IOException e) {
			new RuntimeException(e);

		}
	}

	public void setDependency(List<String> uses) {
		this.uses.addAll(uses);
	}

	public List<String> getDependency() {
		this.uses.removeAll(Collections.singleton(null));  
		return this.uses;
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

	public List<String> getClassifiers() {
		// TODO Auto-generated method stub
		return this.classifiers;
	}
	public List<String> getAssociation() {
		// TODO Auto-generated method stub
		return this.classifiers;
	}


}
