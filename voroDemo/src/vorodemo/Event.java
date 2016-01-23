package vorodemo;

public abstract class Event implements Comparable<Event>{
	float y;
	Voronoi voronoi;
	String type;
	public Event(Voronoi voronoi, String type) {
		this.voronoi = voronoi;
		this.type = type;
	}
	public void eventHandler() {
		
	}
	public int compareTo(Event other) {
		if (this.y < other.y) {
			return -1;
		}    
		if (this.y > other.y) {
			return 1;
		}
		return 0;
	}
	public float y() {
		return y;
	}
	public String getType() {
		return type;
	}
}
