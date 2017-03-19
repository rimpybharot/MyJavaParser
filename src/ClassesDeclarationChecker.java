
import java.io.File;

import java.io.IOException;
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
	private String className;
	private String interfaceName;
	private String implementsClass;
	private String extendsClass;
	private List<String> implemntations;
	private List<String> extensions;
	
	public ClassesDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.interfaces = new ArrayList<String>();
		this.classes = new ArrayList<String>();
		this.implemntations = new ArrayList<String>();
		this.extensions = new ArrayList<String>();
	}

	
	public void classOrInterfaceFinder(File file) throws ParseException {
		
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
//			System.out.println(citype.getNameAsString());
//			this.extensions.add(citype.getNameAsString());

			if(citype != null){
				this.extensions.add("\n"+n.getNameAsString() + " *- " + citype.getNameAsString());
			}
		}

	}

	public void setImplements(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
//		System.out.println("this is for getting the interfaces");
		for(ClassOrInterfaceType citype : n.getImplementedTypes()){
			
//			System.out.println(citype.getNameAsString());
//			this.implemntations.add(citype.getNameAsString());
			
			if(citype != null){
//				System.out.println(citype.getNameAsString());
				this.implemntations.add("\n"+ citype.getNameAsString()+ " <|-- " + n.getNameAsString());

			}
		}
	}

	public void setInterfaces(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
//		this.interfaceName = "interface " + n.getNameAsString();
		this.interfaces.add("\ninterface " + n.getNameAsString());
	}

	public void setClasses(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
//			this.className = "class  " + n.getNameAsString();
		this.classes.add(n.getNameAsString());
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

	public String getClasses() {
		// TODO Auto-generated method stub
//		System.out.println(this.className);
		return this.className;
	}
}
