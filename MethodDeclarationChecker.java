import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodDeclarationChecker {



	private List<SimpleName> methods = new ArrayList<SimpleName>();

	public MethodDeclarationChecker(File file) throws ParseException, IOException {
		// TODO Auto-generated constructor stub
		List<SimpleName>methods = new ArrayList<SimpleName>();
		new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodDeclaration n, Object arg) {
					super.visit(n, arg);
				methods.add(n.getName());
				}
			}.visit(JavaParser.parse(file), null);
			System.out.println(methods);
	}
	
	public void setMethods(List<SimpleName> methods2)
	{
		this.methods = methods2;
	}
	
	public List<SimpleName> getMethods(){
		return this.methods;
	}

	public void getGetters(){
		for (SimpleName method: this.methods){
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
		for (SimpleName method: this.methods){
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
