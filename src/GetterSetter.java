import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

public class GetterSetter {

	public List<MethodDeclaration> methodList = new ArrayList<>();
	List<FieldDeclaration> fieldList = new ArrayList<>();

	public void getGetterSetter(List<MethodDeclaration> methodList, List<FieldDeclaration> fieldList){

		this.methodList = methodList;
		this.fieldList = fieldList;

		String getMethodName = "";
		String setMethodName = "";


		ArrayList<MethodDeclaration> gettersSetters = new ArrayList<MethodDeclaration>();
		ArrayList<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();


		for(FieldDeclaration f : fieldList){
			for(VariableDeclarator v : f.getVariables()){
				getMethodName = "get"+v.getNameAsString().toLowerCase();
				setMethodName = "set"+v.getNameAsString().toLowerCase();
			}
			for(MethodDeclaration m : methodList){
				if(!gettersSetters.contains(getMethodName)){
					if(m.getNameAsString().toLowerCase().contains(getMethodName)){
						for(MethodDeclaration m1 : methodList){
							if(m1.getNameAsString().toLowerCase().contains(setMethodName)){
								this.methodList.remove(m);
								this.methodList.remove(m1);
								if(!f.isPublic()){
									gettersSetters.add(m);
									gettersSetters.add(m1);
									fields.add(f);
									break;
								}
							}
						}
					}
				}
				break;
			}
		}
		
		changeField(fields, gettersSetters);
	}

	public void changeField(ArrayList<FieldDeclaration> fields, ArrayList<MethodDeclaration> gettersSetters) {
		for(FieldDeclaration f : fields){
			if(!f.isPublic()){
				this.fieldList.remove(f);
				f.setModifiers(EnumSet.of(Modifier.PUBLIC));
				this.fieldList.add(f);
			}
		}
		this.methodList.removeAll(gettersSetters);
	}

	public List<FieldDeclaration> getFields(){
		this.fieldList.removeAll(Collections.singleton(null));
		return this.fieldList;
	}
	public List<MethodDeclaration> getMethods(){
		this.methodList.removeAll(Collections.singleton(null));

		return this.methodList;
	}
}
