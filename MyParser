import com.github.javaparser.ast.Node;

public class MyParser{
	
	public interface NodeHandler {
	        boolean handle(Node node);
	    }
	 
	    private NodeHandler nodeHandler;
	 
	    public void NodeIterator(NodeHandler nodeHandler) {
	        this.nodeHandler = nodeHandler;
	    }
	 
	    public void explore(Node node) {
	        if (nodeHandler.handle(node)) {
	            for (Node child : node.getChildNodes()) {
	                explore(child);
	            }
	        }
	    }
	}
	
