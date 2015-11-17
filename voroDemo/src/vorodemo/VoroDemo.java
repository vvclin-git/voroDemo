package vorodemo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	//PApplet canvas;
	ArrayList<Parabola> parabolae = new ArrayList<Parabola>();
	//ArrayList<Arc> arcs = new ArrayList<Arc>();
	ArrayList<BreakPoint> breakPoints = new ArrayList<BreakPoint>();
	ArrayList<Site> sitesAbove = new ArrayList<Site>();
	ArrayList<Site> sitesBelow = new ArrayList<Site>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	TreeMap<Point, Point> beachLine = new TreeMap<Point, Point>();
	TreeMap<Point, Parabola> arcs = new TreeMap<Point, Parabola>();
	//TreeMap<Float, Site> sitesTree = new TreeMap<Float, Site>();
	Directrix dictx = new Directrix(100, this);	
	
	private void siteEvent(Site site) {
		if (beachLine.isEmpty()) {
			Parabola newPara = new Parabola(dictx, site, 600, this);
			beachLine.put(newPara.leftBpt, newPara.leftBpt);
			beachLine.put(newPara.rightBpt, newPara.rightBpt);
			breakPoints.add(newPara.leftBpt);
			breakPoints.add(newPara.rightBpt);
			arcs.put(newPara.leftBpt, newPara);
		}
		else {
			// determine which arc to insert a site
			BreakPoint bptLeft = (BreakPoint) beachLine.floorEntry(site).getValue();
			BreakPoint bptRight = (BreakPoint) beachLine.ceilingEntry(site).getValue();
			Parabola newPara = new Parabola(dictx, site, bptLeft.paraRight, this);
			// create new break points
			BreakPoint newBptLeft = newPara.leftBpt;
			BreakPoint newBptRight = newPara.rightBpt;
			beachLine.put(newBptLeft, newBptLeft);
			beachLine.put(newBptRight, newBptRight);
			breakPoints.add(newBptLeft);
			breakPoints.add(newBptRight);
			// add new arcs
			arcs.remove(bptLeft);
			arcs.put(bptLeft, new Parabola (dictx, bptLeft.paraRight.site, bptLeft, newBptLeft, this));
			arcs.put(newBptLeft, newPara);
			arcs.put(newBptRight, new Parabola (dictx, bptLeft.paraRight.site, newBptRight, bptRight, this));			
		}
	}
	private void updateBpts() {
		for (BreakPoint bpt : breakPoints) {
			bpt.update();
		}
	}
	public void mouseClicked() {  
		Site site = new Site(mouseX, mouseY, this);		
		if (site.y() > dictx.y()) {
			System.out.print(site + ", ");
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
				updateBpts();				
				//updateArc();
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
					System.out.println();
					for (Parabola arc : arcs.values()) {						
						System.out.print(arc.leftBpt.type + ", " + arc.rightBpt.type + "|");
					}
					System.out.println();
					for (Parabola arc : arcs.values()) {
						System.out.print("(" + arc.leftBpt.x() + ", "  + arc.leftBpt.y() + "), " + "(" + arc.rightBpt.x() + ", " +arc.rightBpt.y() + ")" + "|");						
					}
					System.out.println();
//					for (Parabola arc : arcs.values()) {
//						System.out.print(arc.leftBpt.y() + ", " + arc.rightBpt.y() + "|");
//					}
//					System.out.println();
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
		//float[][] testSites = {{376.0f, 122.0f},{269.0f, 126.0f},{151.0f, 162.0f},{98.0f, 212.0f}};
//		float[][] testSites = {{376.0f, 122.0f},{269.0f, 122.0f},{151.0f, 122.0f},{98.0f, 122.0f}}; 
//		for (float[] coord : testSites) {
//			sitesBelow.add(new Site(coord[0], coord[1], this));
//		}
	}
	
	public void drawBeachLine() {
		for (Parabola arc : arcs.values()) {
			arc.draw();
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
		for (BreakPoint bpt : breakPoints) {
			bpt.draw();
		}
		drawBeachLine();
		
//		if (!circles.isEmpty()) {
//			for (Circle circle : circles) {
//				circle.draw();
//			}
//		}

		
	}
	public static void main(String args[]) {
		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	}
}
