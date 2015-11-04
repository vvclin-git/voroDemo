package vorodemo;
import processing.core.PApplet;
public class Arc extends Parabola {
	BreakPoint rightBpt;
	BreakPoint leftBpt;
	PApplet c;
	boolean hide = false;
	Arc (Directrix dictx, Site site, BreakPoint leftBpt, BreakPoint rightBpt, PApplet c) {    
		super(dictx, site, c);
		this.rightBpt = rightBpt;
		this.leftBpt = leftBpt;
		this.site = site;
		this.c = c;
	}

	boolean inArc (Site site) {
		return (site.x() > this.leftBpt.x() & site.x() < this.rightBpt.x());
	}
	boolean hidden() {
		return hide;
	}
	void update () {
		//FIXME possible root cause of incorrect drawing of beach line! 
		this.leftBpt.update();
		this.rightBpt.update();
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

}
