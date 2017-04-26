import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableParser {
	List<String> association = new ArrayList<>();
	HashMap<String, ArrayList<String>> associations = new HashMap<String, ArrayList<String>>();
	String cardinality;
	List<FieldDeclaration> removeFields;
	ArrayList<String> relatedClasses = new ArrayList<String>();

	public VariableParser(ClassOrInterfaceDeclaration cd, List<String> classifierNames) {
		cardinality = "";
		removeFields = new ArrayList<FieldDeclaration>();
		new VoidVisitorAdapter<Object>() {
			public void visit(FieldDeclaration fd, Object arg) {
				for(VariableDeclarator v : fd.getVariables()){
					String typeOfVariable = v.getType().getClass().getSimpleName();
					String relatedClass = new String();
					switch(typeOfVariable){
					case "ClassOrInterfaceType" :
						//					if(typeOfVariable=="ClassOrInterfaceType"){
						String variableType = fd.toString();
						if(variableType.contains("Collection")
								|| variableType.contains("Set")
								|| variableType.contains("HashMap")
								|| variableType.contains("Map")
								|| variableType.contains("List")
								|| variableType.contains("ArrayList")){
							relatedClass = (variableType.substring(variableType.indexOf("<") + 1,
									variableType.indexOf(">")));
							cardinality = "0..*";
							setAssociation(cd, relatedClass , cardinality, v.getName().toString());
							setFieldsToBeRemoved(fd);
						}
						else if(variableType.contains("[") && variableType.contains("]")){
							cardinality = (variableType.substring(variableType.indexOf("[") + 1,
									variableType.indexOf("]")));	
							relatedClass = fd.getElementType().toString();
							setFieldsToBeRemoved(fd);

							setAssociation(cd, relatedClass , cardinality, v.getName().toString());
						}
						//						else if(!variableType.contains("String")){
						else if(classifierNames.contains(fd.getElementType().toString())){
							relatedClass = fd.getElementType().toString();
							//							cardinality = "1";
							cardinality = "-"+v.getNameAsString();
							setFieldsToBeRemoved(fd);

							setAssociation(cd, relatedClass , cardinality, v.getName().toString());
						}
						break;
					default : ; break;
					}
				}
			}
		}.visit(cd, null);
	}

	public void setFieldsToBeRemoved(FieldDeclaration fd) {
		this.removeFields.add(fd);
	}

	public void setAssociation(ClassOrInterfaceDeclaration cd,
			String classifier, String cardinality, String objectName) {


		ArrayList<String> values = new ArrayList<String>();
		values.add(classifier);
		values.add(cardinality);
		values.add(objectName);
		this.relatedClasses.addAll(values);
		this.associations.put(cd.getNameAsString(), this.relatedClasses);
	}


	public HashMap<String, ArrayList<String>> getAssociations() {
		return this.associations;
	}

	public List<FieldDeclaration> getFieldsToBeRemoved(){
		return this.removeFields;
	}
}
