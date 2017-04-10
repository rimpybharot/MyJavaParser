import java.util.List;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FieldParser{
	private NodeList<FieldDeclaration> fields;
	public FieldParser() {}
	public FieldParser(ClassOrInterfaceDeclaration cd) {
		fields = new NodeList<>();
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
			return modifier +" " + name + " : " + type;
		}
		else if(n.isPublic()){
			modifier ="+";
			return modifier +" " + name + " : " + type;
		}
		else{
			return "";
		}
	}
}
