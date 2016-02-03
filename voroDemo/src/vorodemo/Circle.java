package vorodemo;
import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Arrays;
public class Circle implements Comparable<Circle>{
	Site p1, p2, p3;
	float x, y, r, yInit;		
	float lowY;
	BptNode bpt1, bpt2;
	PApplet c;
	ArrayList<Site> sites = new ArrayList<Site>(); 
	//public Circle (Point p1, Point p2, Point p3, PApplet c) {
	public Circle (BptNode bpt1, BptNode bpt2, float yInit, PApplet c) {
		this.bpt1 = bpt1;
		this.bpt2 = bpt2;
		this.yInit = yInit;
		p2 = bpt1.getSharedSite(bpt2);
		if (bpt1.leftSite.equals(p2)) {
			p1 = bpt1.rightSite;
		}
		else {
			p1 = bpt1.leftSite;
		}
		if (bpt2.leftSite.equals(p2)) {
			p3 = bpt2.rightSite;
		}
		else {
			p3 = bpt2.leftSite;
		}
		float ma, mb;		
		float dxa, dxb;
		float x1, x2, x3;
		float y1, y2, y3;
		this.c = c;
		sites.add(p1);
		sites.add(p2);
		sites.add(p3);		
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
		lowY = y + r;
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
	public Site getCenter() {
		return new Site(x, y, c);
	}
	void draw() {
		c.stroke(255);
		c.noFill();
		c.ellipse(x, y, r * 2, r * 2);
		
	}
	public boolean containSite(Point site) {
		return sites.contains(site);
	}
	public boolean containProcessedSite() {
		for (Site site : sites) {
			if (site.isProcessed()) {
				return true;				
			}
		}
		return false;
	}
	public BptNode getBpt1() {
		return bpt1;
	}
	public BptNode getBpt2() {
		return bpt2;
	}
	public Site getLeftSite() {
		if (p1.x() < p3.x()) {
			return p1;
		}
		else {
			return p3;
		}		
	}
	public Site getMedSite() {
		return p2;
	}
	public Site getRightSite() {
		if (p1.x() > p3.x()) {
			return p1;
		}
		else {
			return p3;
		}	
	}
	public float getYInit() {
		return yInit;
	}
	public static void main(String args[]) {
//		Site p1 = new Site(10, 15, null);
//		Site p2 = new Site(10, 23, null);
//		Site p3 = new Site(43, 56, null);
//		Circle c = new Circle(p1, p2, p3, null);
//		System.out.print(c.x() + ", " + c.y() + ", " + c.r);
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
