import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class DependencyGetter {
	private List<String> dependencies = new ArrayList<String>();

	public DependencyGetter(ClassOrInterfaceDeclaration classID, List<String> interfaces) {
		String className = classID.getNameAsString();
		
		
		

		if(!classID.isInterface()){
			new VoidVisitorAdapter<Object>() {
				@Override
				public void visit(MethodDeclaration n, Object arg) {
					super.visit(n, arg);
					
					
					
					VariableDeclaration vd = new VariableDeclaration();
					String methodDependency = vd.getDependenciesFromMethods(n, interfaces);
					if(methodDependency!=null){
					dependencies.add("\n"+className + "..>" + methodDependency+":uses\n");
					}

					
					
					NodeList<Parameter> parameters = n.getParameters();
					for(Parameter p : parameters){
						if(interfaces.toString().contains(p.getType().toString())){
							dependencies.add("\n"+className + "..>" + p.getType().toString()+":uses\n");
						}
					
					}

				}
			}.visit(classID, null);
		}

			new VoidVisitorAdapter<Object>() {

				@Override
				public void visit(ConstructorDeclaration n, Object arg) {
					super.visit(n, arg);
					NodeList<Parameter> parameters = n.getParameters();
					for(Parameter p : parameters){
						if(interfaces.contains(p.getType().toString())){
							dependencies.add("\n"+className + "..>" + p.getType().toString()+":uses\n");
						}

					}

				}
			}.visit(classID, null);
		}

	public List<String> getUses(){


		Set<String> hs = new HashSet<>();
		hs.addAll(dependencies);
		dependencies.clear();
		dependencies.addAll(hs);
		this.dependencies.removeAll(Collections.singleton(null));
		return this.dependencies;
	}
}
