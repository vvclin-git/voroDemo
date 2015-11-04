package vorodemo;
import processing.core.PApplet;

public class Parabola {
	float focusX, focusY, direY; 
	float leftX = 0; 
	float rightX;  
	float p, k;
	int step = 120;
	Site site;
	Directrix dictx;
	PApplet c;
	Parabola (Directrix dictx, Site site, PApplet c) {    
		this.focusX = site.x();
		this.focusY = site.y();		
		this.rightX = c.width;
		this.dictx = dictx;
		this.c = c;
		this.site = site;
	}
	void draw() {    
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
	BreakPoint leftInterPt(Parabola other) {
		float da, db, dc, h, x;
		da = this.a() - other.a();
		db = this.b() - other.b();
		dc = this.c() - other.c();
		h = (float) (Math.pow(db, 2) - 4 * da * dc);		
		if (h >= 0) {
			x = (float) ((-db - (float) Math.sqrt(h)) / da * 0.5);
		}
		else {
			x = -1;
		}
		if (x > 0) {
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
		}
		else {
			x = -1;
		}
		if (x > 0) {
			return new BreakPoint(x, other.y(x), "right", other, this, c);
		}
		else {
			return null;
		}
	}	

}
