package vorodemo;
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
			// special case where there will be only one old node
			if (oldLeftNode == oldRightNode) {				
				BptNode oldNode = oldLeftNode;
				BptNode oldPrevNode = beachLineTree.lowerKey(oldNode);
				Site leftSite, rightSite;
				if (oldNode.getLeftSite().x() < oldNode.getRightSite().x()) { 
					// to guarantee that left/right site is exactly the one on the left/right of the new site
					leftSite = oldNode.getLeftSite();
					rightSite = oldNode.getRightSite();
				}
				else {
					leftSite = oldNode.getRightSite();
					rightSite = oldNode.getLeftSite();
				}
				newLeftNode = new BptNode("left", leftSite, site, site, voronoi.dictx);
				newRightNode = new BptNode("right", site, rightSite, site, voronoi.dictx);				
				Parabola oldArc = beachLineTree.get(oldNode).clone();
				beachLineTree.remove(oldNode);
				beachLineTree.put(newRightNode, oldArc);
				beachLineTree.get(newRightNode).setLeftBptNode(newRightNode);
				// create new arc
				Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
				beachLineTree.put(newLeftNode, newArc);			
				// modify existing arcs (break)
				beachLineTree.get(newRightNode).setLeftBptNode(newRightNode);				
				beachLineTree.get(oldPrevNode).setRightBptNode(newLeftNode);				
			}
			else {
				// create new nodes
				newLeftNode = new BptNode("left", oldLeftNode.rightSite, site, site, voronoi.dictx);
				newRightNode = new BptNode("right", site, oldRightNode.leftSite, site, voronoi.dictx);
				Parabola oldArc = beachLineTree.get(oldLeftNode).clone();
				// create new arc
				Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
				beachLineTree.put(newLeftNode, newArc);			
				// modify existing arcs (break)
				beachLineTree.get(oldLeftNode).setRightBptNode(newLeftNode);
				beachLineTree.put(newRightNode, oldArc);
				beachLineTree.get(newRightNode).setLeftBptNode(newRightNode);		
			}				
		}
	}
	public void removeArc(BptNode leftBptNode, BptNode rightBptNode, Circle circle) {		
		// remove arcs
		// TODO: need better way to remove bpts
//		System.out.println("x-pos of left/right bptNode to be removed: " + leftBptNode.x() + "," + rightBptNode.x());
//		System.out.println("left/right bptNode to be removed: " + leftBptNode + "," + rightBptNode);
//		System.out.println("the beach line before removal");
//		printBptNodeX();
//		printBptNode();		
		// remove bptNodes
		Parabola arcRemoved1 = beachLineTree.remove(leftBptNode);		
		Parabola arcRemoved2 = beachLineTree.remove(rightBptNode);
		// remove leftBptNode again in case of the natural order of beachLineTree messing up by the modified compareTo method		
		if (arcRemoved1 == null) {
			leftBptNode.update();
			arcRemoved1 = beachLineTree.remove(leftBptNode);
		}
		if (arcRemoved2 == null) {
			rightBptNode.update();
			arcRemoved2 = beachLineTree.remove(rightBptNode);
		}
		// to ensure the bptNodes are removed successfully
		assert arcRemoved1 != null;
		assert arcRemoved2 != null;
//		System.out.println("the beach line after removal");
//		printBptNodeX();
//		printBptNode();
		// set both of bptNodes as processed (for circle event removal)
		leftBptNode.setProcessed();
		rightBptNode.setProcessed();
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
			p.fill(255, 255, 0);
			p.stroke(255, 255, 0);
			p.ellipse(bptNode.x, bptNode.y, r, r);			
		}
		p.stroke(255);
		for (Parabola arc : beachLineTree.values()) {			
			if (arc != null) {
				arc.draw();
			}
		}
	}
	public void printBptNodeX() {		
		System.out.print("the y-pos of directrix: " + voronoi.dictx.y() + "| the x-pos of bptNodes: ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.x() + ", ");
		}
		System.out.println();
	}
	public void printBptNodeType() {		
		System.out.print("the y-pos of directrix: " + voronoi.dictx.y() + "| the type of bptNodes: ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.getType() + ", ");
		}
		System.out.println();
	}
	public void printBptNode() {		
		System.out.print("the y-pos of directrix: " + voronoi.dictx.y() + "| the bptNodes: ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode + ", ");
		}
		System.out.println();
	}
	public void printBptNodeY() {		
		System.out.print("the y-pos of directrix: " + voronoi.dictx.y() + "| the y-pos of bptNodes: ");
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			System.out.print(bptNode.y() + ", ");
		}
		System.out.println();
	}
	public boolean checkBptOrder() {
		float xTmp = Float.NEGATIVE_INFINITY;
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			if (bptNode.getType() != "leftBound") {
				if (bptNode.x() < xTmp) {
					return false;
				}
				else {					
					xTmp = bptNode.x();
				}
			}
		}
		return true;
	}
}
