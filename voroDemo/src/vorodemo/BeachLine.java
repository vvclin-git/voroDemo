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
	public void removeArc(BptNode leftBptNode, BptNode rightBptNode) {		
		// remove arcs
		beachLineTree.remove(leftBptNode);		
		beachLineTree.remove(rightBptNode);		
		// generate new arcs
		BptNode oldRightBptNode = beachLineTree.higherKey(rightBptNode);
		BptNode newBptNode = new BptNode("left", leftBptNode.getLeftSite(), rightBptNode.getRightSite(), rightBptNode.getRightSite(), voronoi.dictx);		
		beachLineTree.put(newBptNode, new Parabola(voronoi.dictx, rightBptNode.getRightSite(), newBptNode, oldRightBptNode, voronoi.p));		
		beachLineTree.lowerEntry(newBptNode).getValue().setRightBptNode(newBptNode);
		// connecting edges
		BptNode vertex = new BptNode("vertex", newBptNode);
		Edge leftEdge = leftBptNode.getEdge();
		Edge rightEdge = rightBptNode.getEdge();
		System.out.println(leftEdge + "," + rightEdge);
		leftEdge.replaceNode(leftBptNode, vertex);
		rightEdge.replaceNode(rightBptNode, vertex);
		Edge newEdge = new Edge(newBptNode, vertex, voronoi.p);
		voronoi.edges.add(newEdge);
		newBptNode.setEdge(newEdge);
		// for debugging
		System.out.println();
		System.out.print(voronoi.dictx.y() + "| ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.x() + ", ");
		}
//		System.out.println();
	}
	public BptNode getNewLeftNode() {
		return newLeftNode;
	}
	public BptNode getNewRightNode() {
		return newRightNode;
	}
	public void draw() {
//		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
//			Parabola arc = beachLineTree.get(bptNode);			
//			p.ellipse(bptNode.x, bptNode.y, r, r);
//			if (arc != null) {
//				arc.draw();
//			}
//		}
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {						
			p.ellipse(bptNode.x, bptNode.y, r, r);			
		}
		for (Parabola arc : beachLineTree.values()) {			
			if (arc != null) {
				arc.draw();
			}
		}
	}

}
