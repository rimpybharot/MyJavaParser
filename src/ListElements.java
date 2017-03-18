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
	public static void main(String[] args) throws ParseException, IOException {
		FileHandling1 fh = new FileHandling1(args[0]);
		List<File> javafiles = fh.getJavaFiles();

		PlantUMLNotationMaker p = new PlantUMLNotationMaker(javafiles);

		new PlantUMLDiargramGenerator(p.getumlTextNoation());


	}
}
