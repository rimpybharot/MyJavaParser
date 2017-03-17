
import java.io.File;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassesDeclarationChecker {
	
	private String className;
	 
    public ClassesDeclarationChecker(File file) throws ParseException {
            try {
                new VoidVisitorAdapter<Object>() {

					@Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                        super.visit(n, arg);
//                        if (n instanceof Interface){
//                        	
//                        }
                        if(n.isInterface()){
                        	setClassName("interface " + n.getNameAsString());
                        }
                        else{
                        	setClassName("class " + n.getNameAsString());
                        }
                        
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }

 
    public void setClassName(String sn){
    	this.className = sn;
    }
    
    public String getClassName(){
    	return this.className;
    }

}
