package vorodemo;
import processing.core.PApplet;

public class Parabola {
	float focusX, focusY, direY; 
	float leftX = 0; 
	float rightX;  
	float p, k;
	int step = 120;
	BreakPoint rightBpt;
	BreakPoint leftBpt;
	Site site;
	Directrix dictx;
	PApplet c;
	boolean hide = false;
	Parabola (Directrix dictx, Site site, PApplet c) {    
		this.focusX = site.x();
		this.focusY = site.y();		
		this.rightX = c.width;
		this.dictx = dictx;
		this.c = c;
		this.site = site;
		this.leftBpt = new BreakPoint(0, this.y(0), "leftBound", null, this, c);
		this.rightBpt = new BreakPoint(c.width, this.y(c.width), "rightBound", this, null, c);
	}
	Parabola (Directrix dictx, Site site, Parabola that, PApplet c) {    
		this.focusX = site.x();
		this.focusY = site.y();		
		this.rightX = c.width;
		this.dictx = dictx;
		this.c = c;
		this.site = site;
		this.leftBpt = this.leftInterPt(that);
		this.rightBpt = this.rightInterPt(that);
	}
	void draw() {
		//update();
		float dX = (rightX - leftX) / step;
		float x0 = leftX;
		float y0;
		float x1, y1;		
		p = (float) (Math.abs(focusY - dictx.y()) * 0.5);
		k = dictx.y() - p;
		for (int i = 0; i < (rightX - leftX) / dX; i+= 1) {
			x1 = x0 + dX;
			y0 = y(x0);
			y1 = y(x1);      
			c.line(x0, y0, x1, y1);			
			x0 = x1;
		}      
	}
	float y(float x) {
		return (float) ((Math.pow(x, 2) - 2 * focusX * x + (Math.pow(focusX, 2) + Math.pow(focusY, 2)) - Math.pow(dictx.y(), 2)) / (focusY - dictx.y()) * 0.5);
	}
	public float a() {
		return (float) (0.5 / (focusY - dictx.y()));
	}
	float b() {
		return -focusX / (focusY - dictx.y());
	}
	float c() {
		return (float) (0.5 / (focusY - dictx.y()) * ((Math.pow(focusX, 2) + Math.pow(focusY, 2)) - Math.pow(dictx.y(), 2)));
	}
	void setLeft(float leftX) {
		this.leftX = leftX;
		return;
	}
	void setRight(float rightX) {
		this.rightX = rightX;
		return;
	}
	boolean hidden() {
		return hide;
	}
	boolean inArc (Site site) {
		return (site.x() > this.leftBpt.x() & site.x() < this.rightBpt.x());
	}
	void update() {
//		this.leftBpt.update();
//		this.rightBpt.update();
		rightX = rightBpt.x();
		leftX = leftBpt.x();		
		p = (float) (Math.abs(focusY - dictx.y()) * 0.5);
		k = dictx.y() - p;
		if (leftX < 0) { // deal with corner case (x < 0)
			leftX = 0;
		}
		if (y(leftX) < 0) { // deal with corner case (y < 0)			
			leftX = (float) (focusX - Math.sqrt(Math.pow(dictx.y(), 2)-Math.pow(focusY, 2) + 0.1f * 2 * (focusY - dictx.y())));
			if (leftX >= rightX) { //the arc is outside of the canvas
				hide = true;
				return;
			}
		}
		if (y(rightX) < 0) {			
			rightX = (float) (focusX + Math.sqrt(Math.pow(dictx.y(), 2)-Math.pow(focusY, 2) + 0.1f * 2 * (focusY - dictx.y())));
			if (rightX <= leftX) { //the arc is outside of the canvas
				hide = true;
				return;
			}
		}		
	}
	BreakPoint leftInterPt(Parabola other) {
		float da, db, dc, h, x;
		da = this.a() - other.a();
		db = this.b() - other.b();
		dc = this.c() - other.c();
		h = (float) (Math.pow(db, 2) - 4 * da * dc);		
		if (h >= 0) {
			x = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
			return new BreakPoint(x, other.y(x), "left", this, other, c);
		}
		else {
			return null;	
		}		
	}
	BreakPoint rightInterPt(Parabola other) {
		float da, db, dc, h, x;
		da = this.a() - other.a();
		db = this.b() - other.b();
		dc = this.c() - other.c();
		h = (float) (Math.pow(db, 2) - 4 * da * dc);		
		if (h >= 0) {
			x = (float) ((-db + (float) Math.sqrt(h)) / da * 0.5);
			return new BreakPoint(x, other.y(x), "right", other, this, c);
		}
		else {
			return null;			
		}		
	}	

}
