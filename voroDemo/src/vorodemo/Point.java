package vorodemo;
import processing.core.PApplet;
public abstract class Point implements Comparable<Point>{
	float x, y;
	float r = 2;
	PApplet p;
	Point (float x, float y, PApplet p) {
		this.x = x;
		this.y = y;
		this.p = p;
	}
	float y() {
		return this.y;
	}
	float x() {
		return this.x;
	}
	abstract Object copy();
	void draw() {
		p.ellipse(x, y, r, r);		
	}
	public int compareTo(Point other) {
		if (this.x < other.x) {
			return -1;
		}    
		if (this.x > other.x) {
			return 1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}
	public double distSqrTo(Point that) {
		float dx = this.x() - that.x();
		float dy = this.y() - that.y();
		return Math.pow(dx, 2) + Math.pow(dy, 2);
	}
}
