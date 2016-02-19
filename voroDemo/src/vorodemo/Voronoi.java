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
//	TreeMap<BptNode, BptNode> beachLine = new TreeMap<BptNode, BptNode>();
//	TreeMap<BptNode, Parabola> arcs = new TreeMap<BptNode, Parabola>();
	TreeMap<BptNode, Parabola> beachLineTree = new TreeMap<BptNode, Parabola>();
	BeachLine beachLine;
	Directrix dictx;
	PApplet p;
	boolean drawCircle = true;
	public Voronoi(float dictY, PApplet p) {		
		this.p = p;
		dictx = new Directrix(dictY, p);
		beachLine = new BeachLine(this, p);
	}
	public void addSite(Site site) {
		sites.add(site);
		//add site event
		voroCells.add(site.voroCell);
		events.add(new SiteEvent(this, site));
		printEventsX();
	}
	public void draw() {
		dictx.draw();
		for (Site site : sites) {
			site.draw();
		}
		if (drawCircle) {
			for (Circle circle : circles) {
				circle.draw();
			}
		}
		for (Edge edge : edges) {
			edge.draw();
		}
		for (BptNode vertex : vertices) {						
			p.ellipse(vertex.x, vertex.y, 2, 2);			
		}
		beachLine.draw();
	}
	public void printEvents() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
		while (!eventsOut.isEmpty()) {
			Event event =  eventsOut.poll();
			System.out.print(event.type + ", ");			
		}
		System.out.println();
	}
	public void printEventsY() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
		while (!eventsOut.isEmpty()) {
			Event event =  eventsOut.poll();
			System.out.print(event.y + ", ");			
		}
		System.out.println();
	}
	public void printEventsX() {
		PriorityQueue<Event> eventsOut = new PriorityQueue<Event>(events);
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
//		for (Site site : sites) {			
//			System.out.print(site.processed + ", ");
//		}
//		System.out.println();
//		for (Site site : sites) {
//			System.out.print(site.x() + ", ");			
//		}
//		System.out.println();
		beachLine.printBptNodeX();
		//beachLine.printBptNode();
		//beachLine.printBptNodeY();
//		System.out.print(events.size() + "| ");
		
		printEvents();
		printEventsX();
//		System.out.println();
//		System.out.println(edges.size());
//		System.out.println();
//		System.out.print(dictx.y() + "| ");
//		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
//			System.out.print(bptNode.x() + ", ");
//		}
//		System.out.println();
//		System.out.print(dictx.y() + "| ");
//		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
//			System.out.print(bptNode.type + ", ");
//		}
//		System.out.println();
	}
	public void reset() {
		beachLineTree.clear();
		sites.clear();
		vertices.clear();
		events.clear();
		circles.clear();
		edges.clear();
		dictx.setY(10);
	}
	public void toggleCircle() {
		if (drawCircle) {
			drawCircle = false;
		}
		else {
			drawCircle = true;
		}
	}
	
}
