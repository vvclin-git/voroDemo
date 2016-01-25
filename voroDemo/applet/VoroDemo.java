package vorodemo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	PApplet canvas;
	Voronoi voronoi = new Voronoi(100, this);
	Integer step = 1;
	
	public void mouseClicked() {
		//Event[] eventsOut;
		if (mouseY > voronoi.dictx.y()) {
			System.out.print("(" + mouseX + ", " + mouseY + "), ");			
			voronoi.addSite(new Site(mouseX, mouseY, this));
			//for debugging
//			Event[] eventsOut = new Event[voronoi.events.size()];
//			eventsOut = voronoi.events.toArray(new Event[voronoi.events.size()]);
//			System.out.println();
//			Arrays.sort(eventsOut);
//			for (Event event : eventsOut) {
//				System.out.print(event.toString() + ",");
//			}
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
			if (keyCode == UP) { // not necessary
			} 
			else if (keyCode == DOWN) {				
//				voronoi.dictx.move(1);
//				voronoi.update();
				//System.out.println(voronoi.circles.size());
				// event
				if (!voronoi.events.isEmpty()) {					
					if (voronoi.events.peek().y() - voronoi.dictx.y() <= step) {
						//voronoi.dictx.setY(Math.round((voronoi.events.peek().y() / step)) * step);
						voronoi.dictx.setY(voronoi.events.peek().y());						
						while (voronoi.events.peek().y() == voronoi.dictx.y()) { // "<=" is a must have in the future						
							voronoi.events.poll().eventHandler();
							// for debugging
							//System.out.println(voronoi.arcs.size());
							if (voronoi.events.isEmpty()) break;
							voronoi.update();
						}
//						voronoi.dictx.setY(voronoi.events.peek().y());
						voronoi.update();
					}
					else {
						voronoi.dictx.move(step);
						voronoi.update();
					}
				}
				else {
					voronoi.dictx.move(step);
					voronoi.update();
				}
			} 
		} 
	}

	public void setup() {
		background(0);				
	}
	public void settings() {		
		size(600, 600);
		// known good sites
//		voronoi.addSite(new Site(270, 118, this));
//		voronoi.addSite(new Site(252, 126, this));
//		voronoi.addSite(new Site(287, 136, this));
		// known good
		voronoi.addSite(new Site(320, 124, this));
		voronoi.addSite(new Site(269, 130, this));		
		voronoi.addSite(new Site(343, 157, this));
		voronoi.addSite(new Site(352, 217, this));
		voronoi.addSite(new Site(472, 585, this));
		voronoi.addSite(new Site(123, 460, this));
		voronoi.addSite(new Site(413, 346, this));
		voronoi.addSite(new Site(180, 285, this));
		
		// known good
//		voronoi.addSite(new Site(320, 124, this));
//		voronoi.addSite(new Site(269, 130, this));		
//		voronoi.addSite(new Site(343, 157, this));
//		voronoi.addSite(new Site(200, 217, this));
		
//		// known bad sites (work sometimes?)
//		voronoi.addSite(new Site(320, 124, this));
//		voronoi.addSite(new Site(269, 130, this));		
//		voronoi.addSite(new Site(343, 157, this));
		
		//
//		voronoi.addSite(new Site(320, 124, this));
//		voronoi.addSite(new Site(269, 130, this));		
//		voronoi.addSite(new Site(343, 130, this));
		//voronoi.addSite(new Site(200, 217, this));
		
		

	}	

	public void draw() {
		clear();
		voronoi.draw();		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
