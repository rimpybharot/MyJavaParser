import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableDeclaration {
	
	private String methodDependency = null;

	public VariableDeclaration() {
		// TODO Auto-generated constructor stub
	}

	public String getDependenciesFromMethods(MethodDeclaration method, List<String> interfaces) {

		new VoidVisitorAdapter<Object>() {
			public void visit(VariableDeclarationExpr n, Object arg) {

			
				if(interfaces.contains(n.getElementType().toString())){
					methodDependency = n.getElementType().toString();
				}
				
			}
		}.visit(method, null);

		return methodDependency;
	}
}
