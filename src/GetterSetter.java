import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;

public class GetterSetter {
	
	public List<MethodDeclaration> methodList = new ArrayList<>();
	List<FieldDeclaration> fieldList = new ArrayList<>();
	private Enum PUBLIC;
	private Enum PRIVATE;

	
	public void getGetterSetter(List<MethodDeclaration> methodList, List<FieldDeclaration> fieldList){

		this.methodList = methodList;
		this.fieldList = fieldList;

		boolean getterExists = false;
		boolean setterExists = false;

		String getMethodName = "";
		String setMethodName = "";
		MethodDeclaration getMethod = null ;
		MethodDeclaration setMethod = null;
		
		
		HashMap<String, String> getterSetterMap = new HashMap<String, String>();

		for(MethodDeclaration m : methodList){

			String methodString = m.getNameAsString();

			if(!getterSetterMap.entrySet().contains(getMethodName)){
				if(methodString.toLowerCase().contains("get")){
					getMethodName = methodString;
					getMethod = m;
					getterExists = true;
					break;

				}
			}
		}


		if(getterExists){
			setMethodName = 's'+getMethodName.substring(1,getMethodName.length());
//			System.out.println(setMethodName);
			for(MethodDeclaration m : methodList){
				String methodString = m.getNameAsString();
//				System.out.println(methodString);
				if(methodString.toLowerCase().equals(setMethodName.toLowerCase())){
					setMethod = m;
					setterExists = true;
						break;
				}}
		}

		
		if(getterExists && setterExists){
			System.out.println("yes");
			getterSetterMap.put(getMethodName, setMethodName);
			FieldDeclaration fremove = getFieldGetSet(getMethod, setMethod, fieldList);
			if(fremove!=null){
				if(!fremove.isPublic()){
					this.fieldList.remove(fremove);
					fremove.setModifiers(EnumSet.of(Modifier.PUBLIC));
					this.fieldList.add(fremove);
					
				}
			this.methodList.remove(getMethod);
			this.methodList.remove(setMethod);
			}

		}
	}


	public FieldDeclaration getFieldGetSet(MethodDeclaration getMethod, MethodDeclaration setMethod, List<FieldDeclaration> fieldList){

		Optional<BlockStmt> getBody = getMethod.getBody();
		Optional<BlockStmt> setBody = setMethod.getBody();

		for(FieldDeclaration f : fieldList){
			for(VariableDeclarator v : f.getVariables()){
				System.out.println(f.getModifiers());

				if((getBody.toString().contains(v.getNameAsString()))&&(setBody.toString().contains(v.getNameAsString()))){
					System.out.println(f.getModifiers());
					return f;
				}
			}
		}
		return null;
	}
	
	public List<FieldDeclaration> getFields(){
		return this.fieldList;
	}
	public List<MethodDeclaration> getMethods(){
		return this.methodList;
	}
}
