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
    
//    public Map<String, Object> parseFile(File file) throws IOException, ParseException {
//    	final CompilationUnit unit = JavaParser.parse(file);
//    	final List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
//    	final List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
//    	final Map<String, Object> ret = new HashMap<String, Object>();
//    	ret.put("fields", fields);
//    	ret.put("methods", methods);
//
//    	new VoidVisitorAdapter<Object>() {
//    		@Override
//    		public void visit(ClassOrInterfaceDeclaration node, Object arg) {
//    			if (ret.containsKey("classOrInterface")) {
//    				return;
//    			}
//    			
//    			ret.put("classOrInterface", node);
////    			super.visit(node, arg);
//    		}
//
//    		@Override
//    		public void visit(FieldDeclaration node, Object arg) {
//    			fields.add(node);
////    			super.visit(node, arg);
//    		}
//
//    		@Override
//    		public void visit(MethodDeclaration node, Object arg) {
//    			methods.add(node);
////    			super.visit(node, arg);
//    		}
//    	}.visit(unit, null);
//
//    	return ret;
//    }
//     
   
	public static void main(String[] args) throws ParseException, IOException {
        
    	FileHandling1 fh = new FileHandling1(args[0]);
    	ListElements lc = new ListElements();
    	List<File> javafiles = fh.getJavaFiles();
    	VariableParser vp = new VariableParser();


    	for (File file : javafiles){
    		
    		
    		
//    		System.out.println(file.toString().substring(file.toString().lastIndexOf("\\")+1));
    		
    		lc.listClasses(file);
//    		lc.listVariables(file);
//
//    		MethodDeclarationChecker m = new MethodDeclarationChecker(file);
//    		m.getGetters();
//    		m.getSetters();
    		vp.listVariables(file);
    		String fileName = (file.getName()).replaceAll("java", "txt");

        	try{
        	    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        	    writer.println(lc.getClassName());
        	    writer.close();
        	} catch (IOException e) {
        	   System.err.println("IO Exception");
        	}
        	File f = new File(fileName);
        	PlantUMLDiargramGenerator pdg = new PlantUMLDiargramGenerator(f);

    	}
		    

    	
    	
	}
    }
