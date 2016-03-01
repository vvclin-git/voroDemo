package vorodemo;
import processing.core.PApplet;
import java.util.ArrayList;
public class Circle implements Comparable<Circle>{
	Site p1, p2, p3;
	float x, y, r, yInit;		
	float lowY;
	BptNode bptNode1, bptNode2;
	PApplet c;
	ArrayList<Site> sites = new ArrayList<Site>(); 
	//public Circle (Point p1, Point p2, Point p3, PApplet c) {
	public Circle (BptNode bptNode1, BptNode bptNode2, float yInit, PApplet c) {
		this.bptNode1 = bptNode1;
		this.bptNode2 = bptNode2;
		this.yInit = yInit;
		p2 = bptNode1.getSharedSite(bptNode2);
		if (bptNode1.leftSite.equals(p2)) {
			p1 = bptNode1.rightSite;
		}
		else {
			p1 = bptNode1.leftSite;
		}
		if (bptNode2.leftSite.equals(p2)) {
			p3 = bptNode2.rightSite;
		}
		else {
			p3 = bptNode2.leftSite;
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
	public Point getCenter() {
		return new Point(x, y, c);
	}
	public Site getCenterSite() {
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
//	public boolean containProcessedSite() {
//		for (Site site : sites) {
//			if (site.isProcessed()) {
//				return true;				
//			}
//		}
//		return false;
//	}
	public boolean containProcessedBptNode() {
		if (bptNode1.isProcessed() & bptNode2.isProcessed()) {
			return true;
		}
//		if (bptNode2.isProcessed()) {
//			return true;
//		}
		return false;
	}
	public BptNode getBptNode1() {
		return bptNode1;
	}
	public BptNode getBptNode2() {
		return bptNode2;
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
	public Site getLowSite() {
		float lowY = p1.y();
		Site lowSite = p1;		
		if (p2.y() < lowY) {
			lowY = p2.y();
			lowSite = p2;
		}
		else if (p3.y() < lowY) {
			lowY = p3.y();
			lowSite = p3;
		}
		return lowSite;
	}
	public float getYInit() {
		return yInit;
	}
	public boolean isConverge1() {
		Point center = getCenter();
		float m1 =  (center.y() - bptNode1.y()) / (center.x() - bptNode1.x());
		float m2 =  (center.y() - bptNode2.y()) / (center.x() - bptNode2.x());
		System.out.println("the x-pos of bptnodes: " + bptNode1.x() + ", " + bptNode2.x());
		System.out.println("the orientation of bptnodes: " + bptNode1.getSiteOrient() + ", " + bptNode2.getSiteOrient());
		if (m1 != m2) {	
			if (bptNode1.getType() == bptNode2.getType()) {
				return (bptNode1.getSiteOrient() != bptNode2.getSiteOrient());
			}
			else {
				return ((bptNode1.getSiteOrient() != bptNode2.getSiteOrient()) | (bptNode1.getSiteOrient() == 0 & bptNode2.getSiteOrient() == 0));				
			}
		}
		return false;		
	}
	public boolean isConverge() {
		Point center = getCenter();
		float m1 =  (center.y() - bptNode1.y()) / (center.x() - bptNode1.x());
		float m2 =  (center.y() - bptNode2.y()) / (center.x() - bptNode2.x());
//		System.out.println(bpt1 + ", " + bpt2);
		System.out.println("x-pos of left/right bptNode: " + bptNode1.x() + ", " + bptNode2.x());
		System.out.println("center pos: " + center);
		boolean status1 = false;
		boolean status2 = false;
		if (m1 != m2) {			
			if (bptNode1.getType() == "right") {
				if (bptNode1.x() < center.x()) {
					status1 = true;
				}
				else {
					status1 = false;
				}
			}
			if (bptNode1.getType() == "left") {
				if (bptNode1.x() > center.x()) {
					status1 = true;
				}
				else {
					status1 = false;
				}
			}	
			if (bptNode2.getType() == "right") {
				if (bptNode2.x() < center.x()) {
					status2 = true;
				}
				else {
					status2 = false;
				}
			}
			if (bptNode2.getType() == "left") {
				if (bptNode2.x() > center.x()) {
					status2 = true;
				}
				else {
					status2 = false;
				}
			}
		}
		return (status1 & status2);
//		if (status) {
//			return true;
//		}		
//		return false;
	}
	
	public static void main(String args[]) {
//		Site p1 = new Site(10, 15, null);
//		Site p2 = new Site(10, 23, null);
//		Site p3 = new Site(43, 56, null);
//		Circle c = new Circle(p1, p2, p3, null);
//		System.out.print(c.x() + ", " + c.y() + ", " + c.r);
	}
	
	public boolean isEqual(Circle that) {
		System.out.println(this.x() + ", " + this.y() + "|" + that.x() + ", " + that.y());
		return (this.x() == that.x() & this.y() == that.y());
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
