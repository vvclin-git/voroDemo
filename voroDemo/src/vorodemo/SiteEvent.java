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
//			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
//			voronoi.beachLine.printBptNode();
//			voronoi.beachLine.printBptNodeX();
			// add new arc on an existing arc
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = voronoi.beachLineTree.floorKey(queryNode);										
			BptNode oldRightNode = voronoi.beachLineTree.ceilingKey(queryNode);				
			voronoi.beachLine.addArc(site);			
			BptNode newLeftNode = voronoi.beachLine.getNewLeftNode();								
			BptNode newRightNode = voronoi.beachLine.getNewRightNode();			
			if (oldLeftNode == oldRightNode) { // special case
				BptNode oldNode = oldLeftNode;
				BptNode vertex = new BptNode("vertex", oldNode);
				Edge newEdge1 = new Edge(newLeftNode, vertex, newLeftNode.getLeftSite(), site, voronoi.p);
				assert(newEdge1.getSite1() != null);
				assert(newEdge1.getSite2() != null);
				Edge newEdge2 = new Edge(newRightNode, vertex, newRightNode.getRightSite(), site, voronoi.p);
				assert(newEdge2.getSite1() != null);
				assert(newEdge2.getSite2() != null);				
				voronoi.edges.add(newEdge1);
				voronoi.edges.add(newEdge2);
				newLeftNode.setEdge(newEdge1);
				newRightNode.setEdge(newEdge2);
			}
			else { // normal cases
//				Edge newEdge = new Edge(newLeftNode, newRightNode, oldLeftNode.getSharedSite(oldRightNode), site, voronoi.p);
				Edge newEdge = new Edge(newLeftNode, newRightNode, oldLeftNode.getSite(), site, voronoi.p);
				assert(newEdge.getSite1() != null);
				assert(newEdge.getSite2() != null);
				newLeftNode.setEdge(newEdge);
				newRightNode.setEdge(newEdge);
				voronoi.edges.add(newEdge);				
			}			
			// create circle event			
			if (oldLeftNode.type != "leftBound") {
				Circle newCircle = new Circle(oldLeftNode, newLeftNode, voronoi.dictx.y(), voronoi.p);
//				System.out.println("convergence of left circle: " + newCircle.isConverge());
				if (newCircle.isConverge()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));					
				}				
			}
			if (oldRightNode.type != "rightBound") {
				Circle newCircle = new Circle(newRightNode, oldRightNode, voronoi.dictx.y(), voronoi.p);
//				System.out.println("convergence of right circle: " + newCircle.isConverge());
				if (newCircle.isConverge()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));					
				}
				//System.out.println(newCircle.getCenter());
			}
//			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
		}
		else {
//			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
			voronoi.beachLine.addArc(site);
			//TODO: need to deal with special case here (the first N site events share the same y-pos)
//			System.out.println("===site event=== | y = " + voronoi.dictx.y() + " | " + voronoi.beachLineTree.size());
		}
		
	}
	public String toString() {
		return this.site.toString();
	}
}
