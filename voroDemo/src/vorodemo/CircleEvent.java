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
//		voronoi.beachLine.printBptNodeX();
//		voronoi.beachLine.printBptNode();
		voronoi.beachLine.removeArc(circle.getBptNode1(), circle.getBptNode2(), circle);
		voronoi.circles.remove(this.circle);
		this.circle.getMedSite().setProcessed();
	}

}
