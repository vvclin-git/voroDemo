package vorodemo;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JFileChooser;

import processing.core.PApplet;

public class VoroDemo extends PApplet {
	PApplet canvas;
	Voronoi voronoi = new Voronoi(1, this);
	Integer step = 1; // for drawing
	int eventInd = 0;
	int targetEvent = 29;
	Path fileReadPath;
//	Path fileReadPath = FileSystems.getDefault().getPath("./res/test.txt");
//	Path fileReadPath = FileSystems.getDefault().getPath("./res/collection/croc skin.txt");
	Path fileTmpWritePath = FileSystems.getDefault().getPath("./bin/tmp.txt");
	VoroCell lastSelectedVCell;
	boolean update = false;
	int loadFile = 0;
	static int printOut = 0;
	public void mouseClicked() {
		//Event[] eventsOut;		
		Point mousePos = new Point(mouseX, mouseY, this);
//		if (mouseY > voronoi.dictx.y()) {
		if (loadFile != 1 & mouseY > voronoi.dictx.y()) {
				System.out.println("voronoi.addSite(new Site(" + mouseX + ", " + mouseY + ", this));");			
				voronoi.addSite(new Site(mouseX, mouseY, this));
		}
		else {
			if (!voronoi.events.isEmpty()) {				
				System.out.print("mouse pos: " + mousePos);				
				System.out.println("the nearest site is: " + voronoi.getNearestSite(mousePos));
			}
			else {
				if (lastSelectedVCell != null) {
					lastSelectedVCell.setNotSelected();
				}
				VoroCell voroCell = voronoi.getNearestSite(mousePos).getVoroCell();
				if ( voroCell != null) {
					voroCell.setSelected();
					lastSelectedVCell = voroCell;
				}
			}
		}
	}
	public void mouseMoved() {
//		Point mousePos = new Point(mouseX, mouseY, this);
//		for (VoroCell voroCell : voronoi.voroCells) {
//			if (voroCell.isEnclosed()) {
//				if (voroCell.inCell(mousePos)) {
//					voroCell.setSelected();
//				}
//				else {
//					voroCell.setNotSelected();
//				}
//			}			
//		}
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
		// l : load temporary output file
		// e : toggle edge drawing on / off
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
		if (key == 'e') {
			voronoi.toggleEdge();
		}
		if (key == 'n') {			
			if (!voronoi.events.isEmpty()) {
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType());
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
//				if (update) {
					voronoi.update();
//				}				
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
//				System.out.print("-====Event #: " + eventInd + "=====");
//				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();				
				if (update) {
					voronoi.update();
				}
//				voronoi.printEvents();
//				voronoi.printEventsY();
//				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
			}			
			voronoi.printEvents();			
		}
		if (key == 'f') {
			final long startTime = System.currentTimeMillis();			
			while (!voronoi.events.isEmpty()) {
//				System.out.print("-====Event #: " + eventInd + "=====");
//				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());				
				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();				
				if (update) {
					voronoi.update();
				}
//				voronoi.printEvents();
//				voronoi.printEventsY();
//				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
			}			
			voronoi.update();
			final long endTime = System.currentTimeMillis();
			System.out.println(voronoi.sites.size() + " sites processed");
			System.out.println(voronoi.edges.size() * 0.5 + " edges created");
			System.out.println(voronoi.vertices.size() + " vertices created");
			System.out.println(voronoi.voroCells.size() + " voronoi cells created");
			System.out.println("Total execution time: " + (endTime - startTime) );
		}
		if (key == 't') {
			while (eventInd < targetEvent) {
				System.out.print("-====Event #: " + eventInd + "=====");
				System.out.println(" y: " + voronoi.events.peek().y() + " | " + voronoi.events.peek().getType() + " | " + voronoi.beachLine.beachLineTree.size());				voronoi.dictx.setY(voronoi.events.peek().y());
				voronoi.events.poll().eventHandler();
				voronoi.beachLine.printBptNode();
				voronoi.beachLine.printBptNodeX();
				if (update) {
					voronoi.update();
				}
				voronoi.printEvents();
				voronoi.printEventsY();
				System.out.println("=====Event #: " + eventInd + "=====");
				eventInd += 1;
				
			}			
		}
		if (key == 'x') {
			voronoi.update();
		}
		if (key == 'o') {
			writeSites(fileTmpWritePath);
		}
		if (key == 'l') {
			voronoi.reset();
			eventInd = 0;
			initSites();
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
		if (loadFile == 1) {
			JFileChooser fileChooser = new JFileChooser("./");
	        int returnValue = fileChooser.showOpenDialog(null);
	        if (returnValue == JFileChooser.APPROVE_OPTION) {
	          File selectedFile = fileChooser.getSelectedFile();
	          fileReadPath = selectedFile.toPath();
	          readSites(fileReadPath);	
	        }
					
		}
		else {
//			voronoi.addSite(new Site(355, 392, this));
//			voronoi.addSite(new Site(309, 296, this));
//			voronoi.addSite(new Site(413, 322, this));
//			voronoi.addSite(new Site(481, 95, this));
//			voronoi.addSite(new Site(542, 95, this));
//			voronoi.addSite(new Site(520, 142, this));
			voronoi.addSite(new Site(67.37361f, 47.325226f, this));
			voronoi.addSite(new Site(105.19646f, 42.45215f, this));
			voronoi.addSite(new Site(149.71062f, 44.202606f, this));
			voronoi.addSite(new Site(61.0719f, 64.09102f, this));
			voronoi.addSite(new Site(93.59216f, 63.638596f, this));
			voronoi.addSite(new Site(128.80014f, 60.448914f, this));
			voronoi.addSite(new Site(161.36874f, 62.790207f, this));
		}
//		readSites(fileReadPath);
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
//		if (printOut == 0) {
//			System.setOut(new PrintStream(new OutputStream() {
//			     @Override
//			     public void write(int arg0) throws IOException {
//			     }
//			  }));
//		}
		
	    PApplet.main(new String[] { vorodemo.VoroDemo.class.getName() });
	    
	}
}
