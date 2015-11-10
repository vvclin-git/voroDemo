package vorodemo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	//PApplet canvas;	
	ArrayList<Site> beachLine = new ArrayList<Site>();
	ArrayList<Site> sitesAbove = new ArrayList<Site>();
	ArrayList<Site> sitesBelow = new ArrayList<Site>();
	ArrayList<BreakPoint> breakpts = new ArrayList<BreakPoint>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	Directrix dictx = new Directrix(100, this);
	
	private void siteEvent(Site site) {
		
		
		
	}
	private void circleEvent(Circle circle) {
		
	}
	public void mouseClicked() {  
		Site site = new Site(mouseX, mouseY, this);		
		if (site.y() > dictx.y()) {
			sitesBelow.add(site);
			//Collections.sort(sitesBelow);
		}
		else if (site.y() < dictx.y()) {
			sitesAbove.add(site);
			//Collections.sort(sitesAbove);
		} 
	}
	public void keyPressed() {
		if (key == CODED) {
			//for debugging
//			System.out.println();
//			System.out.print("above " + sitesAbove.size());
//			System.out.print(" below " + sitesBelow.size());
//			if (!arcs.isEmpty()) {
//				System.out.println(arcs.get(0).leftX + " " + arcs.get(0).rightX);
//			}
			Site siteTmpRef = null;
			if (keyCode == UP) { // not necessary
//				dictx.move(-1);				
//				for (Site site : sitesAbove) {
//					if (site.y() > dictx.y()) {
//						sitesBelow.add(site.copy());
//						siteTmp = site;						
//					}
//				}
//				sitesAbove.remove(siteTmp);
			} 
			else if (keyCode == DOWN) {
				dictx.move(1);
				for (Site site : sitesBelow) {
					if (site.y() < dictx.y()) {
						sitesAbove.add(site.copy());
						siteTmpRef = site;
						System.out.println(sitesAbove.size());
						if (sitesAbove.size() >= 3) {
							for (int i = 0; i <= sitesAbove.size() - 3; i++) {
								circles.add(new Circle(sitesAbove.get(i), sitesAbove.get(i + 1), sitesAbove.get(i + 2), this));
							}
							Collections.sort(circles);
						}
						//for debugging
//						for (Site site1 : sitesBelow) {
//							System.out.print(site1.y() + ", ");
//						}
//						System.out.println();
//						System.out.println("test");
					}
				}
				if (siteTmpRef != null) {
					siteEvent(siteTmpRef.copy());
					sitesBelow.remove(siteTmpRef);					
					siteTmpRef = null;
				}
				if (!arcs.isEmpty()) {					
					
//					System.out.println();
				}
			} 
		} 
	}

	public void setup() {
		background(0);				
	}
	public void settings() {		
		size(600, 600);
	}
	

	public void draw() {
		clear();
		dictx.draw();		
		if (!sitesAbove.isEmpty()) {
			for (Site site : sitesAbove) {
				site.draw();
			}
		}
		if (!sitesBelow.isEmpty()) {
			for (Site site : sitesBelow) {
				site.draw();
			}
		}
		
		if (!edges.isEmpty()) {
//			arcs.get(0).draw();
			for (Edge edge : edges) {				
					edge.draw();				
			}
		}
		if (!circles.isEmpty()) {
//			arcs.get(0).draw();
			for (Circle circle : circles) {				
					circle.draw();				
			}
		}	

		
	}
	public static void main(String args[]) {
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	}
}
