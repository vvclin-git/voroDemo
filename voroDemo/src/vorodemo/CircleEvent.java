package vorodemo;

import processing.core.PApplet;
import java.util.ArrayList;

public class CircleEvent extends Event{
	Circle circle;
	public CircleEvent(Voronoi voronoi, Circle circle) {
		super(voronoi, "circle");
		this.y = circle.getLowY();
		this.circle = circle;
	}
	public void eventHandler() {
		
		// modify beach line
		System.out.println("===circle event===");
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : voronoi.beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.x() + ", ");
		}
		System.out.println();
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : voronoi.beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.type + ", ");
		}
		System.out.println();
		
		voronoi.beachLine.removeArc(circle.getBpt1(), circle.getBpt2(), circle);
		
		// TODO add new edge
		// TODO remove non-existing circle events
		ArrayList<Event> circleEventDelList = new ArrayList<Event>();
		for (Event event : voronoi.events) {
			if (event.getType() == "circle") {
				CircleEvent tmpCircleEvent = (CircleEvent) event;
				if (tmpCircleEvent.circle.containSite(circle.getMedSite()) & tmpCircleEvent != this) {
					circleEventDelList.add(event);
					System.out.println("remove");
				}
			}
		}
		for (Event event : circleEventDelList) {
			voronoi.events.remove(event);
		}
		voronoi.circles.remove(this.circle);
		this.circle.getMedSite().setProcessed();
	}

}
