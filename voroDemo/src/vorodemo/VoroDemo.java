package vorodemo;
import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	//PApplet canvas;
	ArrayList<Parabola> parabolae = new ArrayList<Parabola>();
	ArrayList<Parabola> arcs = new ArrayList<Parabola>();
	ArrayList<Site> sitesAbove = new ArrayList<Site>();
	ArrayList<Site> sitesBelow = new ArrayList<Site>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	Directrix dictx = new Directrix(100, this);
	
	private void siteEvent(Site site) {
		BreakPoint leftBpt, rightBpt;
		Parabola arcTmpRef = null;
		if (!arcs.isEmpty()) {
			for (Parabola arc : arcs) { // Linear range search
				if (arc.inArc(site)) {
					arcTmpRef = arc;
					break;
				}
			}
			if (arcTmpRef != null) {
				Parabola newPara = new Parabola(dictx, site, this);
				leftBpt = arcTmpRef.leftInterPt(newPara);
				rightBpt = arcTmpRef.rightInterPt(newPara);
				// create new edge
				if (leftBpt.getType() != "leftBound" & rightBpt.getType() != "rightBound") {
					edges.add(new Edge(leftBpt, rightBpt, this));
				}
				
				// add left sub arcTmpRef (part of original arcTmpRef)
				arcs.add(new Arc (dictx, arcTmpRef.site, arcTmpRef.leftBpt, leftBpt, this));
//				System.out.print(arcTmpRef.leftBpt.x() + ", " + leftBpt.x() + " | ");
				// add new arcTmpRef
				arcs.add(new Arc (dictx, site, leftBpt, rightBpt, this));
//				System.out.print(leftBpt.x() + ", " + rightBpt.x() + " | ");
				// add right sub arcTmpRef (part of original arcTmpRef)
				arcs.add(new Arc (dictx, arcTmpRef.site, rightBpt, arcTmpRef.rightBpt, this));
//				System.out.print(rightBpt.x() + ", " + arcTmpRef.rightBpt.x() + " | ");
//				System.out.println();
				//remove original arcTmpRef
				arcs.remove(arcTmpRef);
			}
		}
		else {
			Parabola newPara = new Parabola(dictx, site, this);			
//			arcs.add(new Arc (dictx, site, new BreakPoint(0, newPara.y(0), "leftBound", null, newPara, this), new BreakPoint(width, newPara.y(width), "rightBound", newPara, null, this), this));
			arcs.add(newPara);
		}
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
					for (Parabola arc : arcs) {
						arc.update();//
						System.out.print(arc.leftBpt.x() + "," + arc.rightBpt.x() + " : ");
						System.out.print(arc.leftX + "," + arc.rightX + " | ");
					}
					System.out.println();
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
		if (!arcs.isEmpty()) {
//			arcs.get(0).draw();
			for (Parabola arc : arcs) {
				if (!arc.hidden()) {
					arc.draw();
				}
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
