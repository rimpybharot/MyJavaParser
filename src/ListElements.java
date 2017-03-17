/* @author Rimpy Bharot */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import com.github.javaparser.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.*;

public class ListElements{

	private String className;

	public void listClasses(File file) throws ParseException {
		try {
			new VoidVisitorAdapter<Object>() {

				@Override
				public void visit(ClassOrInterfaceDeclaration n, Object arg) {
					super.visit(n, arg);
					setClassName(n.getName());
				}
			}.visit(JavaParser.parse(file), null);
		} catch (IOException e) {
			new RuntimeException(e);
		}
	}


	public void setClassName(SimpleName sn){
		this.className = "class " + sn.toString();
	}

	public String getClassName(){
		return this.className;
	}

	public static void main(String[] args) throws ParseException, IOException {
		FileHandling1 fh = new FileHandling1(args[0]);
		ListElements lc = new ListElements();
		List<File> javafiles = fh.getJavaFiles();


		for (File file : javafiles){
			

			PlantUMLDiargramGenerator pm = new PlantUMLDiargramGenerator(file);

			
//
		}
	}
}
