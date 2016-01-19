package vorodemo;
import processing.core.PApplet;
public class Edge {
	PApplet c;
	BptNode leftBptNode;
	BptNode rightBptNode;
	public Edge(BptNode leftBptNode, BptNode rightBptNode, PApplet c) {
		this.leftBptNode = leftBptNode;
		this.rightBptNode = rightBptNode;
		this.c = c;
	}
	void draw() {
//		System.out.println(leftBptNode.x() + ", " + leftBptNode.y() + ", " + rightBptNode.x() + ", " + rightBptNode.y());
//		System.out.println(leftBptNode + ", " + rightBptNode);
		c.line(leftBptNode.x(), leftBptNode.y(), rightBptNode.x(), rightBptNode.y());
	}

}
