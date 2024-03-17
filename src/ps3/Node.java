package ps3;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ps3.Action;


public class Node {
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET = "\u001B[0m";

	private Side[] state;
	private Node previous;
	private Action action;
	private int cost;
	private boolean boatOnRight ;

	public Node(Side[] st, Node prev, Action act, int cst) {
		state = st;
		previous = prev;
		action = act;
		cost = cst;
		boatOnRight = bootRightSide();
	}
	public boolean bootRightSide() {
		boatOnRight = is_root() || get_action().name().startsWith("R");
		return boatOnRight;
	}

	public Node get_parent() {
		return previous;
	}

	public int get_cost() {
		return cost;
	}

	public Action get_action() {
		return action;
	}
	public Side[] get_state() {
		return state;
	}
	public boolean is_goal() {
		return state[1].get_all() == 6;
	}

	public boolean is_failure() {
		return (state[1].get_c() > state[1].get_m() && state[1].get_m() > 0) || (state[0].get_c() > state[0].get_m() && state[0].get_m() > 0);
	}
	public boolean is_root() {
		return (state[0].get_c() == state[0].get_m()) && (state[0].get_m() == 3) && (state[0].get_all() == 6);
	}
	
	public List<Action> get_actions() {
		/*
		 * I will try to use combination instead of hardcoding the problem
		 */
		List <Action> actions = new ArrayList<>(); 
        String right = "M".repeat(state[0].get_m()) + "C".repeat(state[0].get_c());
        String left = "C".repeat(state[1].get_c()) + "M".repeat(state[1].get_m());
//        
//        String[] combinations = Node.generateCombinations(input);
//        for (String combination : combinations) {
//            System.out.println(combination);
//            System.out.println(1);
//
//        }
		if (this.is_root () || (this.action.name().startsWith("R"))) { // Boot on the right side, and all 6 personnel are on the right side as well.  
			List<String> combinations = generateCharacterCombinations(right);
			combinations = removeDuplicates(combinations);
	        actions = actionMapper(false, combinations);
		}
		else if (  (this.action.name().startsWith("L"))) {
			List<String> combinations = generateCharacterCombinations(left);
			combinations = removeDuplicates(combinations);
	        actions = actionMapper(true, combinations);
		}
		return actions;
		
	}
	
	

	public Node get_child(Action act) {
		Side[] child_state = new Side[2];
		child_state[0] = new Side( this.state[0].missionaries,this.state[0].cannibals);
		child_state[1] = new Side( this.state[1].missionaries,this.state[1].cannibals);

			if (this.is_root ()) {
				switch(act) {
				case R_1C:
					child_state[0].inc_c();
					child_state[1].dec_c();
					break;
				case R_2C:
					child_state[0].inc_c();
					child_state[0].inc_c();
					child_state[1].dec_c();
					child_state[1].dec_c();
					break;
				case R_1C1M:
					child_state[0].inc_c();
					child_state[0].inc_m();
					child_state[1].dec_c();
					child_state[1].dec_m();
					break;  
				case R_1M:
					child_state[0].inc_m();
					child_state[1].dec_m();
					break;
				case R_2M:
					child_state[0].inc_m();
					child_state[0].inc_m();
					child_state[1].dec_m();
					child_state[1].dec_m();
					break;
				case L_1C:
					child_state[1].inc_c();
					child_state[0].dec_c();
					break;		     
				case L_1C1M:
					child_state[1].inc_c();
					child_state[1].inc_m();
					child_state[0].dec_c();
					child_state[0].dec_m();
					break;		     
				case L_1M:
					child_state[1].inc_m();
					child_state[0].dec_m();
					break;
				case L_2M:
					child_state[1].inc_m();
					child_state[1].inc_m();
					child_state[0].dec_m();
					child_state[0].dec_m();
					break;
				case L_2C:
					child_state[1].inc_c();
					child_state[1].inc_c();
					child_state[0].dec_c();
					child_state[0].dec_c();
					break;	
				default:
					break;
	         		
				}
				return new Node(child_state, this, act, cost+1 );	

			}
			
			else {
				
				if (  (this.action.name().startsWith("L")) && (act.name().startsWith("R")) ) {
					switch(act) {
					case R_1C:
						child_state[0].inc_c();
						child_state[1].dec_c();
						break;
					case R_1C1M:
						child_state[0].inc_c();
						child_state[0].inc_m();
						child_state[1].dec_c();
						child_state[1].dec_m();
						break;  
					case R_1M:
						child_state[0].inc_m();
						child_state[1].dec_m();
						break;
					case R_2M:
						child_state[0].inc_m();
						child_state[0].inc_m();
						child_state[1].dec_m();
						child_state[1].dec_m();
						break;
					case R_2C:
						child_state[0].inc_c();
						child_state[0].inc_c();
						child_state[1].dec_c();
						child_state[1].dec_c();
						break;
					default:
						break;		         		
					}
					return new Node(child_state, this, act, cost+1 );	
				}
				
				if ( (this.action.name().startsWith("R")) && (act.name().startsWith("L"))  ) {
					switch(act) {
					case L_1C:
						child_state[1].inc_c();
						child_state[0].dec_c();
						break;		     
					case L_1C1M:
						child_state[1].inc_c();
						child_state[1].inc_m();
						child_state[0].dec_c();
						child_state[0].dec_m();
						break;		     
					case L_1M:
						child_state[1].inc_m();
						child_state[0].dec_m();
						break;
					case L_2M:
						child_state[1].inc_m();
						child_state[1].inc_m();
						child_state[0].dec_m();
						child_state[0].dec_m();
						break;
					case L_2C:
						child_state[1].inc_c();
						child_state[1].inc_c();
						child_state[0].dec_c();
						child_state[0].dec_c();
						break;						
					default:
						break;
					}
					return new Node(child_state, this, act, cost+1 );
				}
				
				else {
					System.out.println(ANSI_RED+"You must change direction"+ANSI_RESET);
					return null;
				}
			}
		    
		 
	}

    public static List<String> generateCharacterCombinations(String input) {
        List<String> combinations = new ArrayList<>();
        // Generate combinations of single characters
        for (int i = 0; i < input.length(); i++) {
            combinations.add(Character.toString(input.charAt(i)));
        }
        
        // Generate combinations of two characters
        for (int i = 0; i < input.length() - 1; i++) {
            combinations.add(input.substring(i, i + 2));
        }
        return combinations;
    }

    
    public static List<String> removeDuplicates(List<String> listWithDuplicates) {
        // Create a Set to store unique elements
        Set<String> set = new HashSet<>(listWithDuplicates);
        // Create a new ArrayList to store elements without duplicates
        List<String> listWithoutDuplicates = new ArrayList<>(set);
        return listWithoutDuplicates;
    }
    
    
    public static List<Action> actionMapper(boolean leftDirecion, List<String> passengers) {
    	List<Action> actions = new ArrayList<Action>();
    	for (String group: passengers) {
    		if (leftDirecion) {
    			if (group.contains("CC")) {
    				actions.add(Action.R_2C);
    			}
    			else if (group.contains("MM")) {
    				actions.add(Action.R_2M);
    			}
    			else if (group.contains("C") && group.contains("M")) {
    				actions.add(Action.R_1C1M);
    			}
    			else if (group.contains("M")) {
    				actions.add(Action.R_1M);
    			}    	
    			else if (group.contains("C")) {
    				actions.add(Action.R_1C);
    			}
    		}
    		else {
    			if (group.contains("CC")) {
    				actions.add(Action.L_2C);
    			}
    			else if (group.contains("MM")) {
    				actions.add(Action.L_2M);
    			}
    			else if (group.contains("C") && group.contains("M")) {
    				actions.add(Action.L_1C1M);
    			}
    			else if (group.contains("M")) {
    				actions.add(Action.L_1M);
    			}    	
    			else if (group.contains("C")) {
    				actions.add(Action.L_1C);
    			}
    		}
    	}
    	return actions;
    }

    
	@Override
	public String toString() {
		// ⏪⏩  ⇦⇨ ⮕ ⬅ ⬅ ⬅←→ ⇦⇨ ⬅➡ ⬅➡
		String dir = "";
		if (this.action!=null && this.action.name().startsWith("R")) {
			dir += " 🢂";
		}
		else if (this.action!=null){
			dir += " 🢀";
		}		
		String right = "O".repeat(state[0].get_m()) + "X".repeat(state[0].get_c());
		int right_num = state[0].get_all();
		String left = "X".repeat(state[1].get_c()) + "O".repeat(state[1].get_m());
		int left_num = state[1].get_all();
		String boat = "";
		if (boatOnRight) {
			boat+= " _____🛶_ ";
		}
		else {
			boat+= " _🛶_____ ";
		}
		
		String str = "\n---------------------------\n";
		str += " ".repeat(6 )  + "   "+ dir + "   " +  " ".repeat(6) + "\n";
		str += " ".repeat(6 - left_num) + left + boat + right + " ".repeat(6 - right_num);
		if (this.is_failure()) {
			str += "  💀";
		}
		if (this.is_goal()) {
			str += "  ✅";
		}
		if (this.is_root()) {
			str += "  🪵";
		}
		
		str += "\n---------------------------";
		return str;
		
	}
	
    // Override equals method
	   @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null || getClass() != obj.getClass()) {
	            return false;
	        }
	        Node other = (Node) obj;
	        return (Arrays.equals(this.get_state(), other.get_state())) && (this.bootRightSide()== other.bootRightSide());
	    }

}
