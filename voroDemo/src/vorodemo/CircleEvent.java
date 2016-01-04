package vorodemo;

public class CircleEvent extends Event{
	Circle circle;
	public CircleEvent(Voronoi voronoi, Circle circle) {
		super(voronoi);
		this.y = circle.getLowY();
		this.circle = circle;
	}
	public void eventHandler() {
		
		// pull off the degenerate arc and break points (for drawing)
		voronoi.breakPoints.remove(circle.getMedSite().parabola.leftBpt);
		voronoi.breakPoints.remove(circle.getMedSite().parabola.rightBpt);
		voronoi.arcs.floorEntry(circle.getBpt1()).getValue().rightBpt = voronoi.arcs.ceilingEntry(circle.getBpt1()).getValue().leftBpt;
		System.out.println(voronoi.arcs.containsKey(circle.getBpt1()));
		voronoi.arcs.remove(circle.getBpt1());
		//System.out.println(circle.getBpt1().x + ", " + circle.getBpt2().x);		
//		for (BptNode x : voronoi.arcs.keySet()) {
//			if (x.equalsTo(circle.getBpt1())) {
//			System.out.println(x.x);
//			}
//		}
//		System.out.println(voronoi.arcs.keySet());
//		voronoi.arcs.containsKey(circle.getBpt1());
//		System.out.println(voronoi.arcs.containsKey(circle.getBpt2()));
		// add new break point
		voronoi.breakPoints.add(new BreakPoint(circle.x(), circle.y(), "right", circle.getLeftSite().parabola, circle.getRightSite().parabola, voronoi.p));
		// join the break points and update the beachLine
		voronoi.beachLine.remove(circle.getBpt1());
		voronoi.beachLine.remove(circle.getBpt2());
		BptNode newBptNode = new BptNode("right", circle.getLeftSite(), circle.getRightSite(), voronoi.dictx);
		voronoi.beachLine.put(newBptNode, newBptNode);
		// 
	}

}
