import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableParser {



	List<String> association = new ArrayList<>();
	HashMap<String, List<String>> associations = new HashMap<String, List<String>>();
	String cardinality;
	List<FieldDeclaration> removeFields;

	public VariableParser(ClassOrInterfaceDeclaration cd, List<String> classifierNames) {
		cardinality = "";
		removeFields = new ArrayList<FieldDeclaration>();
		System.out.println("*****\nClass " + cd.getNameAsString());
		new VoidVisitorAdapter<Object>() {
			public void visit(FieldDeclaration fd, Object arg) {
				for(VariableDeclarator v : fd.getVariables()){
					String typeOfVariable = v.getType().getClass().getSimpleName();
					String relatedClass = new String();
					switch(typeOfVariable){
					case "ClassOrInterfaceType" :
						String varaibleType = v.getType().toString();
						if(varaibleType.contains("Collection")
								|| varaibleType.contains("Set")
								|| varaibleType.contains("HashMap")
								|| varaibleType.contains("Map")
								|| varaibleType.contains("List")
								|| varaibleType.contains("ArrayList")){
//							System.out.println(varaibleType);
							relatedClass = (varaibleType.substring(varaibleType.indexOf("<") + 1,
									varaibleType.indexOf(">")));
//							System.out.println("Related Class is " + relatedClass);
							cardinality = "0..*";
							setAssociation(cd, relatedClass , cardinality, v.getName().toString());
							setFieldsToBeRemoved(fd);

						}
						else if(varaibleType.contains("[") && varaibleType.contains("]")){
							cardinality = (varaibleType.substring(varaibleType.indexOf("[") + 1,
									varaibleType.indexOf("]")));	
							relatedClass = fd.getElementType().toString();
							setFieldsToBeRemoved(fd);

							setAssociation(cd, relatedClass , cardinality, v.getName().toString());
						}
						else if(!varaibleType.contains("String")){
							relatedClass = fd.getElementType().toString();
							cardinality = "1";
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
		List<String> values = new ArrayList<>();

		values.add(classifier);
		values.add(cardinality);
		this.associations.put(cd.getNameAsString(), values);

		if(cardinality==""){
			this.association.add("\n"+cd.getNameAsString()+"--"+classifier
					//					+":"+objectName
					+"\n");
		}
		else{
			this.association.add("\n"+cd.getNameAsString()+"--"+"\""+cardinality+"\""+classifier
					//				+":"+objectName
					+"\n");
		}

	}
	public List<String> getAssociation() {
		// TODO Auto-generated method stub
		return this.association;

	}
	
	public HashMap<String, List<String>> getAssociations() {
		// TODO Auto-generated method stub
		return this.associations;

	}

	public List<FieldDeclaration> getFieldsToBeRemoved(){
		return this.removeFields;
	}
}
