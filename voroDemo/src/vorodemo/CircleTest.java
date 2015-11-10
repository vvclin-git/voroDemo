package vorodemo;

import java.util.ArrayList;

import processing.core.PApplet;

public class CircleTest extends PApplet{
	ArrayList<Site> sites = new ArrayList<Site>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	int siteInd = 0;
	public void mouseClicked() {  
		Site site = new Site(mouseX, mouseY, this);		
		sites.add(site);
		if (sites.size() % 3 == 0) {
			circles.add(new Circle (sites.get(siteInd), sites.get(siteInd + 1), sites.get(siteInd + 2), this));
			siteInd += 3;				
			}			
		} 
	
	public void setup() {
		background(0);				
	}
	public void settings() {		
		size(600, 600);
	}
	

	public void draw() {
		if (!circles.isEmpty()) {
			for (Circle circle : circles) {
				circle.draw();
			}
		}
		if (!sites.isEmpty()) {
			for (Site site : sites) {
				site.draw();
			}
		}
	
	}
	public static void main(String args[]) {
	    PApplet.main(new String[] { vorodemo.CircleTest.class.getName() });
	}

}
