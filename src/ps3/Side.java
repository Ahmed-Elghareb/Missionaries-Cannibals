package ps3;

import java.util.Arrays;

public class Side {
	int cannibals;
	int missionaries;
	public Side(int m, int c) {
		cannibals = c;
		missionaries = m; 
	}
	public int get_c() {
		return cannibals;
	}
	public int get_m() {
		return missionaries;
	}
	public void inc_c() {
		if (cannibals < 3)
		cannibals++;
	}
	public void inc_m() {
		if (missionaries < 3)
		 missionaries ++;
	}
	public void dec_c() {
		if (cannibals > 0)
		cannibals--;
	}
	public void dec_m() {
		if (missionaries > 0)
		 missionaries --;
	}
	public int get_all() {
		return missionaries + cannibals;
	}

	   @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null || getClass() != obj.getClass()) {
	            return false;
	        }
	        Side other = (Side) obj;
	        return this.get_c()==other.get_c() && this.get_m()==other.get_m();
	    }
	
}