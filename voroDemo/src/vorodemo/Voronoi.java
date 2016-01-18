package vorodemo;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.PriorityQueue;

import processing.core.PApplet;

public class Voronoi extends PApplet{
	PriorityQueue<Event> events = new PriorityQueue<Event>();
	ArrayList<BreakPoint> breakPoints = new ArrayList<BreakPoint>();
	ArrayList<Edge> edges = new ArrayList<Edge>();
	ArrayList<Circle> circles = new ArrayList<Circle>();
	ArrayList<Site> sites = new ArrayList<Site>();
//	TreeMap<BptNode, BptNode> beachLine = new TreeMap<BptNode, BptNode>();
//	TreeMap<BptNode, Parabola> arcs = new TreeMap<BptNode, Parabola>();
	TreeMap<BptNode, Parabola> beachLineTree = new TreeMap<BptNode, Parabola>();
	BeachLine beachLine;
	Directrix dictx;
	PApplet p;
	public Voronoi(float dictY, PApplet p) {		
		this.p = p;
		dictx = new Directrix(dictY, p);
		beachLine = new BeachLine(this, p);
	}
	public void addSite(Site site) {
		sites.add(site);
		//add site event
		events.add(new SiteEvent(this, site));
	}
	public void draw() {
		dictx.draw();
		for (Site site : sites) {
			site.draw();
		}
		for (Circle circle : circles) {
			circle.draw();
		}
		beachLine.draw();
	}
	public void update() {
		beachLine.update();
//		System.out.println();
//		System.out.print(dictx.y() + "| ");
//		for (BptNode bptNode : beachLineTree.navigableKeySet()) {
//			System.out.print(bptNode.x() + ", ");
//		}
	}
}
