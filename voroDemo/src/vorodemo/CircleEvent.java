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
		// pull off the degenerate arc and break points (for drawing)
		System.out.println(voronoi.breakPoints.toString());
		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getLeftBpt());
		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getRightBpt());
		System.out.println(voronoi.breakPoints.toString());
		voronoi.arcs.remove(circle.getBpt1());
		Parabola prevArc = voronoi.arcs.floorEntry(circle.getBpt1()).getValue();
		Parabola nextArc = voronoi.arcs.get(circle.getBpt2());
		BreakPoint tmpLeftBpt = nextArc.leftInterPt(prevArc);
		BreakPoint tmpRightBpt = nextArc.rightInterPt(prevArc);
		BreakPoint newBpt;
		if (tmpLeftBpt.distSqrTo(circle.getCenter()) > tmpRightBpt.distSqrTo(circle.getCenter())) {
			newBpt = tmpRightBpt; 
		}
		else {
			newBpt = tmpLeftBpt;
		}
//		BreakPoint newBpt = nextArc.leftInterPt(prevArc);
		
//		BreakPoint newBpt = new BreakPoint(circle.x(), prevArc.y(circle.x()), "right", prevArc, nextArc, voronoi.p);
		voronoi.breakPoints.add(newBpt);
		System.out.println(voronoi.breakPoints.toString());
		prevArc.setRightBpt(newBpt);
		nextArc.setLeftBpt(newBpt);

		// add new break point
		
		// join the break points and update the beachLine
		System.out.println(voronoi.beachLine.size() + " , " + circle.getBpt1().x + ", " + circle.getBpt2().x);
		System.out.println("==1===");
		for (BptNode x : voronoi.beachLine.keySet()) {			
			System.out.println(x.x);			
		}
		System.out.println("=====");
		voronoi.beachLine.remove(circle.getBpt1());
		System.out.println(voronoi.beachLine.size() + " , " + circle.getBpt1().x + ", " + circle.getBpt2().x);
		System.out.println("==2===");
		for (BptNode x : voronoi.beachLine.keySet()) {			
			System.out.println(x.x);			
		}
		System.out.println("=====");
		voronoi.beachLine.remove(circle.getBpt2());
		System.out.println(voronoi.beachLine.size() + " , " + circle.getBpt1().x + ", " + circle.getBpt2().x);
		System.out.println("==3===");
		for (BptNode x : voronoi.beachLine.keySet()) {			
			System.out.println(x.x);			
		}
		System.out.println("=====");		
//		BptNode newBptNode = new BptNode("right", circle.getLeftSite(), circle.getRightSite(), voronoi.dictx);
//		voronoi.beachLine.put(newBptNode, newBptNode);
//		System.out.println(voronoi.beachLine.floorKey(circle.getBpt1()).x);
//		System.out.println("==4==");
//		voronoi.beachLine.remove(circle.getBpt1());
//		for (BptNode x : voronoi.beachLine.keySet()) {			
//			System.out.println(x.x);			
//		}
//		System.out.println("=====");
		// 
		voronoi.dictx.setY(tempY);
	}

}
