package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import processing.core.PApplet;
public class BeachLine {
	TreeMap<BptNode, Parabola> beachLineTree;
	PApplet p;
	Voronoi voronoi;
	float r = 2; 
	BptNode newLeftNode, newRightNode;
	BptNode newBptNode;
	public BeachLine(Voronoi voronoi, PApplet p) {
		this.voronoi = voronoi;
		this.p = p;
		this.beachLineTree = voronoi.beachLineTree;
	}
	public void addArc(Site site) {
		if (beachLineTree.isEmpty()) {
			// create boundary nodes
			newLeftNode = new BptNode("leftBound", null, site, site, voronoi.dictx);
			newRightNode = new BptNode("rightBound", site, null, site, voronoi.dictx);
			// create new arc
			Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
			site.setPara(newArc);
			beachLineTree.put(newLeftNode, newArc);
			beachLineTree.put(newRightNode, null);
		}
		else {
			// looking for the existing arc for adding a new one
			BptNode queryNode = new BptNode("query", site);
			BptNode oldLeftNode = beachLineTree.floorKey(queryNode);										
			BptNode oldRightNode = beachLineTree.ceilingKey(queryNode);
			Parabola oldArc = beachLineTree.get(oldLeftNode).clone();
			// create new nodes
			newLeftNode = new BptNode("left", oldLeftNode.rightSite, site, site, voronoi.dictx);
			newRightNode = new BptNode("right", site, oldRightNode.leftSite, site, voronoi.dictx);
			// create new arc
			Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
			beachLineTree.put(newLeftNode, newArc);			
			// modify existing arcs (break)
			beachLineTree.get(oldLeftNode).setRightBptNode(newLeftNode);
			beachLineTree.put(newRightNode, oldArc);
			beachLineTree.get(newRightNode).setLeftBptNode(newRightNode);			
		}
	}
	public void removeArc(BptNode leftBptNode, BptNode rightBptNode, Circle circle) {		
		// remove arcs
		// TODO: need better way to remove bpts		
		float yTemp = voronoi.dictx.y();
		Site newBptNodeSite1, newBptNodeSite2;
		voronoi.dictx.setY(circle.getYInit());
		System.out.println(leftBptNode.x() + "," + rightBptNode.x());
		System.out.println(voronoi.beachLineTree.size());
//		System.out.println(leftBptNode + "," + rightBptNode);
//		printBptNodeX();
//		printBptNode();
		beachLineTree.remove(leftBptNode);		
		beachLineTree.remove(rightBptNode);
		System.out.println(voronoi.beachLineTree.size());
		leftBptNode.setProcessed();
		rightBptNode.setProcessed();
		//printBptNode();
		voronoi.dictx.setY(yTemp);
		// for generate new arcs
		BptNode oldRightBptNode = beachLineTree.higherKey(rightBptNode);
		// create new bptNode and circle event
		//System.out.println(leftBptNode.getLeftSite().x() + ", " + rightBptNode.getRightSite().x());
		if (circle.getLeftSite().compareTo(circle.getRightSite()) == 1) {
			newBptNodeSite1 = circle.getLeftSite();
			newBptNodeSite2 = circle.getRightSite();
		}
		else if (circle.getLeftSite().compareTo(circle.getRightSite()) == -1) {
			newBptNodeSite1 = circle.getRightSite();
			newBptNodeSite2 = circle.getLeftSite();
		}
		else {
			newBptNodeSite1 = circle.getLeftSite();
			newBptNodeSite2 = circle.getRightSite();
		}
		if (circle.getCenter().x() > newBptNodeSite1.x()) {
			newBptNode = new BptNode("right", newBptNodeSite1, newBptNodeSite2, newBptNodeSite1, voronoi.dictx);
			BptNode nextRightBptNode = voronoi.beachLineTree.higherKey(newBptNode);
			if (nextRightBptNode.getType() != "rightBound") {
				Circle newCircle = new Circle(newBptNode, nextRightBptNode, voronoi.dictx.y(), voronoi.p);
				//System.out.println(voronoi.dictx.y() + ", " + newCircle.y() + ", " + newCircle.getLowY() + " | " + newCircle.containProcessedBptNode() + ", " + newCircle.isConverge());				
				//System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));
				//System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));
				if (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));
				}				
			}
		}
		else {
			newBptNode = new BptNode("left", newBptNodeSite2, newBptNodeSite1, newBptNodeSite1, voronoi.dictx);
			BptNode nextLeftBptNode = voronoi.beachLineTree.lowerKey(newBptNode);
			if (nextLeftBptNode.getType() != "leftBound") {				
				Circle newCircle = new Circle(nextLeftBptNode, newBptNode, voronoi.dictx.y(), voronoi.p);
				//System.out.println(voronoi.dictx.y() + ", " + newCircle.y() + ", " + newCircle.getLowY() + " | " + newCircle.containProcessedBptNode() + ", " + newCircle.isConverge());
				//System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));
				//System.out.println((!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.y() > voronoi.dictx.y()));
				if (!newCircle.containProcessedBptNode() & newCircle.isConverge() & newCircle.getLowY() > voronoi.dictx.y()) {
					voronoi.circles.add(newCircle);
					voronoi.events.add(new CircleEvent(voronoi, newCircle));
				}				
			}			
		}		

		// for debugging		
//		System.out.println("=" + newBptNode.x());		
//		System.out.println("=" + newBptNode.getType());
//		System.out.println("=" + newBptNode);
//		printBptNodeX();
//		printBptNodeType();
		beachLineTree.put(newBptNode, new Parabola(voronoi.dictx, rightBptNode.getRightSite(), newBptNode, oldRightBptNode, voronoi.p));		
		beachLineTree.lowerEntry(newBptNode).getValue().setRightBptNode(newBptNode);		
		// connecting edges
		BptNode vertex = new BptNode("vertex", newBptNode);
		Edge leftEdge = leftBptNode.getEdge();
		Edge rightEdge = rightBptNode.getEdge();
		//System.out.println(leftEdge + "," + rightEdge);
		leftEdge.replaceNode(leftBptNode, vertex);
		rightEdge.replaceNode(rightBptNode, vertex);
		Edge newEdge = new Edge(newBptNode, vertex, voronoi.p);
		voronoi.edges.add(newEdge);
		newBptNode.setEdge(newEdge);
		// for debugging
		
	}
	public BptNode getNewLeftNode() {
		return newLeftNode;
	}
	public BptNode getNewRightNode() {
		return newRightNode;
	}
	public BptNode getNewNode() {
		return newBptNode;
	}
	public void draw() {
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {						
			p.ellipse(bptNode.x, bptNode.y, r, r);			
		}
		for (Parabola arc : beachLineTree.values()) {			
			if (arc != null) {
				arc.draw();
			}
		}
	}
	public void printBptNodeX() {		
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.x() + ", ");
		}
		System.out.println();
	}
	public void printBptNodeType() {		
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.getType() + ", ");
		}
		System.out.println();
	}
	public void printBptNode() {		
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode + ", ");
		}
		System.out.println();
	}
	public void printBptNodeY() {		
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.y() + ", ");
		}
		System.out.println();
	}

}
