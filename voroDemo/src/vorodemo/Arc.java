package vorodemo;
import processing.core.PApplet;
public class Arc extends Parabola {
	PApplet c;
	boolean hide = false;
	Arc (Directrix dictx, Site site, BreakPoint leftBpt, BreakPoint rightBpt, PApplet c) {    
		super(dictx, site, leftBpt, rightBpt, c);
		this.rightBpt = rightBpt;
		this.leftBpt = leftBpt;
		this.site = site;
		this.c = c;
	}
}
