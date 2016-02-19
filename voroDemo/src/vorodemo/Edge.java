package vorodemo;
import processing.core.PApplet;
public class Edge {
	PApplet c;
	BptNode startBptNode, endBptNode;	
	Site site1, site2;
	public Edge(BptNode startBptNode, BptNode endBptNode, Site site1, Site site2, PApplet c) {
		this.startBptNode = startBptNode;
		this.endBptNode = endBptNode;
		this.site1 = site1;
		this.site2 = site2;
		this.c = c;
	}
	public Edge twins() {
		return new Edge(endBptNode, startBptNode, site1, site2, c);
	}
	public void replaceNode(BptNode oldBptNode, BptNode newBptNode) {		
		if (startBptNode == oldBptNode) {			
			startBptNode = newBptNode;
		}		
		else if (endBptNode == oldBptNode) {			
			endBptNode = newBptNode;
		}
	}
	public boolean isStatic() {
		return (startBptNode.getType() == "vertex" & endBptNode.getType() == "vertex");
	}
	public Site getSite1() {
		return site1;
	}
	public Site getSite2() {
		return site2;
	}
	
	void draw() {
//		System.out.println(leftBptNode.x() + ", " + leftBptNode.y() + ", " + rightBptNode.x() + ", " + rightBptNode.y());
//		System.out.println(leftBptNode + ", " + rightBptNode);
		c.line(startBptNode.x(), startBptNode.y(), endBptNode.x(), endBptNode.y());
	}

}
