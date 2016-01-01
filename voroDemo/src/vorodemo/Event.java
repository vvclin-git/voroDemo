package vorodemo;

public abstract class Event implements Comparable<Event>{
	float y;
	Voronoi voronoi;
	public Event(Voronoi voronoi) {
		this.voronoi = voronoi;
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
}
