import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableParser {

	private static  Map<String, String[]> variableMap = new HashMap<>();

	final List<String> fields = new ArrayList<String>();

	public void listVariables(File file) throws ParseException, IOException {

		new VoidVisitorAdapter<Object>() {
			public void visit(FieldDeclaration n, Object arg) {
				super.visit(n, arg);

				fields.add(n.toString());
			}
		}.visit(JavaParser.parse(file), null);


//		var(fields);
		listComments(file);

	}


	public void listComments(File file) throws ParseException, IOException {

		new VoidVisitorAdapter<Object>() {
			public void visit(com.github.javaparser.ast.comments.LineComment n, Object arg) {
				super.visit(n, arg);

				System.out.println(n.toString());
				
			}
		}.visit(JavaParser.parse(file), null);



	}
	
	public void var(List<String> fields){
		
		for (String s : fields){
//			System.out.println(s);
			s = s.trim();
			s= s.replace(";", "");
			s = s.replace("\\/$", "");
			System.out.println(s);
			
//			boolean publicVar = false;
//			boolean packageScopeVar = false;
//			String strPublic = "// public attribute via setters and getters";
//			String strPackageScope = "// package scope";
//			if(s.contains(strPublic)){
//				s = (s.trim()).replace(strPublic, "");}
//				publicVar = true;
//			if(s.contains("// package scope")){
//				System.out.println(s);
//				s = (s.trim()).replace(s,strPackageScope);
//				packageScopeVar = true;
//			}
//			String[] results = s.split(" ");
//			
//			if(publicVar == true){
//				results[0] = "public";
//			}
//			if(packageScopeVar == true){
//				for (String st : results){
//				System.out.println(st);}
//				results[] = "package private";
//			}
			
//			switch(results[0]){
//			case "public" : results[0]  = "+"; break;
//			case "private" : results[0] = "-"; break;
//			case "protected" : results[0] = "#"; break;
//			case "package private" : results[2] = "~";break;
//			default: results[0] = "-"; break;
//			}
//			
//			String variableNotation = results[0]+results[2];
//			System.out.println(variableNotation);
//			
			
//			  for (String result : results) {
//				  System.out.println("~~~~~~~");
//			      System.out.println(result);
//			  
//			}
		}
				
//				System.out.print(results);
//				this.variableMap(s, results);
				

//			}
			
//			System.out.println(this.variableMap);
//		}
	}


	private void variableMap(String s, String[] split) {
		// TODO Auto-generated method stub
		
	}


}
