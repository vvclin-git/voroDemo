package vorodemo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	//PApplet canvas;
	ArrayList<Parabola> parabolae = new ArrayList<Parabola>();
	ArrayList<Parabola> arcs = new ArrayList<Parabola>();
	ArrayList<Site> sitesAbove = new ArrayList<Site>();
	ArrayList<Site> sitesBelow = new ArrayList<Site>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	TreeMap<Point, BreakPoint> beachLine = new TreeMap<Point, BreakPoint>();
	Directrix dictx = new Directrix(100, this);
	
	private void siteEvent(Site site) {
		BreakPoint leftBpt, rightBpt;
		BreakPoint floorBpt;
		Parabola newPara;
		if (beachLine.isEmpty()) {
			newPara = new Parabola(dictx, site, this);
			
		}
		else {
			floorBpt = (BreakPoint) beachLine.floorKey(site);			
			newPara = new Parabola(dictx, site, floorBpt.getRightPara(), this);
			floorBpt.paraRight.rightBpt = newPara.getLeftBpt();
		}
		parabolae.add(newPara);
		leftBpt = newPara.getLeftBpt();
		rightBpt = newPara.getRightBpt();
		beachLine.put(leftBpt, leftBpt);
		beachLine.put(rightBpt, rightBpt);
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
				if (!beachLine.isEmpty()) {
					for (BreakPoint bpt : beachLine.values()) {
						Parabola para = bpt.getRightPara();
						if (para != null) {
							para.update();
						}							
					}
				}
				for (Site site : sitesBelow) {
					if (site.y() < dictx.y()) {
						sitesAbove.add(site.copy());
						siteTmpRef = site;
						//System.out.println(sitesAbove.size());
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
					System.out.println(beachLine.toString());
					sitesBelow.remove(siteTmpRef);					
					siteTmpRef = null;
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
	public void drawBeachLine() {
//		ArrayList<BreakPoint> bpts = new ArrayList<BreakPoint>();
		for (BreakPoint bpt : beachLine.values()) {
			Parabola para = bpt.getRightPara();			
			if (para != null) {
				para.draw();
			}
		}
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
		
		drawBeachLine();
		
		if (!circles.isEmpty()) {
			for (Circle circle : circles) {
				circle.draw();
			}
		}

		
	}
	public static void main(String args[]) {
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	}
}
