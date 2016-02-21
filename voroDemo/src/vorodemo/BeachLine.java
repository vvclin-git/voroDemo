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
			System.out.println(oldLeftNode + "," + oldRightNode);
			if (oldLeftNode == oldRightNode) { // special case where there will be only one old node
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
				// create new arc
				Parabola newArc = new Parabola(voronoi.dictx, site, newLeftNode, newRightNode, voronoi.p);
				beachLineTree.put(newLeftNode, newArc);			
				// modify existing arcs (break)
				beachLineTree.get(oldNode).setLeftBptNode(newRightNode);				
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
		System.out.println(leftBptNode.x() + "," + rightBptNode.x());
		System.out.println(leftBptNode + "," + rightBptNode);
		printBptNodeX();
		printBptNode();		
		// remove bptNodes
		Parabola arcRemoved = beachLineTree.remove(leftBptNode);		
		beachLineTree.remove(rightBptNode);
		// remove leftBptNode again in case of the natural order of beachLineTree messing up by the modified compareTo method		
		if (arcRemoved == null) {
			beachLineTree.remove(leftBptNode);
		}
		printBptNodeX();
		printBptNode();
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
			p.fill(255, 0, 0);
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
