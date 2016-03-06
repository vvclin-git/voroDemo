package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.PriorityQueue;

import processing.core.PApplet;

public class Voronoi extends PApplet{
	PriorityQueue<Event> events = new PriorityQueue<Event>();
	ArrayList<BreakPoint> breakPoints = new ArrayList<BreakPoint>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<BptNode> vertices = new ArrayList<BptNode>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Site> sites = new ArrayList<Site>();
	ArrayList<VoroCell> voroCells = new ArrayList<VoroCell>();
	TreeMap<BptNode, Parabola> beachLineTree = new TreeMap<BptNode, Parabola>();
	BeachLine beachLine;
	Directrix dictx;
	PApplet p;
	boolean drawCircle = true;
	boolean drawCell = true;
	boolean drawSite = true;
	boolean drawEdge = true;
	float initDictY;
	public Voronoi(float dictY, PApplet p) {		
		this.p = p;
		dictx = new Directrix(dictY, p);
		beachLine = new BeachLine(this, p);
		initDictY = dictY;
	}	
	public void addSite(Site site) {
		sites.add(site);
		//add site event
		voroCells.add(site.voroCell);
		events.add(new SiteEvent(this, site));
//		printEventsX();
	}	
	public void draw() {
		dictx.draw();
		if (drawSite) {
			for (Site site : sites) {
				site.draw();
			}
		}		
		if (drawCircle) {
			// draw the nearest circle
			circles.sort(null);
			if (!circles.isEmpty()) {
				circles.get(0).draw();
			}			
			// draw all circles
//			for (Circle circle : circles) {
//				circle.draw();
//			}
		}
		if (drawEdge) {
			for (Edge edge : edges) {
				edge.draw();
			}
		}		
//		for (BptNode vertex : vertices) {
//			p.fill(255, 0, 0);
//			p.stroke(255, 0, 0);
//			p.ellipse(vertex.x(), vertex.y(), 3, 3);			
//		}
		p.stroke(255);
		beachLine.draw();
		if (drawCell) {
			for (VoroCell voroCell : voroCells) {
				if (voroCell.isEnclosed()) {
					voroCell.draw("idle");
				}
			}
//			for (VoroCell voroCell : voroCells) {
//				if (voroCell.isEnclosed() & voroCell.isSelected()) {
//					voroCell.draw("selected");
//				}
//			}
		}
		
	}
	public Site getNearestSite(Point point) {
		sites.sort(point.distSqrToOrder());
		return sites.get(0);
	}
	public void printEvents() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
		System.out.print("the following events are: ");
		while (!eventsOut.isEmpty()) {
			Event event =  eventsOut.poll();
			System.out.print(event.type + ", ");			
		}
		System.out.println();
	}
	public void printEventsY() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
		System.out.print("the y-pos of following events are: ");
		while (!eventsOut.isEmpty()) {
			Event event =  eventsOut.poll();
			System.out.print(event.y + ", ");			
		}
		System.out.println();
	}
	public void printEventsX() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
		System.out.print("the x-pos of following events are: ");
		while (!eventsOut.isEmpty()) {
			Event event =  eventsOut.poll();
			System.out.print(event.x + ", ");			
		}
		System.out.println();
	}
	public void update() {
		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
			bptNode.update();
		}
		beachLine.printBptNodeType();
		beachLine.printBptNodeX();
		beachLine.printBptNode();
		//beachLine.printBptNodeY();
//		System.out.print(events.size() + "| ");		
		printEvents();
		printEventsY();
	}
	public void reset() {
		beachLineTree.clear();
		sites.clear();
		vertices.clear();
		events.clear();
		circles.clear();
		edges.clear();
		dictx.setY(initDictY);
		voroCells.clear();
	}
	public void toggleCircle() {
		if (drawCircle) {
			drawCircle = false;
		}
		else {
			drawCircle = true;
		}
	}
	public void toggleCell() {
		if (drawCell) {
			drawCell = false;
		}
		else {
			drawCell = true;
		}
	}
	public void toggleEdge() {
		if (drawEdge) {
			drawEdge = false;
		}
		else drawEdge = true;
	}
	public void toggleSite() {
		if (drawSite) {
			drawSite = false;
		}
		else {
			drawSite = true;
		}
	}	
}
