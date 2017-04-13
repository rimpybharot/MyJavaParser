import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationChecker {


	private List<MethodDeclaration> methods;

	private List<String> methodNames;
	private NodeList<Parameter> parameters;
	private List<String> pmnames;
	private List<String> parameterType;
	private List<String> usesRelation;

	public MethodDeclarationChecker(){};


	public MethodDeclarationChecker(ClassOrInterfaceDeclaration n){
		this.methods = new ArrayList<MethodDeclaration>();
		this.methodNames = new ArrayList<String>();
		this.parameters = new NodeList<>();
		//		this.pmnames = new ArrayList<String>();
		this.parameterType = new ArrayList<String>();
		this.usesRelation = new ArrayList<String>();

		new VoidVisitorAdapter<Object>() {
			@Override
			public void visit(MethodDeclaration n, Object arg) {
				super.visit(n, arg);
				setClassesMethods(n);
			}
		}.visit(n, null);
	}

	public String methodStringCreator(MethodDeclaration n){


		String type = null;
		String name = null;
		String modifier = null;


		String p1 ="";
		parameters = n.getParameters();


		boolean first = true;
		for (Parameter p : parameters) {
			if (!first) {
				p1 += " , "+p.getNameAsString() + ": " + p.getType();
			}
			else{
				p1 += p.getNameAsString() + ": " + p.getType();
			}

			first = false;
		}
		//		for(Parameter p : parameters){
		//			p1 += p.getNameAsString() + ": " + p.getType();
		//
		//		}
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
		return modifier +" " + name + " : " + type;

	}


	public void setClassesMethods(MethodDeclaration n) {
		// TODO Auto-generated method stub
		this.methods.add(n);

	}


	//	public void setMethods(MethodDeclaration n){
	//		this.methods.add(n);
	//
	//	}
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
		// TODO Auto-generated method stub
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