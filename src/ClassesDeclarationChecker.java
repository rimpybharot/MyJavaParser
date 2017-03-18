
import java.io.File;

import java.io.IOException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassesDeclarationChecker {

	private String className;
	private String interfaceName;
	private String implementsClass;
	private String extendsClass;
	private String[] implemntations;
	private String[] extensions;

	
	public void classOrInterfaceFinder(File file) throws ParseException {
		
		System.out.println("Reading file " + file.getName());
		
		try {
			new VoidVisitorAdapter<Object>() {


				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);
					if(n.isInterface()){
						System.out.println("getting interfaces for " + n.getNameAsString());
						setInterfaces(n);
					}
					else{
						System.out.println("getting classes etc for " + n.getNameAsString());
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
				this.extendsClass += (n.getNameAsString() + " *- " + citype.getNameAsString());
			}
		}

	}

	public void setImplements(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
		for(ClassOrInterfaceType citype : n.getImplementedTypes()){
			if(citype != null){
				this.implementsClass += (n.getNameAsString() + " <|-- " + citype.getNameAsString());
			}
		}
	}

	public void setInterfaces(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
		this.interfaceName = "interface " + n.getNameAsString();
	}

	public void setClasses(ClassOrInterfaceDeclaration n) {
		// TODO Auto-generated method stub
			this.className = "class  " + n.getNameAsString();
	}

	public String getExtends() {
		// TODO Auto-generated method stub
		return this.extendsClass;
	}

	public String getImplements() {
		// TODO Auto-generated method stub
		return this.implementsClass;
	}

	public String getInterfaces() {
		// TODO Auto-generated method stub
		return this.interfaceName;
	}

	public String getClasses() {
		// TODO Auto-generated method stub
		System.out.println(this.className);
		return this.className;
	}
}
