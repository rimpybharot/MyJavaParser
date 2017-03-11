/* @author Rimpy Bharot*/

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ListClasses{
 
    public void listClasses(File file) throws ParseException {
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" Class " + n.getName());
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }

 
    
    public void listMethodCalls(File file) throws ParseException {
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodCallExpr n, Object arg) {
                        super.visit(n, arg);
                        System.out.println(" [L " + n.getBegin() + "] " + n);
                    }
                }.visit(JavaParser.parse(file), null);
                System.out.println(); // empty line
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }
    

	public static void main(String[] args) throws ParseException {
        
    	FileHandling fh = new FileHandling(args[0]);
    	ListClasses lc = new ListClasses();
    	List<File> javafiles = fh.getJavaFiles();
    	for (File file : javafiles)
    	{
    		lc.listClasses(file);
    		lc.listMethodCalls(file);
    	}
    	
    	
    }
}
