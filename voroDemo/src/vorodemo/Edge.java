package vorodemo;
import processing.core.PApplet;
public class Edge {
	PApplet c;
	BptNode leftBptNode;
	BptNode rightBptNode;
	Edge prevEdge, nextEdge;	
	public Edge(BptNode leftBptNode, BptNode rightBptNode, PApplet c) {
		this.leftBptNode = leftBptNode;
		this.rightBptNode = rightBptNode;
		this.c = c;
	}
	public Edge twins() {
		return new Edge(rightBptNode, leftBptNode, c);
	}
	public void replaceNode(BptNode oldBptNode, BptNode newBptNode) {		
		if (leftBptNode == oldBptNode) {			
			leftBptNode = newBptNode;
		}		
		else if (rightBptNode == oldBptNode) {			
			rightBptNode = newBptNode;
		}
	}
	public boolean isStatic() {
		return (leftBptNode.getType() == "vertex" & rightBptNode.getType() == "vertex");
	}
	public void setPrev(Edge edge) {
		prevEdge = edge;
	}
	public void setNext(Edge edge) {
		nextEdge = edge;
		
	}
	void draw() {
//		System.out.println(leftBptNode.x() + ", " + leftBptNode.y() + ", " + rightBptNode.x() + ", " + rightBptNode.y());
//		System.out.println(leftBptNode + ", " + rightBptNode);
		c.line(leftBptNode.x(), leftBptNode.y(), rightBptNode.x(), rightBptNode.y());
	}

}
