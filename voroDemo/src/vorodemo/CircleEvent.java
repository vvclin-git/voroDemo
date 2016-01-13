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
		float tempY = voronoi.dictx.y();
		voronoi.dictx.setY(this.y());
		// update data structures
//		System.out.println(voronoi.beachLine.size() + " , " + circle.getBpt1().x + ", " + circle.getBpt2().x);
//		System.out.println("==1===");
//		for (BptNode x : voronoi.beachLine.keySet()) {			
//			System.out.println(x.x);			
//		}
//		System.out.println("=====");
		// remove old bptnodes
		voronoi.beachLine.remove(circle.getBpt1());		
		voronoi.beachLine.remove(circle.getBpt2());				
		// create new bptnode
		BptNode newBptNode = new BptNode("right", circle.getLeftSite(), circle.getRightSite(), circle.getLeftSite(), voronoi.dictx);
		voronoi.beachLine.put(newBptNode, newBptNode);
		// for drawing
		System.out.println(voronoi.breakPoints.toString());
		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getLeftBpt());
		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getRightBpt());
		System.out.println(voronoi.breakPoints.toString());
		voronoi.arcs.remove(circle.getBpt1());
		Parabola prevArc = voronoi.arcs.floorEntry(circle.getBpt1()).getValue();
		Parabola nextArc = voronoi.arcs.get(circle.getBpt2());
		BreakPoint tmpLeftBpt = nextArc.leftInterPt(prevArc);
		BreakPoint tmpRightBpt = nextArc.rightInterPt(prevArc);
		BreakPoint newLeftBpt; 
		BreakPoint newRightBpt = nextArc.getRightBpt();
		// determine the relation between new break point and its neighbor arcs
		if (tmpLeftBpt.distSqrTo(circle.getCenter()) > tmpRightBpt.distSqrTo(circle.getCenter())) {
			newLeftBpt = tmpRightBpt;			
		}
		else {
			newLeftBpt = tmpLeftBpt;
		}
		voronoi.breakPoints.add(newLeftBpt);
		System.out.println(voronoi.breakPoints.toString());
		prevArc.setRightBpt(newLeftBpt);
		nextArc.setLeftBpt(newLeftBpt);
		voronoi.arcs.remove(circle.getBpt2());
		voronoi.arcs.put(circle.getBpt2(), new Parabola (voronoi.dictx, circle.getRightSite(), newLeftBpt, newRightBpt, voronoi.p));
		for (BptNode k : voronoi.arcs.keySet()) {
			k.update();
			System.out.println(k.x);
		}
		
		voronoi.dictx.setY(tempY);
	}

}
