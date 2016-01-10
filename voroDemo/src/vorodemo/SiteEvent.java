package vorodemo;

public class SiteEvent extends Event {
	Site site;
	public SiteEvent(Voronoi voronoi, Site site) {
		super(voronoi);
		this.y = site.y();
		this.site = site;		
	}	
	public void eventHandler() {
		if (voronoi.beachLine.isEmpty()) {			
			Parabola newPara = new Parabola(voronoi.dictx, site, 600, voronoi.p);
			site.setPara(newPara);
			BptNode leftNode = new BptNode("leftBound", null, site, site, voronoi.dictx);
			BptNode rightNode = new BptNode("rightBound", site, null, site, voronoi.dictx);
			voronoi.beachLine.put(leftNode, leftNode);
			voronoi.beachLine.put(rightNode, rightNode);
			// for drawing beach line
			voronoi.breakPoints.add(newPara.leftBpt);
			voronoi.breakPoints.add(newPara.rightBpt);
			voronoi.arcs.put(leftNode, newPara);
		}
		else {
			// determine which arc to insert a site
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = voronoi.beachLine.floorEntry(queryNode).getValue();
			BptNode oldRightNode = voronoi.beachLine.ceilingEntry(queryNode).getValue();
			// for drawing beach line
			Parabola oldPara = voronoi.arcs.get(oldLeftNode);
			BreakPoint oldLeftBpt = oldPara.leftBpt;
			BreakPoint oldRightBpt = oldPara.rightBpt;
			//System.out.println("old para " + oldPara.a() + ", " + oldPara.b() + ", " + oldPara.c());			
			Parabola newPara = new Parabola(voronoi.dictx, site, oldPara, voronoi.p);
			site.setPara(newPara);
			//System.out.println("new para " + newPara.a() + ", " + newPara.b() + ", " + newPara.c());
			// create new break points
			BptNode newLeftNode = new BptNode("left", oldLeftNode.rightSite, site, site, voronoi.dictx);
			BptNode newRightNode = new BptNode("right", site, oldRightNode.leftSite, site, voronoi.dictx);
			System.out.println("add: " + newLeftNode.x);
			voronoi.beachLine.put(newLeftNode, newLeftNode);
			System.out.println("add: " + newRightNode.x);
			voronoi.beachLine.put(newRightNode, newRightNode);
			// for drawing beach line
			BreakPoint newLeftBpt = newPara.leftBpt;
			BreakPoint newRightBpt = newPara.rightBpt;			
			voronoi.breakPoints.add(newLeftBpt);
			voronoi.breakPoints.add(newRightBpt);
			// add new voronoi.arcs (for drawing beach line)
			voronoi.arcs.remove(oldLeftNode);
			voronoi.arcs.put(oldLeftNode, new Parabola (voronoi.dictx, oldLeftNode.rightSite, oldLeftBpt, newLeftBpt, voronoi.p));
			voronoi.arcs.put(newLeftNode, newPara);
			voronoi.arcs.put(newRightNode, new Parabola (voronoi.dictx, oldLeftNode.rightSite, newRightBpt, oldRightBpt, voronoi.p));			
			// add new circle (ignore left and right virtual nodes) 
			if (oldLeftNode.type != "leftBound") {
				Circle newCircle = new Circle(oldLeftNode, newLeftNode, voronoi.p);
				voronoi.circles.add(newCircle);
				voronoi.events.add(new CircleEvent(voronoi, newCircle));
			}
			if (oldRightNode.type != "rightBound") {
				Circle newCircle = new Circle(newRightNode, oldRightNode, voronoi.p);
				voronoi.circles.add(newCircle);
				voronoi.events.add(new CircleEvent(voronoi, newCircle));
			}
			// for the potential removal of existed circle event (see if the new site is already inside of a circle)
			
		}
	}
	public String toString() {
		return this.site.toString();
	}
}
