package vorodemo;
import processing.core.PApplet;

public class Parabola {
	float focusX, focusY, direY; 
	float leftX = 0; 
	float rightX;  
	float p, k;
	int step = 120;
	Parabola thatPara;
	BptNode rightBptNode;
	BptNode leftBptNode;
	Site site;
	Directrix dictx;
	PApplet c;
	boolean hide = false;
	Parabola (Directrix dictx, Site site, BptNode leftBptNode, BptNode rightBptNode, PApplet c) {    
		this.focusX = site.x();
		this.focusY = site.y();		
		this.dictx = dictx;
		this.c = c;
		this.site = site;
		this.leftBptNode = leftBptNode;
		this.rightBptNode = rightBptNode;		
	}
	void draw() {
		// for site event
		if (dictx.y() == site.y()) {
			if (thatPara != null) {
				c.line(site.x(), site.y(), site.x(), thatPara.y(site.x()));				
			}
			else {
				c.line(site.x(), site.y(), site.x(), 0);				
			}			
		}
		// for normal situation
		else {
			rightX = rightBptNode.x();
			leftX = leftBptNode.x();
			if (rightX > c.width) {
				rightX = c.width;
			}
			if (leftX < 0) {
				leftX = 0;
			}
			float dX = (rightX - leftX) / step;
			float x0 = leftX;
			float y0;
			float x1, y1;		
			p = (float) (Math.abs(focusY - dictx.y()) * 0.5);
			k = dictx.y() - p;
			//System.out.println(rightBptNode.x() + "," + leftBptNode.x() + "," + dX);
			//System.out.println(rightBptNode + "," + leftBptNode + "," + dX);
			for (int i = 0; i < (rightX - leftX) / dX; i += 1) {
				x1 = x0 + dX;
				y0 = y(x0);
				y1 = y(x1);			
				c.line(x0, y0, x1, y1);			
				x0 = x1;
				//System.out.println(x0 + "," + y0 + "," + x1 + "," + y1);				
				//System.out.println(dX);
			}
		}
	}
	float y(float x) {
		return (float) ((Math.pow(x, 2) - 2 * focusX * x + (Math.pow(focusX, 2) + Math.pow(focusY, 2)) - Math.pow(dictx.y(), 2)) / (focusY - dictx.y()) * 0.5);
	}
	public float a() {
		return (float) (0.5 / (focusY - dictx.y()));
	}
	public float b() {
		return -focusX / (focusY - dictx.y());
	}
	public float c() {
		return (float) (0.5 / (focusY - dictx.y()) * ((Math.pow(focusX, 2) + Math.pow(focusY, 2)) - Math.pow(dictx.y(), 2)));
	}
	public void setLeft(float leftX) {
		this.leftX = leftX;
		return;
	}
	public void setRight(float rightX) {
		this.rightX = rightX;
		return;
	}
	public void setLeftBptNode(BptNode leftBptNode) {
		this.leftBptNode = leftBptNode;
		return;
	}
	public void setRightBptNode(BptNode rightBptNode) {
		this.rightBptNode = rightBptNode;
		return;
	}
	public BptNode getLeftBptNode() {		
		return leftBptNode;
	}
	public BptNode getRightBptNode() {		
		return rightBptNode;
	}
//	public void setLeftBpt(BreakPoint leftBpt) {
//		this.leftBpt = leftBpt;
//		return;
//	}
//	public void setRightBpt(BreakPoint rightBpt) {
//		this.rightBpt = rightBpt;
//		return;
//	}
//	public BreakPoint getLeftBpt() {		
//		return leftBpt;
//	}
//	public BreakPoint getRightBpt() {		
//		return rightBpt;
//	}
	public boolean hidden() {
		return hide;
	}
//	public boolean inArc (Site site) {
//		return (site.x() > this.leftBpt.x() & site.x() < this.rightBpt.x());
//	}
	
	public BreakPoint leftInterPt(Parabola other) {
		float da, db, dc, h, x;
		float x1, x2;
		da = this.a() - other.a();
		db = this.b() - other.b();
		dc = this.c() - other.c();
		h = (float) (Math.pow(db, 2) - 4 * da * dc);		
		if (h >= 0) {
			x1 = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
			x2 = (float) ((-db + (float) Math.sqrt(h)) / da * 0.5);
			if (x1 > x2) {
				x = x2;				
			}
			else {
				x = x1;
			}
			return new BreakPoint(x, other.y(x), "left", other, this, c);
		}
		else {
			return null;	
		}		
	}
	public BreakPoint rightInterPt(Parabola other) {
		float da, db, dc, h, x;
		float x1, x2;
		da = this.a() - other.a();
		db = this.b() - other.b();
		dc = this.c() - other.c();
		h = (float) (Math.pow(db, 2) - 4 * da * dc);		
		if (h >= 0) {
			x1 = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
			x2 = (float) ((-db + (float) Math.sqrt(h)) / da * 0.5);
			if (x1 < x2) {
				x = x2;				
			}
			else {
				x = x1;
			}
			return new BreakPoint(x, other.y(x), "right", this, other, c);
		}
		else {
			return null;			
		}		
	}
	public Parabola clone() {
		return new Parabola(dictx, site, leftBptNode, rightBptNode, c);
	}

}
