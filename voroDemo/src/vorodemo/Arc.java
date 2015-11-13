package vorodemo;
import processing.core.PApplet;
public class Arc extends Parabola {
	PApplet c;
	boolean hide = false;
	Arc (Directrix dictx, Site site, BreakPoint leftBpt, BreakPoint rightBpt, PApplet c) {    
		super(dictx, site, c);
		this.rightBpt = rightBpt;
		this.leftBpt = leftBpt;
		this.site = site;
		this.c = c;
	}
	
//	void update () {
//		//FIXME possible root cause of incorrect drawing of beach line! 
//		this.leftBpt.update();
//		this.rightBpt.update();
//		rightX = rightBpt.x();
//		leftX = leftBpt.x();		
//		p = (float) (Math.abs(focusY - dictx.y()) * 0.5);
//		k = dictx.y() - p;
//		if (leftX < 0) { // deal with corner case (x < 0)
//			leftX = 0;
//		}
//		if (leftX > c.width) { // deal with corner case (x < 0)
//			leftX = c.width;
//			hide = true;
//		}
//		if (rightX < 0) { // deal with corner case (x < 0)
//			rightX = 0;
//			hide = true;
//		}
//		if (!hide) {
//			if (y(leftX) < 0 & y(rightX) > 0) { // deal with corner case (y < 0)			
//				leftX = (float) (focusX - Math.sqrt(Math.pow(dictx.y(), 2)-Math.pow(focusY, 2) + 0.1f * 2 * (focusY - dictx.y())));
//				if (leftX >= rightX) {
//					hide = true;
//				}
//	
//			}
//			else if (y(leftX) > 0 & y(rightX) < 0) {			
//				rightX = (float) (focusX + Math.sqrt(Math.pow(dictx.y(), 2)-Math.pow(focusY, 2) + 0.1f * 2 * (focusY - dictx.y())));
//				if (rightX <= leftX) {
//					hide = true;
//				}
//	
//			}
//			else if (y(leftX) < 0 & y(rightX) < 0) {
//				hide = true;			
//			}
//		}
//	}

}
