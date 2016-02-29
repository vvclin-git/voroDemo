package vorodemo;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import processing.core.PApplet;

public class VoroDemo extends PApplet {
	PApplet canvas;
	Voronoi voronoi = new Voronoi(10, this);
	Integer step = 1; // for drawing
	int eventInd = 0;
	int targetEvent = 19;
	Path fileReadPath = FileSystems.getDefault().getPath("./bin/testdata/test.txt");
	Path fileTmpWritePath = FileSystems.getDefault().getPath("./bin/testdata/tmp.txt");
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
		// o : output sites
		if (key == 'r') {
			voronoi.reset();
			eventInd = 0;
		}
		if (key == 's') {
			voronoi.reset();
			eventInd = 0;
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
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType());
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				//voronoi.update();
				voronoi.printEvents();
				voronoi.printEventsY();
				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
			}
			
		}
		if (key == 'm') {
			voronoi.reset();
			initSites();
			targetEvent = eventInd - 1;
			eventInd = 0;			
			while (eventInd < targetEvent) {
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();				
				//voronoi.update();
				voronoi.printEvents();
				voronoi.printEventsY();
				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
			}			
			voronoi.printEvents();			
		}
		if (key == 'f') {			
			while (!voronoi.events.isEmpty()) {
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();				
				//voronoi.update();
				voronoi.printEvents();
				voronoi.printEventsY();
				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
			}
			voronoi.update();
		}
		if (key == 't') {
			while (eventInd < targetEvent) {
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				voronoi.beachLine.printBptNode();
				voronoi.beachLine.printBptNodeX();
				voronoi.printEvents();
				voronoi.printEventsY();
				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
				//voronoi.update();
			}			
		}
		if (key == 'x') {
			voronoi.update();
		}
		if (key == 'o') {
			writeSites(fileTmpWritePath);
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
		readSites(fileReadPath);
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

//		voronoi.addSite(new Site(479, 337, this));
//		voronoi.addSite(new Site(301, 354, this));
//		voronoi.addSite(new Site(376, 374, this));
//		voronoi.addSite(new Site(175, 377, this));
//		voronoi.addSite(new Site(292, 402, this));
//		voronoi.addSite(new Site(348, 410, this));
//		voronoi.addSite(new Site(452, 411, this));
//		voronoi.addSite(new Site(456, 428, this));
//		voronoi.addSite(new Site(264, 442, this));
//		voronoi.addSite(new Site(128, 443, this));
//		voronoi.addSite(new Site(95, 489, this));
//		voronoi.addSite(new Site(409, 490, this));
//		voronoi.addSite(new Site(263, 518, this));
		
//		voronoi.addSite(new Site(452,411, this));		
//		voronoi.addSite(new Site(456,428, this));		
//		voronoi.addSite(new Site(409,490, this));

		
	}
	public void draw() {
		clear();
		voronoi.draw();		
	}
	public void readSites(Path filePath) {
		try (Stream<String> stream = Files.lines(filePath)) {
			stream.forEach(line -> voronoi.addSite(new Site(Float.valueOf(line.split(",")[0]),Float.valueOf(line.split(",")[1]), this)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeSites(Path filePath) {		
		ArrayList<String> lines = new ArrayList<String>();
		voronoi.sites.sort(null);
		for (Site site : voronoi.sites) {
			lines.add(site.toOutString());
		}
		try {
			Files.write(filePath, lines);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public static void main(String args[]) {		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
