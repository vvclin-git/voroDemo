package vorodemo;
import processing.core.PApplet;
public class Circle implements Comparable<Circle>{
	Point p1, p2, p3;
	float x, y, r;		
	float lowY;
	PApplet c;
	public Circle (Point p1, Point p2, Point p3, PApplet c) {
		float ma, mb;		
		float dxa, dxb;
		float x1, x2, x3;
		float y1, y2, y3;
		this.c = c;
		x1 = p1.x();
		x2 = p2.x();
		x3 = p3.x();
		y1 = p1.y();
		y2 = p2.y();
		y3 = p3.y();
		dxa = (x2 - x1);
		dxb = (x3 - x2);		
		if (dxb == 0) {
			x1 = p3.x();
			y1 = p3.y();
			x2 = p1.x();
			y2 = p1.y();
			x3 = p2.x();
			y3 = p2.y();
			dxa = (x2 - x1);
			dxb = (x3 - x2);
		}
		else if (dxa == 0) {
			x1 = p2.x();
			y1 = p2.y();
			x2 = p3.x();
			y2 = p3.y();
			x3 = p1.x();
			y3 = p1.y();
			dxa = (x2 - x1);
			dxb = (x3 - x2);
		}			
		ma = (y2 - y1) / dxa;
		mb = (y3 - y2) / dxb;
		x = ((ma * mb) * (y1 - y3) + mb * (x1 + x2) - ma * (x2 + x3)) / 
				(2 * (mb - ma));		
		y = (-1 / ma) * (x - (x1 + x2) / 2) + (y1 + y2) /2;
		r = (float) Math.sqrt(Math.pow((double) (p1.x() - x), 2) + Math.pow((double) (p1.y() - y), 2));
		lowY = y - r;
	}
	float getLowY() {
		return lowY;
	}
	float x() {
		return x;
	}
	float y() {
		return y;
	}
	void draw() {
		c.stroke(255);
		c.noFill();
		c.ellipse(x, y, r * 2, r * 2);
		
	}
	public static void main(String args[]) {
		Site p1 = new Site(10, 15, null);
		Site p2 = new Site(10, 23, null);
		Site p3 = new Site(43, 56, null);
		Circle c = new Circle(p1, p2, p3, null);
		System.out.print(c.x() + ", " + c.y() + ", " + c.r);
	}
	@Override
	public int compareTo(Circle that) {
		// TODO Auto-generated method stub
		if (this.lowY > that.lowY) {
			return 1;
		}
		if (this.lowY < that.lowY) {
			return -1;
		}
		return 0;
	}	

}
