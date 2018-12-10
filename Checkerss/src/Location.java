
public class Location implements Comparable<Location>{
	
	private int x;
	private int y;
	
	public Location (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) { 
		  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Location)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Location l = (Location) o; 
          
        // Compare the data members and return accordingly  
        return Integer.compare(x, l.x) == 0 && Integer.compare(y, l.y) == 0; 
    }
	
	@Override
	public int compareTo(Location l) {
		if (this.x == l.getX() && this.y == l.getY()) {
			return 0;
		}
		else if (x == l.getX() && y < l.getY()) {
			return 1;
		}
		else if (x < l.getX()) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
