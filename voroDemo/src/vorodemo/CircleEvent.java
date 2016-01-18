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
		// fix the y-position of dictx to the exact value of circle event
		voronoi.dictx.setY(this.y());
		voronoi.beachLine.update();
//		System.out.println();
//		System.out.print(voronoi.dictx.y() + "| ");
//		for (BptNode bptNode : voronoi.beachLineTree.navigableKeySet()) {
//			System.out.print(bptNode.x() + ", ");
//		}
		// modify beach line
		voronoi.beachLine.removeArc(circle.getBpt1(), circle.getBpt2());
		// after the event being processed, reset the y-pos of dictx
		voronoi.dictx.setY(tempY);
//		// update data structures
//		// remove old bptnodes
//		voronoi.beachLine.remove(circle.getBpt1());		
//		voronoi.beachLine.remove(circle.getBpt2());				
//		// create new bptnode
//		BptNode newBptNode = new BptNode("left", circle.getLeftSite(), circle.getRightSite(), circle.getRightSite(), voronoi.dictx);
//		System.out.println("====" + newBptNode.x);
//		voronoi.beachLine.put(newBptNode, newBptNode);
//		// for drawing
//		//System.out.println(voronoi.breakPoints.toString());
//		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getLeftBpt());
//		voronoi.breakPoints.remove(voronoi.arcs.get(circle.getBpt1()).getRightBpt());
//		//System.out.println(voronoi.breakPoints.toString());
//		voronoi.arcs.remove(circle.getBpt1());
//		Parabola prevArc = voronoi.arcs.floorEntry(circle.getBpt1()).getValue();
//		Parabola nextArc = voronoi.arcs.get(circle.getBpt2());
//		BreakPoint tmpLeftBpt = nextArc.leftInterPt(prevArc);
//		BreakPoint tmpRightBpt = nextArc.rightInterPt(prevArc);
//		BreakPoint newLeftBpt; 
//		BreakPoint newRightBpt = nextArc.getRightBpt();
//		// determine the relation between new break point and its neighbor arcs
//		if (tmpLeftBpt.distSqrTo(circle.getCenter()) > tmpRightBpt.distSqrTo(circle.getCenter())) {
//			newLeftBpt = tmpRightBpt;			
//		}
//		else {
//			newLeftBpt = tmpLeftBpt;
//		}
//		voronoi.breakPoints.add(newLeftBpt);
//		//System.out.println(voronoi.breakPoints.toString());
//		prevArc.setRightBpt(newLeftBpt);
//		nextArc.setLeftBpt(newLeftBpt);
//		voronoi.arcs.remove(circle.getBpt2());
//		voronoi.arcs.put(newBptNode, new Parabola (voronoi.dictx, circle.getRightSite(), newLeftBpt, newRightBpt, voronoi.p));
//		for (BptNode k : voronoi.beachLine.keySet()) {
//			k.update();
//			System.out.println(k.x);
//		}		
		
	}

}
