import java.io.File;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableParser{
	private List<String> variables;
	private NodeList<VariableDeclarator> variablesDetails;



	public void listVariables(ClassOrInterfaceDeclaration cd) {
		variables = new ArrayList<String>();
		variablesDetails = new NodeList<VariableDeclarator>();


		new VoidVisitorAdapter<Object>() {

			String type = null;
			String name = null;
			String modifier = "";

			public void visit(FieldDeclaration n, Object arg) {
				List<String> fields = new ArrayList<String>();

				variablesDetails = n.getVariables();
				setMultiplicity(variablesDetails);


				for(VariableDeclarator v : variablesDetails){
//					System.out.println("name is " + v.getNameAsString());
//					System.out.println("type is " + v.getType());

					name = v.getNameAsString();
					type = v.getType().toString();
				}
					if(n.isPrivate()){
						modifier ="-";
					}
					else if(n.isPublic()){
						modifier ="+";
					}
					else if(n.isProtected()){
						modifier ="#";
					}
					fields.add(modifier);
					if(name!=null){
						fields.add(name);	
					}
					if(type!=null){
						fields.add(type);
					}
					setVariables(modifier +" " + name + " : " + type);
				}
			}.visit(cd, null);
		}

		public void listParameters(ClassOrInterfaceDeclaration cd) {
			variables = new ArrayList<String>();
			variablesDetails = new NodeList<VariableDeclarator>();


			new VoidVisitorAdapter<Object>() {
				public void visit(Parameter n, Object arg) {
					System.out.println("the type is " + n.getType().toString());
				}
			}.visit(cd, null);
		}

		protected void setMultiplicity(NodeList<VariableDeclarator> variablesDetails) {
			// TODO Auto-generated method stub


			for(VariableDeclarator vd : variablesDetails){
				//			System.out.println("vd " + vd);
				new VoidVisitorAdapter<Object>() {
					public void visit(VariableDeclarator vd, Object arg) {
						Object a = vd.getType();
						Type t = vd.getType().getElementType();
//						System.out.println(a.getClass().getSimpleName());
//						System.out.println("type of variable " + t);
//						System.out.println("Element type " + a.getClass());
//						System.out.println(vd1.getClass().getName());
//						System.out.println(t.toString().contains("Collection"));
					}
				}.visit(vd, null);
			}
		}


		public void setVariables(String string) {
			// TODO Auto-generated method stub
			this.variables.add(string);

		}

		public List<String> getVariables(){
			return this.variables;
		}


		public String createVarString(List<String> variableDefinition){
			String finalVariable = null;

			if(variableDefinition.size()>0){
				switch(variableDefinition.get(0).toString()){
				case "public" : variableDefinition.set(0, "+"); break;
				case "private" : variableDefinition.set(0, "-"); break;
				case "protected" : variableDefinition.set(0, "#"); break;
				case "package private" : variableDefinition.set(0, "~"); break;
				case "": variableDefinition.set(0, "~"); break;
				default: variableDefinition.set(0, "-"); break;
				}
				String name = variableDefinition.get(2).toString();
				String type = variableDefinition.get(1).toString();
				String modifier = variableDefinition.get(0).toString();
				finalVariable = modifier +" " + type + " : " + name;
				return finalVariable;
			}
			else{
				return "";
			}



		}

		public void listComments(File file) throws ParseException, IOException {

			new VoidVisitorAdapter<Object>() {
				public void visit(com.github.javaparser.ast.comments.LineComment n, Object arg) {
					super.visit(n, arg);

					System.out.println(n.toString());

				}
			}.visit(JavaParser.parse(file), null);



		}


	}
