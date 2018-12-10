import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UndoObject {
	
	private TakenObject start;
	private TakenObject end;
	private List<TakenObject> taken;
	
	public UndoObject(TakenObject start, TakenObject end, TakenObject taken) {
		this.start = start;
		this.end = end;
		this.taken = new LinkedList<>();
		if (taken != null) {
			this.taken.add(taken);
		}
		
	}
	
	public void addTo(TakenObject l) {
		taken.add(l);
	}
	
	public TakenObject getStart() {
		return start;
	}
	
	public TakenObject getEnd() {
		return end;
	}
	
	public List<TakenObject> getTaken() {
		return taken;
	}
	
	public void updateEnd(TakenObject t) {
		end = t;
	}
}
