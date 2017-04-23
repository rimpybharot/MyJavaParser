import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorChecker {
	
	private List<ConstructorDeclaration> constructors;
	private NodeList<Parameter> parameters;

	public ConstructorChecker(){
		
	}
	
	public ConstructorChecker(ClassOrInterfaceDeclaration n){
		this.constructors = new ArrayList<>();
		this.parameters = new NodeList<>();

		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(ConstructorDeclaration n, Object arg) {
				super.visit(n, arg);
				setClassesConstructor(n);
			}				
		}.visit(n, null);
	}


	public void setClassesConstructor(ConstructorDeclaration n) {
		// TODO Auto-generated method stub
		this.constructors.add(n);
		
	}
	public List<ConstructorDeclaration> getClassesConstructor() {
		// TODO Auto-generated method stub
		return this.constructors;
		
	}
	public String constructorStringCreator(ConstructorDeclaration n){

//		String p1 ="";
//		parameters = n.getParameters();
//
//		String name = null;
//
//
//		for(Parameter p : parameters){
//			//			//////////System.out.println("parameters " + p.getType());
//			p1 += p.getNameAsString() + ": " + p.getType();
//
//		}
//		name = n.getNameAsString() + "("+p1+")";
//		String modifier = "";
//		if(n.isPrivate()){
//			modifier ="-";
//		}
//		else if(n.isPublic()){
//			modifier ="+";
//		}
//		else if(n.isProtected()){
//			modifier ="#";
//		}
//		return modifier +" " + name;
		
		

		

		String type = null;
		String name = null;
		String modifier = null;


		String p1 ="";
		parameters = n.getParameters();
		
		


		boolean first = true;
		
		
		for (Parameter p : parameters) {
			if (first==false) {
				System.out.println(p.getNameAsString());
				p1 += " , "+p.getNameAsString() + ": " + p.getType();
			}
			else{
				p1 += p.getNameAsString() + ": " + p.getType();
			}

			first = false;
		}
		name = n.getNameAsString() + "("+p1+")";
		modifier = "+";
		return modifier +" " + name + " : " + type;


	}
}
