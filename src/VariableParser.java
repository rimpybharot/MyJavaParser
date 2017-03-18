import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sourceforge.plantuml.graph.LenghtLinkComparator;

public class VariableParser {

	private static  Map<String, String[]> variableMap = new HashMap<>();


	public String listVariables(File file) throws ParseException, IOException {

		List<String> fields = new ArrayList<String>();

		new VoidVisitorAdapter<Object>() {

			String type = null;
			String name = null;
			String modifier = null;

			public void visit(FieldDeclaration n, Object arg) {
				name = n.getVariable(0).toString();
				type = n.getElementType().toString();
				EnumSet<Modifier> e = n.getModifiers();
				for (Modifier m : e){
					modifier = (m.toString()).toLowerCase();
				}
				if(modifier != null){
					fields.add(modifier);
				}
				if(name!=null){
					fields.add(name);	
				}
				if(type!=null){
					fields.add(type);
				}

			}
		}.visit(JavaParser.parse(file), null);
//		System.out.println(var(fields));
		return var(fields);
}


	public String var(List<String> variableDefinition){
		String finalVariable = null;

		if(variableDefinition.size()>0){
			switch(variableDefinition.get(0).toString()){
			case "public" : variableDefinition.set(0, "+"); break;
			case "private" : variableDefinition.set(0, "-"); break;
			case "protected" : variableDefinition.set(0, "#"); break;
			case "package private" : variableDefinition.set(0, "~"); break;
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
