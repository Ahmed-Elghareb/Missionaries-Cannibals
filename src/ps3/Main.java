package ps3;

import java.util.List;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Side[] state = new Side[2];
		state[0] = new Side(3,3); // 0 : right side, start with missionaries then cannibals. 
		state[1] = new Side(0,0);
		
		Node root = new Node (state, null, null, 0);
		
		Node goal = BFSearch.search(root);
		
		BFSearch.goalPath(goal);
		

		
		
        
	}
	
	


}
