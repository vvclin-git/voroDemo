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
	Integer step = 1; // for drawing
	int eventInd = 0;
	int targetEvent = 69;
	public void mouseClicked() {
		//Event[] eventsOut;		
		if (mouseY > voronoi.dictx.y()) {			
				System.out.println("voronoi.addSite(new Site(" + mouseX + ", " + mouseY + ", this));");			
				voronoi.addSite(new Site(mouseX, mouseY, this));
		}
		else {
			if (!voronoi.events.isEmpty()) {
				System.out.println("(" + mouseX + ", " + mouseY + ")");
			}
			else {
				for (VoroCell voroCell : voronoi.voroCells) {
					if (voroCell.isSelected()) {
						System.out.println(voroCell);
					}					
				}				
			}
		}
	}
	public void mouseMoved() {
		Point mousePos = new Point(mouseX, mouseY, this);
		for (VoroCell voroCell : voronoi.voroCells) {
			if (voroCell.isEnclosed()) {
				if (voroCell.inCell(mousePos)) {
					voroCell.setSelected();
				}
				else {
					voroCell.setNotSelected();
				}
			}			
		}
	}
	public void keyPressed() {
		// r : clear canvas
		// s : clear canvas and load preset sites
		// c : toggle circle drawing on / off
		// v : toggle cell drawing on / off
		// b : toggle site drawing on / off
		// n : to next event
		// m : to previous event
		// f : process all sites at once
		// t : process until target event
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
		if (key == 'b') {
			voronoi.toggleSite();
		}
		if (key == 'n') {			
			if (!voronoi.events.isEmpty()) {
				System.out.println("=====Event #: " + eventInd + "=====");
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				voronoi.update();
				eventInd += 1;
			}
			System.out.println("=====Event #: " + eventInd + "=====");
		}
		if (key == 'm') {
			voronoi.reset();
			initSites();
			eventInd = 0;
			targetEvent -= 1;
			while (eventInd < targetEvent) {
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				eventInd += 1;
				voronoi.update();
			}
			System.out.println("=====Event #: " + eventInd + "=====");
			voronoi.printEvents();			
		}
		if (key == 'f') {
			while (!voronoi.events.isEmpty()) {
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();				
				voronoi.update();
			}
			voronoi.printEvents();
		}
		if (key == 't') {
			while (eventInd < targetEvent) {
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				eventInd += 1;
				voronoi.update();
			}
			System.out.println("=====Event #: " + eventInd + "=====");
			voronoi.printEvents();
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
//		voronoi.addSite(new Site(143, 435, this));
//		voronoi.addSite(new Site(158, 240, this));
//		voronoi.addSite(new Site(383, 390, this));
//		voronoi.addSite(new Site(357, 214, this));
//		voronoi.addSite(new Site(115, 97, this));
//		voronoi.addSite(new Site(78, 320, this));
//		voronoi.addSite(new Site(207, 137, this));
//		voronoi.addSite(new Site(372, 84, this));
//		voronoi.addSite(new Site(529, 185, this));
//		voronoi.addSite(new Site(274, 249, this));
//		voronoi.addSite(new Site(273, 344, this));
//		voronoi.addSite(new Site(482, 270, this));
//		voronoi.addSite(new Site(541, 437, this));
//		voronoi.addSite(new Site(272, 433, this));
//		voronoi.addSite(new Site(213, 542, this));
//		voronoi.addSite(new Site(433, 505, this));
//		voronoi.addSite(new Site(72, 518, this));
//		voronoi.addSite(new Site(115, 196, this));
//		voronoi.addSite(new Site(39, 112, this));
//		voronoi.addSite(new Site(245, 52, this));
//		voronoi.addSite(new Site(568, 62, this));
//		voronoi.addSite(new Site(455, 188, this));
//		voronoi.addSite(new Site(451, 112, this));
//		voronoi.addSite(new Site(302, 169, this));
//		 special case (co-y)
//		voronoi.addSite(new Site(287, 161, this));
//		voronoi.addSite(new Site(225, 191, this));
//		voronoi.addSite(new Site(331, 191, this));
		// special case (site & circle at the same time)
		
		// special case (co circular)		
//		voronoi.addSite(new Site(400,300, this));		
//		voronoi.addSite(new Site(300,400, this));		
//		voronoi.addSite(new Site(200,300, this));		
//		voronoi.addSite(new Site(300,200, this));
		
//		voronoi.addSite(new Site(40,130,this));
//		voronoi.addSite(new Site(40,70,this));
//		voronoi.addSite(new Site(160,130,this));
//		voronoi.addSite(new Site(160,70,this));
//		voronoi.addSite(new Site(130,160,this));
//		voronoi.addSite(new Site(130,40,this));
//		voronoi.addSite(new Site(70,160,this));
//		voronoi.addSite(new Site(70,40,this));

//		voronoi.addSite(new Site(160, 315, this));
//		voronoi.addSite(new Site(242, 171, this));
//		voronoi.addSite(new Site(375, 191, this));
//		voronoi.addSite(new Site(368, 285, this));
//		voronoi.addSite(new Site(343, 422, this));
//		voronoi.addSite(new Site(267, 281, this));

		
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
		
		voronoi.addSite(new Site(254, 175, this));
		
		voronoi.addSite(new Site(321, 252, this));
		voronoi.addSite(new Site(331, 118, this));
		voronoi.addSite(new Site(203, 250, this));
		voronoi.addSite(new Site(109, 152, this));
		voronoi.addSite(new Site(203, 79, this));
		voronoi.addSite(new Site(153, 220, this));
		voronoi.addSite(new Site(40, 73, this));
		voronoi.addSite(new Site(62, 243, this));
		voronoi.addSite(new Site(117, 47, this));
		voronoi.addSite(new Site(119, 232, this));
		voronoi.addSite(new Site(166, 145, this));
		voronoi.addSite(new Site(283, 76, this));
		voronoi.addSite(new Site(294, 204, this));
		voronoi.addSite(new Site(386, 72, this));
		voronoi.addSite(new Site(398, 132, this));
		voronoi.addSite(new Site(428, 71, this));
		voronoi.addSite(new Site(477, 156, this));
		voronoi.addSite(new Site(549, 48, this));
		voronoi.addSite(new Site(512, 98, this));
		voronoi.addSite(new Site(571, 201, this));	 

		
	}
	public void draw() {
		clear();
		voronoi.draw();		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
