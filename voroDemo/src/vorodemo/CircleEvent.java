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
//		if (circle.containProcessedSite()) {
//			voronoi.circles.remove(this.circle);
//			return;
//		}
		if (circle.containProcessedBptNode()) {
			voronoi.circles.remove(this.circle);
			System.out.println("circle removed");
			return;
		}
		// modify beach line
		System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
//		voronoi.beachLine.printBptNodeX();
//		voronoi.beachLine.printBptNode();
		voronoi.beachLine.removeArc(circle.getBptNode1(), circle.getBptNode2(), circle);
		voronoi.circles.remove(this.circle);
		System.out.println("===circle event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());		
	}

}
