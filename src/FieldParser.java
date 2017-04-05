import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldParser{
	private List<String> variableNames;
	private NodeList<FieldDeclaration> fields;
	private List<String> association;


	public FieldParser() {}


	public FieldParser(ClassOrInterfaceDeclaration cd) {
		variableNames = new ArrayList<String>();
		fields = new NodeList<>();
		association = new ArrayList<String>();


		new VoidVisitorAdapter<Object>() {
			public void visit(FieldDeclaration n, Object arg) {
			setFields(n);
			}
		}.visit(cd, null);
	}

	public void setFields(FieldDeclaration n) {
		
		this.fields.add(n);
	}

	public List<FieldDeclaration> getFields(){
		return this.fields;
	}

	public String variablestringCreator(FieldDeclaration n){
		String type = null;
		String name = null;
		String modifier = "";
		for(VariableDeclarator v : n.getVariables()){
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
		
		return modifier +" " + name + " : " + type;
	}
	






}
