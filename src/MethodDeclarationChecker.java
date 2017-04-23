import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationChecker {


	private List<MethodDeclaration> methods;

	private List<String> methodNames;
	private NodeList<Parameter> parameters;
	private List<String> pmnames;
	private List<String> parameterType;

	public MethodDeclarationChecker(){};


	public MethodDeclarationChecker(ClassOrInterfaceDeclaration n){
		this.methods = new ArrayList<MethodDeclaration>();
		this.methodNames = new ArrayList<String>();
		this.parameters = new NodeList<>();
		this.parameterType = new ArrayList<String>();

		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				setClassesMethods(n);
			}
		}.visit(n, null);
	}

	public String methodStringCreator(MethodDeclaration n){

		
		if(n.isPublic()){

		String type = null;
		String name = null;
		String modifier = null;


		String p1 ="";
		parameters = n.getParameters();


		boolean first = true;
		
		System.out.println(n.getNameAsString());
		
		for (Parameter p : parameters) {
			if (first==false) {
				System.out.println(first);
				p1 += " , "+p.getNameAsString() + ": " + p.getType();
			}
			else{
				p1 += p.getNameAsString() + ": " + p.getType();
			}

			first = false;
		}
		name = n.getNameAsString() + "("+p1+")";
		type = n.getType().toString();
		modifier = "+";
		return modifier +" " + name + " : " + type;
		}
		else{
			return " ";
		}

	}


	public void setClassesMethods(MethodDeclaration n) {
		this.methods.add(n);
	}


	public List<MethodDeclaration> getClassesMethods(){
		return this.methods;

	}

	public void setMethodNames(String methodName){
		this.methodNames.add(methodName);
	}

	public List<String> getMethodNames(){
		this.methodNames.removeAll(Collections.singleton(null));

		return this.methodNames;

	}

	public void setParamaterType(String parameterType) {
		this.parameterType.add(parameterType);
	}

	public void setUsesRelation(){
		this.methods.removeAll(Collections.singleton(null));  
		for(MethodDeclaration m : this.methods){
			this.methodNames.add(m.getNameAsString());
		}
	}

	public List<String> getParameters(){
		this.pmnames.removeAll(Collections.singleton(null)); 
		return pmnames;
	}

}