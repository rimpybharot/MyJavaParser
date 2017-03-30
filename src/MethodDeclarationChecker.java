import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationChecker {


	private List<MethodDeclaration> methods;
	private List<String> methodNames;
	private NodeList<Parameter> parameters;
	private List<String> pmnames;
	private List<String> parameterType;
	private List<String> usesRelation;


	public MethodDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.methods = new ArrayList<MethodDeclaration>();
		this.methodNames = new ArrayList<String>();
		this.parameters = new NodeList<>();
//		this.pmnames = new ArrayList<String>();
		this.parameterType = new ArrayList<String>();
		this.usesRelation = new ArrayList<String>();

	}


	public void setConstructor(ClassOrInterfaceDeclaration n){


		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(ConstructorDeclaration n, Object arg) {
				super.visit(n, arg);
				String p1 ="";
				parameters = n.getParameters();
				

				String name = null;

				
				for(Parameter p : parameters){
					p1 += p.getNameAsString() + ": " + p.getType();
					
				}
				name = n.getNameAsString() + "("+p1+")";
				setMethodNames(name);


				}

		}.visit(n, null);
	}
	public void setMethodsDetails(ClassOrInterfaceDeclaration n){


		setConstructor(n);
		new VoidVisitorAdapter<Object>() {

			
			
			String type = null;
			String name = null;
			String modifier = null;
//			private List<String> parameterType 

			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				
//				List<String> fields = new ArrayList<String>();
				setMethods(n);

				String p1 ="";
				parameters = n.getParameters();
				
				
				
				for(Parameter p : parameters){
//					System.out.println("parameters " + p.getType());
					p1 += p.getNameAsString() + ": " + p.getType();
					
				}
				name = n.getNameAsString() + "("+p1+")";
				type = n.getType().toString();
				modifier = "";
				if(n.isPrivate()){
					modifier ="-";
				}
				else if(n.isPublic()){
					modifier ="+";
				}
				else if(n.isProtected()){
					modifier ="#";
				}
				setMethodNames(modifier +" " + name + " : " + type);
			}

		}.visit(n, null);
	}


	protected void setParameters(ClassOrInterfaceDeclaration n, List<String> classNames) {

		if(!n.isInterface()){
			
		new VoidVisitorAdapter<Object>() {

			String className = n.getNameAsString();

			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);

				NodeList<Parameter> parameters = n.getParameters();
				
				
				
				for(Parameter p : parameters){
					if(classNames.contains(p.getType().toString())){
						String relation = "\n"+className + "--" + p.getType().toString()+"\n";
						setUses(relation);
					}
					
				}

			}
		}.visit(n, null);
		
		new VoidVisitorAdapter<Object>() {

			String className = n.getNameAsString();

			@Override
			public void visit(ConstructorDeclaration n, Object arg) {
				super.visit(n, arg);
				NodeList<Parameter> parameters = n.getParameters();
				for(Parameter p : parameters){
					if(classNames.contains(p.getType().toString())){
						setUses("\n"+className + "--" + p.getType().toString()+"\n");
					}
					
				}

			}
		}.visit(n, null);
	}
		
}




	protected void setUses(String uses) {
		// TODO Auto-generated method stub
		if(!this.usesRelation.contains(uses)){
		this.usesRelation.add(uses);}
		
	}

	public List<String> getUses(){
		this.usesRelation.removeAll(Collections.singleton(null));
		for(String m : this.usesRelation){
			System.out.println(m);
		}
		return this.usesRelation;

	}
	public void setMethods(MethodDeclaration n){
		this.methods.add(n);

	}
//
//
//
//	public String createMethodString(List<String> methodFields){
//		String finalVariable = null;
//
//		if(methodFields.size()>0){
//			switch(methodFields.get(0).toString()){
//			case "public" : methodFields.set(0, "+"); break;
//			case "private" : methodFields.set(0, "-"); break;
//			case "protected" : methodFields.set(0, "#"); break;
//			case "package private" : methodFields.set(0, "~"); break;
//			case "": methodFields.set(0, "~"); break;
//			default: methodFields.set(0, "-"); break;
//			}
//			String name = methodFields.get(2).toString();
//			String type = methodFields.get(1).toString();
//			String modifier = methodFields.get(0).toString();
//			finalVariable = modifier +" " + type + " : " + name;
//			return finalVariable;
//		}
//		else{
//			return "";
//		}
//	}

	public void setMethodNames(String methodName){
		this.methodNames.add(methodName);
	}

	public List<String> getMethodNames(){
		this.methodNames.removeAll(Collections.singleton(null));
		for(String m : this.methodNames){
//			System.out.println(m);
		}
		return this.methodNames;

	}

//	public void setParameters(MethodDeclaration node){
//			new VoidVisitorAdapter<Object>() {
//
//				@Override
//				public void visit(Parameter node, Object arg) {
//					super.visit(node, arg);
//					System.out.println("type "+node.getType().toString());
//					setParamaterType(node.getType().toString());
//					
//
//				}
//			}.visit(node, null);
//		
//	}


	protected void setParamaterType(String parameterType) {
		// TODO Auto-generated method stub
		this.parameterType.add(parameterType);
	}



//
//	public void getParametersDetails(){
//		this.pm.removeAll(Collections.singleton(null));  
//		for(Node parameter : this.pm){
//			this.pmnames.add(parameter.toString());
//
//
//		}
//	}

	public void setUsesRelation(){
		this.methods.removeAll(Collections.singleton(null));  
		for(MethodDeclaration m : this.methods){
			this.methodNames.add(m.getNameAsString());
//			System.out.println(m.getParameterByType("A1"));
			
		}


	}



//	public void setParameterDetails(){
//		this.pm.removeAll(Collections.singleton(null));  
//		for(Node p : this.pm){
//			for(Node child: p.getChildNodes()){
//				System.out.println("parameter is " + child.toString());
//			}
//		}
//	}

	public List<String> getParameters(){
		this.pmnames.removeAll(Collections.singleton(null)); 
		for(String parameter : pmnames){


			System.out.println(parameter);
		}
		return pmnames;
	}


	public void getGetters(){
		for (String method: this.methodNames){
			String methodString = method.toString();
			if(methodString.contains("get")){
				//				System.out.println(methodString);	
				int indexEnd = methodString.lastIndexOf(";");
				int indexBegin = methodString.lastIndexOf("return");
				System.out.println(methodString.substring(indexBegin + 7,indexEnd));
			}
		}
	}
	public void getSetters(){
		for (String method: this.methodNames){
			String methodString = method.toString();
			if(methodString.contains("set")){
				//				System.out.println(methodString);
				int indexEnd = methodString.lastIndexOf("=");
				int indexBegin = methodString.indexOf("{");
				System.out.println((methodString.substring(indexBegin + 1, indexEnd - 1)).trim());
			}
		}
	}
}
