import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class Realization {

	HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> classesMethods = new HashMap<>();
	List<MethodDeclaration> classesMethodsRefined = new ArrayList<>();

	public Realization(HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> classesMethods,
			HashMap<ClassOrInterfaceDeclaration, List<String>> classesImplements) {

		this.classesMethods = classesMethods;


		for(Entry<ClassOrInterfaceDeclaration, List<String>> entry: classesImplements.entrySet()){
			this.classesMethodsRefined = this.classesMethods.get(entry.getKey());


			if(!entry.getValue().isEmpty()){
				for(String interfaceName : entry.getValue()){
					classesMethodsRefined= getCommonMethods(entry.getKey().getNameAsString(), interfaceName);
				}
			}
			if(!classesMethodsRefined.isEmpty()){
				entry.getValue().removeAll(classesMethodsRefined);
			}
		}
		
	}

	public List<MethodDeclaration> getCommonMethods(String className, String interfaceName) {
		List<MethodDeclaration> classMethods = new ArrayList<>();
		List<MethodDeclaration> interfaceMethods = new ArrayList<>();
		List<MethodDeclaration> classesMethodsRefined = new ArrayList<>();

		//		////System.out.println("My class name " + className);
		//		////System.out.println("My interface name " + interfaceName);

		for(Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry:
			this.classesMethods.entrySet()){
			if(entry.getKey().getNameAsString().equals(interfaceName)){
				//				////System.out.println("Found the interface");
				interfaceMethods = entry.getValue();
			}
			if(entry.getKey().getNameAsString().equals(className)){
				//				////System.out.println("Found the class");
				classMethods = entry.getValue();
			}

		}
		
		List<MethodDeclaration> commonMethods = new ArrayList<>();
		
		for (MethodDeclaration interfaceMethod : interfaceMethods){
//			////System.out.println("interface method " +interfaceMethod.getNameAsString());
			for(MethodDeclaration classMethod : classMethods){
//				////System.out.println("checking method " + classMethod.getNameAsString());
				if (interfaceMethod.getNameAsString().equals(classMethod.getNameAsString())){
//					////System.out.println("Found " + interfaceMethod.getNameAsString());
					commonMethods.add(interfaceMethod);
					break;
				}
				else{
					//System.out.println("not found");
				}
			}
		}
		for(MethodDeclaration m : commonMethods){

			System.out.println("common for "+ className + "   " +m.getNameAsString());
			
		}
		for(MethodDeclaration m : interfaceMethods){

			System.out.println("interfaceMethods for " + interfaceName + "  " +m.getNameAsString());
		}

		for(Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry:
			this.classesMethods.entrySet()){
			
			entry.getValue().removeAll(commonMethods);
			
			if(entry.getKey().getNameAsString().equals(className)){
				this.classesMethods.put(entry.getKey(), entry.getValue().removeAll(commonMethods));
			}

		}

		return commonMethods;

	}
	
	public HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> getRefinedMethods(){
		return this.classesMethods;

	}

}
