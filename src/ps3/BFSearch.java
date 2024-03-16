package ps3;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BFSearch {
	
	
	public static Node search(Node root){
		List<Node> fringe= new ArrayList<Node>();
		List<Node> closed= new ArrayList<Node>();

		fringe.add(root);
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
    		while(!fringe.isEmpty()) {		
    			Node node = fringe.get(0);
    			writer.println(node);
    			closed.add(node);
    			fringe.remove(0);
    			List<Action> actions = node.get_actions();
    			for(Action act: actions) {
    				Node child = node.get_child(act);
    				if( closed.contains(child)  ) { //closed.contains(child)    containsNode (closed, child)
    					continue;
    				}
    				else if (child.is_failure()) {
    					continue;
    				}
    				else if (child.is_goal()) {
    	    			writer.println(child);
    					return child;
    				}
    				else {
    					fringe.add(child);
    				}
    			}		
    		}
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

		return null;
	}
	
	public static void goalPath(Node goal) {
		List<Node> path= new ArrayList<Node>();
		
		Node parent = goal.get_parent();
		path.add(goal);
		while(! parent.is_root()) {
			path.add(parent);
			parent = parent.get_parent();
		}
		path.add(parent);
		
		for (int i = path.size()-1; i >= 0; i --) {
			try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt", true))) {
				writer.println(path.get(i));
				System.out.println(path.get(i));
				
			} catch (IOException e) {
				System.err.println("Error writing to file: " + e.getMessage());
			}
		}
		
	}
	private static boolean containsNode(List<Node> list, Node node) {
		for(Node n:list) {
			if (n.equals(node)) {
				return true;
			}
		}
		return false;
	}
	

}
