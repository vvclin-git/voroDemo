package vorodemo;
import processing.core.PApplet;
public class Edge {
	PApplet c;
	BreakPoint leftBpt;
	BreakPoint rightBpt;
	public Edge(BreakPoint leftBpt, BreakPoint rightBpt, PApplet c) {
		this.rightBpt = rightBpt;
		this.leftBpt = leftBpt;
		this.c = c;
	}
	void draw() {
		c.line(leftBpt.x(), leftBpt.y(), rightBpt.x(), rightBpt.y());
	}

}
