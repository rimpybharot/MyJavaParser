import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		List<MethodDeclaration> finalMethods = new ArrayList<>();

		for(Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry:
			this.classesMethods.entrySet()){
			if(entry.getKey().getNameAsString().equals(interfaceName)){
				interfaceMethods = entry.getValue();
			}
			if(entry.getKey().getNameAsString().equals(className)){
				classMethods = entry.getValue();
				classesMethodsRefined = entry.getValue();
			}

		}

		List<MethodDeclaration> commonMethods = new ArrayList<>();

		for (MethodDeclaration interfaceMethod : interfaceMethods){
			if(!interfaceMethod.isAbstract()){
				for(MethodDeclaration classMethod : classMethods){
					if (interfaceMethod.getNameAsString().equals(classMethod.getNameAsString())){
						commonMethods.add(interfaceMethod);
						break;
					}
					else{
						////System.out.println("not found");
					}
				}
			}
		}

		Iterator<MethodDeclaration> allMethods = classesMethodsRefined.iterator();

		while (allMethods.hasNext()) {
			MethodDeclaration all = allMethods.next();
			Iterator<MethodDeclaration> commons = commonMethods.iterator();
			boolean isNotCommon = true;

			while (commons.hasNext()) {
				MethodDeclaration common = commons.next();
				if((all.getNameAsString().equals(common.getNameAsString()))){
					isNotCommon = false;
					break;
				}
			}
			if(isNotCommon && (!finalMethods.contains(all))){
				finalMethods.add(all);
			}
		}
		setRefinedMethods(className, finalMethods);
		return finalMethods;
	}

	private void setRefinedMethods(String className, List<MethodDeclaration> finalMethods) {
		for(Entry<ClassOrInterfaceDeclaration, List<MethodDeclaration>> entry:
			this.classesMethods.entrySet()){
			if((!entry.getKey().isInterface())&&(entry.getKey().getNameAsString().equals(className))){
				entry.setValue(finalMethods);
			}
		}
	}

	public HashMap<ClassOrInterfaceDeclaration, List<MethodDeclaration>> getRefinedMethods(){
		return this.classesMethods;

	}

}
