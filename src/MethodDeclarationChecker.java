import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationChecker {


	private List<MethodDeclaration> methods;
	private List<String> methodNames;
	private NodeList<Node> pm;
	private List<String> pmnames;


	public MethodDeclarationChecker() {
		// TODO Auto-generated constructor stub
		this.methods = new ArrayList<MethodDeclaration>();
		this.methodNames = new ArrayList<String>();
		this.pm = new NodeList<>();
		this.pmnames = new ArrayList<String>();
	}




	public void setMethodsDetails(ClassOrInterfaceDeclaration n){

		
		new VoidVisitorAdapter<Object>() {


			String type = null;
			String name = null;
			String modifier = null;

			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				List<String> fields = new ArrayList<String>();

				
				name = n.getNameAsString() + "()";
				type = n.getType().toString();
				EnumSet<Modifier> e = n.getModifiers();
				modifier = "";

				for (Modifier m : e){
					modifier = (m.toString()).toLowerCase();
//					System.out.println("modifier " + modifier);
				}
//				if(modifier != null){
					fields.add(modifier);
//				}
				if(name!=null){
					fields.add(name);	
				}
				if(type!=null){
					fields.add(type);
				}
				
				setMethodNames(createMethodString(fields));
			}
			
		

		}.visit(n, null);

//		this.methods.addAll(n.getMethods());
		
		
	}

	public String createMethodString(List<String> methodFields){
		String finalVariable = null;

		if(methodFields.size()>0){
			switch(methodFields.get(0).toString()){
			case "public" : methodFields.set(0, "+"); break;
			case "private" : methodFields.set(0, "-"); break;
			case "protected" : methodFields.set(0, "#"); break;
			case "package private" : methodFields.set(0, "~"); break;
			case "": methodFields.set(0, "~"); break;
			default: methodFields.set(0, "-"); break;
			}
			String name = methodFields.get(2).toString();
			String type = methodFields.get(1).toString();
			String modifier = methodFields.get(0).toString();
			finalVariable = modifier +" " + type + " : " + name;
			return finalVariable;
		}
		else{
			return "";
		}



	}
	public void setMethodNames(String methodName){
//		this.methods.removeAll(Collections.singleton(null));  
//		for(MethodDeclaration m : this.methods){
			this.methodNames.add(methodName);
//		}
	}

	public List<String> getMethodNames(){
		this.methodNames.removeAll(Collections.singleton(null));
		for(String m : this.methodNames){
		}
		return this.methodNames;
		
	}

	public void setParameters(){
		this.methods.removeAll(Collections.singleton(null));  
		for(MethodDeclaration m : this.methods){
			this.pm.addAll(m.getParameters());
		}
		
		
		
		for(Node parameter : this.pm){
			this.pmnames.add(parameter.toString());
			
		}
		
		
	}

	public void setUsesRelation(){
		this.methods.removeAll(Collections.singleton(null));  
		for(MethodDeclaration m : this.methods){
			this.methodNames.add(m.getNameAsString());
				System.out.println(m.getParameterByType("A1"));
		}
	
		
	}
//	
//	
//	
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
			
			
			System.out.println("****");
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
