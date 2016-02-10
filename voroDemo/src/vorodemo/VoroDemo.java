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
			System.out.println("voronoi.addSite(new Site(" + mouseX + ", " + mouseY + ", this));");			
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
		else {
			System.out.println("(" + mouseX + ", " + mouseY + ")");
		}
	}
	public void keyPressed() {
		if (key == 'r') {
			voronoi.reset();
		}
		if (key == 's') {
			voronoi.reset();
			initSites();
		}
		if (key == 'c') {
			voronoi.toggleCircle();
			
		}
		if (key == 'n') {
			if (!voronoi.events.isEmpty()) {
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				voronoi.update();
			}			
		}
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
		initSites();
		// known good sites
//		voronoi.addSite(new Site(270, 118, this));
//		voronoi.addSite(new Site(252, 126, this));
//		voronoi.addSite(new Site(287, 136, this));
		// known good
//		voronoi.addSite(new Site(320, 124, this));
//		voronoi.addSite(new Site(269, 130, this));		
//		voronoi.addSite(new Site(343, 157, this));
//		voronoi.addSite(new Site(352, 217, this));
//		voronoi.addSite(new Site(472, 585, this));
//		voronoi.addSite(new Site(123, 460, this));
//		voronoi.addSite(new Site(413, 346, this));
//		voronoi.addSite(new Site(180, 285, this));	

		// known good
//		voronoi.addSite(new Site(220, 169, this));
//		voronoi.addSite(new Site(274, 139, this));
//		voronoi.addSite(new Site(330, 156, this));
//		voronoi.addSite(new Site(232, 220, this));
		
		// known good
//		voronoi.addSite(new Site(235, 151, this));
//		voronoi.addSite(new Site(283, 131, this));
//		voronoi.addSite(new Site(306, 142, this));
//		voronoi.addSite(new Site(247, 193, this));
		
		// known bad
//		voronoi.addSite(new Site(270, 140, this));
//		voronoi.addSite(new Site(206, 182, this));
//		voronoi.addSite(new Site(316, 182, this));
		
		// known bad
//		voronoi.addSite(new Site(419, 187, this));
//		voronoi.addSite(new Site(363, 269, this));
//		voronoi.addSite(new Site(278, 336, this));
		
		// known bad
//		voronoi.addSite(new Site(235, 151, this));
//		voronoi.addSite(new Site(283, 131, this));
//		voronoi.addSite(new Site(306, 142, this));
//		voronoi.addSite(new Site(247, 193, this));
		// known bad
//		voronoi.addSite(new Site(152, 306, this));
//		voronoi.addSite(new Site(301, 354, this));
//		voronoi.addSite(new Site(253, 276, this));
//		voronoi.addSite(new Site(175, 377, this));
//		voronoi.addSite(new Site(377, 291, this));
//		voronoi.addSite(new Site(264, 442, this));
//		voronoi.addSite(new Site(376, 374, this));
//		voronoi.addSite(new Site(292, 402, this));
//		voronoi.addSite(new Site(348, 410, this));
//		voronoi.addSite(new Site(263, 518, this));
//		voronoi.addSite(new Site(95, 489, this));
//		voronoi.addSite(new Site(128, 443, this));
//		voronoi.addSite(new Site(409, 490, this));
//		voronoi.addSite(new Site(479, 337, this));
//		voronoi.addSite(new Site(452, 411, this));
//		voronoi.addSite(new Site(523, 222, this));
//		voronoi.addSite(new Site(440, 208, this));
//		voronoi.addSite(new Site(516, 163, this));
//		voronoi.addSite(new Site(397, 180, this));
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
	public void initSites() {
		
//		voronoi.addSite(new Site(391, 156, this));
//		voronoi.addSite(new Site(367, 183, this));
//		voronoi.addSite(new Site(337, 201, this));
		
		voronoi.addSite(new Site(152, 306, this));
		voronoi.addSite(new Site(301, 354, this));
		voronoi.addSite(new Site(253, 276, this));
		voronoi.addSite(new Site(175, 377, this));
		voronoi.addSite(new Site(377, 291, this));
		voronoi.addSite(new Site(264, 442, this));
		voronoi.addSite(new Site(376, 374, this));
		voronoi.addSite(new Site(292, 402, this));
		voronoi.addSite(new Site(348, 410, this));
		voronoi.addSite(new Site(263, 518, this));
		voronoi.addSite(new Site(95, 489, this));
		voronoi.addSite(new Site(128, 443, this));
		voronoi.addSite(new Site(409, 490, this));
		voronoi.addSite(new Site(479, 337, this));
		voronoi.addSite(new Site(452, 411, this));
		voronoi.addSite(new Site(523, 222, this));
		voronoi.addSite(new Site(440, 208, this));
		voronoi.addSite(new Site(516, 163, this));
		voronoi.addSite(new Site(397, 180, this));
	}
	public void draw() {
		clear();
		voronoi.draw();		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
