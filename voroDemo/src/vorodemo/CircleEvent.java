package vorodemo;

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
		voronoi.breakPoints.remove(circle.getMedSite().parabola.leftBpt);
		voronoi.breakPoints.remove(circle.getMedSite().parabola.rightBpt);
		Parabola prevArc = voronoi.arcs.floorEntry(circle.getBpt1()).getValue();
		Parabola nextArc = voronoi.arcs.get(circle.getBpt2());
		BreakPoint newBpt = prevArc.rightInterPt(nextArc);
		voronoi.arcs.remove(circle.getBpt1());
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
		voronoi.beachLine.remove(circle.getBpt2());
		System.out.println(voronoi.beachLine.size() + " , " + circle.getBpt1().x + ", " + circle.getBpt2().x);
		System.out.println("==2===");
		for (BptNode x : voronoi.beachLine.keySet()) {			
			System.out.println(x.x);			
		}
		System.out.println("=====");
		voronoi.beachLine.remove(circle.getBpt1());
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
