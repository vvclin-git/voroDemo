package vorodemo;

import processing.core.PApplet;

public class ParabolaTest extends PApplet{
	
	
	public void setup() {
		background(0);				
	}
	public void settings() {		
		size(600, 600);
	}
	Directrix dictx = new Directrix(300, this);
	Site site1 = new Site(300, 250, this);	
//	BreakPoint leftBpt = new BreakPoint(0, -625, "leftBound", null, null, this);
//	BreakPoint rightBpt = new BreakPoint(600, -625, "rightBound", null, null, this);
	//Parabola paraTest = new Parabola(dictx, site1, leftBpt, rightBpt, this);
	Parabola paraTest = new Parabola(dictx, site1, 600, this);
	public void draw() {
		//System.out.print(paraTest.leftX + ", " + paraTest.rightX + "|");
		//System.out.print(paraTest.leftBpt.x() + ", " + paraTest.rightBpt.x() + "|");	
		
		site1.draw();
		paraTest.draw();
		dictx.draw();
	
	}
	public static void main(String args[]) {
	    PApplet.main(new String[] { vorodemo.ParabolaTest.class.getName() });	    
	}

}
