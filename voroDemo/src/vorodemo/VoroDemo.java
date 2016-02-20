package vorodemo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

import processing.core.PApplet;


public class VoroDemo extends PApplet {
	PApplet canvas;
	Voronoi voronoi = new Voronoi(10, this);
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
		if (key == 'v') {
			voronoi.toggleCell();
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

	}	
	public void initSites() {
		
		// known good (random)
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
//		 special case (co-y)
//		voronoi.addSite(new Site(287, 161, this));
//		voronoi.addSite(new Site(225, 191, this));
//		voronoi.addSite(new Site(331, 191, this));

		// special case (co circular)		
//		voronoi.addSite(new Site(400,300, this));		
//		voronoi.addSite(new Site(300,400, this));		
//		voronoi.addSite(new Site(200,300, this));		
//		voronoi.addSite(new Site(300,200, this));
		// special case (grid)		
//		voronoi.addSite(new Site(400,400, this));		
//		voronoi.addSite(new Site(300,400, this));		
//		voronoi.addSite(new Site(200,400, this));	
//		voronoi.addSite(new Site(100,400, this));		
//		voronoi.addSite(new Site(400,300, this));		
//		voronoi.addSite(new Site(300,300, this));		
//		voronoi.addSite(new Site(200,300, this));		
//		voronoi.addSite(new Site(100,300, this));
		// known bad
//		voronoi.addSite(new Site(121, 95, this));
//		voronoi.addSite(new Site(194, 189, this));
//		voronoi.addSite(new Site(349, 212, this));
//		
		voronoi.addSite(new Site(265, 215, this)); 
		voronoi.addSite(new Site(437, 151, this)); 
		voronoi.addSite(new Site(367, 326, this)); 


		
	}
	public void draw() {
		clear();
		voronoi.draw();		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
