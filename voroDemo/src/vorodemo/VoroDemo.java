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
	public void mouseClicked() {
		//Event[] eventsOut;
		if (mouseY > voronoi.dictx.y()) {
			//System.out.print("(" + mouseX + ", " + mouseY + "), ");			
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
				voronoi.dictx.move(1);
				voronoi.update();
				//System.out.println(voronoi.circles.size());
				// event
				if (!voronoi.events.isEmpty()) {
					while (voronoi.events.peek().y() <= voronoi.dictx.y()) { // "<=" is a must have in the future
						voronoi.events.poll().eventHandler();
						// for debugging
						//System.out.println(voronoi.arcs.size());
						if (voronoi.events.isEmpty()) break;
					}
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

	public void draw() {
		clear();
		voronoi.draw();		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	}
}
