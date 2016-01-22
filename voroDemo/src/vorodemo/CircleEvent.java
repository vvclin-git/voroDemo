package vorodemo;

import processing.core.PApplet;

public class CircleEvent extends Event{
	Circle circle;
	public CircleEvent(Voronoi voronoi, Circle circle) {
		super(voronoi);
		this.y = circle.getLowY();
		this.circle = circle;
	}
	public void eventHandler() {
		// modify beach line
		System.out.println("===");
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
		voronoi.beachLine.removeArc(circle.getBpt1(), circle.getBpt2());
		// TODO add new edge
		// TODO remove non-existing circle events
		
	}

}
