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
		if (circle.containProcessedSite()) {
			voronoi.circles.remove(this.circle);
			return;
		}
		// modify beach line
		System.out.println("===circle event===");
		voronoi.beachLine.printBptNodeX();
		voronoi.beachLine.printBptNodeType();
		voronoi.beachLine.removeArc(circle.getBpt1(), circle.getBpt2(), circle);
//		BptNode newBptNode = voronoi.beachLine.getNewNode();
//		BptNode oldLeftBptNode = voronoi.beachLineTree.lowerKey(newBptNode);
//		BptNode oldRightBptNode = voronoi.beachLineTree.higherKey(newBptNode);
//		if (newBptNode.isConverge(oldRightBptNode))
		// TODO remove non-existing circle events
//		ArrayList<Event> circleEventDelList = new ArrayList<Event>();
//		for (Event event : voronoi.events) {
//			if (event.getType() == "circle") {
//				CircleEvent tmpCircleEvent = (CircleEvent) event;
//				if (tmpCircleEvent.circle.containSite(circle.getMedSite()) & tmpCircleEvent != this) {
//					circleEventDelList.add(event);
//					System.out.println("remove");
//				}
//			}
//		}
//		for (Event event : circleEventDelList) {
//			voronoi.events.remove(event);
//		}
		voronoi.circles.remove(this.circle);
		this.circle.getMedSite().setProcessed();
	}

}
