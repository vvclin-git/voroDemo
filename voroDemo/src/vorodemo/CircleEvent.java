package vorodemo;

public class CircleEvent extends Event{
	Circle circle;
	public CircleEvent(Voronoi voronoi, Circle circle) {
		super(voronoi);
		this.y = circle.getLowY();
		this.circle = circle;
	}
	public void eventHandler() {
		
	}

}
