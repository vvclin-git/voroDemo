package vorodemo;

public class SiteEvent extends Event {
	Site site;
	public SiteEvent(Voronoi voronoi, Site site) {
		super(voronoi, "site");
		this.y = site.y();
		this.x = site.x();
		this.site = site;		
	}	
	public void eventHandler() {		
		if (!voronoi.beachLineTree.isEmpty()) {
			// TODO need to deal with special case (circle event + site event)
			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
			// add new arc on an existing arc
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = voronoi.beachLineTree.floorKey(queryNode);										
			BptNode oldRightNode = voronoi.beachLineTree.ceilingKey(queryNode);				
			voronoi.beachLine.addArc(site);
			
			BptNode newLeftNode = voronoi.beachLine.getNewLeftNode();								
			BptNode newRightNode = voronoi.beachLine.getNewRightNode();
			// create half edges
			Edge newEdge = new Edge(newLeftNode, newRightNode, oldLeftNode.getSharedSite(oldRightNode), site, voronoi.p);
			newLeftNode.setEdge(newEdge);
			newRightNode.setEdge(newEdge);
			voronoi.edges.add(newEdge);
			// create circle event			
			if (oldLeftNode.type != "leftBound") {
				Circle newCircle = new Circle(oldLeftNode, newLeftNode, voronoi.dictx.y(), voronoi.p);
				System.out.println("site | " + oldLeftNode.x() + ", " + newLeftNode.x() + ", " + newCircle.isConverge());
				if (!newCircle.containProcessedSite() & newCircle.isConverge()) {
//					System.out.println("site | " + oldLeftNode.x() + ", " + newLeftNode.x());
//					System.out.println("site | " + oldLeftNode.getType() + ", " + newLeftNode.getType());
					//oldLeftNode.setProcessed();
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));					
				}
				//System.out.println(newCircle.getCenter());
			}
			if (oldRightNode.type != "rightBound") {
				Circle newCircle = new Circle(newRightNode, oldRightNode, voronoi.dictx.y(), voronoi.p);
				System.out.println("site | " + newRightNode.x() + ", " + oldRightNode.x() + ", " + newCircle.isConverge());
				if (!newCircle.containProcessedSite() & newCircle.isConverge()) {
//					System.out.println("site | " + newRightNode.x() + ", " + oldRightNode.x());
//					System.out.println("site | " + newRightNode.getType() + ", " + oldRightNode.getType());
					//oldRightNode.setProcessed();
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));					
				}
				//System.out.println(newCircle.getCenter());
			}
			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
		}
		else {
			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
			voronoi.beachLine.addArc(site);
			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
		}
		
	}
	public String toString() {
		return this.site.toString();
	}
}
