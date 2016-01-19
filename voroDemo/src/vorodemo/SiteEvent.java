package vorodemo;

public class SiteEvent extends Event {
	Site site;
	public SiteEvent(Voronoi voronoi, Site site) {
		super(voronoi);
		this.y = site.y();
		this.site = site;		
	}	
	public void eventHandler() {		
		if (!voronoi.beachLineTree.isEmpty()) {
			// add new arc on an existing arc
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = voronoi.beachLineTree.floorKey(queryNode);										
			BptNode oldRightNode = voronoi.beachLineTree.ceilingKey(queryNode);				
			voronoi.beachLine.addArc(site);
			BptNode newLeftNode = voronoi.beachLine.getNewLeftNode();								
			BptNode newRightNode = voronoi.beachLine.getNewRightNode();
			// create half edges
			voronoi.edges.add(new Edge(newLeftNode, newRightNode, voronoi.p));
			// create circle event
			if (oldLeftNode.type != "leftBound") {
				Circle newCircle = new Circle(oldLeftNode, newLeftNode, voronoi.p);
				voronoi.circles.add(newCircle);
				voronoi.events.add(new CircleEvent(voronoi, newCircle));
				System.out.println(newCircle.getCenter());
			}
			if (oldRightNode.type != "rightBound") {
				Circle newCircle = new Circle(newRightNode, oldRightNode, voronoi.p);
				voronoi.circles.add(newCircle);
				voronoi.events.add(new CircleEvent(voronoi, newCircle));
				System.out.println(newCircle.getCenter());
			}
		}
		else {
			voronoi.beachLine.addArc(site);
		}
		
	}
	public String toString() {
		return this.site.toString();
	}
}
