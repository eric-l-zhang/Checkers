
public class TakenObject {
	private int x;
	private int y;
	private int color;
	private Location location;
	
	public TakenObject(Location l, int color) {
		this.x = l.getX();
		this.y = l.getY();
		location = l;
		this.color = color;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Location getLoc() {
		return location;
	}
	
	public int getColor() {
		return color;
	}
}
